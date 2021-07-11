package com.pom.steps.home;

import com.automacent.fwk.annotations.Pages;
import com.automacent.fwk.annotations.Step;
import com.automacent.fwk.reporting.Logger;
import com.pom.pages.AbstractHomePage;
import com.pom.pages.home.CatalogPage;
import com.pom.steps.login.LoginSteps;
import org.testng.Assert;

public class CatalogPageSteps {

    String ExpectedURL = "https://mcmp-stagedal-master-autoui.multicloud-ibm.com/lite/consume/storeFront";
    String ExpectedPageTitle = "IBM Services for Multicloud Management";
    String CategoryList = "All Categories" ;
    private static Logger _logger = Logger.getLogger(LoginSteps.class);

    @Pages
    private AbstractHomePage abstractHomePageView;
    @Pages
    private CatalogPage catalogPageView;


    // Steps ----------------------------------------------
    @Step
    public void confirmHomePageIsLoaded() {
        catalogPageView.pageValidation().validate();
    }

    @Step
    public void confirmPageUrl() {
        Assert.assertEquals(abstractHomePageView.validatePageUrl(), ExpectedURL, "Actual page Url is same as expected");
    }

    @Step
    public void confirmPageTitle(){
        Assert.assertEquals(abstractHomePageView.validatePageTitle(), ExpectedPageTitle, "Actual page title is same as expected");
    }

    @Step
    public void verifyCategoryIndexIsPresent(String CategoryList){
        catalogPageView.switchIframe();
        Assert.assertTrue(catalogPageView.isCategoryListPresent(CategoryList), "Verify  All Categories is present on Catalog page");
    }

    @Step
    public void verifyCategoryListPresent(){
       Assert.assertTrue(catalogPageView.verifyCategoriesLoaded(),  "Verify Catory is loaded on th page");
    }

    @Step
    public void verifyAndClickOnCategory(String category){
        catalogPageView.clickOnCategory(category); // fill the value in page
    }

    @Step
    public void verifyProviderListDisplayed(){
         Assert.assertTrue(catalogPageView.isProviderListDisplayed(), "Is provider list is displayed and enabled");
    }

    @Step
    public void clickOnTheProviderAccount(String providerName){
        catalogPageView.clickOnTheProvider(providerName);
    }

    @Step
    public void verifyServicePresentAndClick(String bluePrintName){
        catalogPageView.clickOnService(bluePrintName);
    }

    @Step
    public void selectCategory(String category){
        _logger.info("Tets started at catalog page");
        confirmPageUrl();
        confirmPageTitle();
        verifyCategoryIndexIsPresent(CategoryList);
        verifyCategoryListPresent();
        verifyAndClickOnCategory(category);
    }

    @Step
    public void selectProvider(String providerName){
        verifyProviderListDisplayed();
        clickOnTheProviderAccount(providerName);
    }

    @Step
    public void selectServiceTemplet(String bluePrintName){
        verifyServicePresentAndClick(bluePrintName);
    }
}
