package com.mcmp.tests.orderIntegration.vra.e2e;

import com.automacent.fwk.annotations.Repeat;
import com.mcmp.tests.LoginTest;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

public class e2eCentOS77CompositeVRA82 extends LoginTest {

    @BeforeTest
    public void verifyMainParameters(){

    }

    @Test (priority = 0, description="Verify fields on Main Parameters page.", testName = "VRA82: CentOS77 - Composite")
    @Severity(SeverityLevel.BLOCKER)
    @Parameters({ "Username", "Password" })
    @Repeat
    public void verifyMainParameters(String Username, String Password){
    }

    @Test (priority = 0, description="Verify fields on Main Parameters page.", testName = "VRA82: CentOS77 - Composite")
    @Severity(SeverityLevel.BLOCKER)
    @Parameters({ "Username", "Password" })
    @Repeat
    public void verifyAdditionalParameters(String Username, String Password){
    }
}
