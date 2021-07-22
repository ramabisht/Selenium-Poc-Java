package com.autoui.fwk.exceptions;

/**
 * Exception thrown on the executing rest client
 *
 * @author rama.bisht
 */
public class RestClientExecutionException extends RuntimeException {
    /**
     *
     */
    private static final long serialVersionUID = 1L;

    public RestClientExecutionException(String message, Throwable cause) {
        super(message, cause);
    }

    public RestClientExecutionException(String message) {
        super(message);
    }
}
