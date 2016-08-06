package me.gking2224.model.execution.groovy;

import groovy.lang.GroovyClassLoader;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;

import me.gking2224.model.execution.ModelExecutionException;
import me.gking2224.model.jpa.Model;
import me.gking2224.model.service.ModelExecutionRequest;
import me.gking2224.model.service.ModelExecutionResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.jmx.export.annotation.ManagedAttribute;
import org.springframework.jmx.export.annotation.ManagedResource;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;

@Component
@ManagedResource(
//        objectName="ModelServerx:name=groovyCLModelExecutorV2",
        description="Groovy Classloader Model Executor v2",
        log=true,
        logFile="jmx.log",
        currencyTimeLimit=15,
        persistPolicy="OnUpdate",
        persistPeriod=200,
        persistLocation="foo",
        persistName="bar")
public class GroovyClassLoaderModelExecutorV2 extends AbstractGroovyScriptModelExecutor
implements ApplicationContextAware {

    private static final String DEFAULT_NATURE = "groovy.class.loader.v2";
    
    @SuppressWarnings("unused")
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private ApplicationContext applicationContext;

    private String path = "/WEB-INF/classes";

    private int numberOfModelExecutions = 0;
    private int successes = 0;
    private int failures = 0;

    public GroovyClassLoaderModelExecutorV2() {
        addNature(DEFAULT_NATURE);
    }

    @Override
    protected void doExecute(Model model, ModelExecutionRequest request, ModelExecutionResponse response,
            Logger modelLogger) {

        String scriptString = getScriptString(model);
        
        modelLogger.debug("Evaluating script:\n"+model.getScript());
        try (
                GroovyClassLoader gcl = new GroovyClassLoader(this.getClass().getClassLoader());
                ) {
            configureClassLoader(gcl);
            @SuppressWarnings("unchecked")
            Class<GroovyClassLoaderScriptV1> clazz =
                    gcl.parseClass(scriptString);
            GroovyClassLoaderScriptV1 s = clazz.getConstructor(new Class[] {
                    Object.class,
                    Object.class,
                    Object.class,
                    Object.class}).newInstance(
                            request.getInputParams(), response.getWarnings(),
                            response.getOutputs(), modelLogger);
            s.execute();
            if (response.isSuccess()) { successes++; }
            else { failures++; }
        } catch (Throwable t) {
            failures++;
            throw new ModelExecutionException(301, t);
        }
    }

    private void configureClassLoader(GroovyClassLoader gcl) throws MalformedURLException {
        
        URL u = ((WebApplicationContext)this.applicationContext).getServletContext().getResource(path);
        gcl.addURL(u);
    }

    private String getScriptString(Model model) {
        try (
                InputStream is = this.getClass().getResourceAsStream("AbstractGroovyClassLoaderScriptV1.txt");
        ) {
            StringBuilder sb = new StringBuilder();
            BufferedReader br = new BufferedReader(new InputStreamReader(is, Charset.defaultCharset()));
            String line = null;
            while ((line = br.readLine()) != null) {
                if (line.indexOf("INJECTED_SCRIPT") != -1) {
                    line = line.replace("INJECTED_SCRIPT", model.getScript());
                }
                sb.append(line);
                sb.append("\n");
            }
            return sb.toString();
        } catch (Throwable t) {
            throw new ModelExecutionException(301, t);
        }
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext)
            throws BeansException {
        this.applicationContext = applicationContext;
    }

    @ManagedAttribute(description="Num executions", currencyTimeLimit=15)
    public int getNumberOfModelExecutions() {
        return numberOfModelExecutions;
    }

    @ManagedAttribute(description="Num successes", currencyTimeLimit=15)
    public int getSuccesses() {
        return successes;
    }

    @ManagedAttribute(description="Num failures", currencyTimeLimit=15)
    public int getFailures() {
        return failures;
    }

}
