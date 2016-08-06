package me.gking2224.model.execution;

import me.gking2224.model.jpa.Model;
import me.gking2224.model.service.ModelExecutionRequest;

public interface ModelExecutorProvider {

    ModelExecutor getExecutor(Model model, ModelExecutionRequest request);

}
