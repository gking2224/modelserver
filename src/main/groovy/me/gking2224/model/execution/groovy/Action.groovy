package me.gking2224.model.execution.groovy

abstract class Action extends DSLElement {
    
    def Action(def logger) {
        super(logger)
    }
    def Action(def delegate, def logger) {
        super(delegate, logger)
    }
    
    def doIt() { // override
        
    }
    
    def end() { // override
        doIt()
        return NullAction.INSTANCE
    }
}
