package com.autoui.fwk.exceptions;

/**
 * Exception on acquiring token for rest client from rest service.
 *
 * @author rama.bisht
 */
public class RestClientTokenException extends RuntimeException {
    /**
     *
     */
    private static final long serialVersionUID = 1L;

    public RestClientTokenException(String message, Throwable cause) {
        super(message, cause);
    }

    public RestClientTokenException(String message) {
        super(message);
    }
}
