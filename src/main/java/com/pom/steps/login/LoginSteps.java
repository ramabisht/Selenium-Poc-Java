package com.pom.steps.login;

import java.util.Date;

import com.automacent.fwk.annotations.Steps;
import com.automacent.fwk.core.WebTestSteps;
import com.pom.steps.AbstractHomeSteps;
import com.pom.utils.LoadData;
import org.testng.Assert;
import com.automacent.fwk.annotations.Pages;
import com.automacent.fwk.annotations.Step;
import com.automacent.fwk.core.BaseTest;
import com.automacent.fwk.reporting.Logger;
import com.automacent.fwk.utils.ThreadUtils;
import com.pom.pages.login.LoginPageView;

public final class LoginSteps extends AbstractHomeSteps {

    private static Logger _logger = Logger.getLogger(LoginSteps.class);

    // Pages ----------------------------------------------

    @Pages
    private LoginPageView loginView;

    // Steps ----------------------------------------------

    @Steps
    private LoadData loadData;

    @Step
    public void confirmLoginPageIsLoaded() {
        loginView.pageValidation().validate();
    }

    @Step
    private void validateUserNameCredentials(String username) {
        Assert.assertEquals(loginView.getUserName(), username);
    }

    @Step
    private void validatePasswordCredentials(String password) {
        Assert.assertEquals(loginView.getPassword(), password);
    }

    @Step
    private void clickContinue() {
        Assert.assertTrue(loginView.clickContinueButton(), "Continue clicked");
    }

    @Step
    private void clickLoginButton() {
        Assert.assertTrue(loginView.clickLoginButton(), "LogIn button clicked");
    }


    @Step
    public void checkForLoginErrors() {
        long startTime = new Date().getTime();
        long timeoutInMillis = BaseTest.getTestObject().getTimeoutInSeconds() * 1000l;
      //  do {
            String loginErrorMessage = loginView.getLoginErrorMessage();
            Assert.assertTrue(loginErrorMessage.equals(""), String.format("Login Error message found - %s", loginErrorMessage));
       //     if (new Date().getTime() - startTime > timeoutInMillis) {
       //         Assert.fail(String.format("Login did not complete in %s seconds", timeoutInMillis / 1000));
       //     }
            //ThreadUtils.sleepFor(5);
       // } while (loginView.isLoginFormFound());
       // _logger.info(String.format("Login took %s seconds", (new Date().getTime() - startTime) / 1000l));
    }

    @Step
    public void performLogin(String username, String password) {
        // loadData.loadApplicationUrl();
        loginView.enterUserName(username);
        validateUserNameCredentials(username);
        clickContinue();
        loginView.enterPassword(password);
        validatePasswordCredentials(password);
        clickLoginButton();
    }

    @Step
    public void performLoginByPassword(String password) {
        loginView.enterPassword(password);
        //validateEnteredCredentials(password);
        loginView.clickLoginButton();
    }

    @Step
    public void acceptPrivacy() {
        Assert.assertTrue(loginView.acceptPrivacy(), "Privacy accepted");
        ThreadUtils.sleepFor(5);
    }
}
