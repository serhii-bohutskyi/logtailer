package com.bohutskyi.logtailer.exception;

/**
 * @author Serhii Bohutskyi
 */
public class SshClientException extends RuntimeException {
    public SshClientException(String message) {
        super(message);
    }

    public SshClientException(String message, Throwable cause) {
        super(message, cause);
    }
}
