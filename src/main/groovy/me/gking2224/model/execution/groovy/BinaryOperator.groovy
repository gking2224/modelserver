package me.gking2224.model.execution.groovy

abstract class BinaryOperator extends Operator {
    
    def ExpressionValue leftOperand
    def ExpressionValue rightOperand
    
    def BinaryOperator(def logger) {
        super(logger)
    }
    def BinaryOperator(def delegate, def logger) {
        super(delegate, logger)
    }
    
    def void setLeftOperand(ExpressionValue leftOperand) { this.leftOperand = leftOperand }
    def ExpressionValue getLeftOperand() { leftOperand }
    
    def void setRightOperand(ExpressionValue  rightOperand) { this.rightOperand = rightOperand }
    def ExpressionValue getRightOperand() {rightOperand}
    
    def getDeclaredProperty(String name) {
        def rv = super.getDeclaredProperty(name)
        if (rv != null) return rv
        if ("leftOperand" == name) rv = leftOperand
        if ("rightOperand" == name) rv = rightOperand
        return rv
    }
}
