package me.gking2224.model.service;

import java.io.PrintWriter;
import java.io.StringWriter;

public class ModelExecutionError {
    int code;
    private String message;
    private String exceptionClass;
    private String stackTace;

    public ModelExecutionError(int code, String message, String exceptionClass, String stackTrace) {
        super();
        this.code = code;
        this.message = message;
        this.exceptionClass = exceptionClass;
        this.stackTace = stackTrace;
    }

    public static ModelExecutionError fromThrowable(Throwable t, int code) {

        String message = t.getMessage();
        String exceptionClass = t.getClass().getName();

        StringWriter w = new StringWriter();
        PrintWriter p = new PrintWriter(w, false);
        t.printStackTrace(p);
        return new ModelExecutionError(code, message, exceptionClass, w.toString());
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getExceptionClass() {
        return exceptionClass;
    }

    public void setExceptionClass(String exceptionClass) {
        this.exceptionClass = exceptionClass;
    }

    public String getStackTace() {
        return stackTace;
    }

    public void setStackTace(String stackTace) {
        this.stackTace = stackTace;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

}
