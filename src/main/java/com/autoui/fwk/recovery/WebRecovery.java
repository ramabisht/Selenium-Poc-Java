package com.autoui.fwk.recovery;

import org.openqa.selenium.WebDriver;

import com.autoui.fwk.core.BaseTest;
import com.autoui.fwk.exceptions.RecoveryFailedException;
import com.autoui.fwk.reporting.Logger;

/**
 * All the classes intending to implement recovery scenarios/steps must inherit
 * this class and override the {@link #recover()} method.
 *
 * @author rama.bisht
 */
public abstract class WebRecovery {

    protected static final Logger _logger = Logger.getLogger(WebRecovery.class);

    protected WebDriver driver;

    public WebRecovery() {
        driver = BaseTest.getTestObject().getDriverManager().getActiveDriver().getWebDriver();
    }

    /**
     * Check if driver is active to confirm that the recovery steps can be executed.
     */
    public final void checkRecoveryParameters() {
        if (driver == null) {
            throw new RecoveryFailedException("Driver is null");
        }
    }

    /**
     * Calls to recovery steps goes here.
     */
    public void recover() {

    }
}
