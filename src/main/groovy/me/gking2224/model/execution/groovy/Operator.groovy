package me.gking2224.model.execution.groovy

abstract class Operator extends DSLElement {
    
    def Operator(def logger) {
        super(logger)
    }
    def Operator(def delegate, def logger) {
        super(delegate, logger)
    }
    
    abstract boolean operate()
}
