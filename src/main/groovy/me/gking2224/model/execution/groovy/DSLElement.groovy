package me.gking2224.model.execution.groovy

import java.lang.reflect.Method

import me.gking2224.model.execution.ModelExecutionException

import org.codehaus.groovy.runtime.NullObject

abstract class DSLElement {

    def delegate
    def logger
    
    def ReflectionHelper rh
    
    public DSLElement(def delegate, def logger) {
        this.delegate = delegate
        this.logger = logger
        rh = ReflectionHelper.getInstance(logger)
    }
    
    public DSLElement(def logger) {
        this(null, logger)
    }
    
    def getDelegate() { delegate }
    def getLogger() { logger }
    def getRh() {rh}
    
    def methodMissing(String name, def a) {
        getLogger().trace("DSLElement.methodMissing( $name, $a )")
        
        def rv
        if (a != null && a.length == 1 && ForwardReference.isAssignableFrom(a[0].getClass())) {
            def methodResult = rh.getMethodOrPropertyOnClass(this, name, null)
            def forwardResult = ((ForwardReference)a[0]).resolve(methodResult)
            rv = forwardResult
        }
        
        // I don't know why this is called, but...
        else if (a != null && rh.getArrayLength(a) == 1 && a[0] != null && a[0] == NullAction.INSTANCE)
            rv = NullAction.INSTANCE
        else rv = getMethodOrPropertyValue(name, a)
        if (rv == null) rv = this
        return rv
    }
    
    // also looking in delegate, in order this.prop, this.method, delegate.prop, delegate.method
    def getMethodOrPropertyValue(String name, def args) {
        
        def d = this.delegate
        // try on this
        def rv = rh.getMethodOrPropertyOnClass(this, name, args);
        if (rv == null) {
            throw new ModelExecutionException("Could not find $name($args) on this ($this)")
        }
        else if (rv != null && rv == NullAction.INSTANCE) {
            return rv
        }
        else return interceptValue(rv)
    }
    
    // looking in delegate after this
    def getMethodValue(String name, def args) {
        
        def d = this.delegate
        // try on this
        def rv = rh.getMethodValueOnClass(this, name, args);
        if (rv == null && d != null) {
            rv = rh.getMethodValueOnClass(d, name, args);
        }
        
        if (rv == null) {
            throw new ModelExecutionException("Could not find $name($args) on this ($this) or delegate ($delegate)")
        }
        else if (rv != null && rv == NullAction.INSTANCE) {
            return rv
        }
        else return interceptValue(rv)
    }
    
    def getProperty(String name) {
        if (name.indexOf("\$:")== 0)
            return getProperty(name.substring(2), false) // review
        else
            return getProperty(name, true)
    }
    
    def getProperty(String name, boolean checkMethod) {
        getLogger().trace("DSLElement.getProperty( $name )")
        def rv = getDeclaredProperty(name)
        getLogger().trace(" - ${this}.getDeclaredProperty returns: $rv")
        if (rv == null) {
            def cp = doGetProperty(name)
            getLogger().trace(" - ${this}.doGetProperty returns: $cp")
            if (cp != null) return cp
            // final resort - try for a matching no-arg method
            else if (checkMethod) {
                def mv = getMethodValue(name, [])
                getLogger().trace(" - ${this}.getMethodValue returns: $mv")
                if (mv != null) rv = interceptValue(mv)
                getLogger().trace(" - ${this}.interceptValue returns: $rv")
            }
        }
        rv
    }
    
    def getDeclaredProperty(String name) {
        def rv
        if ("logger".equals(name)) {
            rv = getLogger()
        }
        else if ("delegate".equals(name)) {
            rv = getDelegate()
        }
        else if ("rh".equals(name)) {
            rv = getRh()
        }
        rv
    }
    
    def doGetProperty(String name) {
        return null; // default, overwrite in child classes
    }
    
    def interceptValue(def mv) { // default, override in child classes
        return mv
    }
}
