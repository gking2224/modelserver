package me.gking2224.model.execution.groovy

class ForwardReference extends DSLElement {
    
    def String name
    def args
    
    def ForwardReference(def delegate, String name, def args, def logger) {
        super(delegate, logger)
        this.name = name
        this.args = args
    }
    def ForwardReference(def delegate, String name, def logger) {
        this(delegate, name, null, logger)
    }
    
    def String getName() { name }
    def getArgs() { args }
    
    def resolve(def obj) {
        getLogger().trace( "ForwardReference.resolve( $obj )")
        def rv = getRh().getMethodOrPropertyOnClass(obj, getName(), getArgs());
        if (rv != null && ForwardReference.class.isAssignableFrom(rv.getClass())) {
//            if (((ForwardReference)rv).name == this.name)
            rv = null
//            else 
//                rv = ((ForwardReference)rv).resolve(obj)
        }
        return rv
    }
    
    def String toString() {
        return "ForwardReference $name ( $args )"
    }
    
    def doGetProperty(String name) {
        return new Constant(delegate, logger, name)
    }
}
