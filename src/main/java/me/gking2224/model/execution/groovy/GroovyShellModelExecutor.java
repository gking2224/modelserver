package me.gking2224.model.execution.groovy;

import java.io.BufferedWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.codehaus.groovy.control.CompilerConfiguration;
import org.codehaus.groovy.runtime.StringBufferWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import groovy.lang.Binding;
import groovy.lang.GroovyShell;
import groovy.util.ObjectGraphBuilder;
import me.gking2224.model.execution.ModelExecutionException;
import me.gking2224.model.execution.ModelExecutor;
import me.gking2224.model.execution.StaticDataProviderFactory;
import me.gking2224.model.jpa.Model;
import me.gking2224.model.service.ModelExecutionRequest;
import me.gking2224.model.service.ModelExecutionResponse;

@Component
public class GroovyShellModelExecutor implements ModelExecutor {

    private static final String EXECUTE_METHOD = "executeModel";

    @SuppressWarnings("unused")
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private StaticDataProviderFactory staticDataProviderFactory;

    private static Set<String> NATURES = new HashSet<String>();

    static {
        NATURES.add("me.gking2224.model.execution.groovy.shell");
    }

    @Override
    public boolean canExecuteNature(String nature) {
        return NATURES.contains(nature);
    }

    @Override
    public void executeModel(Model model, ModelExecutionRequest request, ModelExecutionResponse response) {
        String script = model.getScript();

        Map<String, Object> inputParams = request.getInputParams();
        StringBuffer stderr = new StringBuffer();
        StringBuffer stdout = new StringBuffer();
        try (
                Writer out = new BufferedWriter(new StringBufferWriter(stdout));
                Writer err = new BufferedWriter(new StringBufferWriter(stderr))) {
            Map<String, Object> output = new HashMap<>();
            response.setOutputs(output);
            
            Binding binding = new ObjectGraphBuilder();
            binding.setVariable("input", inputParams);
            binding.setVariable("output", output);
            binding.setVariable("out", out);
            binding.setVariable("err", err);
            addStaticDataProviders(model, binding);
            
            new GroovyShell(binding).parse(script).invokeMethod(EXECUTE_METHOD, null);
            out.flush();
            err.flush();
            response.setStderr(stderr.toString());
            response.setStdout(stdout.toString());
        } catch (Exception e) {
            throw new ModelExecutionException(e);
        }
    }

    protected void addStaticDataProviders(Model model, Binding binding) {
        staticDataProviderFactory.getStaticDataProviders(model.getNatures()).forEach((s)-> {
            binding.setVariable(s.getShortIdentifier(), s);
        });
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
}
