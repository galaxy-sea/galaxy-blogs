package com.galaxy.result;

public class ErrorResult extends Exception{
    public ErrorResult() {
        super();
    }

    public ErrorResult(String message) {
        super(message);
    }

    public ErrorResult(String message, Throwable cause) {
        super(message, cause);
    }

    public ErrorResult(Throwable cause) {
        super(cause);
    }

    protected ErrorResult(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
