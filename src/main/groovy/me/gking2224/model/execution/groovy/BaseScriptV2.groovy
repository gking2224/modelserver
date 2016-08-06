package me.gking2224.model.execution.groovy;

import me.gking2224.model.execution.ModelExecutionException

abstract class BaseScriptV2 extends Script {
    
    def input = [:] as Map
    
    int warningIdx = 0;
    
    abstract void scriptBody()
    
    def run() {
        for (String key : _input.keySet()) input.put(key, _input.get(key));
        return scriptBody()
    }
    def setOutput(def name, Closure c) {
        _output.put(new String(name), c())
    }
    def setOutput(def name, String v) {
        _output.put(new String(name), new String(v))
    }
    
    def void println(def s) {
        if (s == null) s = "null"
        _stdout.write( new String(s.toString()) )
        _stdout.write( "\n" )
    }
    
    def void printerr(def s) {
        if (s == null) s = "null"
        _stderr.write( new String(s) )
        _stderr.write( "\n" )
    }
    
    def warn(int code, String summary, String detail) {
        String prefix = new String("${warningIdx}")
        _warnings.put(new String("${prefix}Code"), code);
        _warnings.put(new String("${prefix}Summary"), new String(summary));
        _warnings.put(new String("${prefix}Detail"), new String(detail));
        warningIdx = warningIdx+1;
    }
    
    def warn(String summary) {
        warn(0, summary, "");
    }
    
    def warn(int code, summary) {
        warn(code, summary, "");
    }
    
    def warn(String summary, String detail) {
        warn(0, summary, detail);
    }

    def propertyMissing(String name, def value) {
        _output.put(new String(name), (value==null)?null:value.toString())
    }

    def propertyMissing(String name) {
        return input.get(name)
    }
    
}