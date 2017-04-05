package com.bohutskyi.logtailer.exception;

/**
 * @author Serhii Bohutskyi
 */
public class LogFileWriterException extends RuntimeException {
    public LogFileWriterException(String message) {
        super(message);
    }

    public LogFileWriterException(String message, Throwable cause) {
        super(message, cause);
    }
}
