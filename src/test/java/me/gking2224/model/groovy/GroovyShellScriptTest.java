package me.gking2224.model.groovy;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

import org.codehaus.groovy.control.CompilerConfiguration;
import org.codehaus.groovy.runtime.StringBufferWriter;
import org.junit.Test;

import groovy.lang.Binding;
import groovy.lang.GroovyShell;
import groovy.lang.Script;
import groovy.util.ObjectGraphBuilder;
import me.gking2224.model.execution.groovy.BaseScriptV1;
import me.gking2224.model.execution.groovy.BaseScriptV2;

public class GroovyShellScriptTest {

    @Test
    public void test() throws IOException {
        Map<String, Object> input = new HashMap<>();
        input.put("name", "Barry");
        StringBuffer stdout = new StringBuffer();
        try (
                Writer out = new BufferedWriter(new StringBufferWriter(stdout))
        ) {
            
            Binding binding = new ObjectGraphBuilder();
            binding.setVariable("_stdout", out);
            binding.setVariable("_input", input);
            
            CompilerConfiguration config = new CompilerConfiguration();
            config.setScriptBaseClass(BaseScriptV1.class.getName());
            GroovyShell shell = new GroovyShell(binding, config);
            Script parsedScript = shell.parse(""
                    +"def key = 'name';"
                    +"println \"Hello\"; "
                    +"println input.get(key); "
                    );
            parsedScript.run();
            
            out.flush();
        }
        
        assertEquals("Hello\nBarry\n", stdout.toString());
    }

    @Test
    public void testWarning() throws IOException {
        Map<String, Object> input = new HashMap<>();
        Map<String, Object> warnings = new HashMap<>();
        input.put("name", "Barry");
        StringBuffer stdout = new StringBuffer();
        try (
                Writer out = new BufferedWriter(new StringBufferWriter(stdout))
        ) {
            
            Binding binding = new ObjectGraphBuilder();
            binding.setVariable("_stdout", out);
            binding.setVariable("_warnings", warnings);
            binding.setVariable("_input", input);
            
            CompilerConfiguration config = new CompilerConfiguration();
            config.setScriptBaseClass(BaseScriptV1.class.getName());
            GroovyShell shell = new GroovyShell(binding, config);
            Script parsedScript = shell.parse("ifInputMissing(\"abc\").thenWarn()");
            parsedScript.run();
            assertTrue(warnings.containsKey("0Summary"));
            
            out.flush();
        }
    }

    @Test
    public void testInputsCheckingDsl() throws IOException {
        Map<String, Object> input = new HashMap<>();
        Map<String, Object> warnings = new HashMap<>();
        input.put("name", "Barry");
        StringBuffer stdout = new StringBuffer();
        try (
                Writer out = new BufferedWriter(new StringBufferWriter(stdout))
        ) {
            
            Binding binding = new ObjectGraphBuilder();
            binding.setVariable("_stdout", out);
            binding.setVariable("_warnings", warnings);
            binding.setVariable("_input", input);
            
            CompilerConfiguration config = new CompilerConfiguration();
            config.setScriptBaseClass(BaseScriptV2.class.getName());
            GroovyShell shell = new GroovyShell(binding, config);
            Script parsedScript = shell.parse("ifInput(\"abc\").doesNot().exist().then().warn(\"abc missing\")");
            parsedScript.run();
            assertTrue(warnings.containsKey("0Summary"));
            
            out.flush();
        }
    }

    @Test
    public void testInputsCheckingDslNoDots() throws IOException {
        Map<String, Object> input = new HashMap<>();
        Map<String, Object> warnings = new HashMap<>();
        input.put("name", "Barry");
        StringBuffer stdout = new StringBuffer();
        try (
                Writer out = new BufferedWriter(new StringBufferWriter(stdout))
        ) {
            
            Binding binding = new ObjectGraphBuilder();
            binding.setVariable("_stdout", out);
            binding.setVariable("_warnings", warnings);
            binding.setVariable("_input", input);
            
            CompilerConfiguration config = new CompilerConfiguration();
            config.setScriptBaseClass(BaseScriptV2.class.getName());
            GroovyShell shell = new GroovyShell(binding, config);
            Script parsedScript = shell.parse("ifInput(\"abc\") doesNot() exist() then() warn(\"abc missing\")");
            parsedScript.run();
            assertTrue(warnings.containsKey("0Summary"));
            
            out.flush();
        }
    }

    @Test
    public void testInputsCheckingDslNoDotsOrBrackets() throws IOException {
        Map<String, Object> input = new HashMap<>();
        Map<String, Object> warnings = new HashMap<>();
        input.put("name", "Barry");
        StringBuffer stdout = new StringBuffer();
        try (
                Writer out = new BufferedWriter(new StringBufferWriter(stdout))
        ) {
            
            Binding binding = new ObjectGraphBuilder();
            binding.setVariable("_stdout", out);
            binding.setVariable("_warnings", warnings);
            binding.setVariable("_input", input);
            
            CompilerConfiguration config = new CompilerConfiguration();
            config.setScriptBaseClass(BaseScriptV2.class.getName());
            GroovyShell shell = new GroovyShell(binding, config);
            Script parsedScript = shell.parse("ifInput(\"abc\") doesNot exist then warn(\"abc missing\")");
            parsedScript.run();
            assertTrue(warnings.containsKey("0Summary"));
            
            out.flush();
        }
    }
}
