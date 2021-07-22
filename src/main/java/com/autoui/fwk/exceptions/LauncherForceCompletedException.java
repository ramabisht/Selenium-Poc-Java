package com.autoui.fwk.exceptions;

/**
 * Exception thrown when the test exceeds the set test duration
 *
 * @author rama.bisht
 */
public class LauncherForceCompletedException extends RuntimeException {

    /**
     *
     */
    private static final long serialVersionUID = -4174917245245884909L;

    public LauncherForceCompletedException(String message) {
        super(message);
    }

}
