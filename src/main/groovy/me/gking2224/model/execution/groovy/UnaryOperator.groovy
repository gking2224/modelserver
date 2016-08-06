package me.gking2224.model.execution.groovy

abstract class UnaryOperator extends Operator {
    def operand
    
    def UnaryOperator(def logger) {
        super(logger)
    }
    def UnaryOperator(def delegate, def logger) {
        super(delegate, logger)
    }
    
    def setOperand(def operand) { this.operand = operand }
    def getOperand() { operand }
}
