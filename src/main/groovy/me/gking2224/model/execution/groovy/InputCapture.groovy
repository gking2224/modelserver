package me.gking2224.model.execution.groovy

import me.gking2224.model.execution.ModelExecutionException

class InputCapture extends ExpressionValue {
    Map input
    def name
    
    public InputCapture(def delegate, def logger, Map input, def name) {
        super(delegate, logger)
        this.input = input
        this.name = name
    }
    
    public InputCapture(def delegate, def logger, Map input) {
        this(delegate, logger, input, null)
    }
    
    def doGetProperty(String name) {
        this.name = name
        this
    }
    
    def getAt(String i) {
        this.input.get(i)
    }
    
    def getAt(InputCapture i) {
        this.input.get(i.getName())
    }
    
    def getAt(ForwardReference f) {
        this.input.get(f.getName())
    }
    def multiply(InputCapture o) {
        def left = evaluate();
        def right = o.evaluate();
        getLogger().debug("Evaluating $left * $right)");
        if (left == null || right == null) {
            throw new ModelExecutionException("Cannot evaluate $left * $right");
        }
        left * right;
    }
    def multiply(ForwardReference f) {
        evaluate() * f.resolve(this)
    }
    def void setProperty(String name, def value) {
        if (value != null && Closure.isAssignableFrom(value.getClass())) {
            value = value()
        }
        if (GString.isAssignableFrom(value.getClass())) {
            value = new String(value)
        }
        this.output.put(name, value)
    }
    
    def int compareTo(Constant c) {
        return evaluate().compareTo(c.getValue())
    }
    
    def int compareTo(InputCapture c) {
        return evaluate().compareTo(c.evalutate())
    }
    
    def int compareTo(Number n) {
        return evaluate().compareTo(n)
    }
    
    def int compareTo(def n) {
        return evaluate().compareTo(n)
    }
    
    def doGetMethodValue(String name, def a) {
        def rv
        if (a != null && a.length == 1) {
            this.setProperty(name, a[0])
            rv = NullAction.INSTANCE
        }
        rv
    }
    def evaluate() {
        def rv = input.get(name)
        if (rv == null) {
            getLogger().error("InputCapture: input[ $name ] is null");
        }
        rv
    }
    
}
