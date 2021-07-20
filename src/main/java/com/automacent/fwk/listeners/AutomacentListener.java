package com.automacent.fwk.listeners;

import java.util.*;
import java.util.logging.LogManager;

import com.automacent.fwk.notifiers.Slack;
import com.automacent.fwk.rest.Client;
import com.automacent.fwk.utils.JacksonUtils;
import org.testng.*;

import com.automacent.fwk.annotations.StepsAndPagesProcessor;
import com.automacent.fwk.core.BaseTest;
import com.automacent.fwk.enums.RepeatMode;
import com.automacent.fwk.enums.RetryMode;
import com.automacent.fwk.enums.ScreenshotMode;
import com.automacent.fwk.enums.ScreenshotModeForIteration;
import com.automacent.fwk.enums.ScreenshotType;
import com.automacent.fwk.exceptions.TestOrConfigurationSkipException;
import com.automacent.fwk.execution.IterationManager;
import com.automacent.fwk.launcher.LauncherClientManager;
import com.automacent.fwk.reporting.ExecutionLogManager;
import com.automacent.fwk.reporting.Logger;
import com.automacent.fwk.reporting.ReportingTools;
import com.automacent.fwk.utils.FileUtils;
import com.automacent.fwk.utils.ThreadUtils;

import io.github.bonigarcia.wdm.DriverManagerType;

/**
 * This is the custom reporter for Automacent framework and should be specified
 * in the testNG listener. It contains methods for logging information on after
 * test completion (failure/pass)
 *
 * @author rama.bisht
 */
public class AutomacentListener extends TestListenerAdapter
        implements IInvokedMethodListener, IExecutionListener, IMethodInterceptor, ISuiteListener {

    private static final Logger _logger = Logger.getLogger(AutomacentListener.class);
    HashMap<String, Object> testResult = new HashMap<String, Object>();
    List<Map<String, Long>> passedTest = new ArrayList<Map<String, Long>>();
    List<Map<String, Long>> failedTest = new ArrayList<Map<String, Long>>();
    List<Map<String, Long>> skippedTest = new ArrayList<Map<String, Long>>();

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
        value = value == null || value.isEmpty() ? System.getProperty(String.format("automacent.%s", key), defaultValue)
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

        // automacentInternalSetLauncherClients -----------

        setDefaultParameters(parameters, "launcherClients", "");
        setDefaultParameters(parameters, "runName", "");
        setDefaultParameters(parameters, "batchNumber", "");

        // automacentInternalSetParameters ----------------

        setDefaultParameters(parameters, "repeatMode", RepeatMode.OFF.name());
        setDefaultParameters(parameters, "testDurationInSeconds", "0");
        setDefaultParameters(parameters, "invocationCount", "0");
        setDefaultParameters(parameters, "delayBetweenIterationInSeconds", "0");
        setDefaultParameters(parameters, "timeoutInSeconds", "20");
        setDefaultParameters(parameters, "slowdownDurationInSeconds", "0");
        setDefaultParameters(parameters, "retryMode", RetryMode.OFF.name());
        setDefaultParameters(parameters, "recoveryClasses", "");

        // automacentInternalSetDriverParameters ----------

        setDefaultParameters(parameters, "ieDriverLocation", "");
        setDefaultParameters(parameters, "chromeDriverLocation", "");
        setDefaultParameters(parameters, "geckoDriverLocation", "");
        setDefaultParameters(parameters, "scriptTimeoutInSeconds", "300");
        setDefaultParameters(parameters, "pageLoadTimeoutInSeconds", "300");
        setDefaultParameters(parameters, "socketTimeoutInSeconds", "300");

        // automacentInternalSetWebTestParameters ---------

        setDefaultParameters(parameters, "browser", DriverManagerType.CHROME.name());
        setDefaultParameters(parameters, "debuggerAddress", "");
        setDefaultParameters(parameters, "screenshotType", ScreenshotType.BROWSER_SCREENSHOT.name());
        setDefaultParameters(parameters, "screenshotMode", ScreenshotMode.ON_FAILURE.name());
        setDefaultParameters(parameters, "screenshotModeForIteration",
                ScreenshotModeForIteration.LAST_ITERATION.name());
        setDefaultParameters(parameters, "baseUrl", "");

        _logger.info("Setup default framework parameters completed");
        suite.getXmlSuite().setParameters(parameters);
    }

    @Override
    public void onFinish(ISuite iSuite) {
        _logger.info("Finish tests invoked results");
        iSuite.getResults().forEach((k, v) -> {
            _logger.info("Key >>" + k + " value >>" + v);
        });

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
        _logger.info("onTestFailure invoked");
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
        _logger.info("onTestSkipped invoked");
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
        _logger.info("Finish tests invoked");

        Set<ITestResult> passedTestResults = testContext.getPassedTests().getAllResults();
        for(ITestResult result : passedTestResults){
           _logger.info("Passed Test Name:" + result.getName() + "Name " + result.getTestName() + "Name "+ result.getTestContext().getName() +", Status " + result.getStatus() + ", Start Time: " + result.getStartMillis() + ", End time " + result.getEndMillis() );
           Map<String, Long> results = new HashMap<String, Long>(){{
               put(result.getTestContext().getName(), result.getEndMillis() - result.getStartMillis());
           }};
            passedTest.add(results);
        }

        Set<ITestResult> failedTestResults = testContext.getFailedTests().getAllResults();
        for(ITestResult  result: failedTestResults){
            _logger.info("Failed Test Name:" + result.getName() + "Name " + result.getTestName() + "Name "+ result.getTestContext().getName() +", Status " + result.getStatus() + ", Start Time: " + result.getStartMillis() + ", End time " + result.getEndMillis() );
            Map<String, Long> results = new HashMap<String, Long>(){{
                put(result.getTestContext().getName(), result.getEndMillis() - result.getStartMillis());
            }};
            failedTest.add(results);

        }

        Set<ITestResult> skippedTestResults = testContext.getSkippedTests().getAllResults();
        for(ITestResult  result: skippedTestResults){
            _logger.info("Skipped Test Name:" + result.getName() + "Name " + result.getTestName() + "Name "+ result.getTestContext().getName() +", Status " + result.getStatus() + ", Start Time: " + result.getStartMillis() + ", End time " + result.getEndMillis() );
            Map<String, Long> results = new HashMap<String, Long>(){{
                put(result.getTestContext().getName(), result.getEndMillis() - result.getStartMillis());
            }};
            skippedTest.add(results);
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
        if (!methodName.startsWith("automacentInternal")
                && BaseTest.getTestObject().getDriverManager().getActiveDriver() != null)
            StepsAndPagesProcessor.processAnnotation(invokedMethod.getTestMethod().getInstance());
    }

    /**
     * Delete temporary folder before testNG execution start
     */
    @Override
    public void onExecutionStart() {
        _logger.info("OnExecution tests onExecutionStart() invoked");
        FileUtils.cleanTempDirectory();
    }

    /**
     * Delete temporary folder after testNG execution complete
     */
    @Override
    public void onExecutionFinish() {
        _logger.info("Finish tests onExecutionFinish() invoked");
        testResult.put("Pass", passedTest);
        testResult.put("Fail", failedTest);
        testResult.put("Skip", skippedTest);
        String output = JacksonUtils.getString(testResult);
        _logger.info("Final test result :" + output);
        Slack.postRequestToSlack("", output);
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
