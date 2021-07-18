package com.pom.pages.home;

import com.automacent.fwk.annotations.Action;
import com.automacent.fwk.annotations.Step;
import com.automacent.fwk.core.BaseTest;
import com.automacent.fwk.core.PageObject;
import com.automacent.fwk.reporting.Logger;
import com.pom.utils.LoadData;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.testng.Assert;

import java.util.List;

public class HomePageView extends PageObject {

    private static final String HAMBURGER_CSS = "ibm-hamburger button";
    private static final String LEFT_NAVIGATION_XPATH = "//span[@class = 'bx--side-nav__submenu-title']";
    private static final String LEFT_NAVIGATION_LINK_SELECT_XPATH = "//span[@class = 'bx--side-nav__submenu-title";
    private static final String ELEMENT_TO_BE_CLICKED = "//span[@class = 'bx--side-nav__submenu-title'";
    private static final String LEFT_NAVIGATE_PAGE_XPATH = "//span[@class = 'bx--side-nav__link-text']";
    private static final String LEFT_NAVIGATE_PAGE_TO_BE_CLICKED_XPATH = "//a[@class = 'bx--side-nav__link'";

    private static Logger _logger = Logger.getLogger(HomePageView.class);

    @Override
    public PageValidation pageValidation() {
        return new PageValidation() {

            @Override
            public void validate() {
                validateHomePageIsLoaded();
            }

            @Step
            public void validateHomePageIsLoaded() {
                Assert.assertTrue(isHamburgerButtonFound(), "Hamburger button is displayed.");
                LoadData loadData = new LoadData();
                Assert.assertEquals(driver.getTitle(), loadData.getParamValue(loadData.loadApplicationTitle(), "launchpadTitle"), "Page title validation failed");
                Assert.assertEquals(driver.getCurrentUrl(), BaseTest.getTestObject().getBaseUrl() +
                        loadData.getParamValue(loadData.loadApplicationUrl(), "launchpadUrl"), "Page title validation failed");

            }
        };
    }

    // Actions--------------------------------------------------------------

    @FindBy(css = HAMBURGER_CSS)
    private WebElement hamburgerButton;

    @Action
    public boolean isHamburgerButtonFound() {
        return isElementFound(hamburgerButton) && hamburgerButton.isDisplayed();
    }

    @Action
    public boolean clickHamburgerButton() {
        if (isHamburgerButtonFound()) {
            hamburgerButton.click();
            return true;
        }
        return false;
    }

    // --------------------------------------------------------------

    // Click on the left navigation link
    @FindBy(xpath = LEFT_NAVIGATION_XPATH)
    private List<WebElement> leftNavigationLink;

    @Action
    private WebElement selectLeftNavigation(String elementName) {
        WebElement leftNavigationElement = null;
        for (WebElement element : leftNavigationLink) {
            if (element.getText().equals(elementName) && isElementFound(element)) {
                leftNavigationElement = element;
                break;
            }
        }
        Assert.assertNotNull(leftNavigationElement, "Element not found :" + elementName);
        return leftNavigationElement;
    }

    @Action
    public boolean clickMenuItem(String elementName) {
        if (isElementFound(driver.findElement(By.xpath(ELEMENT_TO_BE_CLICKED + " and text() ='" + selectLeftNavigation(elementName).getText() + "']")))) {
            driver.findElement(By.xpath(ELEMENT_TO_BE_CLICKED + " and text() ='" + selectLeftNavigation(elementName).getText() + "']")).click();
            return true;
        }
        return false;
    }

    // --------------------------------------------------------------
    // Click on left navigation required page
    @FindBy(xpath = LEFT_NAVIGATE_PAGE_XPATH)
    private List<WebElement> leftNavigationPage;

    @Action
    private WebElement selectNavigationPage(String elementName) {
        WebElement leftNavigationClickableElement = null;
        for (WebElement element : leftNavigationPage) {
            //CATALOG_PAGE
            if (element.getText().equals(elementName)) {
                leftNavigationClickableElement = element;
            }
        }
        Assert.assertNotNull(leftNavigationClickableElement, "Element not found :" + elementName);
        return leftNavigationClickableElement;
    }

    @Action
    public boolean clickSubMenuItem(String elementName) {
        if (isElementFound(driver.findElement(By.xpath(LEFT_NAVIGATE_PAGE_TO_BE_CLICKED_XPATH + " and @title ='" + selectNavigationPage(elementName).getText() + "']")))) {
            driver.findElement(By.xpath(LEFT_NAVIGATE_PAGE_TO_BE_CLICKED_XPATH + " and @title ='" + selectNavigationPage(elementName).getText() + "']")).click();
            return true;
        }
        return false;
    }
}
