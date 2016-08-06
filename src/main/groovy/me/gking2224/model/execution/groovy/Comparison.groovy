package me.gking2224.model.execution.groovy

class Comparison extends BinaryOperator {
    
    boolean greaterThan = true
    boolean matchEquality = false
    def expectingEqual = false
    
    def Comparison(def delegate, def logger, boolean greaterThan, boolean matchEquality) {
        super(delegate, logger)
        this.greaterThan = greaterThan
        this.matchEquality = matchEquality
    }
    
//    def doGetProperty(String name) {
//        if (name != null && name.length() > 3 && "num" == name.substring(0,3)) {
//            right = new Integer(name.substring(3)); 
//        }
//        return this;
//    }
    
    def boolean operate() {
        def leftOperandEvaluate = leftOperand.evaluate()
        def rightOperandEvaluate = rightOperand.evaluate()
        
        def matchingTypes = rh.matchTypes(leftOperandEvaluate, rightOperandEvaluate)
        def matchingLeft = matchingTypes.get(0)
        def matchingRight = matchingTypes.get(1)
        
        if (matchingLeft == matchingRight) return matchEquality
        def rv = matchingLeft.compareTo(matchingRight) > 0;
        (greaterThan)?rv:!rv
    }
    
    def than() {
        than(null)
    }
    
    def or() {
        expectingEqual = true
        return this
    }
    
    def equal() {
        if (expectingEqual) throw new IllegalStateException("try 'equalTo' instead of 'equal to'")
        else throw new IllegalStateException("unexpected 'equal'")
    }
    
    def equalTo(Number n) {
        matchEquality = true
        expectingEqual = false
        to(n)
    }
    
    def equalTo() {
        expectingEqual = false
        matchEquality = true
        return this
    }
    
    def to(Number n) {
        if (!matchEquality) throw new IllegalStateException("unexpected 'to'")
        than(n)
    }
    
    def than(Number n) {
        if (n != null) 
            setRightOperand(new Constant(this, logger, n))
        return this
    }
    
    def getDeclaredProperty(String name) {
        def rv = super.getDeclaredProperty(name)
        if (rv != null) return rv
        if ("greaterThan" == name)
            rv = greaterThan
        rv
    }
    
    def doGetProperty(String name) {
        getLogger().trace("Comparison.doGetProperty( $name )")
        def rv = super.doGetProperty(name)
        if (rv != null) return rv        

        rv
    }
    
//    def String toString() {
//        return "${leftOperand} ${(greaterThan)?'>':'<'} $rightOperand"
//    }
}
