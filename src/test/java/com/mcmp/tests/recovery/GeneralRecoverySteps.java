package com.mcmp.tests.recovery;

import com.autoui.fwk.annotations.Steps;
import com.autoui.fwk.recovery.WebRecovery;
import com.pom.steps.home.HomePageSteps;

public class GeneralRecoverySteps extends WebRecovery {

    @Steps
    private HomePageSteps homePageSteps;

    @Override
    public void recover(){
        homePageSteps.navigateToHomePage();
    }

}
