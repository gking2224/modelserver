package me.gking2224.model.service;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.core.NestedRuntimeException;

import me.gking2224.model.execution.ModelExecutionException;

public class ModelExecutionResponse {

    private String executorClassName;
    private String requestTime;
    private Instant requestTimeAsInstant;
    private int durationInNano;
    private boolean success = false;
    private ModelExecutionError error;
    private List<ModelExecutionWarning> warnings = new ArrayList<ModelExecutionWarning>();
    private Map<String, Object> outputs;
    private String stdout;
    private String stderr;
    public ModelExecutionResponse() {
        super();
    }
    public String getRequestTime() {
        return requestTime;
    }
    public void setRequestTime(String requestTime) {
        this.requestTime = requestTime;
    }
    public int getDurationInNano() {
        return durationInNano;
    }
    public void setDuration(Duration duration) {
        this.durationInNano = duration.getNano();
    }
    public boolean isSuccess() {
        return success;
    }
    public void setSuccess(boolean success) {
        this.success = success;
    }
    public ModelExecutionError getError() {
        return error;
    }
    public void setError(ModelExecutionError error) {
        this.error = error;
    }
    public List<ModelExecutionWarning> getWarnings() {
        return warnings;
    }
    public void addWarning(ModelExecutionWarning warning) {
        this.warnings.add(warning);
    }
    public void setWarnings(List<ModelExecutionWarning> warnings) {
        this.warnings = warnings;
    }
    public void captureException(final Throwable throwable, final boolean traverseNested) {
        int code = ModelExecutionException.DEFAULT_CODE;
        if (ModelExecutionException.class.isAssignableFrom(throwable.getClass())) {
            code = ((ModelExecutionException)throwable).getCode();
        }
        Throwable t = throwable;
        while (
                traverseNested
                && NestedRuntimeException.class.isAssignableFrom(t.getClass())
                && ((NestedRuntimeException)t).getCause() != null) {
            t = ((NestedRuntimeException)t).getCause();
        }
        error = ModelExecutionError.fromThrowable(t, code);        
    }
    public void setOutputs(Map<String, Object> outputs) {
        this.outputs = outputs;
    }
    public Map<String, Object> getOutputs() {
        return outputs;
    }
    public String getStdout() {
        return stdout;
    }
    public void setStdout(String stdout) {
        this.stdout = stdout;
    }
    public String getStderr() {
        return stderr;
    }
    public void setStderr(String stderr) {
        this.stderr = stderr;
    }
    public Instant getRequestTimeAsInstant() {
        return requestTimeAsInstant;
    }
    public void setRequestTimeAsInstant(Instant requestTimeAsInstant) {
        this.requestTimeAsInstant = requestTimeAsInstant;
    }
    public String getExecutorClassName() {
        return executorClassName;
    }
    public void setExecutorClassName(String executorClassName) {
        this.executorClassName = executorClassName;
    }
}
