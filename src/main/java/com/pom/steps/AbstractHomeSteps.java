package com.pom.steps;


import com.autoui.fwk.annotations.Pages;
import com.autoui.fwk.core.WebTestSteps;
import com.autoui.fwk.reporting.Logger;
import com.pom.pages.home.*;

/*
 * Common functionality for validate page url and title and many more
 */

public class AbstractHomeSteps extends WebTestSteps {

    private static final Logger _logger = Logger.getLogger(AbstractHomeSteps.class);

    @Pages
    protected HomePageView homePageView;

    public void refreshPageUsingRefreshButton() {
        //homePage.header().clickRefreshButton();
    }

}
