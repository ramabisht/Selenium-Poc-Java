package com.autoui.fwk.core;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.autoui.fwk.annotations.Steps;
import com.autoui.fwk.annotations.StepsAndPagesProcessor;
import com.autoui.fwk.enums.BrowserId;
import com.autoui.fwk.exceptions.SetupFailedFatalException;
import com.autoui.fwk.reporting.Logger;
import io.github.bonigarcia.wdm.DriverManagerType;
import net.lightbody.bmp.BrowserMobProxy;
import org.openqa.selenium.Proxy;

/**
 * Manage {@link Driver} object. This class can initialize {@link Driver}
 * objects and manage multiple {@link Driver} instances
 *
 * @author rama.bisht
 */
public class DriverManager {

    private static final Logger _logger = Logger.getLogger(DriverManager.class);

    /**
     * Create a new {@link Driver} instance from the default {@link Driver} instance
     *
     * @param browserId {@link BrowserId}
     * @return
     */
    private Driver configureNewDriverFromDefaultDriver(BrowserId browserId) {
        return Driver.cloneDefaultDriver(browserId);
    }

    // Driver Manager Type ------------------------------------------

    private DriverManagerType driverManagerType;

    /**
     * Get Browser value of type {@link DriverManagerType}
     *
     * @return {@link DriverManagerType}
     */
    public DriverManagerType getDriverManagerType() {
        return driverManagerType;
    }

    /**
     * Set Browser value of type {@link DriverManagerType}
     *
     * @param browser Browser value of type {@link DriverManagerType}
     */
    public void setDriverManagerType(DriverManagerType browser) {
        this.driverManagerType = browser;
        _logger.info(String.format("Browser set to %s", getDriverManagerType()));
    }

    // Driver -------------------------------------------------------

    private Map<BrowserId, Driver> driverMap = new HashMap<>();

    /**
     * Get Driver instance.
     *
     * @param browserId {@link BrowserId}
     * @return {@link Driver} instance with provided {@link BrowserId}
     */
    private Driver getDriver(BrowserId browserId) {
        Driver driver = driverMap.get(browserId);
        if (driver == null)
            throw new SetupFailedFatalException(
                    String.format("Requested driver instance with browser id %s is not found",
                            browserId.name()));
        return driver;
    }

    /**
     * Initialize driver, open browser and set active {@link Driver} based on the
     * primary {@link BrowserId} ALPHA and default {@linkplain #driverManagerType}
     * set in the {@link DriverManager}
     *
     * @param testClassInstance Test class instance
     */
    public void startDriverManager(Object testClassInstance) {
        startDriverManager(testClassInstance, BrowserId.getDefault());
    }

    /**
     * Initialize driver, open browser and set active {@link Driver} based on the
     * provided {@link BrowserId} and default {@linkplain #driverManagerType} set in
     * the {@link DriverManager}
     *
     * @param testClassInstance Test class instance
     * @param browserId         {@link BrowserId}
     */
    public void startDriverManager(Object testClassInstance, BrowserId browserId) {
        startDriverManager(testClassInstance, browserId, getDriverManagerType());
    }

    /**
     * Initialize driver, open browser and set active {@link Driver} based on the
     * provided {@link BrowserId} and {@link DriverManagerType} parameters.
     *
     * @param testClassInstance Test class instance
     * @param browserId         {@link BrowserId}
     * @param driverManagerType {@link DriverManagerType}
     */
    public void startDriverManager(Object testClassInstance, BrowserId browserId, DriverManagerType driverManagerType) {
        if (driverMap.containsKey(browserId)) {
            throw new SetupFailedFatalException(
                    String.format("Error starting new browser. The provided browser id, %s, is already in use",
                            browserId));
        } else {
            Driver driver = configureNewDriverFromDefaultDriver(browserId);
            driver.startDriver(driverManagerType);
            driverMap.put(browserId, driver);
            setActiveDriver(browserId, testClassInstance);
        }

    }

    // Active Driver ------------------------------------------------

    private Driver activeDriver;

    /**
     * @return active {@link Driver} object
     */
    public Driver getActiveDriver() {
        return activeDriver;
    }

    /**
     * Set active {@link Driver} and initialize {@link Steps} class fields with the
     * active driver
     *
     * @param browserId         {@link BrowserId}
     * @param testClassInstance Test class instance
     */
    public void setActiveDriver(BrowserId browserId, Object testClassInstance) {
        this.activeDriver = getDriver(browserId);
        StepsAndPagesProcessor.processAnnotation(testClassInstance);
    }

    /**
     * Return the count of {@link Driver} instances managed by current
     * {@link DriverManager} instance
     *
     * @return count of {@link Driver} instance
     */
    public int getDriverMapSize() {
        return driverMap.size();
    }

    /**
     * Close the default {@link Driver} instance with {@link BrowserId} ALPHA
     */
    public void killDriverManager() throws IOException {
        killDriverManager(BrowserId.getDefault());
    }

    /**
     * Kill the {@link Driver} instance with provided {@link BrowserId}
     *
     * @param browserId {@link BrowserId}
     */
    public void killDriverManager(BrowserId browserId) throws IOException {
        Driver driver = getDriver(browserId);
        driverMap.remove(browserId);
        driver.terminateDriver();
    }

    /**
     * Open the set Base URL parameter
     */
    public void openBaseUrl() {
        getActiveDriver().getWebDriver().get(BaseTest.getTestObject().getBaseUrl());
    }


    /* Proxy Server */

    /**
     * @return active {@link BrowserMobProxy} object
     */
    public BrowserMobProxy getBrowserMobProxy() {
        return Driver.getDefaultDriver().getBrowserMobProxy();
    }

    /**
     * @return active {@link BrowserMobProxy} object
     */
    public BrowserMobProxy getBrowserMobProxy(BrowserId browserId) {
        Driver driver = getDriver(browserId);
        return driver.getBrowserMobProxy();
    }

    /**
     * @return active {@link Proxy} object
     */
    public Proxy getSeleniumProxy() {
        return Driver.getDefaultDriver().getSeleniumProxy();
    }


}
