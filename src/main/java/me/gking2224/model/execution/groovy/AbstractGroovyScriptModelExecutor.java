package me.gking2224.model.execution.groovy;

import java.io.BufferedWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.codehaus.groovy.runtime.StringBufferWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import me.gking2224.model.execution.ModelExecutionException;
import me.gking2224.model.execution.ModelExecutor;
import me.gking2224.model.execution.groovy.LoggerAdapter.Level;
import me.gking2224.model.jpa.Model;
import me.gking2224.model.service.ModelExecutionRequest;
import me.gking2224.model.service.ModelExecutionResponse;
import me.gking2224.model.service.ModelExecutionWarning;

public abstract class AbstractGroovyScriptModelExecutor implements ModelExecutor {

    private static final String LOG_LEVEL_KEY = "log.level";

    private static final Level DEFAULT_LOG_LEVEL = Level.DEBUG;

    private static final String LOG_PATTERN_KEY = "log.pattern";

    // 0 = model name java.lang.String
    // 1 = date java.util.Date
    // 2 = level java.lang.String
    // 3 = message java.lang.String
    protected String defaultLogPattern = "{1,date} {1,time} {2} {0}- {3}";

    @SuppressWarnings("unused")
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private final Set<String> NATURES = new HashSet<String>();

    @Override
    public final boolean canExecuteNature(String nature) {
        return NATURES.contains(nature);
    }
    
    protected final void addNature(final String nature) {
        NATURES.add(nature);
    }

    @Override
    public void executeModel(Model model, ModelExecutionRequest request, ModelExecutionResponse response) {
        
        StringBuffer stderr = new StringBuffer();
        StringBuffer stdout = new StringBuffer();
        List<ModelExecutionWarning> warnings = new ArrayList<ModelExecutionWarning>();
        
        try (Writer out = new BufferedWriter(new StringBufferWriter(stdout));
                Writer err = new BufferedWriter(new StringBufferWriter(stderr))) {
            Map<String, Object> output = new HashMap<>();
            response.setOutputs(output);
            response.setWarnings(warnings);
            
            Logger modelLogger = getLogger(model, request, out, err);
            
            response.setOutputs(output);
            
            doExecute(model, request, response, modelLogger);
            
        } catch (Exception e) {
            e.printStackTrace();
            throw new ModelExecutionException(200, e);
        } finally {
            response.setStderr(stderr.toString());
            response.setStdout(stdout.toString());
        }
    }

    protected abstract void doExecute(
            Model model,
            ModelExecutionRequest request,
            ModelExecutionResponse response,
            Logger modelLogger);

    protected StreamWrappingLogger getLogger(Model model, ModelExecutionRequest request, Writer out, Writer err) {
        return new StreamWrappingLogger(
                getLogPattern(request.getInputParams()),
                getLoggerName(model),
                getLogLevel(request.getInputParams()),
                out, err);
    }

    protected Level getLogLevel(Map<String, Object> inputParams) {
        return inputParams.containsKey(LOG_LEVEL_KEY)?LoggerAdapter.Level.valueOf((String)inputParams.get(LOG_LEVEL_KEY)):DEFAULT_LOG_LEVEL;
    }

    protected String getLogPattern(Map<String, Object> inputParams) {
        return inputParams.containsKey(LOG_PATTERN_KEY)?(String)inputParams.get(LOG_PATTERN_KEY):this.defaultLogPattern;
    }
    
    protected String getLoggerName(Model model) {
        return String.format("%s.%s", model.getManifest().getType().getName(), model.getManifest().getName()); 
    }
}
