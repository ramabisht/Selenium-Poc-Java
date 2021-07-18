package com.pom.pages.login;

import com.automacent.fwk.annotations.Action;
import com.automacent.fwk.annotations.Step;
import com.automacent.fwk.core.BaseTest;
import com.automacent.fwk.core.PageObject;
import com.automacent.fwk.reporting.Logger;
import com.pom.utils.LoadData;
import org.checkerframework.checker.units.qual.A;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.testng.Assert;

public final class LoginPageView extends PageObject {


    private static final Logger _logger = Logger.getLogger(LoginPageView.class);

    @Override
    public PageValidation pageValidation() {
        return new PageValidation() {

            @Override
            public void validate() {
                validateLoginPageIsLoaded();
            }

            @Step
            public void validateLoginPageIsLoaded() {
                LoadData loadData = new LoadData();
                Assert.assertEquals(driver.getTitle(), loadData.getParamValue(loadData.loadApplicationTitle(), "loginTitle"), "Page title validation failed");
                Assert.assertTrue(isLoginFormFound(), "Login Form is loaded");
                Assert.assertTrue(isUserNameFieldFound(), "UserName Field on Login Page is loaded");
                //Assert.assertTrue(isContinueButtonFound(), "Continue Button on Login Page is loaded");

                //Commenting this out as Login Url looks to be dynamically generated for SSO login
                /*
                Assert.assertEquals(driver.getCurrentUrl(), BaseTest.getTestObject().getBaseUrl() +
                        loadData.getParamValue(loadData.loadApplicationUrl(), "launchpadUrl"), "Page title validation failed");
                 */
            }
        };
    }

    // Actions--------------------------------------------------------------

    @FindBy(xpath = ".//div[normalize-space(.)='Log in to IBM']")
    private WebElement loginForm;

    @Action
    public boolean isLoginFormFound() {
        return isElementFound(loginForm);
    }

    //Username related methods
    @FindBy(id = "username")
    private WebElement userNameField;

    @Action
    public boolean isUserNameFieldFound() {
        return isElementFound(userNameField) && userNameField.isDisplayed();
    }

    @Action
    public void enterUserName(String username) {
        userNameField.sendKeys(username);
    }

    @Action
    public String getUserName() {
        return userNameField.getAttribute("value");
    }


    //Continue button
    @FindBy(id = "continue-button")
    private WebElement continueButton;

    @Action
    private boolean isContinueButtonFound() {
        return isElementFound(continueButton) && continueButton.isDisplayed();
    }

    @Action
    public boolean clickContinueButton() {
        if (isContinueButtonFound()) {
            continueButton.click();
            return true;
        }
        return false;
    }


    //Password related methods
    @FindBy(id = "password")
    private WebElement passwordField;

    @Action
    public boolean isPasswordFieldFound() {
        return isElementFound(continueButton) && passwordField.isDisplayed();
    }

    @Action
    public String getPassword() {
        return passwordField.getAttribute("value");
    }

    @Action
    public void enterPassword(String password) {
        passwordField.sendKeys(password);
    }

    //Login Button after password
    @FindBy(id = "signinbutton")
    private WebElement loginButton;

    @Action
    private boolean isLoginButtonFound() {
        return isElementFound(loginButton) && loginButton.isDisplayed();
    }

    @Action
    public boolean clickLoginButton() {
        if (isLoginButtonFound()) {
            loginButton.click();
            return true;
        }
        return false;
    }

    //Accept Privacy warning
    @FindBy(xpath = "//button[@id='privacy-accept']")
    private WebElement privacyAccept;

    @Action
    public boolean isPrivacyWarningFound() {
        return isElementFound(privacyAccept) && privacyAccept.isDisplayed();
    }

    @Action
    public boolean acceptPrivacy() {
        if (isPrivacyWarningFound()) {
            privacyAccept.click();
            return true;
        }
        return false;
    }

    //Login Error
    @FindBy(id = "password-error-msg")
    private WebElement loginErrorMessage;

    @Action
    private boolean isLoginErrorFound() {
        return isElementFound(loginErrorMessage, 3);
    }

    @Action
    public String getLoginErrorMessage() {
        if (isLoginErrorFound())
            return loginErrorMessage.getText().trim();

        return "";
    }

}
