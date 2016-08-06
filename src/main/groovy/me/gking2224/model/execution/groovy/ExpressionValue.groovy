package me.gking2224.model.execution.groovy

import org.codehaus.groovy.runtime.NullObject;

abstract class ExpressionValue extends DSLElement {

    def ExpressionValue(def delegate, def logger) {
        super(delegate, logger);
    }

    def ExpressionValue(def logger) {
        super(logger);
    }
    
    def notNull(def v) {
        (v == null)?NullObject.nullObject:v
    }
    
    abstract def evaluate()
}
