package com.autoui.fwk.enums;

/**
 * Enum describing the Method Type. Based on this, the framework determines the
 * type of method that is being executed and helps in managing the control flow
 * and logging
 *
 * @author rama.bisht
 */
public enum MethodType {
    BEFORE, TEST, AFTER, ACTION, STEP, RECOVERY, ITERATION, SLEEP, RETRY;
}
