package com.autoui.fwk.exceptions;

import com.autoui.fwk.annotations.Action;

/**
 * Exception while executing {@link Action} methods. This exception will help in
 * reporting test failures
 *
 * @author rama.bisht
 */
public class ActionExecutionException extends RuntimeException {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    private String methodName;

    public ActionExecutionException(String methodName, Throwable cause) {
        super(String.format("[@Action %s] %s", methodName, cause.getMessage()), cause);
        this.methodName = methodName;
    }

    /**
     * Get the method name where the failure occurred
     *
     * @return Method name
     */
    public String getMethodName() {
        return methodName;
    }
}
