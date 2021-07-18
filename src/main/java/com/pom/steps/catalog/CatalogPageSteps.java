package com.pom.steps.catalog;

import com.automacent.fwk.annotations.Pages;
import com.automacent.fwk.annotations.Step;
import com.automacent.fwk.reporting.Logger;
import com.pom.pages.AbstractHomePageView;
import com.pom.pages.catalog.CatalogPageView;
import com.pom.steps.AbstractHomeSteps;
import com.pom.steps.login.LoginSteps;
import org.testng.Assert;

public class CatalogPageSteps extends AbstractHomeSteps  {

    /*
    String ExpectedURL = "https://mcmp-dev2fra-release-autoui.multicloud-ibm.com/lite/consume/storeFront";
    String ExpectedPageTitle = "IBM Services for Multicloud Management";
     */

    String CategoryList = "All Categories";
    private static final String MCMP_IFRAME = "mcmp-iframe";


    private static Logger _logger = Logger.getLogger(CatalogPageSteps.class);

    //-------- Pages --------------------------------
    @Pages
    private AbstractHomePageView abstractHomePageView;

    @Pages
    private CatalogPageView catalogPageView;


    // Steps ----------------------------------------------

    @Step
    public void confirmCatalogPageIsLoaded() {
        catalogPageView.pageValidation().validate();
    }

    /*
    @Step
    public void confirmPageUrl() {
        Assert.assertEquals(abstractHomePageView.validatePageUrl(), ExpectedURL, "Actual page Url is same as expected");
    }

    @Step
    public void confirmPageTitle() {
        Assert.assertEquals(abstractHomePageView.validatePageTitle(), ExpectedPageTitle, "Actual page title is same as expected");
    }*/


    @Step
    private void switchToMCMPIFrame(){
        switchToIFrame(MCMP_IFRAME);
    }


    @Step
    private void verifyCategoryIndexIsPresent(String CategoryList) {
        Assert.assertTrue(catalogPageView.isCategoryListPresent(CategoryList), "Verify  All Categories is present on Catalog page");
    }

    @Step
    private void verifyCategoryListPresent() {
        Assert.assertTrue(catalogPageView.verifyCategoriesLoaded(), "Verify Category is loaded on th page");
    }

    @Step
    private void verifyAndClickOnCategory(String category) {
        Assert.assertTrue(catalogPageView.clickOnCategory(category), "Category clicked :" + category);
    }

    @Step
    private void verifyProviderListDisplayed() {
        Assert.assertTrue(catalogPageView.isProviderListDisplayed(), "Is provider list is displayed and enabled");
    }

    @Step
    private void clickOnProviderAccount(String providerName) {
        Assert.assertTrue(catalogPageView.clickOnProvider(providerName), "Provider Clicked :" + providerName);
    }

    @Step
    private void verifyServicePresentAndClick(String bluePrintName) {
        Assert.assertTrue(catalogPageView.clickOnService(bluePrintName), "Service clicked :" + bluePrintName);
    }

    //---------------- Steps--------------------------------------
    @Step
    public void selectCategory(String category) {
        _logger.info("Selecting category as:" + category);
        switchToMCMPIFrame();
        verifyCategoryIndexIsPresent(CategoryList);
        verifyCategoryListPresent();
        verifyAndClickOnCategory(category);
        switchToDefaultContent();
        _logger.info("Category selected as:" + category);
    }

    @Step
    public void selectProvider(String providerName) {
        _logger.info("Selecting provider name as:" + providerName);
        switchToMCMPIFrame();
        verifyProviderListDisplayed();
        clickOnProviderAccount(providerName);
        switchToDefaultContent();
        _logger.info("Provider name selected as:" + providerName);
    }

    @Step
    public void selectServiceTemplate(String bluePrintName) {
        _logger.info("Selecting service template as:" + bluePrintName);
        switchToMCMPIFrame();
        verifyServicePresentAndClick(bluePrintName);
        switchToDefaultContent();
        _logger.info("Selected service template as :" + bluePrintName);
    }
}
