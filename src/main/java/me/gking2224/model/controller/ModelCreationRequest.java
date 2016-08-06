package me.gking2224.model.controller;


import me.gking2224.model.jpa.Model;
import me.gking2224.model.jpa.Version;

public class ModelCreationRequest extends EntityCreationRequest<Model> {

    private Version.IncrementType incrementType = Version.IncrementType.MINOR;

    @Override
    protected Model cleanEntity() {

        Model cleanedModel = entity;
        cleanedModel.setId(null);
        cleanedModel.setVersion(null);
        return cleanedModel;
    }

    public Version.IncrementType getIncrementType() {
        return incrementType;
    }

    public void setIncrementType(Version.IncrementType incrementType) {
        this.incrementType = incrementType;
    }

}
