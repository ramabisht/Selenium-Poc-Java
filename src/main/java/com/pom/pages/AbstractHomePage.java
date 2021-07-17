package com.pom.pages;

import com.automacent.fwk.annotations.Action;
import com.automacent.fwk.core.PageObject;

public class AbstractHomePage extends PageObject {


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
