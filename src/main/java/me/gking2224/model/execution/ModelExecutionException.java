package me.gking2224.model.execution;

import org.springframework.core.NestedRuntimeException;

public class ModelExecutionException extends NestedRuntimeException {
    
    public static final int DEFAULT_CODE = 0;
    private int code;

    public ModelExecutionException(int code, String msg, Throwable cause) {
        super(msg, cause);
        this.code = code;
    }

    public ModelExecutionException(String msg) {
        this(DEFAULT_CODE, msg, null);
    }

    public ModelExecutionException(int code, String msg) {
        this(code, msg, null);
    }

    public ModelExecutionException(Throwable cause) {
        this(DEFAULT_CODE, cause.getMessage(), cause);
    }

    public ModelExecutionException(String msg, Throwable cause) {
        this(DEFAULT_CODE, msg, cause);
    }

    public ModelExecutionException(int code, Throwable cause) {
        this(DEFAULT_CODE, cause.getMessage(), cause);
    }

    /**
     * 
     */
    private static final long serialVersionUID = -2489769322096105401L;

    public int getCode() {
        return code;
    }
    
}