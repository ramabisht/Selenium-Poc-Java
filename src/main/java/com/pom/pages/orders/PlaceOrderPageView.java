package com.pom.pages.orders;

import com.automacent.fwk.annotations.Action;
import com.automacent.fwk.annotations.Step;
import com.automacent.fwk.core.BaseTest;
import com.automacent.fwk.core.PageObject;
import com.automacent.fwk.reporting.Logger;
import com.automacent.fwk.utils.ThreadUtils;
import com.pom.utils.LoadData;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.testng.Assert;

public class PlaceOrderPageView extends PageObject {

    private static final String MAIN_PARAMETERS_PAGE_XPATH = ".//*[contains(text(),'Main Parameters')]";
    private static final String ADDITIONAL_PARAMETERS_PAGE_XPATH = ".//*[contains(text(),'Additional Parameters')]";
    private static final String REVIEW_ORDER_PARAMETERS_PAGE_XPATH = ".//*[contains(text(),'Review Order')]";
    private static final String NEXT_BUTTON_XPATH = "//button[contains(@id,'next-button')]";
    private static final String PREVIOUS_BUTTON_XPATH = "//button[contains(@id,'previous')]";
    private static final String SUBMIT_BUTTON_CSS = "button[id$='primary-btn-review-order']";


    private static final Logger _logger = Logger.getLogger(OrderDetailsPageView.class);

    @Override
    public PageValidation pageValidation() {
        return new PageValidation() {
            @Override
            public void validate() {
                validatePlaceOrderPageLoaded();
            }

            @Step
            public void validatePlaceOrderPageLoaded() {
                Assert.assertTrue(verifyLandedToMainParameterPage(), "UserName Field on Login Page is loaded");
                LoadData loadData = new LoadData();
                Assert.assertEquals(driver.getTitle(),  loadData.getParamValue(loadData.loadApplicationTitle(), "launchpadTitle"), "Page title validation failed");
                Assert.assertEquals(driver.getCurrentUrl(), BaseTest.getTestObject().getBaseUrl() +
                        loadData.getParamValue(loadData.loadApplicationUrl(), "launchpadUrl"), "Page title validation failed");
            }
        };
    }

    // Actions--------------------------------------------------------------

    @FindBy(xpath = MAIN_PARAMETERS_PAGE_XPATH)
    private WebElement mainParamPage;

    @Action
    public boolean verifyLandedToMainParameterPage() {
        return isElementFound(mainParamPage) && mainParamPage.isEnabled();
    }

    @FindBy(xpath = ADDITIONAL_PARAMETERS_PAGE_XPATH)
    private WebElement additionalParamPage;

    @Action
    public boolean verifyLandedToAdditionalParameterPage() {
        return isElementFound(additionalParamPage) && additionalParamPage.isEnabled();
    }

    @FindBy(xpath = REVIEW_ORDER_PARAMETERS_PAGE_XPATH)
    private WebElement reviewOrderPage;

    @Action
    public boolean verifyLandedToReviewOrderPage() {
        return isElementFound(additionalParamPage) && reviewOrderPage.isEnabled();
    }

    @FindBy(xpath = NEXT_BUTTON_XPATH)
    private WebElement nextButton;

    @Action
    private boolean isNextButtonEnabled() {
        return isElementFound(additionalParamPage) && nextButton.isEnabled();
    }

    @Action
    public boolean clickOnNextButton() {
        if(isNextButtonEnabled()) {
            nextButton.click();
            return true;
        }
        return false;
    }

    @FindBy(xpath = PREVIOUS_BUTTON_XPATH)
    private WebElement previousButton;

    @Action
    private boolean isPreviousButtonEnabled() {
        return isElementFound(previousButton) && previousButton.isEnabled();
    }

    @Action
    public boolean clickOnPreviousButton() {
        if(isPreviousButtonEnabled()) {
            previousButton.click();
            return true;
        }
        return false;
    }

    @FindBy(xpath = SUBMIT_BUTTON_CSS)
    private WebElement submitButton;

    @Action
    private boolean isSubmitButtonEnabled() {
        ThreadUtils.sleepFor(10);
        return isElementFound(submitButton) && submitButton.isEnabled();
    }

    @Action
    public boolean clickOnSubmitButton() {
        if(isSubmitButtonEnabled()) {
            submitButton.click();
            return true;
        }
        return false;
    }
}
