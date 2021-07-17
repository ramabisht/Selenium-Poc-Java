package com.pom.steps.login;

import java.util.Date;

import com.automacent.fwk.annotations.Steps;
import com.automacent.fwk.core.WebTestSteps;
import com.pom.utils.LoadData;
import org.testng.Assert;
import com.automacent.fwk.annotations.Pages;
import com.automacent.fwk.annotations.Step;
import com.automacent.fwk.core.BaseTest;
import com.automacent.fwk.reporting.Logger;
import com.automacent.fwk.utils.ThreadUtils;
import com.pom.pages.login.LoginPage;

public final class LoginSteps extends WebTestSteps {

    private static Logger _logger = Logger.getLogger(LoginSteps.class);

    // Pages ----------------------------------------------

    @Pages
    private LoginPage loginView;

    // Steps ----------------------------------------------

    @Steps
    private LoadData loadData;

    @Step
    public void confirmLoginPageIsLoaded() {
        loginView.pageValidation().validate();
    }

    @Step
    public void validateUserNameCredentials(String username) {
        Assert.assertEquals(loginView.getUserName(), username);
    }

    @Step
    public void validatePasswordCredentials(String password) {
        Assert.assertEquals(loginView.getPassword(), password);
    }

    @Step
    public void clickContinue() {
        loginView.clickContinueButton();
    }


    @Step
    public void checkForLoginErrors() {
        long startTime = new Date().getTime();
        long timeoutInMillis = BaseTest.getTestObject().getTimeoutInSeconds() * 1000l;
        do {
            if (loginView.isLoginErrorFound()) {
                Assert.fail(String.format("Login Error message found - %s", loginView.getLoginErrorMessage()));
            }
            if (new Date().getTime() - startTime > timeoutInMillis) {
                Assert.fail(String.format("Login did not complete in %s seconds", timeoutInMillis / 1000));
            }
            ThreadUtils.sleepFor(5);
        } while (loginView.isLoginFormFound());
        _logger.info(String.format("Login took %s seconds", (new Date().getTime() - startTime) / 1000l));
    }

    @Step
    public void performLogin(String username, String password) {
        // loadData.loadApplicationUrl();
        loginView.enterUserName(username);
        validateUserNameCredentials(username);
        loginView.clickContinueButton();
        loginView.enterPassword(password);
        validatePasswordCredentials(password);
        loginView.clickLoginButton();
    }

    @Step
    public void performLoginByPassword(String password) {
        loginView.enterPassword(password);
        //validateEnteredCredentials(password);
        loginView.clickLoginButton();
    }

    @Step
    public void acceptPrivacy() {
        if (loginView.isPrivacyWarningFound()) {
            loginView.acceptPrivacy();
        }
    }

    //Use below methods for validating any post login Error on the home page

    /*
    @Step
    public boolean isErrorMessageAlertFound() {
        return homePage.isErrorMessageAlertFound(10);
    }

    @Step
    public void checkForErrorMessageAlertHeader() {
        if (isErrorMessageAlertFound()) {
            Assert.fail(String.format("Error message alert found on header - %s", homePage.getErrorMessageFromAlert()));
        }
    }
*/

    //Need to add code for performing the logout
    /*
    @Step
    public void performsLogout() {
        Assert.assertTrue(homePage.header().userMenu().isUserMenuFound(), "User menu is loaded");
        homePage.header().userMenu().clickUserMenu();
        homePage.header().userMenu().clickLogoutSubMenu();
    }*/
}
