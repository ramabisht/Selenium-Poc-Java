package com.pom.pages.pageobjects;

import com.automacent.fwk.annotations.Action;
import com.automacent.fwk.annotations.Step;
import com.automacent.fwk.core.PageObject;
import com.pom.pages.orderdetails.FillOrderDetailsPages;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.testng.Assert;

public class PlaceOrderPage extends PageObject {

    private static final String MAINPARAMETERPAGEXPATH = ".//*[contains(text(),'Main Parameters')]";
    private static final String ADDITIONALPARAMETERPAGEXPATH = ".//*[contains(text(),'Additional Parameters')]";
    private static final String REVIEWORDERPARAMETERPAGEXPATH = ".//*[contains(text(),'Review Order')]";
    private static final String NEXTBUTTONXPATH = "//button[contains(@id,'next-button')]";
    private static final String PREVIOUSBUTTONXPATH = "//button[contains(@id,'previous')]";
    private static final com.automacent.fwk.reporting.Logger _logger = com.automacent.fwk.reporting.Logger.getLogger(FillOrderDetailsPages.class);

    @Override
    public PageValidation pageValidation() {
        return new PageValidation() {
            @Override
            public void validate() {
                validatePlaceOrderPageLoaded();
            }

            @Step
            public void validatePlaceOrderPageLoaded() {
                Assert.assertTrue(verifyLandedToMainParaPage(), "UserName Field on Login Page is loaded");
            }
        };
    }

    @FindBy(xpath = MAINPARAMETERPAGEXPATH)
    private WebElement mainParamPage;

    @Action
    public boolean verifyLandedToMainParaPage() {
        return mainParamPage.isEnabled();
    }

    @FindBy(xpath = ADDITIONALPARAMETERPAGEXPATH)
    private WebElement additionalParamPage;

    @Action
    public boolean verifyLandedToAdditionalParaPage() {
        return additionalParamPage.isEnabled();
    }

    @FindBy(xpath = REVIEWORDERPARAMETERPAGEXPATH)
    private WebElement reviewOrderPage;

    @Action
    public boolean verifyLandedToReviewOrderPage() {
        return reviewOrderPage.isEnabled();
    }

    @FindBy(xpath = NEXTBUTTONXPATH)
    private WebElement nextButton;

    @Action
    public boolean isNextButtonEnabled() {
        return nextButton.isEnabled();
    }

    @Action
    public void clickOnNextButton() {
        _logger.info("Click on the next button");
        nextButton.click();
    }

    @FindBy(xpath = PREVIOUSBUTTONXPATH)
    private WebElement previousButton;

    @Action
    public boolean isPreviousButtonEnabled() {
        return previousButton.isEnabled();
    }

    @Action
    public void clickOnPreviousButton() {
        _logger.info("Click on the previous button");
        previousButton.click();
    }

}
