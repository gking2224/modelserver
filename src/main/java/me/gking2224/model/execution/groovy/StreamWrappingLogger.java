package me.gking2224.model.execution.groovy;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;
import java.text.MessageFormat;
import java.util.Date;

import me.gking2224.model.execution.ModelExecutionException;

public class StreamWrappingLogger extends LoggerAdapter {
    
    private Writer err;
    private Writer out;
    private String pattern;

    StreamWrappingLogger(String pattern, String name, Level logLevel, Writer out, Writer err) {
        super(name, logLevel);
        this.out = out;
        this.err = err;
        this.pattern = pattern;
    }

    @Override
    protected void doLog(Level level, String msg) {
        String formatted = formatLogMsg(level, msg);
        try {
            out.write(formatLogMsg(level, msg));
            out.write("\n");
        } catch (IOException e) {
            System.out.println("unable to log, using System.out : "+e.getMessage());
            System.out.println(formatted);
        }
    }

    private String formatLogMsg(Level level, String msg) {
        return MessageFormat.format(this.pattern, this.name, new Date(), level.toString(), msg);
    }

    @Override
    protected void doLog(Level level, String msg, Throwable throwable) {
        String formatted = formatLogMsg(level, msg);
        try {
            out.write(formatted);
            out.write("\n");
            throwable.printStackTrace(new PrintWriter(err));
        } catch (IOException e) {
            System.out.println("unable to log, using System.out : "+e.getMessage());
            System.out.println(formatted);
        }
    }

    Writer getErr() {
        return err;
    }

    Writer getOut() {
        return out;
    }
}