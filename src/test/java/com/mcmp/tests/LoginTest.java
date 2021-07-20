package com.mcmp.tests;


import com.automacent.fwk.annotations.Steps;
import com.automacent.fwk.utils.ThreadUtils;
import com.pom.steps.home.HomePageSteps;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import com.pom.steps.login.LoginSteps;
import org.testng.Assert;
import org.testng.annotations.*;


public class LoginTest extends BaseTest {

    @Steps
    private LoginSteps loginSteps;

    @Steps
    private HomePageSteps homePageSteps;


    @BeforeClass(alwaysRun = true)
    @Parameters({"Username", "Password"})
    public void login(String Username, String Password) {
        loginSteps.confirmLoginPageIsLoaded();
        loginSteps.performLogin(Username, Password);
        loginSteps.checkForLoginErrors();
        loginSteps.acceptPrivacy();
        homePageSteps.confirmHomePageIsLoaded();
    }


    @Test(priority = 0, description = "Login user case with username and password.", testName = "loginTest")
    @Severity(SeverityLevel.BLOCKER)
    @Parameters({"Username", "Password"})
    //@Repeat
    public void loginTest(String Username, String Password) {
        homePageSteps.confirmHomePageIsLoaded();
        Assert.assertTrue(true);
    }

    //We can invoke few cleaning up methods and activities here
    @AfterClass(alwaysRun = true)
    public void logout() {
        ThreadUtils.sleepFor(2);
        homePageSteps.navigateToHomePage();
        homePageSteps.performLogOut();
    }

}
