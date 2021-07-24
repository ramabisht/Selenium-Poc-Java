package com.pom.steps.catalog;

import com.autoui.fwk.annotations.Pages;
import com.autoui.fwk.annotations.Step;
import com.autoui.fwk.reporting.Logger;
import com.pom.pages.AbstractHomePageView;
import com.pom.pages.catalog.CatalogPageView;
import com.pom.steps.AbstractHomeSteps;
import org.testng.Assert;

public class CatalogPageSteps extends AbstractHomeSteps {

    String CategoryList = "All Categories";
    //private static final String MCMP_IFRAME = "mcmp-iframe";


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

    @Step
    private void switchToMCMPIFrame() {
        Assert.assertTrue(catalogPageView.switchToCatalogIFrame(), "Switch to catalog iFrame completed");
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
        Assert.assertTrue(catalogPageView.verifyProviderPresent(providerName), "Provider Clicked :" + providerName);
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
        _logger.info("Category selected as:" + category);
    }

    @Step
    public void selectProvider(String providerName) {
        _logger.info("Selecting provider name as:" + providerName);
        verifyProviderListDisplayed();
        clickOnProviderAccount(providerName);
        _logger.info("Provider name selected as:" + providerName);
    }

    @Step
    public void selectServiceTemplate(String bluePrintName) {
        _logger.info("Selecting service template as:" + bluePrintName);
        verifyServicePresentAndClick(bluePrintName);
        _logger.info("Selected service template as :" + bluePrintName);
    }


    @Step
    public void switchToDefaultContent() {
        switchToDefaultContent();
    }
}
