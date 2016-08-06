package me.gking2224.model.service;

import java.util.List;

import me.gking2224.model.jpa.Model;
import me.gking2224.model.jpa.Version;
import me.gking2224.model.jpa.Version.IncrementType;
import me.gking2224.model.jpa.ModelManifest;
import me.gking2224.model.jpa.ModelType;

public interface ModelAdminService {

    ModelManifest createModelManifest(ModelManifest manifest);

    List<ModelType> findAllModelTypes();

    List<ModelManifest> findModelManifestByType(String typeName);

    Model getModelByTypeAndNameAndVersion(String typeName, String name, Version version);

    ModelType createModelType(ModelType type);

    ModelType updateModelType(ModelType type);

    void deleteModelType(Long id);

    ModelType findTypeById(Long id);

    ModelManifest updateModelManifest(ModelManifest manifest);

    void deleteModelManifest(Long id);

    ModelManifest findManifestById(Long id);

    Model getModelById(Long id);

    Model createNewModel(Model model, IncrementType incrementType);

    List<Model> getModelsByManifest(Long manifestId);

    Model updateModel(Model model);

}
