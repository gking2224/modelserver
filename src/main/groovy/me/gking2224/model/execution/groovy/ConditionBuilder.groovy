package me.gking2224.model.execution.groovy

class ConditionBuilder extends DSLElement {
    
    def lhs
    def rhs
    def Operator operator
    def boolean positive = true

    public ConditionBuilder(def delegate, def logger, ExpressionValue lhs) {
        super(delegate, logger);
        this.lhs = lhs
    }

    public ConditionBuilder(def delegate, def logger) {
        this(delegate, logger, null);
    }

    public ConditionBuilder(def logger) {
        super(logger);
    }
    
    def input() {
        getLogger().info("::input")
        lhs = new InputValue(this, getLogger())
    }
    
    def does() {
        getLogger().info("does")
        return this
    }
    
    def equal() { equalTo() }
    def equal(def obj) { equalTo(obj) }
    
    def exist() { exists() }
    
    def not() {
        getLogger().info("::not")
        positive = !positive
        return this
    }
    
    def equalTo() {
        getLogger().info("::equals")
        operator = new Equals(this, logger)
        operator.setLeftOperand(lhs)
        return operator
    }
    
    def equalTo(def obj) {
        getLogger().info("::equals")
        operator = new Equals(this, logger)
        operator.setLeftOperand(lhs)
        operator.setRightOperand(new Constant(this, logger, obj))
        return operator
    }
    
    def exists() {
        getLogger().info("::exists")
        operator = new Exists(this, logger)
        operator.setOperand(lhs)
        return operator
    }
    
    def is() {
        return this
    }
    
    def greater() {
        greaterThan()
    }
    
    def greaterThan() {
        greaterThan(null)
    }
    
    def greaterThan(def obj) {
        comparison(true, false, obj)
    }
    
    def less() {
        lessThan()
    }
    
    def lessThan() {
        lessThan(null)
    }
    
    def lessThan(def obj) {
        comparison(false, false, obj)
    }
    
    def comparison(boolean positive, boolean matchEquals, def obj) {
        operator = new Comparison(this, logger, positive, matchEquals)
        operator.setLeftOperand(lhs)
        operator.setRightOperand(new Constant(this, logger, obj))
        return operator
    }
    
    def then() {
        then(null)
    }
    
    def then(Action a) {
        getLogger().info("::then")
        def boolean conditionResult = false
        if (operator == null) throw new IllegalStateException("No operator")
        conditionResult = operator.operate()
        if ((positive && conditionResult) || (!positive && !conditionResult)) {
            if (a != null) a.doIt()
            return this
        }
        else {return NullAction.INSTANCE}
    }
    
    def getDeclaredProperty(String name) {
        def rv = super.getDeclaredProperty(name)
        if (rv == null) {
            if ("lhs" == name)
                rv = lhs
            else if ("rhs" == name)
                rv =rhs
            else if ("operator" == name)
                rv = operator
        }
        rv
    }
    
    def doGetProperty(String name) {
        def rv = super.doGetProperty(name)
        if (rv != null) return rv
        getLogger().trace("ConditionBuilder.doGetProperty( $name )")
//        if (lhs == null || (rhs == null && operator != null && !UnaryOperator.isAssignableFrom(operator.getClass()))) {
//            def operand = new Constant(this, logger)
//            operand.setValue(name)
//            if (operator == null) lhs = operand
//            else rhs = operand
//            rv = this
//        }
        rv
    }

    def interceptValue(def value) {
        def rv
        if (value != null && Action.isAssignableFrom(value.getClass())) {
            rv = ((Action)value).doIt()
        }
        return rv
    }
    
}
