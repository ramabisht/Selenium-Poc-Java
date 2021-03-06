package com.autoui.fwk.enums;

/**
 * Enum describing the Repeat Mode. Based on this, the framework determines
 * whether to repeat a test or not and the mode of repetition. This can be set
 * in the test (TestNG xml file) as a parameter so that the MODE will be set for
 * the whole SUITE or for a particular test instance
 *
 * @author rama.bisht
 */
public enum RepeatMode {
    OFF, TEST_DURATION, INVOCATION_COUNT;

    public static RepeatMode getDefault() {
        return OFF;
    }

}
