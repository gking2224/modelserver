package me.gking2224.model.execution.groovy;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import groovy.lang.Binding;
import groovy.lang.GroovyClassLoader;
import groovy.lang.GroovyShell;
import groovy.util.ObjectGraphBuilder;
import me.gking2224.model.execution.ModelExecutionException;
import me.gking2224.model.execution.ModelExecutor;
import me.gking2224.model.jpa.Model;
import me.gking2224.model.service.ModelExecutionRequest;
import me.gking2224.model.service.ModelExecutionResponse;

@Component
public class GroovyClassLoaderModelExecutor implements ModelExecutor {

    private static final String EXECUTE_METHOD = "executeModel";

    @SuppressWarnings("unused")
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private static Set<String> NATURES = new HashSet<String>();

    static {
        NATURES.add("me.gking2224.model.execution.groovy.classloader");
    }

    @Override
    public boolean canExecuteNature(String nature) {
        return NATURES.contains(nature);
    }

    @Override
    public void executeModel(Model model, ModelExecutionRequest request, ModelExecutionResponse response) {
        String script = model.getScript();


         runWithGroovyClassLoader(script, request.getInputParams(), response);
    }

    // private static class CustomClassLoader extends ClassLoader {
    //
    // private Logger logger = LoggerFactory.getLogger(this.getClass());
    //
    // public CustomClassLoader(ClassLoader parent) {
    // super(parent);
    // }
    //
    // protected Class<?> loadClass(String name, boolean resolve)
    // throws ClassNotFoundException {
    // logger.debug("loadClass("+name);
    // return super.loadClass(name, resolve);
    // }
    // }


    void runWithGroovyClassLoader(String script, Map<String, Object> inputParams, ModelExecutionResponse response) {
        try (GroovyClassLoader gcl = new GroovyClassLoader()) {
            @SuppressWarnings("unchecked")
            Class<GroovyModelIntf> scriptClass = gcl.parseClass(script);
            GroovyModelIntf scriptInstance;
            scriptInstance = scriptClass.newInstance();
            Map<String, Object> output = new HashMap<>();
            scriptInstance.executeModel(inputParams, output);
            response.setOutputs(output);

        } catch (Exception ioe) {
            throw new ModelExecutionException(ioe);
        }
    }
}
