package me.gking2224.utils;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.codehaus.groovy.control.CompilerConfiguration;
import org.junit.Test;

import groovy.lang.Binding;
import groovy.lang.GroovyClassLoader;
import groovy.lang.GroovyShell;
import groovy.util.ObjectGraphBuilder;
import me.gking2224.model.execution.ModelExecutionException;
import me.gking2224.model.execution.groovy.GroovyModelIntf;

public class GroovyScriptTest {

    @Test
    public void test() {
        
        String script = "import java.util.Map; class x implements me.gking2224.model.execution.groovy.GroovyModelIntf {   def void executeModel(Map input, Map output) {     output.givenValue=input.a   } }";
        Map<String,Object> inputParams = new HashMap<>();
        inputParams.put("a", "givenValue");
        try (GroovyClassLoader gcl = new GroovyClassLoader()) {
            @SuppressWarnings("unchecked")
            Class<GroovyModelIntf> scriptClass = gcl.parseClass(script);
            GroovyModelIntf scriptInstance;
            try {
                scriptInstance = scriptClass.newInstance();
                Map<String,Object> output = new HashMap<>();
                scriptInstance.executeModel(inputParams, output);
                assertEquals("givenValue", output.get("givenValue"));

            } catch (Exception e) {
                throw new ModelExecutionException(e);
            }
        } catch (IOException ioe) {
            throw new ModelExecutionException("Error closing GroovyClassLoader", ioe);
        }
    }

    @Test
    public void testNoIntf() throws Exception{
        
        String script = "import java.util.Map; class x implements me.gking2224.model.execution.groovy.GroovyModelIntf {   def void executeModel(Map input, Map output) {     output.givenValue=input.a   } }";
        Map<String,Object> inputParams = new HashMap<>();
        Map<String,Object> output = new HashMap<>();
        inputParams.put("a", "givenValue");
        try (GroovyClassLoader gcl = new GroovyClassLoader()) {
            @SuppressWarnings("unchecked")
            Class<GroovyModelIntf> scriptClass = gcl.parseClass(script);
            Object scriptInstance = scriptClass.newInstance();
            scriptClass.getMethod("executeModel", Map.class, Map.class).invoke(scriptInstance, inputParams, output);
        }
        assertEquals("givenValue", output.get("givenValue"));
    }

    @Test
    public void testShell() throws Exception{
        
        String script = "def void executeModel() {     output.givenValue=input.a   }";
        Map<String,Object> inputParams = new HashMap<>();
        inputParams.put("a", "givenValue");
        Binding binding = new ObjectGraphBuilder();
        Map<String,Object> output = new HashMap<>();
        binding.setVariable("input", inputParams);
        binding.setVariable("output", output);
        CompilerConfiguration cc = new CompilerConfiguration();
        new GroovyShell(binding).parse(script).invokeMethod("executeModel", null);
        assertEquals("givenValue", output.get("givenValue"));
    }

}
