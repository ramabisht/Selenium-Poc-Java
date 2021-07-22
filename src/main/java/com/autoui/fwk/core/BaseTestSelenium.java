package com.autoui.fwk.core;

import com.autoui.fwk.enums.*;
import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;

import com.autoui.fwk.exceptions.SetupFailedFatalException;

import io.github.bonigarcia.wdm.DriverManagerType;

import java.io.IOException;

/**
 * All Selenium test classes must inherit this class. Class contains essential
 * setup code for the framework to work
 *
 * @author rama.bisht
 **/

public abstract class BaseTestSelenium extends BaseTest {

    /**
     * Set up parameters required by the framework for managing Selenium drivers.
     * These parameters are set at Test suite level
     *
     * @param ieDriverLocation         Absolute/relative path to the location of IE
     *                                 driver server executable
     * @param chromeDriverLocation     Absolute/relative path to the location of
     *                                 Chrome driver server executable
     * @param geckoDriverLocation      Absolute/relative path to the location of
     *                                 Firefox driver server executable
     * @param scriptTimeoutInSeconds   Timeout for Selenium Javascript Executor
     *                                 script
     * @param pageLoadTimeoutInSeconds Timeout for browser page load
     * @param socketTimeoutInSeconds   Timeout for socket exceptions
     */
    @BeforeSuite
    @Parameters({
            "ieDriverLocation",
            "chromeDriverLocation",
            "geckoDriverLocation",
            "scriptTimeoutInSeconds",
            "pageLoadTimeoutInSeconds",
            "socketTimeoutInSeconds",
            "runInHeadlessMode"
    })
    public void autouiInternalSetDriverParameters(
            String ieDriverLocation,
            String chromeDriverLocation,
            String geckoDriverLocation,
            long scriptTimeoutInSeconds,
            long pageLoadTimeoutInSeconds,
            long socketTimeoutInSeconds,
            boolean runInHeadlessMode) {
        Driver.setupDefaultDriver(ieDriverLocation, chromeDriverLocation, geckoDriverLocation,
                scriptTimeoutInSeconds, pageLoadTimeoutInSeconds, socketTimeoutInSeconds,
                runInHeadlessMode);
    }

    /**
     * Set uo parameters required for Web Test. These parameters are set at test
     * level and custom values per test can be provided
     *
     * @param browser                    Browser name as provided by
     *                                   {@link DriverManagerType}
     * @param screenshotType             {@link ScreenshotType}
     * @param screenshotMode             comma separated {@link ScreenshotMode}
     *                                   values
     * @param screenshotModeForIteration {@link ScreenshotModeForIteration}
     * @param baseUrl                    Base URL of the application
     * @param testContext                testNg {@link ITestContext}
     * @param harType                    {@link HarType}
     */
    @BeforeTest
    @Parameters({
            "browser",
            "debuggerAddress",
            "screenshotType",
            "screenshotMode",
            "screenshotModeForIteration",
            "baseUrl",
            "harCollectionType"
    })
    public void autouiInternalSetWebTestParameters(
            DriverManagerType browser,
            String debuggerAddress,
            ScreenshotType screenshotType,
            String screenshotMode,
            ScreenshotModeForIteration screenshotModeForIteration,
            String baseUrl,
            ITestContext testContext,
            String harType) {
        if (baseUrl.trim().isEmpty())
            throw new SetupFailedFatalException("Please provide baseUrl Parameter, baseUrl, is empty");

        TestObject testObject = getTestObject();
        testObject.setDriverManager(new DriverManager());
        testObject.getDriverManager().setDriverManagerType(browser);
        testObject.setDebuggerAddress(debuggerAddress);
        testObject.setScreenshotType(screenshotType);
        testObject.setScreenshotModes(screenshotMode);
        testObject.setScreenshotModeForIteration(screenshotModeForIteration);
        testObject.setBaseUrl(baseUrl);
        testObject.setHarType(harType);
    }

    /**
     * Start the primary browser with {@link BrowserId} ALPHA and default. The
     * browser, on starting becomes the active browser/driver. Use
     * {@link #setActiveDriver(BrowserId)} to change the active browser/driver
     */
    protected void startBrowser() {
        getTestObject().getDriverManager().startDriverManager(this);
    }

    /**
     * Start new browser with provided {@link BrowserId} and default browser. The
     * browser, on starting becomes the active browser/driver. Use
     * {@link #setActiveDriver(BrowserId)} to change the active browser/driver
     *
     * @param browserId {@link BrowserId}
     */
    protected void startBrowser(BrowserId browserId) {
        getTestObject().getDriverManager().startDriverManager(this, browserId);
    }

    /**
     * Start new browser with provided {@link BrowserId} and provided browser. The
     * browser, on starting becomes the active browser/driver. Use
     * {@link #setActiveDriver(BrowserId)} to change the active browser/driver
     *
     * @param browserId         {@link BrowserId}
     * @param driverManagerType {@link DriverManagerType}
     */
    protected void startBrowser(BrowserId browserId, DriverManagerType driverManagerType) {
        getTestObject().getDriverManager().startDriverManager(this, browserId, driverManagerType);
    }

    /**
     * Set the browser/driver with the specified {@link BrowserId} as the active
     * browser/driver.
     *
     * @param browserId {@link BrowserId}
     */
    protected void setActiveDriver(BrowserId browserId) {
        getTestObject().getDriverManager().setActiveDriver(browserId, this);
    }

    /**
     * Get the current active {@link WebDriver}. This is the only method that allows
     * access to the {@link WebDriver} instance in the test cases
     *
     * @return {@link WebDriver}
     */
    public WebDriver getActiveDriver() {
        return getTestObject().getDriverManager().getActiveDriver().getWebDriver();
    }

    /**
     * @return {@link BrowserId} of the current active {@link WebDriver}
     */
    public String getActiveDriverName() {
        return getTestObject().getDriverManager().getActiveDriver().getBrowserId().name();
    }

    /**
     * Get the count of the total {@link WebDriver} instances spawned by the current
     * test
     *
     * @return count
     */
    public int getDriverCount() {
        return getTestObject().getDriverManager().getDriverMapSize();
    }

    /**
     * Close and quit the primary {@link WebDriver} instance with {@link BrowserId}
     * ALPHA
     */
    protected void quitBrowser() throws IOException {
        getTestObject().getDriverManager().killDriverManager();
    }

    /**
     * Close and quit the {@link WebDriver} instance with the specified
     * {@link BrowserId}
     *
     * @param browserId {@link BrowserId}
     */
    protected void quitBrowser(BrowserId browserId) throws IOException {
        getTestObject().getDriverManager().killDriverManager(browserId);
    }

}
