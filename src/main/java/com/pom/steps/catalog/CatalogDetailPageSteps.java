package com.pom.steps.catalog;

import com.automacent.fwk.annotations.Pages;
import com.automacent.fwk.annotations.Step;
import com.pom.pages.catalog.CatalogDetailPage;
import org.testng.Assert;

public class CatalogDetailPageSteps {

    @Pages
    private CatalogDetailPage catalogDetailPage;

    @Step
    public void confirmPageTitle(String bluePrintName) {
        Assert.assertTrue(catalogDetailPage.verifyServiceNamePresent(bluePrintName), "Actual page heading is same as expected");
    }

    @Step
    public void verifyConfigureButtonIsVisible() {
        Assert.assertTrue(catalogDetailPage.verifyConfigureButtonVisible());
    }

    @Step
    public void clickOnConfigurationButton() {
        catalogDetailPage.clickOnConfigureService();
    }

    @Step
    public void configureService(String bluePrintName) {
        confirmPageTitle(bluePrintName);
        verifyConfigureButtonIsVisible();
        clickOnConfigurationButton();
    }

}
