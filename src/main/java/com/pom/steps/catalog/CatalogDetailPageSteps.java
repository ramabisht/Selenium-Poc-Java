package com.pom.steps.catalog;

import com.automacent.fwk.annotations.Pages;
import com.automacent.fwk.annotations.Step;
import com.automacent.fwk.reporting.Logger;
import com.pom.pages.catalog.CatalogDetailPageView;
import org.testng.Assert;

public class CatalogDetailPageSteps {

    private static Logger _logger = Logger.getLogger(CatalogDetailPageSteps.class);

    @Pages
    private CatalogDetailPageView catalogDetailPageView;

    @Step
    public void confirmPageTitle(String bluePrintName) {
        Assert.assertTrue(catalogDetailPageView.verifyServiceNamePresent(bluePrintName), "Actual page heading is same as expected");
    }

    @Step
    public void verifyConfigureButtonIsVisible() {
        Assert.assertTrue(catalogDetailPageView.verifyConfigureButtonVisible());
    }

    @Step
    public void clickOnConfigurationButton() {
        catalogDetailPageView.clickOnConfigureService();
    }

    @Step
    public void configureService(String bluePrintName) {
        confirmPageTitle(bluePrintName);
        verifyConfigureButtonIsVisible();
        clickOnConfigurationButton();
    }

}
