package me.gking2224.model.execution.groovy;

import java.util.Map;

public interface GroovyModelIntf {

    void executeModel(Map<String,Object> inputParams, Map<String,Object> output) throws Exception;
}
