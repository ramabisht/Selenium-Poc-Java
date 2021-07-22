package com.pom.pages;

import com.autoui.fwk.annotations.Action;
import com.autoui.fwk.core.PageObject;
import com.autoui.fwk.reporting.Logger;

public class AbstractHomePageView extends PageObject {

    private static final Logger _logger = Logger.getLogger(AbstractHomePageView.class);

    @Override
    public PageValidation pageValidation() {
        return null;
    }

    @Action
    public String validatePageUrl() {
        return driver.getCurrentUrl();
    }

    @Action
    public String validatePageTitle() {
        return driver.getTitle();
    }
}
