package com.mcmp.tests;


import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;

import com.automacent.fwk.core.BaseTestSelenium;
import com.automacent.fwk.reporting.Logger;
import com.automacent.fwk.utils.WebUtils;

public abstract class BaseTest extends BaseTestSelenium {

    private static Logger _logger = Logger.getLogger(BaseTest.class);

    @BeforeClass
    public void setupBrowserAndLoadUrl() {
        startBrowser();
        getActiveDriver().get(getTestObject().getBaseUrl());
        WebUtils.handleCertificateError(getActiveDriver());
        WebUtils.handleCertificateError(getActiveDriver());
    }


    @AfterClass(alwaysRun = true)
    public void quitBrowser() {
        try {
            super.quitBrowser();
        } catch (Exception e) {
            _logger.error("quit browser failed, (log & proceed) : ", e);
        }
    }
}

