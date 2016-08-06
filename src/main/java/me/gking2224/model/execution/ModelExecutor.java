package me.gking2224.model.execution;

import me.gking2224.model.jpa.Model;
import me.gking2224.model.service.ModelExecutionRequest;
import me.gking2224.model.service.ModelExecutionResponse;

public interface ModelExecutor {

    boolean canExecuteNature(String nature);
    
    void executeModel(Model model, ModelExecutionRequest request, ModelExecutionResponse response);

}
