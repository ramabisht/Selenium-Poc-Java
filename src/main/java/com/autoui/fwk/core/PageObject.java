package com.autoui.fwk.core;

import com.autoui.fwk.annotations.Action;
import com.autoui.fwk.enums.ExpectedCondition;
import com.autoui.fwk.selenium.AutoUIWebDriverWait;
import com.autoui.fwk.selenium.CustomExpectedConditions;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.DefaultElementLocator;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;


import com.autoui.fwk.reporting.Logger;

import java.time.Duration;
import java.util.Date;
import java.util.concurrent.TimeUnit;
import java.util.function.Predicate;


/**
 * Base class for Page/View. All Page/View classes must extend this class.
 * Inheriting this call will enforce parity in constructor signature and provide
 * common functions for use in View/Page (page object) libraries.
 *
 * @author rama.bisht
 */
public abstract class PageObject implements IPageObject {

    private static Logger _logger = Logger.getLogger(PageObject.class);

    protected WebDriver driver;
    protected SearchContext component;
    protected By parentContainerLocator;
    private WebElement superContainer;

    /**
     * Get the parent container
     *
     * @return Parent container
     */
    protected WebElement getParentContainer() {
        return (WebElement) component;
    }

    /**
     * Reinitializes the {@link PageFactory}. This method can be use to
     * re-initialize {@link WebElement} found by {@link FindBy} in page objects
     *
     * @return true if re-initialization successful
     */
    public boolean reInitializePageObject() {
        _logger.info("Attempting to re-initialize page object");
        boolean isSuccessful = false;
        if (parentContainerLocator != null) {
            if (superContainer == null) {
                try {
                    PageFactory.initElements(field -> {
                        return new DefaultElementLocator(driver.findElement(parentContainerLocator), field);
                    }, this);
                    this.component = driver.findElement(parentContainerLocator);
                    this.superContainer = null;
                    isSuccessful = true;
                } catch (Exception e) {
                    _logger.info(String.format(
                            "Error trying to reinitialize page object with parent Container Locator. Error is %s",
                            e.getMessage()));
                }
            } else {
                try {
                    ((WebElement) superContainer).getTagName();
                    WebElement newSuperContainer = superContainer;
                    PageFactory.initElements(field -> {
                        return new DefaultElementLocator(newSuperContainer.findElement(parentContainerLocator),
                                field);
                    }, this);
                    this.component = superContainer.findElement(parentContainerLocator);
                    this.superContainer = newSuperContainer;
                    isSuccessful = true;
                } catch (Exception e) {
                    e.printStackTrace();
                    _logger.info(String.format(
                            "Error trying to reinitialize page object with parent Container Locator and super container. Error is %s",
                            e.getMessage()));
                }
            }
        } else {
            try {
                ((WebElement) component).getTagName();
                PageFactory.initElements(field -> {
                    return new DefaultElementLocator(component, field);
                }, this);
                this.superContainer = null;
                this.parentContainerLocator = null;
                isSuccessful = true;
            } catch (Exception e) {
                _logger.info(String.format(
                        "Error trying to reinitialize page object with parent Container. Error is %s", e.getMessage()));
            }
        }

        if (isSuccessful)
            _logger.info("Reinitialized page object");
        return isSuccessful;
    }

    /**
     * Initialize page objects using {@link WebDriver}
     */
    public PageObject() {
        By parentContainerLocator = By.tagName("body");
        this.driver = BaseTest.getTestObject().getDriverManager().getActiveDriver().getWebDriver();
        PageFactory.initElements(field -> {
            return new DefaultElementLocator(driver.findElement(parentContainerLocator), field);
        }, this);
        setExplicitWaitInSeconds((int) BaseTest.getTestObject().getTimeoutInSeconds());
        this.parentContainerLocator = parentContainerLocator;
        this.component = driver.findElement(parentContainerLocator);
        this.superContainer = null;
    }

    /**
     * Initialize page objects using the provided parent container XPATH. When
     * invoking this constructor, the provided container should be visible in the
     * Page
     *
     * @param parentContainerLocator {@link By} identifier to the parent container
     *                               element
     */
    public PageObject(By parentContainerLocator) {
        this.driver = BaseTest.getTestObject().getDriverManager().getActiveDriver().getWebDriver();
        PageFactory.initElements(field -> {
            return new DefaultElementLocator(driver.findElement(parentContainerLocator), field);
        }, this);
        setExplicitWaitInSeconds((int) BaseTest.getTestObject().getTimeoutInSeconds());
        this.parentContainerLocator = parentContainerLocator;
        this.component = driver.findElement(parentContainerLocator);
        this.superContainer = null;
    }

    /**
     * Initialize page objects using the provided parent container element. When
     * invoking this constructor, the provided container should be visible in the
     * Page
     *
     * @param parentContainer Parent Container element
     */
    public PageObject(WebElement parentContainer) {
        this.driver = BaseTest.getTestObject().getDriverManager().getActiveDriver().getWebDriver();
        PageFactory.initElements(field -> {
            return new DefaultElementLocator(parentContainer, field);
        }, this);
        setExplicitWaitInSeconds((int) BaseTest.getTestObject().getTimeoutInSeconds());
        this.parentContainerLocator = null;
        this.component = parentContainer;
        this.superContainer = null;
    }

    /**
     * Initialize page objects using the provided parent container identifier. When
     * invoking this constructor, the provided page container should be visible in
     * the Page
     *
     * @param superContainer         Super Parent Container
     * @param parentContainerLocator {@link By} identifier to the parent container
     *                               element
     */
    public PageObject(WebElement superContainer, By parentContainerLocator) {
        this.driver = BaseTest.getTestObject().getDriverManager().getActiveDriver().getWebDriver();
        PageFactory.initElements(field -> {
            return new DefaultElementLocator(superContainer.findElement(parentContainerLocator), field);
        }, this);
        setExplicitWaitInSeconds((int) BaseTest.getTestObject().getTimeoutInSeconds());
        this.parentContainerLocator = parentContainerLocator;
        this.component = superContainer.findElement(parentContainerLocator);
        this.superContainer = superContainer;
    }

    /**
     * Initialize the specified page class and return object
     *
     * @param <T>  Generic Type of Type {@link IPageObject}
     * @param page Page class to be initialized
     * @return Instance of class or null if error
     */
    protected <T extends IPageObject> T getPage(Class<T> page) {
        try {
            return (T) page.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            _logger.warn(String.format("Error initializing component %s", page.getName()), e);
        }
        return null;
    }

    private int explicitWaitInSeconds = 0;

    /**
     * @return Explicit wait timeout in seconds which is equal to timeout in seconds
     * parameter provided in Test class
     */
    protected int getExplicitWaitInSeconds() {
        return explicitWaitInSeconds;
    }

    /**
     * Set Explicit wait timeout in seconds
     *
     * @param explicitWaitInSeconds
     */
    private void setExplicitWaitInSeconds(int explicitWaitInSeconds) {
        this.explicitWaitInSeconds = explicitWaitInSeconds;
    }

    // JavaScript Executor ------------------------------------------

    /**
     * Get {@link JavascriptExecutor} object
     *
     * @return {@link JavascriptExecutor}
     */
    protected JavascriptExecutor getJavascriptExecutor() {
        return ((JavascriptExecutor) driver);
    }

    /**
     * Execute provided JavaScript command
     *
     * @param command  JavaScript
     * @param elements {@link WebElement}s to be used JavaScript
     */
    @SuppressWarnings("all")
    protected void executeJavascript(String command, WebElement... elements) {
        getJavascriptExecutor().executeScript(command, elements);
    }

    protected boolean executeJavascript(String command, String expectedOutput) {
        return getJavascriptExecutor().executeScript(command).equals(expectedOutput);
    }

    protected boolean executeJavascript(String command, int expectedOutput) {
        return (int) getJavascriptExecutor().executeScript(command) == expectedOutput;
    }

    /**
     * Perform JavaScript click
     *
     * @param element {@link WebElement} on which click has to be performed
     */
    @Action
    protected void javascriptClick(WebElement element) {
        executeJavascript("arguments[0].click()", element);
    }

    /**
     * Scroll element to view using scrollIntoView() JavaScript function
     *
     * @param element {@link WebElement} to scroll into view
     */
    @Action
    protected void scrollElementToViewUsingJs(WebElement element) {
        executeJavascript("arguments[0].scrollIntoView(true)", element);
    }

    /**
     * Wait for the page load to complete waitUntilPageIsLoaded() JavaScript function
     */
    @Action
    public void waitUntilPageIsLoaded() {
        new WebDriverWait(driver, (long) getExplicitWaitInSeconds()).until(new org.openqa.selenium.support.ui.ExpectedCondition<Boolean>() {
            public Boolean apply(WebDriver webDriver) {
                return executeJavascript("return document.readyState", "complete");
            }
        });
        _logger.info("Page load completed.");
    }

    /**
     * Clear a {@link WebElement} using JavaScript
     *
     * @param element {@link WebElement}
     */
    @Action
    protected void javascriptClearField(WebElement element) {
        executeJavascript("arguments[0].value = ''", element);
    }

    /**
     * Send keys to {@link WebElement} using JavaScript
     *
     * @param element The {@link WebElement} to which keys have to be sent
     * @param keys    Keys to be sent
     */
    @Action
    protected void javascriptSendKeys(WebElement element, String keys) {
        executeJavascript(String.format("arguments[0].value = '%s'", keys), element);
    }

    // Mouse Action -------------------------------------------------

    /**
     * Get {@link Actions} instance
     *
     * @return {@link Actions} object
     */
    protected Actions mouse() {
        return new Actions(driver);
    }

    /**
     * Execute mouse {@link Actions}
     *
     * @param actions The sequence of {@link Actions} to be performed
     */
    public void performMouseAction(Actions actions) {
        actions.build().perform();
    }

    /**
     * Scroll element to view using selenium {@link Action} (mouse)
     *
     * @param element {@link WebElement} to scroll into view
     */
    @Action
    protected void scrollElementToViewUsingMouse(WebElement element) {
        performMouseAction(mouse().moveToElement(element));
    }

    /**
     * Move mouse relative to an element and click on the location. Offset should be
     * in pixels.
     *
     * @param element Element against which we have to move
     * @param x       x-axis to move
     * @param y       y-axis to move
     */
    @Action
    protected void moveRelativeToElementAndClick(WebElement element, int x, int y) {
        Actions actions = new Actions(driver);
        actions.moveToElement(element).moveByOffset(x, y);
        actions.perform();
        actions.click();

        org.openqa.selenium.interactions.Action action = actions.build();
        action.perform();
    }

    // Explicit wait ------------------------------------------------

    /**
     * Get {@link AutoUIWebDriverWait} (explicit wait) object with the set
     * timeout in seconds value. {@link AutoUIWebDriverWait} extends the
     * Selenium {@link WebDriverWait} and adds additional wait until methods
     *
     * @param explicitWaitInSeconds Explicit wait timeout
     * @return {@link AutoUIWebDriverWait} object
     */
    protected AutoUIWebDriverWait explicitWait(int explicitWaitInSeconds) {
        return new AutoUIWebDriverWait(driver, explicitWaitInSeconds);
    }

    /**
     * Get {@link AutoUIWebDriverWait} (explicit wait) object with the default
     * timeout. {@link AutoUIWebDriverWait} extends the Selenium
     * {@link WebDriverWait} and adds additional wait until methods
     *
     * @return {@link AutoUIWebDriverWait} object
     */
    protected AutoUIWebDriverWait getExplicitWait() {
        return explicitWait(getExplicitWaitInSeconds());
    }

    /**
     * Fluent wait
     *
     * @param explicitWaitInSeconds
     * @return
     */
    protected FluentWait fluentWait(int explicitWaitInSeconds) {
        return new FluentWait(this.driver).withTimeout(Duration.ofSeconds(explicitWaitInSeconds))
                .pollingEvery(Duration.ofMillis(100))
                .ignoring(NoSuchElementException.class);
    }

    /**
     * Fluent wait with implicit max wait duration
     *
     * @return
     */
    protected FluentWait fluentWait() {
        return new FluentWait(this.driver).withTimeout(Duration.ofSeconds(getExplicitWaitInSeconds()))
                .pollingEvery(Duration.ofMillis(100))
                .ignoring(NoSuchElementException.class);
    }


    private void setImplicitWaitToOneSecond() {
        _logger.debug("Setting implicit wait to 1");
        this.driver.manage().timeouts().implicitlyWait(1L, TimeUnit.SECONDS);
    }

    private WebElement waitUntil(ExpectedCondition expectedCondition, By by, WebElement element, int explicitWaitInSeconds) {
        this.setImplicitWaitToOneSecond();
        waitUntilPageIsLoaded();
        WebElement returnElement = null;
        try {
            long startTime = (new Date()).getTime();
            if (expectedCondition.equals(ExpectedCondition.PRESENCE_OF_ELEMENT_LOCATED_BY)) {
               // returnElement = (WebElement) this.explicitWait(explicitWaitInSeconds).until(ExpectedConditions.elementToBeClickable(by));
                returnElement = (WebElement) this.fluentWait(explicitWaitInSeconds).until(ExpectedConditions.elementToBeClickable(by));
            } else if (expectedCondition.equals(ExpectedCondition.PROXY_ELEMENT_LOCATED)) {
                //returnElement = (WebElement) this.explicitWait(explicitWaitInSeconds).until(CustomExpectedConditions.proxyElementLocated(element));
                returnElement = (WebElement) this.fluentWait(explicitWaitInSeconds).until(CustomExpectedConditions.proxyElementLocated(element));
            }
        } catch (Exception var11) {
            throw var11;
        } finally {
            _logger.debug(String.format("Setting wait until implicit wait to %s seconds", BaseTest.getTestObject().getTimeoutInSeconds()));
            this.driver.manage().timeouts().implicitlyWait(BaseTest.getTestObject().getTimeoutInSeconds(), TimeUnit.SECONDS);
        }
        return returnElement;
    }

    protected boolean waitUntilInvisibilityOfElement(By by) {
        this.setImplicitWaitToOneSecond();
        boolean returnValue = false;
        try {
            returnValue = (Boolean) this.explicitWait(this.explicitWaitInSeconds)
                    .until(ExpectedConditions.invisibilityOfElementLocated(by));
        } catch (Exception var7) {
            throw var7;
        } finally {
            _logger.debug(String.format("Setting implicit wait to %s seconds",
                    BaseTest.getTestObject().getTimeoutInSeconds()));
            this.driver.manage().timeouts().implicitlyWait(BaseTest.getTestObject().getTimeoutInSeconds(),
                    TimeUnit.SECONDS);
        }
        return returnValue;
    }

    /**
     * Use this
     *
     * @param element
     * @param explicitWaitInSeconds
     * @return
     */

    protected boolean isElementFound(WebElement element, int explicitWaitInSeconds) {
        boolean isFound = false;

        try {
            WebElement returnElement = this.waitUntil(ExpectedCondition.PROXY_ELEMENT_LOCATED, (By) null, element,
                    explicitWaitInSeconds);
            if (returnElement != null) {
                isFound = true;
            }
        } catch (NoSuchElementException | TimeoutException var5) {
            _logger.debug(String.format("Element not found. %s", var5.getMessage()));
        }

        return isFound;
    }

    protected boolean isElementFound(By by, int explicitWaitInSeconds) {
        boolean isFound = false;
        try {
            WebElement returnElement = this.waitUntil(ExpectedCondition.PRESENCE_OF_ELEMENT_LOCATED_BY, by,
                    (WebElement) null, explicitWaitInSeconds);
            if (returnElement != null) {
                isFound = true;
            }
        } catch (NoSuchElementException | TimeoutException var5) {
            _logger.info(String.format("Element not found. %s", var5.getMessage()));
        }
        return isFound;
    }

    protected boolean isElementFound(WebElement element) {
        return this.isElementFound(element, this.getExplicitWaitInSeconds());
    }

    protected boolean isElementFound(By by) {
        return this.isElementFound(by, this.getExplicitWaitInSeconds());
    }

    /**
     * This method has to be implemented by all the Pages inheriting
     * {@link PageObject}. It can be implemented in any of ways as shown in the
     * below examples
     *
     * <pre>
     * &#64;Override
     * public PageValidation pageValidation() {
     * 	return new PageValidation() {
     *
     * 		&#64;Override
     * 		public void validate() {
     * 			validateLoginWithPage();
     *        }
     *
     * 		&#64;Step
     * 		private void validateLoginPage() {
     * 			Assert.assertTrue(isUsernameFieldFound(), "Username field is found");
     * 			Assert.assertTrue(isUsernameFieldFound(), "Username field is found");
     *        }
     *    };
     * }
     *
     * &#64;Override
     * public PageValidation pageValidation() {
     * 	return new PageValidation() {
     *
     * 		&#64;Override
     * 		public void validate(String... parameters) {
     * 			validateLoginWithPage(parameters[0]);
     *        }
     *
     * 		&#64;Step
     * 		private void validateLoginPage(String companyName) {
     * 			Assert.assertEquals(getCompanyName(), companyName, "");
     *        }
     *    };
     * }
     * </pre>
     *
     * @return {@link PageValidation}
     */

    private boolean waitUntilElementIsClickable(WebElement element, int explicitWaitInSeconds) {
        boolean isFound = false;
        this.setImplicitWaitToOneSecond();
        WebElement returnElement = null;
        try {
            long startTime = (new Date()).getTime();
            returnElement = (WebElement) this.explicitWait(explicitWaitInSeconds).untilElementToBeClickable(element);
        } catch (NoSuchElementException | TimeoutException var5) {
            _logger.debug(String.format("clickable Element not found. %s", var5.getMessage()));
        } catch (Exception var11) {
            throw var11;
        } finally {
            _logger.debug(String.format("Setting implicit wait to %s seconds", BaseTest.getTestObject().getTimeoutInSeconds()));
            this.driver.manage().timeouts().implicitlyWait(BaseTest.getTestObject().getTimeoutInSeconds(), TimeUnit.SECONDS);
        }
        if (returnElement != null) {
            isFound = true;
        }
        return isFound;
    }

    protected boolean isClickableElementFound(WebElement element) {
        return this.waitUntilElementIsClickable(element, this.getExplicitWaitInSeconds());
    }

    protected boolean isClickableElementFound(WebElement element, int explicitWaitInSeconds) {
        return this.waitUntilElementIsClickable(element, explicitWaitInSeconds);
    }

    public abstract PageValidation pageValidation();

    public abstract class PageValidation {

        protected String title;

        public PageValidation() {
        }

        public PageValidation(String title) {
            this.title = title;
        }

        public abstract void validate();

        public void validate(String... parameters) {
        }

        ;
    }
}