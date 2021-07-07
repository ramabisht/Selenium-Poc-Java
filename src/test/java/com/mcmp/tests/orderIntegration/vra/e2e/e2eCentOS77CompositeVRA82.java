package com.mcmp.tests.orderIntegration.vra.e2e;

import com.automacent.fwk.annotations.Repeat;
import com.automacent.fwk.annotations.Steps;
import com.mcmp.tests.LoginTest;
import com.pom.steps.home.HomePageSteps;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import org.testng.annotations.*;

public class e2eCentOS77CompositeVRA82 extends LoginTest {

    @Steps
    private HomePageSteps homePageSteps;

    @BeforeClass
    public void verifyLaunchpadPage(){
        homePageSteps.verifyHamburgerButton();
        homePageSteps.clickOnLeftNavigation();
        homePageSteps.clickOnNavigationPage();
    }

    @Test (priority = 0, description="Verify fields on Main Parameters page.", testName = "VRA82: CentOS77 - Verify Composite-Main Parameters page")
    @Severity(SeverityLevel.CRITICAL)
    @Repeat
    public void verifyMainParameters(){
    }

    @Test (priority = 0, description="Verify Summary details and Additional Details are listed in review Order page.", testName = "VRA82: CentOS77 - Composite- Verify Additional Details are listed in review Order page")
    @Severity(SeverityLevel.NORMAL)
    @Repeat
    public void verifyAdditionalParameters(){
    }

    @AfterClass
    public void logoutFromApplication(){

    }
}
