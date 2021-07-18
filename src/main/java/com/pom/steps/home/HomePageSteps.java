package com.pom.steps.home;

import com.automacent.fwk.annotations.Pages;
import com.automacent.fwk.annotations.Step;
import com.automacent.fwk.core.WebTestSteps;
import com.automacent.fwk.reporting.Logger;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.pom.pages.AbstractHomePageView;
import com.pom.pages.home.HomePageView;
import com.pom.steps.AbstractHomeSteps;
import org.testng.Assert;

public class HomePageSteps extends AbstractHomeSteps {

    // XPath Constants
    //String ExpectedURL = "https://mcmp-dev2fra-release-autoui.multicloud-ibm.com/launchpad";
    //String ExpectedPageTitle = "IBM Services for Multicloud Management";


    private static Logger _logger = Logger.getLogger(HomePageSteps.class);

    // Pages ----------------------------------------------

    @Pages
    private AbstractHomePageView abstractHomePageView;

    @Pages
    private HomePageView homePageView;


    // Steps ----------------------------------------------

    @Step
    public void confirmHomePageIsLoaded() {
        homePageView.pageValidation().validate();
    }

    // Use this method for navigating to home page from any page
    @Step
    public void navigateToHomePage() {
        Assert.assertTrue(homePageView.navigateToHomePage(), "Navigated to home page successfully.");
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
    public void validateHamburgerButton() {
        Assert.assertEquals(homePageView.isHamburgerButtonFound(), true);
    }

    @Step
    public void verifyHamburgerButton() {
        validateHamburgerButton();
    }

    @Step
    public void clickHamburgerButton() {
        Assert.assertTrue(homePageView.clickHamburgerButton(), "Hamburger clicked.");
    }

    @Step
    public void clickOnMenuItem(String menuItem) {
        Assert.assertTrue(homePageView.clickMenuItem(menuItem), "Selected Menu item: " + menuItem);
    }

    @Step
    public void clickOnSubMenuItem(String subMenuItem) {
        Assert.assertTrue(homePageView.clickSubMenuItem(subMenuItem), "Selected SubMenu Item: " + subMenuItem);
    }

    @Step
    public void openUserMenu() {
        Assert.assertTrue(homePageView.openUserMenu(), "User Menu opened");
    }

    @Step
    public void performLogOut() {
        openUserMenu();
        Assert.assertTrue(homePageView.performLogOut(), "Logout performed....");
    }
}
