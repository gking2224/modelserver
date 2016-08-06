package me.gking2224.model.jpa;

public interface ModelDao {

    Version getNextVersion(String modelType, String modelName, boolean majorVersionIncrement);
    
}
