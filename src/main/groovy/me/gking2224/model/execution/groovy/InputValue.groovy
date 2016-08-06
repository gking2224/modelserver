package me.gking2224.model.execution.groovy

import org.codehaus.groovy.runtime.NullObject;

class InputValue extends ExpressionValue {
    
    def inputName

    def InputValue(def delegate, def logger) {
        super(delegate, logger);
    }

    def InputValue(def logger) {
        super(logger);
    }
    
    def getDeclaredProperty(String name) {
        def rv = super.getDeclaredProperty(name)
        if (rv == null) {
            if ("inputName" == name)
                rv = inputName
        }
        rv
    }
    
    def doGetProperty(String name) {
        def rv = super.doGetProperty(name)
        if (rv != null) return rv
        if (this.inputName == null) { // is this a nasty hack?
            this.inputName = name
            getLogger().info("::input($name)")
            return this
        }
        return null
    }
    
    def evaluate() {
        def inputs = getRh().getMethodOrPropertyOnClass(getDelegate(), "getInputs")
        if (inputs == null || !Map.isAssignableFrom(inputs.getClass())) throw new IllegalStateException("Could not resolve input '${inputName}'")
        def rv = notNull(((Map)inputs).get(inputName))
        rv
    }
    
    def String toString() {
        return "InputValue( $inputName )"
    }
}
