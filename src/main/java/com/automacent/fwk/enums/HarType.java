package com.automacent.fwk.enums;

public enum HarType  {
    ON_FAILURE, AFTER_TEST, BEFORE_TEST, AFTER_SUITE, BEFORE_SUITE, NOT_ENABLED;

    public static HarType getDefault() {
        return ON_FAILURE;
    }
}
