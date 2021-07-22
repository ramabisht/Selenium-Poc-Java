package com.autoui.fwk.enums;

public enum HarType {
    ON_FAILURE, AFTER_TEST, NOT_ENABLED;

    public static HarType getDefault() {
        return ON_FAILURE;
    }
}
