package com.pom.pages.login;

import com.automacent.fwk.annotations.Action;
import com.automacent.fwk.annotations.Step;
import com.automacent.fwk.core.PageObject;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.testng.Assert;

public final class LoginPage extends PageObject {


    @Override
    public PageValidation pageValidation() {
        return new PageValidation() {

            @Override
            public void validate() {
                validateLoginPageIsLoaded();
            }

            @Step
            public void validateLoginPageIsLoaded(){
                Assert.assertTrue(isUserNameFieldFound(), "UserName Field on Login Page is loaded");
                Assert.assertTrue(isContinueButtonFound(), "Continue Button on Login Page is loaded");
            }
        };
    }

    // --------------------------------------------------------------

    @FindBy(id = "loginForm")
    private WebElement loginForm;

    public boolean isLoginFormFound() {
        return isElementFound(loginForm,1);
    }


    //Username related methods
    @FindBy(id = "username")
    private WebElement userNameField;

    @Action
    public boolean isUserNameFieldFound(){
        isElementFound(userNameField);
        return userNameField.isDisplayed();
    }

    @Action
    public void enterUserName(String username){
        userNameField.sendKeys(username);
    }

    @Action
    public String getUserName(){
        return userNameField.getAttribute("value");
    }


    //Continue button
    @FindBy(id = "continue-button")
    private WebElement continueButton;

    @Action
    public boolean isContinueButtonFound(){
        isElementFound(continueButton);
        return continueButton.isDisplayed();
    }

    @Action
    public void clickContinueButton(){
        continueButton.click();
    }


    //Password related methods
    @FindBy(id = "password")
    private WebElement passwordField;

    @Action
    public boolean isPasswordFieldFound(){
        return passwordField.isDisplayed();
    }

    @Action
    public String getPassword(){
        return passwordField.getAttribute("value");
    }

    @Action
    public void enterPassword(String password){
        passwordField.sendKeys(password);
    }

    //Login Button after password
    @FindBy(id = "signinbutton")
    private WebElement loginButton;

    @Action
    public boolean isLoginButtonFound(){
        return  loginButton.isDisplayed();
    }

    @Action
    public void clickLoginButton(){
        loginButton.click();
    }

    //Accept Privacy warning
    @FindBy(xpath = "//button[@id='privacy-accept']")
    private WebElement privacyAccept;

    @Action
    public boolean isPrivacyWarningFound(){
        return  privacyAccept.isDisplayed();
    }

    @Action
    public void acceptPrivacy(){
        privacyAccept.click();
    }

    //Login Error
    @FindBy(id = "password-error-msg")
    private WebElement loginErrorMessage;

    @Action
    public boolean isLoginErrorFound(){
        return isElementFound(loginErrorMessage, 5);
    }

    @Action
    public String getLoginErrorMessage(){
        return loginErrorMessage.getText().trim();
    }

}
