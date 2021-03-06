package com.autoui.fwk.core;

import java.io.File;
import java.io.IOException;
import java.net.Inet4Address;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import com.autoui.fwk.enums.HarType;
import com.autoui.fwk.enums.BrowserId;
import io.github.bonigarcia.wdm.DriverManagerType;
import io.github.bonigarcia.wdm.WebDriverManager;


import net.lightbody.bmp.client.ClientUtil;
import net.lightbody.bmp.proxy.CaptureType;
import org.openqa.selenium.Proxy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.ie.InternetExplorerOptions;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;

import com.autoui.fwk.exceptions.SetupFailedFatalException;
import com.autoui.fwk.reporting.Logger;

//HAR file dump related imports
import net.lightbody.bmp.BrowserMobProxy;
import net.lightbody.bmp.BrowserMobProxyServer;


/**
 * Driver object holding {@link WebDriver} instances for Selenium Web Tests
 *
 * @author rama.bisht
 */
public class Driver {

    private static final Logger _logger = Logger.getLogger(Driver.class);

    protected Driver(String ieDriverLocation, String chromeDriverLocation, String geckoDriverLocation,
                     long scriptTimeoutInSeconds, long pageLoadTimeoutInSeconds, long socketTimeoutInSeconds,
                     boolean runInHeadlessMode) {
        this(ieDriverLocation, chromeDriverLocation, geckoDriverLocation, scriptTimeoutInSeconds,
                pageLoadTimeoutInSeconds, socketTimeoutInSeconds, null, runInHeadlessMode);
    }

    protected Driver(String ieDriverLocation, String chromeDriverLocation, String geckoDriverLocation,
                     long scriptTimeoutInSeconds, long pageLoadTimeoutInSeconds, long socketTimeoutInSeconds,
                     BrowserId browserId, boolean runInHeadlessMode) {

        setIeDriverLocation(ieDriverLocation);
        setChromeDriverLocation(chromeDriverLocation);
        setGeckoDriverLocation(geckoDriverLocation);
        setScriptTimeoutInSeconds(scriptTimeoutInSeconds);
        setPageLoadTimeoutInSeconds(pageLoadTimeoutInSeconds);
        setSocketTimeoutInSeconds(socketTimeoutInSeconds);
        setBrowserId(browserId);
        setRunInHeadlessMode(runInHeadlessMode);
    }

    // Default Driver -------------------------------------

    private static Driver defaultDriver;

    /**
     * Setup default {@link Driver}. This will be used when setting up Test suite
     * level parameters in the {@link BaseTestSelenium}
     *
     * @param ieDriverLocation         Path of the IE driver server executable
     * @param chromeDriverLocation     Path of the Chrome driver server executable
     * @param geckoDriverLocation      Path of the Firefox driver server executable
     * @param scriptTimeoutInSeconds   Selenium javascript timeout
     * @param pageLoadTimeoutInSeconds Selenium page load timeout
     * @param socketTimeoutInSeconds   {@link WebDriver} SocketTimeoutException
     *                                 timeout
     */
    public static void setupDefaultDriver(String ieDriverLocation, String chromeDriverLocation,
                                          String geckoDriverLocation, long scriptTimeoutInSeconds, long pageLoadTimeoutInSeconds,
                                          long socketTimeoutInSeconds, boolean runInHeadlessMode) {
        _logger.info("Setting up default driver");
        defaultDriver = new Driver(ieDriverLocation, chromeDriverLocation, geckoDriverLocation,
                scriptTimeoutInSeconds, pageLoadTimeoutInSeconds, socketTimeoutInSeconds,
                runInHeadlessMode);
    }

    /**
     * @return Default {@link Driver} instance
     */
    public static Driver getDefaultDriver() {
        if (defaultDriver == null) {
            throw new SetupFailedFatalException("Default Driver instance is not configured");
        }
        return defaultDriver;
    }

    public static Driver cloneDefaultDriver(BrowserId browserId) {
        _logger.info("Cloning Default Driver");
        return new Driver(getDefaultDriver().getIeDriverLocation(), getDefaultDriver().getChromeDriverLocation(),
                getDefaultDriver().getGeckoDriverLocation(), getDefaultDriver().getScriptTimeoutInSeconds(),
                getDefaultDriver().getPageLoadTimeoutInSeconds(), getDefaultDriver().getSocketTimeoutInSeconds(),
                browserId, getDefaultDriver().getRunInHeadlessMode());
    }

    // Variables ------------------------------------------

    private String ieDriverLocation;
    private String chromeDriverLocation;
    private String geckoDriverLocation;

    private long scriptTimeoutInSeconds = 300;
    private long pageLoadTimeoutInSeconds = 300;
    private long socketTimeoutInSeconds = 300;

    private BrowserId browserId;
    private WebDriver webDriver;

    private static BrowserMobProxy browserMobProxyServer;
    private static Proxy seleniumProxy;

    // Executables --------------------------------------------------

    /**
     * @return absolute IE driver server executable path
     */
    public String getIeDriverLocation() {
        return ieDriverLocation;
    }

    /**
     * Set Custom IE driver server executable path.
     *
     * @param ieDriverLocation IE driver server executable path
     */
    private void setIeDriverLocation(String ieDriverLocation) {
        if (ieDriverLocation != null && !ieDriverLocation.equals("")) {
            if (isCustomDriverFound(ieDriverLocation)) {
                this.ieDriverLocation = ieDriverLocation;
                System.setProperty("webdriver.ie.driver", getIeDriverLocation());
                _logger.info(String.format("ieDriverLocation set to %s", getIeDriverLocation()));
            } else {
                _logger.warn("Invalid custom ieDriverLocation provided. Will use default");
            }
        } else {
            _logger.info("No custom ieDriverLocation provided. Will use default");
        }
    }

    /**
     * @return absolute Chrome driver server executable path
     */
    public String getChromeDriverLocation() {
        return chromeDriverLocation;
    }

    /**
     * Set Custom Chrome driver server executable path.
     *
     * @param chromeDriverLocation Chrome driver server executable path
     */
    private void setChromeDriverLocation(String chromeDriverLocation) {
        if (chromeDriverLocation != null && !chromeDriverLocation.equals("")) {
            if (isCustomDriverFound(chromeDriverLocation)) {
                this.chromeDriverLocation = chromeDriverLocation;
                System.setProperty("webdriver.chrome.driver", getChromeDriverLocation());
                _logger.info(String.format("chromeDriverLocation set to %s", getChromeDriverLocation()));
            } else {
                _logger.warn("Invalid custom chromeDriverLocation provided. Will use default");
            }
        } else {
            _logger.info("No custom chromeDriverLocation provided. Will use default");
        }
    }

    /**
     * @return absolute Firefox driver server executable path
     */
    public String getGeckoDriverLocation() {
        return geckoDriverLocation;
    }

    /**
     * Set Firefox driver server executable path.
     *
     * @param geckoDriverLocation Firefox driver server executable path
     */
    private void setGeckoDriverLocation(String geckoDriverLocation) {
        if (geckoDriverLocation != null && !geckoDriverLocation.equals("")) {
            if (isCustomDriverFound(geckoDriverLocation)) {
                this.geckoDriverLocation = geckoDriverLocation;
                System.setProperty("webdriver.gecko.driver", getGeckoDriverLocation());
                _logger.info(String.format("geckoDriverLocation set to %s", getGeckoDriverLocation()));
            } else {
                _logger.warn("Invalid custom geckoDriverLocation provided. Will use default");
            }
        } else {
            _logger.info("No custom geckoDriverLocation provided. Will use default");
        }
    }

    /**
     * Check if the provided driver server executable path is valid. If path is not
     * valid
     *
     * @param driverServerLocation path of driver server executable
     * @return true if valid
     */
    private boolean isCustomDriverFound(String driverServerLocation) {
        File givenFile = new File(driverServerLocation);
        return givenFile.exists();
    }

    // Timeouts -----------------------------------------------------

    /**
     * @return Selenium javascript timeout
     */
    public long getScriptTimeoutInSeconds() {
        return scriptTimeoutInSeconds;
    }

    /**
     * Set the Selenium javascript timeout
     *
     * @param scriptTimeoutInSeconds Selenium javascript timeout
     */
    private void setScriptTimeoutInSeconds(long scriptTimeoutInSeconds) {
        this.scriptTimeoutInSeconds = scriptTimeoutInSeconds;
        _logger.info(String.format("scriptTimeoutInSeconds set to %s", getScriptTimeoutInSeconds()));
    }

    /**
     * @return Selenium page load timeout
     */
    public long getPageLoadTimeoutInSeconds() {
        return pageLoadTimeoutInSeconds;
    }

    /**
     * Set the Selenium page load timeout
     *
     * @param pageLoadTimeoutInSeconds Selenium page load timeout
     */
    private void setPageLoadTimeoutInSeconds(long pageLoadTimeoutInSeconds) {
        this.pageLoadTimeoutInSeconds = pageLoadTimeoutInSeconds;
        _logger.info(String.format("pageLoadTimeoutInSeconds set to %s", getPageLoadTimeoutInSeconds()));
    }

    /**
     * @return {@link WebDriver} SocketTimeoutException timeout
     */
    public long getSocketTimeoutInSeconds() {
        return socketTimeoutInSeconds;
    }

    /**
     * Set the {@link WebDriver} SocketTimeoutException timeout
     *
     * @param socketTimeoutInSeconds {@link WebDriver} SocketTimeoutException
     *                               timeout
     */
    private void setSocketTimeoutInSeconds(long socketTimeoutInSeconds) {
        this.socketTimeoutInSeconds = socketTimeoutInSeconds;
        _logger.info(String.format("socketTimeoutInSeconds set to %s", getSocketTimeoutInSeconds()));
    }

    // Browser Id ---------------------------------------------------

    /**
     * @return {@link BrowserId} for the driver instance
     */
    public BrowserId getBrowserId() {
        return browserId;
    }

    /**
     * Set {@link BrowserId} for the driver instance
     *
     * @param browserId {@link BrowserId}
     */
    private void setBrowserId(BrowserId browserId) {
        this.browserId = browserId;
    }

    // WebDriver ----------------------------------------------------

    /**
     * @return {@link WebDriver}
     */
    public WebDriver getWebDriver() {
        return webDriver;
    }

    /**
     * @return {@link BrowserMobProxy}
     */
    public BrowserMobProxy getBrowserMobProxy() {
        return browserMobProxyServer;
    }


    /**
     * @return {@link Proxy}
     */
    public Proxy getSeleniumProxy() {
        return seleniumProxy;
    }


    // Headless mode ----------------------------------------

    private boolean runInHeadlessMode;

    /**
     * Get the base URL String
     *
     * @return base URL String
     */
    public boolean getRunInHeadlessMode() {
        return runInHeadlessMode;
    }

    /**
     * Set base URL String
     *
     * @param runInHeadlessMode Base URL String
     */
    private void setRunInHeadlessMode(boolean runInHeadlessMode) {
        this.runInHeadlessMode = runInHeadlessMode;
    }


    /**
     * This method initializes the driver (opens browser), maximizes browser window,
     * sets timeouts and deletes the cookies.
     *
     * @param driverManagerType {@link DriverManagerType}
     */
    public void startDriver(DriverManagerType driverManagerType) {
        try {
            DesiredCapabilities capabilities = new DesiredCapabilities();
            _logger.info("Start driver getHarType status :" +BaseTest.getTestObject().getHarType() );
            if (BaseTest.getTestObject().getHarType() != null &&
                    !BaseTest.getTestObject().getHarType().equals(HarType.NOT_ENABLED)) {
                try {
                    browserMobProxyServer = new BrowserMobProxyServer();
                    browserMobProxyServer.setTrustAllServers(true);
                    browserMobProxyServer.start(0);
                    browserMobProxyServer.enableHarCaptureTypes(CaptureType.REQUEST_CONTENT, CaptureType.RESPONSE_CONTENT);
                    seleniumProxy = ClientUtil.createSeleniumProxy(browserMobProxyServer);
                    String hostIp = Inet4Address.getLocalHost().getHostAddress();
                    seleniumProxy.setHttpProxy(hostIp + ":" + browserMobProxyServer.getPort());
                    seleniumProxy.setSslProxy(hostIp + ":" + browserMobProxyServer.getPort());
                    capabilities.setCapability(CapabilityType.PROXY, seleniumProxy);
                    _logger.info("Setting Browser proxy server to :" + browserMobProxyServer.toString() + " at port:" + browserMobProxyServer.getPort());
                    _logger.info("Setting Selenium proxy server to :" + seleniumProxy.toString());
                } catch (Exception ex) {
                    _logger.error("Setting up browser proxy failed " + ex);
                }

            }
            if (driverManagerType.name().equals(DriverManagerType.IEXPLORER.name())) {
                capabilities = DesiredCapabilities.internetExplorer();
                capabilities.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS, true);
                capabilities.setCapability(InternetExplorerDriver.IE_ENSURE_CLEAN_SESSION, true);
                capabilities.setCapability(InternetExplorerDriver.ENABLE_PERSISTENT_HOVERING, false);
                InternetExplorerOptions ieOptions = new InternetExplorerOptions(capabilities);
                if (ieDriverLocation == null) {
                    WebDriverManager.getInstance(DriverManagerType.IEXPLORER).setup();
                    _logger.info("Using ieDriver from framework");
                }
                webDriver = new InternetExplorerDriver(ieOptions);
            } else if (driverManagerType.name().equals(DriverManagerType.CHROME.name())) {
                if (chromeDriverLocation == null) {
                    WebDriverManager.getInstance(DriverManagerType.CHROME).setup();
                    _logger.info("Using chromeDriver from framework");
                }
                ChromeOptions chromeOptions = new ChromeOptions();
                if (BaseTest.getTestObject().getHarType() != null &&
                        !BaseTest.getTestObject().getHarType().equals(HarType.NOT_ENABLED)) {
                    chromeOptions.merge(capabilities);
                }
                String debuggerAddress = BaseTest.getTestObject().getDebuggerAddress();
                if (debuggerAddress != null) {
                    chromeOptions.setExperimentalOption("debuggerAddress", debuggerAddress);
                    _logger.debug(
                            String.format("Setting chrome experimental option debuggerAddress : %s", debuggerAddress));
                    _logger.debug(
                            String.format("Expecting that chrome is already running at remote debugging address %s",
                                    debuggerAddress));
                } else {
                    chromeOptions.addArguments("--no-sandbox");
                    chromeOptions.addArguments("--disable-dev-shm-usage");
                    chromeOptions.addArguments("--safebrowsing-disable-download-protection");
                    chromeOptions.addArguments(" --ignore-certificate-errors");

                    if (runInHeadlessMode) {
                        _logger.info("Setting headless mode --headless");
                        chromeOptions.addArguments("--headless");
                    }
                    Map<String, Object> chromePrefs = new HashMap<String, Object>();
                    chromePrefs.put("safebrowsing.enabled", "true");
                    chromeOptions.setExperimentalOption("prefs", chromePrefs);
                    _logger.debug("Setting chrome switch --no-sandbox");
                    _logger.debug("Setting chrome switch --disable-dev-shm-usage");
                    _logger.debug("Setting chrome switch --no-sandbox");
                    _logger.debug("Setting chrome pref {safebrowsing.enabled : true}");
                }

                if (BaseTest.getTestObject().getHarType() != null &&
                        !BaseTest.getTestObject().getHarType().equals(HarType.NOT_ENABLED)) {
                    capabilities.setCapability(ChromeOptions.CAPABILITY, chromeOptions);
                    webDriver = new ChromeDriver(capabilities);
                } else {
                    webDriver = new ChromeDriver(chromeOptions);
                }

            } else if (driverManagerType.name().equals(DriverManagerType.FIREFOX.name())) {
                if (geckoDriverLocation == null) {
                    WebDriverManager.getInstance(DriverManagerType.FIREFOX).setup();
                    _logger.info("Using geckoDriver from framework");
                }
                webDriver = new FirefoxDriver();
            } else if (driverManagerType.name().equals(DriverManagerType.CHROMIUM.name())) {
                WebDriverManager.getInstance(DriverManagerType.CHROMIUM).setup();
                webDriver = new ChromeDriver();
            }
        } catch (Exception e) {
            throw new SetupFailedFatalException("Error initializing the driver", e);
        }

        webDriver.manage().timeouts().pageLoadTimeout(getPageLoadTimeoutInSeconds(), TimeUnit.MINUTES);
        _logger.info(String.format("Page Load timeout set to %s seconds", getPageLoadTimeoutInSeconds()));
        webDriver.manage().timeouts().implicitlyWait(BaseTest.getTestObject().getTimeoutInSeconds(), TimeUnit.SECONDS);
        _logger.info(String.format("Implicit wait set on driver to %s seconds",
                BaseTest.getTestObject().getTimeoutInSeconds()));
        webDriver.manage().timeouts().setScriptTimeout(getScriptTimeoutInSeconds(), TimeUnit.SECONDS);
        _logger.info(String.format("Script timeout set on driver to %s seconds", getScriptTimeoutInSeconds()));

        if (BaseTest.getTestObject().getDebuggerAddress() == null) {
            webDriver.manage().window().maximize();
            webDriver.manage().deleteAllCookies();
            _logger.info("Cookies deleted");
        }
    }

    /**
     * Close and quit driver
     */
    public void terminateDriver() {
        if (browserMobProxyServer != null) {
            try {
                browserMobProxyServer.stop();
                _logger.info("Stopped the BMP server");
            } catch (Exception ex) {
                _logger.error("Failed to stop the proxy server running on port " + browserMobProxyServer.getPort() + " reason " + ex.getMessage());
            }
        }

        if (webDriver != null) {
            _logger.info(String.format("Quiting driver %s", webDriver));
            webDriver.close();
            webDriver.quit();
        } else {
            _logger.warn(String.format("Driver %s is already dead", webDriver));
        }

    }
}
