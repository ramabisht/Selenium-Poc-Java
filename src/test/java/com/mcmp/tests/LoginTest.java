package com.mcmp.tests;


import com.automacent.fwk.annotations.Repeat;
import com.automacent.fwk.annotations.Steps;
import com.pom.steps.home.HomePageSteps;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import org.testng.annotations.*;
import com.pom.steps.login.LoginSteps;

public class LoginTest extends BaseTest {

    @Steps
    private LoginSteps loginSteps;

    @Steps
    private HomePageSteps homePageSteps;

    @BeforeClass(alwaysRun = true)
    @Parameters({ "Username", "Password" })
    public void login(String Username, String Password) {
        loginSteps.confirmLoginPageIsLoaded();
        loginSteps.performLogin(Username, Password);
        loginSteps.checkForLoginErrors();
        loginSteps.acceptPrivacy();
        homePageSteps.confirmHomePageIsLoaded();
    }

    @Test (priority = 0, description="Login user case with username and password.", testName = "loginTest")
    @Severity(SeverityLevel.BLOCKER)
    @Parameters({ "Username", "Password" })
    //@Repeat
    public void loginTest(String Username, String Password){
        System.out.println("login invokes");
    }

    //We can invoke few cleaning up methods and activities here
    @AfterMethod(alwaysRun = true)
    public void logout() {
        //loginSteps.performsLogout();
    }

}
