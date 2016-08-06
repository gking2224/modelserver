package me.gking2224.model.execution.groovy

class NullAction extends Action {
    
    static INSTANCE

    def NullAction(def logger) {
        super(logger)
    }
           
    static initialize(def logger) {
        if (INSTANCE == null) {
            INSTANCE = new NullAction(logger)
        }
    }
    def methodMissing(String name, def a) {
        getLogger().trace("NullAction.methodMissing( $name, $a ) - terminating")
        return this
    }
    def getProperty(String name) {
        getLogger().trace("NullAction.doGetProperty( $name ) - terminating")
        return this
    }
}
