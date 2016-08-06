package me.gking2224.model.groovy;

import groovy.lang.Script;

public abstract class MyScript extends Script {

    def i = [:]
    
    abstract def scriptBody();
    
    def run() {
        def key = "x";
        i["x"] = "y";
        scriptBody();
        stdout.write(i[key])
        return null;
    }
    
}