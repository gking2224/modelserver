package me.gking2224.model.execution.groovy

class LogicalAnd extends BinaryOperator {

    def LogicalAnd(def delegate, def logger, def lhs) {
        super(delegate, logger);
    }

    @Override
    def boolean operate() {
        return false;
    }

}
