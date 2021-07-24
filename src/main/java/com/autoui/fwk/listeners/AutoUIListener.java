package com.autoui.fwk.listeners;

import java.util.*;

import com.autoui.fwk.enums.*;
import com.autoui.fwk.notifiers.Slack;
import com.autoui.fwk.execution.IterationManager;
import org.testng.*;

import com.autoui.fwk.annotations.StepsAndPagesProcessor;
import com.autoui.fwk.core.BaseTest;
import com.autoui.fwk.exceptions.TestOrConfigurationSkipException;
import com.autoui.fwk.launcher.LauncherClientManager;
import com.autoui.fwk.reporting.ExecutionLogManager;
import com.autoui.fwk.reporting.Logger;
import com.autoui.fwk.reporting.ReportingTools;
import com.autoui.fwk.utils.FileUtils;
import com.autoui.fwk.utils.ThreadUtils;
import io.github.bonigarcia.wdm.DriverManagerType;

/**
 * This is the custom reporter for autoui framework and should be specified
 * in the testNG listener. It contains methods for logging information on after
 * test completion (failure/pass)
 *
 * @author rama.bisht
 */
public class AutoUIListener extends TestListenerAdapter
        implements IInvokedMethodListener, IExecutionListener, IMethodInterceptor, ISuiteListener {

    private static final Logger _logger = Logger.getLogger(AutoUIListener.class);
    HashMap<String, Object> testResult = new HashMap<>();
    List<Map<String, Object>> passedTest = new ArrayList<>();
    List<Map<String, Object>> failedTest = new ArrayList<>();
    List<Map<String, Object>> skippedTest = new ArrayList<>();

    /**
     * Override method for onStart where we start the {@link IterationManager} class
     * to track the iteration and time.
     */
    @Override
    public void onStart(ITestContext testContext) {
        _logger.info("----------------- Starting XML Test -------------------");
        _logger.info(String.format("XML Test    : %s", testContext.getCurrentXmlTest().getName()));
        _logger.info(String.format("Thread Name : %s", ThreadUtils.getThreadName()));
        _logger.info(String.format("Thread ID : %s", ThreadUtils.getThreadId()));
        _logger.info("-------------------------------------------------------");
        _logger.debug("Starting timekeeper " + IterationManager.getManager().getElapsedTimeInMilliSeconds());
        LauncherClientManager.getManager().enableClient();
        LauncherClientManager.getManager().startTest(testContext);
        super.onStart(testContext);
    }

    private void setDefaultParameters(Map<String, String> parameters, String key, String defaultValue) {
        String value = parameters.get(key);
        value = value == null || value.isEmpty() ? System.getProperty(String.format("autoui.%s", key), defaultValue)
                : value;
        parameters.put(key, value);
        String log = String.format("%s : %s", key, value);
        _logger.debug(defaultValue.equals(value) ? String.format("%s [default]", log) : log);
    }

    /**
     * Override {@link ISuiteListener#onStart(ISuite)} method for setting framework
     * parameters. If not set, apply default values
     */
    @Override
    public void onStart(ISuite suite) {
        ISuiteListener.super.onStart(suite);
        Map<String, String> parameters = suite.getXmlSuite().getAllParameters();

        _logger.info(String.format("Setting up default framework parameters if not explicitly set for suite %s",
                suite.getName()));

        // autouiInternalSetLauncherClients -----------

        setDefaultParameters(parameters, "launcherClients", "");
        setDefaultParameters(parameters, "runName", "");
        setDefaultParameters(parameters, "batchNumber", "");

        // autouiInternalSetParameters ----------------

        setDefaultParameters(parameters, "repeatMode", RepeatMode.OFF.name());
        setDefaultParameters(parameters, "testDurationInSeconds", "0");
        setDefaultParameters(parameters, "invocationCount", "0");
        setDefaultParameters(parameters, "delayBetweenIterationInSeconds", "0");
        setDefaultParameters(parameters, "timeoutInSeconds", "20");
        setDefaultParameters(parameters, "slowdownDurationInSeconds", "0");
        setDefaultParameters(parameters, "retryMode", RetryMode.OFF.name());
        setDefaultParameters(parameters, "recoveryClasses", "");

        // autouiInternalSetDriverParameters ----------

        setDefaultParameters(parameters, "ieDriverLocation", "");
        setDefaultParameters(parameters, "chromeDriverLocation", "");
        setDefaultParameters(parameters, "geckoDriverLocation", "");
        setDefaultParameters(parameters, "scriptTimeoutInSeconds", "300");
        setDefaultParameters(parameters, "pageLoadTimeoutInSeconds", "300");
        setDefaultParameters(parameters, "socketTimeoutInSeconds", "300");

        // autouiInternalSetWebTestParameters ---------

        setDefaultParameters(parameters, "browser", DriverManagerType.CHROME.name());
        setDefaultParameters(parameters, "debuggerAddress", "");
        setDefaultParameters(parameters, "screenshotType", ScreenshotType.BROWSER_SCREENSHOT.name());
        setDefaultParameters(parameters, "screenshotMode", ScreenshotMode.ON_FAILURE.name());
        setDefaultParameters(parameters, "screenshotModeForIteration",
                ScreenshotModeForIteration.LAST_ITERATION.name());

        setDefaultParameters(parameters, "baseUrl", "");
        setDefaultParameters(parameters, "runInHeadlessMode", "");
        setDefaultParameters(parameters, "harCollectionType", HarType.getDefault().name());
        setDefaultParameters(parameters, "slackWebHookUrl", "");

        _logger.info("Setup default framework parameters completed");
        suite.getXmlSuite().setParameters(parameters);
    }

    @Override
    public void onFinish(ISuite iSuite) {
    }

    /**
     * Override method for onTestFailure in the TestNG library. Override is done to
     * log test skips and test failure
     */
    @Override
    public void onTestFailure(ITestResult testResult) {
        Throwable throwable = testResult.getThrowable() == null ? new TestOrConfigurationSkipException()
                : testResult.getThrowable();
        if (throwable instanceof TestNGException)
            ExecutionLogManager.logTestSkip(testResult);
        else
            ExecutionLogManager.logListenerFailure(testResult);
        super.onTestFailure(testResult);
    }

    /**
     * Override method for onTestSkipped in the TestNG library. Override is done to
     * log test skips
     */
    @Override
    public void onTestSkipped(ITestResult testResult) {
        Throwable throwable = testResult.getThrowable() == null ? new TestOrConfigurationSkipException()
                : testResult.getThrowable();
        testResult.setThrowable(throwable);
        ExecutionLogManager.logTestSkip(testResult);
        super.onTestSkipped(testResult);
    }

    /**
     * Override method for onConfigurationSkip in the TestNG library. Override is
     * done to log proper failure
     */
    @Override
    public void onConfigurationSkip(ITestResult testResult) {
        Throwable throwable = testResult.getThrowable() == null ? new TestOrConfigurationSkipException()
                : testResult.getThrowable();
        testResult.setThrowable(throwable);
        ExecutionLogManager.logTestSkip(testResult);
        super.onConfigurationSkip(testResult);
    }

    /**
     * Override method for onFinish in the TestNG library. Override is done to log
     * the iteration details and screenshot management according to the set
     * {@link ScreenshotModeForIteration} parameter.
     */
    @Override
    public void onFinish(ITestContext testContext) {
        ExecutionLogManager.logIterationDetails();
        ReportingTools.wipeScreenshotEntryInReports();
        LauncherClientManager.getManager().stopTest();
        if (BaseTest.getTestObject().getSlackWebHookUrl() != null &&
                !BaseTest.getTestObject().getSlackWebHookUrl().trim().equals("")) {
            for (ITestResult result : testContext.getPassedTests().getAllResults()) {
                passedTest.add(new HashMap<String, Object>() {{
                    put(result.getTestContext().getName(), result);
                }});
            }

            for (ITestResult result : testContext.getFailedTests().getAllResults()) {
                failedTest.add(new HashMap<String, Object>() {{
                    put(result.getTestContext().getName(), result);
                }});
            }

            for (ITestResult result : testContext.getSkippedTests().getAllResults()) {
                skippedTest.add(new HashMap<String, Object>() {{
                    put(result.getTestContext().getName(), result);
                }});
            }
        }
        super.onFinish(testContext);
    }

    @Override
    public void afterInvocation(IInvokedMethod invokedMethod, ITestResult testResult) {
    }

    @Override
    public void beforeInvocation(IInvokedMethod invokedMethod, ITestResult testResult) {

    }

    @Override
    public void afterInvocation(IInvokedMethod invokedMethod, ITestResult testResult, ITestContext testContext) {
    }

    /**
     * Implement beforeInvocation method in the {@link IInvokedMethodListener} to
     * listen for @Before and @After methods that are not part of the internal
     * framework and initialize {@link StepsAndPagesProcessor}
     */
    @Override
    public void beforeInvocation(IInvokedMethod invokedMethod, ITestResult testResult, ITestContext testContext) {
        String methodName = invokedMethod.getTestMethod().getMethodName();
        if (!methodName.startsWith("autouiInternal")
                && BaseTest.getTestObject().getDriverManager().getActiveDriver() != null)
            StepsAndPagesProcessor.processAnnotation(invokedMethod.getTestMethod().getInstance());
    }

    /**
     * Delete temporary folder before testNG execution start
     */
    @Override
    public void onExecutionStart() {
        FileUtils.cleanTempDirectory();
    }

    /**
     * Delete temporary folder after testNG execution complete
     */
    @Override
    public void onExecutionFinish() {
        if (BaseTest.getTestObject().getSlackWebHookUrl() != null &&
                !BaseTest.getTestObject().getSlackWebHookUrl().trim().equals("")) {
            testResult.put("Pass", passedTest);
            testResult.put("Fail", failedTest);
            testResult.put("Skip", skippedTest);
            String slackBody = Slack.getSlackText(testResult);
            HashMap<String, String> slackBodyMap = new HashMap<>();
            slackBodyMap.put("text", slackBody);
            Slack.postRequestToSlack(BaseTest.getTestObject().getSlackWebHookUrl(), slackBodyMap);
        }
        FileUtils.cleanTempDirectory();
    }


    /**
     * Implement {@link IMethodInterceptor#intercept(List, ITestContext)} method to
     * fix the sequencing Test cases in test classes specified within the
     * &lt;TEST&gt; tag in TestNG XML file
     */
    @Override
    public List<IMethodInstance> intercept(List<IMethodInstance> methods, ITestContext context) {
        _logger.info("------------------ Execution Order --------------------");
        _logger.info(String.format("XML Test  : %s", context.getCurrentXmlTest().getName()));
        for (IMethodInstance method : methods) {
            _logger.info(String.format("@Test     : %s", method.getMethod().getQualifiedName()));
        }
        _logger.info("-------------------------------------------------------");
        return methods;
    }
}
