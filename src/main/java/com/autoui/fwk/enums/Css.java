package com.autoui.fwk.enums;

/**
 * ENUM describing CSS properties used in Report
 *
 * @author rama.bisht
 */
public enum Css {
    UNDERLINE_SILVER_1PX_SOLID("border-bottom: solid 1px silver;"), UNDERLINE_NONE("border-botton: none;");

    String cssValue;

    private Css(String cssValue) {
        this.cssValue = cssValue;
    }

    public String getCssValue() {
        return cssValue;
    }

}
