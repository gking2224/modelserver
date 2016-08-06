package me.gking2224.model.execution.groovy;

public class DummyLogger {

    public void trace(String s) {
        _logIt(s);
    }

    public void debug(String s) {
        _logIt(s);
    }

    public void info(String s) {
        _logIt(s);
    }

    public void warn(String s) {
        _logIt(s);
    }

    public void error(String s) {
        _logIt(s);
    }

    private void _logIt(String s) {
        System.out.println(s);
    }
}
