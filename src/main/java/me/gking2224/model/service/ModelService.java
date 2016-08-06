package me.gking2224.model.service;

import me.gking2224.model.jpa.Version;

public interface ModelService {

    ModelExecutionResponse executeModel(String name, String type, Version version, ModelExecutionRequest inputs);

    ModelExecutionResponse executeModel(Long modelId, ModelExecutionRequest inputs);

    ModelExecutionResponse executeModel(ModelExecutionRequest request);
}
