package me.gking2224.model.execution.groovy

import org.codehaus.groovy.runtime.NullObject;

class Exists extends UnaryOperator {

    def Exists(def delegate, def logger) {
        super(delegate, logger);
    }

    def Exists(def logger) {
        super(logger);
    }
    
    def boolean operate() {
        def value = getOperand().evaluate()
        value != null && value != NullObject.nullObject
    }
    
    def String toString() { "exists" }
}
