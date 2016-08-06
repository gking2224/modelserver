package me.gking2224.model.execution.groovy

class Equals extends BinaryOperator {

    def Equals(def delegate, def logger) {
        super(delegate, logger);
    }

    def Equals(def logger) {
        super(logger);
    }
    
    def to() {
        return this
    }
    def boolean operate() {
        def lhs = getLeftOperand().evaluate()
        def rhs = getRightOperand().evaluate()
        return lhs.equals(rhs)
    }
    
//    def String toString() { new String("${super.leftOperand} equals ${super.rightOperand}") }
    
    def doGetProperty(String name) {
        def rv = super.doGetProperty(name)
        if (rv != null) return rv
        if (getRightOperand() == null) { // is this a nasty hack?
            setRightOperand(new Constant(this, logger, name))
            return this
        }
        return null
    }
}
