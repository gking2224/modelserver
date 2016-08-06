package me.gking2224.model.execution.groovy

class OutputCapture extends DSLElement {
    Map output
    def name
    
    public OutputCapture(def delegate, def logger, Map output, def name) {
        super(delegate, logger)
        this.output = output
        this.name = name
    }
    
    public OutputCapture(def delegate, def logger, Map output) {
        this(delegate, logger, output, null)
        this.output = output
    }
    
    def putAt(ForwardReference fwd, Closure c) {
        def fwdName = fwd.resolve(getDelegate())
        if (fwdName == null) fwdName = fwd.getName()
        def value = c()
        output.put(fwdName, value)
    }
    
    def putAt(String name, def value) {
        def rv = null
        rv
    }

    def is(def Closure c) {
        this.output.putAt(name, c());
    }
    
    def doGetProperty(String name) {
        this.name = name
        return this
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
    
    def doGetMethodValue(String name, def a) {
        def rv
        if (a != null && a.length == 1) {
            this.setProperty(name, a[0])
            rv = NullAction.INSTANCE
        }
        rv
    }
}
