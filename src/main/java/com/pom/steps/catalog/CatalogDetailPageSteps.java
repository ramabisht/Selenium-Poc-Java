package com.pom.steps.catalog;

import com.automacent.fwk.annotations.Pages;
import com.automacent.fwk.annotations.Step;
import com.automacent.fwk.reporting.Logger;
import com.pom.pages.catalog.CatalogDetailPageView;
import com.pom.steps.AbstractHomeSteps;
import org.testng.Assert;

public class CatalogDetailPageSteps extends AbstractHomeSteps {

    private static Logger _logger = Logger.getLogger(CatalogDetailPageSteps.class);


    //-------- Pages --------------------------------
    @Pages
    private CatalogDetailPageView catalogDetailPageView;


    //--------- Step -------------------------------

    @Step
    public void confirmCatalogDetailsPageIsLoaded() {
        catalogDetailPageView.pageValidation().validate();
    }

    @Step
    private void verifyBluePrintName(String bluePrintName) {
        Assert.assertEquals(catalogDetailPageView.getBluePrintName(), bluePrintName, "Blue print name is loaded as " + bluePrintName);
    }

    @Step
    private void clickOnConfigurationButton() {
        Assert.assertTrue(catalogDetailPageView.clickOnConfigureService(), "Configure button clicked");
    }


    //----------- Steps ------------------------------------
    @Step
    public void configureService(String bluePrintName) {
        verifyBluePrintName(bluePrintName);
        clickOnConfigurationButton();
    }

}
