package me.gking2224.model.execution.groovy

import me.gking2224.model.service.ModelExecutionWarning

abstract class AbstractGroovyClassLoaderScriptV1 implements GroovyClassLoaderScriptV1 {

    def _input
    def warnings
    def _output
    def logger
    
    def ReflectionHelper rh

    static final DEFAULT_CODE = new Integer(0)
    static final String NO_MESSAGE = "<no message>"

    public AbstractGroovyClassLoaderScriptV1(
    def _input,
    def warnings,
    def output,
    def logger) {

        this._input = _input
        this.warnings = warnings
        this._output = output
        this.logger = logger
        rh = ReflectionHelper.getInstance(logger)
        NullAction.initialize(logger)
    }

    @Override
    public void execute() {
        try {
            getLogger().debug("Preparing for execution")
            doExecute()
        }
        catch (Throwable t) {
            throw t
        }
        finally {
            getLogger().debug("Completed execution")
        }
    }

    protected abstract void doExecute()
    
    def stop() {
        return null
    }
    def setOutput(def name, Closure c) {
        output.put(new String(name), c())
    }
    def setOutput(def name, String v) {
        output.put(new String(name), new String(v))
    }

    def void addWarning( def code, def summary, def detail) {
        getLogger().info(":: addWarn(int, String, String)")
        warnings.add(new ModelExecutionWarning(code, new String(summary), new String(detail)))
        return null
    }

    def warn(Integer code, String summary, String detail) {
        getLogger().info(":: warn(int, String, String)")
        return new AddWarningAction(this, code, summary, detail, getLogger())
    }

    def warn(String summary) {
        getLogger().info(":: warn(String)")
        warn(DEFAULT_CODE, summary, "")
    }

    def warn(Integer code, String summary) {
        getLogger().info(":: warn(int, String)")
        warn(code, summary, "")
    }

    def warn(Integer code) {
        getLogger().info(":: warn(int)")
        warn(code, NO_MESSAGE, "")
    }

    def warn(String summary, String detail) {
        getLogger().info(":: warn(String, String)")
        warn(DEFAULT_CODE, summary, detail)
    }

    def warn() {
        getLogger().info(":: warn()")
        warn(DEFAULT_CODE, NO_MESSAGE, "")
    }
    
    def input() {
        return new InputCapture(this, getLogger(), _input)
    }
    
    def input(ForwardReference fwd) {
        return getInputs().get(fwd.getName())
    }
    
    def getInputs() { _input }

    public ConditionBuilder condition() {
        logger.trace("AbstractGroovyClassLoaderScriptV1.condition( )")
        return new ConditionBuilder(this, getLogger())
    }

    public ConditionBuilder condition(ExpressionValue v) {
        logger.trace("AbstractGroovyClassLoaderScriptV1.condition( $v )")
        return new ConditionBuilder(this, getLogger(), v)
    }

    def propertyMissing(String name) {
        
        logger.trace("AbstractGroovyClassLoaderScriptV1.propertyMissing( $name )")
    }
    def void setProperty(String name, def obj) {
        _output.put(name, obj)
    }
    def getProperty(String name) {
        if (name.indexOf("\$:")== 0) {
            name = name.substring(2)
        }
        if ("delegate" == name)
            return null
        else if ("_input" == name)
            return _input
        else if ("_input" == name)
            return _input
        else if ("warnings" == name)
            return warnings
        else if ("output" == name)
            return new OutputCapture(this, logger, _output)
        else if ("logger" == name)
            return getLogger()
        def checkMethod = true
        def rv = null
        if (checkMethod) // consider input() to return InputCapture?
            rv = rh.getMethodValueOnClass(this, name, [])
        if (rv == null && _output.containsKey(name))
            rv = _output.get(name)
        else if (_input.containsKey(name)) {
            rv = new InputCapture(this, getLogger(), _input);
            rv.setName(name);
        } 
        if (rv == null)
            rv = new ForwardReference(this, name, getLogger())
        rv
    }
    
    def methodMissing(String name, def a) {
        def rv
        logger.trace("Script.methodMissing( $name, $a )");
        
        if ("addWarning" == name) {
            def warnAction = rh.getMethodValueOnClass(this, "warn", a)
            warnAction.doIt()
        }
        else if (a != null && a.length == 1 && ForwardReference.isAssignableFrom(a[0].getClass())) {
            def methodResult = rh.getMethodOrPropertyOnClass(this, name, null)
            rv = ((ForwardReference)a[0]).resolve(methodResult)
        }
//        else if (_output.containsKey(name)) {
//            rv = new OutputCapture(this, getLogger(), _output);
//            rv.setName(name);
//        }
        else if (a != null && a.length == 1 && Closure.isAssignableFrom(a[0].getClass())) {
            rv = new ForwardReference(this, name, a, getLogger())
        }
        return rv;
    }
}
