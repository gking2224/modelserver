package me.gking2224.model.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.core.convert.converter.Converter;

import com.fasterxml.jackson.databind.ObjectMapper;

import me.gking2224.model.execution.groovy.LoggerAdapter.Level;
import me.gking2224.model.jpa.ModelVariable;

public class ModelExecutionRequest {

    private static final String INPUT_PARAMETERS = "inputArgs";

    private Long modelId;
    private Map<String, Object> inputParams = new HashMap<String, Object>();

    private boolean traverseNestedExceptions = true;

    public Map<String, Object> getInputParams() {
        return inputParams;
    }
    
    public ModelExecutionRequest() {
        
    }

    @SuppressWarnings("unchecked")
    public ModelExecutionRequest(Map<String, Object> args) {
        if (args.containsKey(INPUT_PARAMETERS)) {
            this.inputParams = (Map<String, Object>)args.get(INPUT_PARAMETERS);
        }
    }

    public boolean hasVariableOfCorrectType(ModelVariable variable) throws ClassNotFoundException {
        return inputParams.containsKey(variable.getName()) && Class.forName(variable.getType())
                .isAssignableFrom(inputParams.get(variable.getName()).getClass());
    }


    public void setInputParams(Map<String, Object> inputParams) {
        this.inputParams = inputParams;
    }


    public boolean hasVariableOfAnyType(ModelVariable variable) {
        return inputParams.containsKey(variable.getName());
    }


    public Long getModelId() {
        return modelId;
    }

    public void setModelId(Long modelId) {
        this.modelId = modelId;
    }


    public static class StringToModelExecutionRequest implements Converter<String, ModelExecutionRequest> {

        @Override
        public ModelExecutionRequest convert(String source) {
            
            ObjectMapper mapper = new ObjectMapper();
            try {
                @SuppressWarnings("unchecked")
                Map<String,Object> args = mapper.readValue(source, Map.class);
                
                ModelExecutionRequest rv = new ModelExecutionRequest(args);
                return rv;
            } catch (Throwable e) {
                throw new IllegalArgumentException("Cannot parse "+source+" to ModelExecutionRequest", e);
            }
        }
    }


    public boolean isTraverseNestedExceptions() {
        return traverseNestedExceptions;
    }

    public void setTraverseNestedExceptions(boolean traverseNestedExceptions) {
        this.traverseNestedExceptions = traverseNestedExceptions;
    }
}
