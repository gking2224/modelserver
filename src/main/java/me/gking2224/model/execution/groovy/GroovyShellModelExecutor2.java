package me.gking2224.model.execution.groovy;

import java.util.Map;

import org.codehaus.groovy.control.CompilerConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import groovy.lang.Binding;
import groovy.lang.GroovyShell;
import groovy.lang.Script;
import groovy.util.ObjectGraphBuilder;
import me.gking2224.model.execution.StaticDataProviderFactory;
import me.gking2224.model.jpa.Model;
import me.gking2224.model.service.ModelExecutionRequest;
import me.gking2224.model.service.ModelExecutionResponse;

@Component
public class GroovyShellModelExecutor2 extends AbstractGroovyScriptModelExecutor {


    @Autowired
    private StaticDataProviderFactory staticDataProviderFactory;

    private static final String BASE_SCRIPT_V1 = "me.gking2224.model.execution.groovy.BaseScriptV1";
    private static final String BASE_SCRIPT_V2 = "me.gking2224.model.execution.groovy.BaseScriptV2";

    private static final String NATURE = "groovy.shell.v2";
    private static final String NATURE_BASE_SCRIPT_V1 = "groovy.shell.v2.baseScript.v1";
    private static final String NATURE_BASE_SCRIPT_V2 = "groovy.shell.v2.baseScript.v2";

    
    @SuppressWarnings("unused")
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    public GroovyShellModelExecutor2() {
        addNature(NATURE);
        addNature(NATURE_BASE_SCRIPT_V1);
        addNature(NATURE_BASE_SCRIPT_V2);
    }

    private CompilerConfiguration getShellConfig(Model model, Map<String, Object> outputParams) {
        CompilerConfiguration config = new CompilerConfiguration();
        if (model.hasNature(NATURE_BASE_SCRIPT_V2)) {
            outputParams.put(this.getClass().getName()+".baseScript", BASE_SCRIPT_V2);
            config.setScriptBaseClass(BASE_SCRIPT_V2);
        }
        else {
            outputParams.put(this.getClass().getName()+".baseScript", BASE_SCRIPT_V1);
            config.setScriptBaseClass(BASE_SCRIPT_V1);
        }
        return config;
    }

    protected void addStaticDataProviders(Model model, Binding binding) {
        staticDataProviderFactory.getStaticDataProviders(model.getNatures()).forEach((s) -> {
            binding.setVariable(s.getShortIdentifier(), s);
        });
    }

    @Override
    protected void doExecute(Model model, ModelExecutionRequest request, ModelExecutionResponse response,
            Logger modelLogger) {
        
        String script = model.getScript();
        
        Binding binding = new ObjectGraphBuilder();
        binding.setVariable("_input", request.getInputParams());
        binding.setVariable("_warnings", response.getWarnings());
        binding.setVariable("_output", response.getOutputs());
        binding.setVariable("_logger", modelLogger);
        
        // for backwards compatibility

        binding.setVariable("_stdout", ((StreamWrappingLogger)modelLogger).getOut());
        binding.setVariable("_stderr", ((StreamWrappingLogger)modelLogger).getErr());
        
        addStaticDataProviders(model, binding);

        GroovyShell shell = new GroovyShell(binding, getShellConfig(model, response.getOutputs()));
        Script parsedScript = shell.parse(script);
        parsedScript.run();
    }
}
