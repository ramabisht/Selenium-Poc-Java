package com.pom.pages.home;

import com.automacent.fwk.annotations.Action;
import com.automacent.fwk.annotations.Step;
import com.automacent.fwk.core.PageObject;
import com.automacent.fwk.reporting.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.testng.Assert;

import java.util.List;

public class HomePageView extends PageObject {

    private static final String HAMBURGERCSS = "ibm-hamburger button";
    private static final String LEFTNAVIGATIONXPATH = "//span[@class = 'bx--side-nav__submenu-title']";
    private static final String LEFTNAVIGATIONLinkSELECTXPATH = "//span[@class = 'bx--side-nav__submenu-title";
    private static final String ELEMENTNAMETOBECLICKED = "Enterprise Marketplace";
    private static final String ELEMENTTOBECLICKED = "//span[@class = 'bx--side-nav__submenu-title'";
    private static final String PAGETOBECLICKED = "Catalog";
    private static final String LEFTNAVIPAGEXPATH = "//span[@class = 'bx--side-nav__link-text']";
    private static final String LEFTNAVIPAFETOBECLICKEDXPATH = "//a[@class = 'bx--side-nav__link'";

    private static Logger _logger = Logger.getLogger(HomePageView.class);

    @Override
    public PageValidation pageValidation() {
        return new PageValidation() {

            @Override
            public void validate() {
                validateHamburgerButton();
            }

            @Step
            public void validateHamburgerButton() {
                Assert.assertTrue(isHamburgerButtonFound(), "Hamburger button is displayed.");
            }
        };
    }

    // --------------------------------------------------------------

    @FindBy(css = HAMBURGERCSS)
    private WebElement hamburgerButton;

    @Action
    public boolean isHamburgerButtonFound() {
        isElementFound(hamburgerButton);
        return hamburgerButton.isDisplayed();
        //return hamburgerButton.isEnabled();
    }

    @Action
    public void clickHamburgerButton() {
        hamburgerButton.click();
    }

    // --------------------------------------------------------------

    // Click on the left navigation link
    @FindBy(xpath = LEFTNAVIGATIONXPATH)
    private List<WebElement> leftNavigationLink;

    @Action
    public WebElement selectLeftNavigation() {
        WebElement leftNavigationElement = null;
        for (WebElement element : leftNavigationLink) {
            if (element.getText().equals(ELEMENTNAMETOBECLICKED)) {
                _logger.info("element found");
                leftNavigationElement = element;
                break;
            }
        }
        return leftNavigationElement;
    }

    @Action
    public void clickLeftNavigation() {
        _logger.info("click on the link:" + ELEMENTNAMETOBECLICKED);
        if (isElementFound(driver.findElement(By.xpath(ELEMENTTOBECLICKED + " and text() ='" + selectLeftNavigation().getText() + "']")))) {
            driver.findElement(By.xpath(ELEMENTTOBECLICKED + " and text() ='" + selectLeftNavigation().getText() + "']")).click();
        }
    }

    // --------------------------------------------------------------
    // Click on left navigation required page
    @FindBy(xpath = LEFTNAVIPAGEXPATH)
    private List<WebElement> leftNavigationPage;

    @Action
    public WebElement selectNavigationPage() {
        WebElement leftNavigationClickableElement = null;
        for (WebElement element : leftNavigationPage) {
            if (element.getText().equals(PAGETOBECLICKED)) {
                leftNavigationClickableElement = element;
            }
        }
        return leftNavigationClickableElement;
    }

    @Action
    public void clickLeftNavigationPage() {
        if (isElementFound(driver.findElement(By.xpath(LEFTNAVIPAFETOBECLICKEDXPATH + " and @title ='" + selectNavigationPage().getText() + "']")))) {
            driver.findElement(By.xpath(LEFTNAVIPAFETOBECLICKEDXPATH + " and @title ='" + selectNavigationPage().getText() + "']")).click();
        }
    }


}
