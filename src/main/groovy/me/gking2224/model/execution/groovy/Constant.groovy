package me.gking2224.model.execution.groovy

class Constant extends ExpressionValue {

    def value
    
    def Constant(def delegate, def logger, def value) {
        super(delegate, logger);
        if (ForwardReference.isAssignableFrom(value.getClass())) {
            def fwd = value.resolve(delegate)
            if (fwd == null) fwd = value.getName()
            value = fwd
        }
        else if (InputCapture.isAssignableFrom(value.getClass())) {
            value = value.evaluate()
            
        }
        this.value = value
    }
    
    def Constant(def delegate, def logger) {
        this(delegate, logger, null);
    }

    def Constant(def logger) {
        super(logger);
    }

    def evaluate() {
        return value
    }
    
    def void setValue(def value) {this.value = value}
    def getValue() { notNull(value) }
    
    def String toString() { "Constant( $value )" }
}
