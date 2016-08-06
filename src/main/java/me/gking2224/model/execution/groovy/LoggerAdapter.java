package me.gking2224.model.execution.groovy;

import java.text.MessageFormat;

import org.slf4j.Logger;
import org.slf4j.Marker;

public abstract class LoggerAdapter implements Logger {
    
    protected final String name;
    protected Level level;
    
    public enum Level {
        TRACE(1),
        DEBUG(3),
        INFO(5),
        WARN(7),
        ERROR(9);
        
        private int level;

        Level(int level) {
            this.level = level;
        }
        
        int getLevel() {
            return level;
        }
    }
    
    public LoggerAdapter(String name, Level level) {
        this.name = name;
        this.level = level;
    }
    
    public LoggerAdapter(String name, String level) {
        this.name = name;
        this.level = Level.valueOf(level);
    }

    @Override
    public String getName() {
        return name;
    }
    
    // ABSTRACT
    protected abstract void doLog(Level level, String msg);
    protected abstract void doLog(Level level, String msg, Throwable throwable);
    
    // BASE
    private void _log(Level level, String msg) {
        if (this.level.level <= level.level) {
            doLog(level, msg);
        }
    }
    private void _log(Level level, String msg, Throwable throwable) {
        if (this.level.level >= level.level) {
            doLog(level, msg, throwable);
        }
    }
    private boolean _isLevelEnabled(Level level) {
        return this.level.level >= level.level;
    }
    
    //TRACE
    @Override
    public boolean isTraceEnabled() {
        return _isLevelEnabled(Level.TRACE);
    }

    @Override
    public void trace(String msg) {
        _log(Level.TRACE, msg);
    }

    @Override
    public void trace(String format, Object arg) {
        trace(MessageFormat.format(format, arg));
    }

    @Override
    public void trace(String format, Object arg1, Object arg2) {
        trace(MessageFormat.format(format, arg1, arg2));
    }

    @Override
    public void trace(String format, Object... arguments) {
        trace(MessageFormat.format(format, arguments));
    }

    @Override
    public void trace(String msg, Throwable t) {
        _log(Level.TRACE, msg, t);
    }

    @Override
    public boolean isTraceEnabled(Marker marker) {
        throw new IllegalStateException("not implemented");
    }

    @Override
    public void trace(Marker marker, String msg) {
        throw new IllegalStateException("not implemented");
    }

    @Override
    public void trace(Marker marker, String format, Object arg) {
        throw new IllegalStateException("not implemented");
    }

    @Override
    public void trace(Marker marker, String format, Object arg1, Object arg2) {
        throw new IllegalStateException("not implemented");
    }

    @Override
    public void trace(Marker marker, String format, Object... argArray) {
        throw new IllegalStateException("not implemented");
    }

    @Override
    public void trace(Marker marker, String msg, Throwable t) {
        throw new IllegalStateException("not implemented");
    }
    
    //DEBUG
    @Override
    public boolean isDebugEnabled() {
        return _isLevelEnabled(Level.DEBUG);
    }

    @Override
    public void debug(String msg) {
        _log(Level.DEBUG, msg);
    }

    @Override
    public void debug(String format, Object arg) {
        debug(MessageFormat.format(format, arg));
    }

    @Override
    public void debug(String format, Object arg1, Object arg2) {
        debug(MessageFormat.format(format, arg1, arg2));
    }

    @Override
    public void debug(String format, Object... arguments) {
        debug(MessageFormat.format(format, arguments));
    }

    @Override
    public void debug(String msg, Throwable t) {
        _log(Level.DEBUG, msg, t);
    }

    @Override
    public boolean isDebugEnabled(Marker marker) {
        throw new IllegalStateException("not implemented");
    }

    @Override
    public void debug(Marker marker, String msg) {
        throw new IllegalStateException("not implemented");
    }

    @Override
    public void debug(Marker marker, String format, Object arg) {
        throw new IllegalStateException("not implemented");
    }

    @Override
    public void debug(Marker marker, String format, Object arg1, Object arg2) {
        throw new IllegalStateException("not implemented");
    }

    @Override
    public void debug(Marker marker, String format, Object... arguments) {
        throw new IllegalStateException("not implemented");
    }

    @Override
    public void debug(Marker marker, String msg, Throwable t) {
        throw new IllegalStateException("not implemented");
    }
    
    //INFO
    @Override
    public boolean isInfoEnabled() {
        return _isLevelEnabled(Level.INFO);
    }

    @Override
    public void info(String msg) {
        _log(Level.INFO, msg);
    }

    @Override
    public void info(String format, Object arg) {
        info(MessageFormat.format(format, arg));
    }

    @Override
    public void info(String format, Object arg1, Object arg2) {
        info(MessageFormat.format(format, arg1, arg2));
    }

    @Override
    public void info(String format, Object... arguments) {
        info(MessageFormat.format(format, arguments));
    }

    @Override
    public void info(String msg, Throwable t) {
        _log(Level.INFO, msg, t);
    }

    @Override
    public boolean isInfoEnabled(Marker marker) {
        throw new IllegalStateException("not implemented");
    }

    @Override
    public void info(Marker marker, String msg) {
        throw new IllegalStateException("not implemented");
    }

    @Override
    public void info(Marker marker, String format, Object arg) {
        throw new IllegalStateException("not implemented");
    }

    @Override
    public void info(Marker marker, String format, Object arg1, Object arg2) {
        throw new IllegalStateException("not implemented");
    }

    @Override
    public void info(Marker marker, String format, Object... arguments) {
        throw new IllegalStateException("not implemented");
    }

    @Override
    public void info(Marker marker, String msg, Throwable t) {
        throw new IllegalStateException("not implemented");
    }
    
    // WARN
    @Override
    public boolean isWarnEnabled() {
        return _isLevelEnabled(Level.WARN);
    }

    @Override
    public void warn(String msg) {
        _log(Level.WARN, msg);
    }

    @Override
    public void warn(String format, Object arg) {
        warn(MessageFormat.format(format, arg));
    }

    @Override
    public void warn(String format, Object arg1, Object arg2) {
        warn(MessageFormat.format(format, arg1, arg2));
    }

    @Override
    public void warn(String format, Object... arguments) {
        warn(MessageFormat.format(format, arguments));
    }

    @Override
    public void warn(String msg, Throwable t) {
        _log(Level.WARN, msg, t);
    }

    @Override
    public boolean isWarnEnabled(Marker marker) {
        throw new IllegalStateException("not implemented");
    }

    @Override
    public void warn(Marker marker, String msg) {
        throw new IllegalStateException("not implemented");
    }

    @Override
    public void warn(Marker marker, String format, Object arg) {
        throw new IllegalStateException("not implemented");
    }

    @Override
    public void warn(Marker marker, String format, Object arg1, Object arg2) {
        throw new IllegalStateException("not implemented");
    }

    @Override
    public void warn(Marker marker, String format, Object... arguments) {
        throw new IllegalStateException("not implemented");
    }

    @Override
    public void warn(Marker marker, String msg, Throwable t) {
        throw new IllegalStateException("not implemented");
    }
    
    // ERROR
    @Override
    public boolean isErrorEnabled() {
        return _isLevelEnabled(Level.ERROR);
    }

    @Override
    public void error(String msg) {
        _log(Level.ERROR, msg);
    }

    @Override
    public void error(String format, Object arg) {
        error(MessageFormat.format(format, arg));
    }

    @Override
    public void error(String format, Object arg1, Object arg2) {
        error(MessageFormat.format(format, arg1, arg2));
    }

    @Override
    public void error(String format, Object... arguments) {
        error(MessageFormat.format(format, arguments));
    }

    @Override
    public void error(String msg, Throwable t) {
        _log(Level.ERROR, msg, t);
    }

    @Override
    public boolean isErrorEnabled(Marker marker) {
        throw new IllegalStateException("not implemented");
    }

    @Override
    public void error(Marker marker, String msg) {
        throw new IllegalStateException("not implemented");
    }

    @Override
    public void error(Marker marker, String format, Object arg) {
        throw new IllegalStateException("not implemented");
    }

    @Override
    public void error(Marker marker, String format, Object arg1, Object arg2) {
        throw new IllegalStateException("not implemented");
    }

    @Override
    public void error(Marker marker, String format, Object... arguments) {
        throw new IllegalStateException("not implemented");
    }

    @Override
    public void error(Marker marker, String msg, Throwable t) {
        throw new IllegalStateException("not implemented");
    }

}
