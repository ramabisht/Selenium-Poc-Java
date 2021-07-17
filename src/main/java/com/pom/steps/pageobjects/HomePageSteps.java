package com.pom.steps.pageobjects;

import com.automacent.fwk.annotations.Pages;
import com.automacent.fwk.annotations.Step;
import com.automacent.fwk.core.WebTestSteps;
import com.automacent.fwk.reporting.Logger;
import com.pom.pages.AbstractHomePage;
import com.pom.pages.pageobjects.HomePage;
import org.testng.Assert;

public class HomePageSteps extends WebTestSteps {

    // XPath Constants
    String ExpectedURL = "https://mcmp-dev2fra-release-autoui.multicloud-ibm.com/launchpad";
    String ExpectedPageTitle = "IBM Services for Multicloud Management";

    private static Logger _logger = Logger.getLogger(HomePageSteps.class);

    // Pages ----------------------------------------------

    @Pages
    private AbstractHomePage abstractHomePageView;

    @Pages
    private HomePage homePageView;


    // Steps ----------------------------------------------
    @Step
    public void confirmHomePageIsLoaded() {
        homePageView.pageValidation().validate();
    }

    @Step
    public void confirmPageUrl() {
        Assert.assertEquals(abstractHomePageView.validatePageUrl(), ExpectedURL, "Actual page Url is same as expected");
    }

    @Step
    public void confirmPageTitle() {
        Assert.assertEquals(abstractHomePageView.validatePageTitle(), ExpectedPageTitle, "Actual page title is same as expected");
    }

    @Step
    public void validateHamburgerButton() {
        Assert.assertEquals(homePageView.isHamburgerButtonFound(), true);
    }

    @Step
    public void verifyHamburgerButton() {
        confirmPageUrl();
        confirmPageTitle();
        validateHamburgerButton();

    }

    @Step
    public void clickHamburgerButton() {
        homePageView.clickHamburgerButton();
    }

    @Step
    public void clickOnLeftNavigation() {
        homePageView.selectLeftNavigation();
        homePageView.clickLeftNavigation();
    }

    @Step
    public void clickOnNavigationPage() {
        homePageView.selectNavigationPage();
        homePageView.clickLeftNavigationPage();
    }
}
