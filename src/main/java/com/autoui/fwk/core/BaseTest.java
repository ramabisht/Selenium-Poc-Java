package com.autoui.fwk.core;

import java.util.HashMap;
import java.util.Map;

import com.autoui.fwk.enums.HarType;
import com.autoui.fwk.enums.RepeatMode;
import com.autoui.fwk.enums.RetryMode;
import com.autoui.fwk.launcher.LauncherClientManager;
import com.autoui.fwk.recovery.RecoveryManager;
import com.autoui.fwk.reporting.ReportingTools;
import com.autoui.fwk.utils.ThreadUtils;
import org.testng.ITestContext;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;

import com.autoui.fwk.annotations.Action;
import com.autoui.fwk.reporting.Logger;

/**
 * All Web services and Unit test classes must inherit this class. Class
 * contains essential setup code for the framework to work
 *
 * @author rama.bisht
 */
public abstract class BaseTest {

    private static final Logger _logger = Logger.getLogger(BaseTest.class);

    private static Map<Long, TestObject> testObjectMap = new HashMap<>();

    /**
     * Get the {@link TestObject} for the current test. If {@link TestObject} is not
     * initiated for current Test, a new {@link TestObject}is created
     *
     * @return {@link TestObject}
     */
    public synchronized static TestObject getTestObject() {
        long threadId = ThreadUtils.getThreadId();
        if (!testObjectMap.containsKey(threadId)) {
            _logger.info(String.format("Constructing Test Object for threadId %s", threadId));
            testObjectMap.put(threadId, new TestObject());
        }
        return testObjectMap.get(threadId);
    }

    /**
     * Get the test parameter defined by {@link Parameters} or added to the test
     * parameter dynamically by calling
     * {@link BaseTest#addTestParameter(String, String)} or
     * {@link BaseTest#appendTestParameter(String, String)}
     *
     * @param key Name of the parameter
     * @return Parameter value
     */
    protected String getTestParameter(String key) {
        return BaseTest.getTestObject().getTestParameter(key);
    }

    /**
     * Add test parameter. If the parameter with key already exists, the value is
     * overwritten
     *
     * @param key   Name of the parameter
     * @param value Value of the parameter
     */
    protected void addTestParameter(String key, String value) {
        BaseTest.getTestObject().addTestParameter(key, value);
    }


    /**
     * Add test parameter. If the parameter with key already exists, the value is
     * overwritten
     */
    protected void startHarCollection() {
        try {
            if (BaseTest.getTestObject().getHarType() != null
                    && !BaseTest.getTestObject().getHarType().equals(HarType.NOT_ENABLED)) {
                ReportingTools.startHarCapture();
                _logger.info("Har capturing started...");
            }
        } catch (Exception ex) {
            _logger.error("Har capturing enablement failed onStart() reason " + ex);
        }
    }

    /**
     * Append test parameter. this method will get the test parameter with the given
     * key and append the provided value as comma seperated String
     *
     * @param key   Name of the parameter
     * @param value Value of the parameter
     */
    protected void appendTestParameter(String key, String value) {
        BaseTest.getTestObject().appendTestParameter(key, value);
    }

    /**
     * Set up launcher clients for framework.Launcher clients are REST based
     * services which can update test run result to a custom dashboard. These
     * parameters are set for the whole Test suite
     *
     * @param launcherClients   Comma separated list of fully qualified launcher
     *                          client class names
     * @param runName           Run Name in the logger application
     * @param batchNumber       Batch number in the logger application
     * @param slackWebHookUrl   SlackWebHook Url
     * @param harCollectionType {@link HarType}
     */
    @BeforeSuite
    @Parameters({
            "launcherClients",
            "runName",
            "batchNumber",
            "slackWebHookUrl",
            "harCollectionType"
    })
    public void autouiInternalSetLauncherClients(
            String launcherClients,
            String runName,
            String batchNumber,
            String slackWebHookUrl,
            String harCollectionType) {
        LauncherClientManager.getManager().generateLauncherClientMasterMap(launcherClients);

        if (slackWebHookUrl != null)
            BaseTest.getTestObject().setSlackWebHookUrl(slackWebHookUrl);
        else
            BaseTest.getTestObject().setSlackWebHookUrl("");

        if (harCollectionType != null)
            BaseTest.getTestObject().setHarType(harCollectionType);
        else
            BaseTest.getTestObject().setHarType(HarType.getDefault().toString());
    }

    /**
     * Set up common parameters required by the framework. These parameters will be
     * set at Test Level. Each test can provide their custom set of parameters
     *
     * @param repeatMode                     {@link RepeatMode}
     * @param testDurationInSeconds          Duration for which the tests should run
     *                                       in case {@link RepeatMode} ==
     *                                       {@code TEST_DURATION}
     * @param invocationCount                Number of times the tests should run in
     *                                       case {@link RepeatMode} ==
     *                                       {@code INVOCATION_COUNT}
     * @param delayBetweenIterationInSeconds Delay between each repeat in case
     *                                       {@link RepeatMode} ==
     *                                       {@code INVOCATION_COUNT} ||
     *                                       {@code TEST_DURATION}
     * @param timeoutInSeconds               Wait time before exception is thrown
     * @param slowdownDurationInSeconds      Wait between subsequent {@link Action}
     *                                       methods
     * @param retryMode                      {@link RetryMode}
     * @param recoveryClasses                Comma separated list of fully qualified
     *                                       recovery class names in case the
     *                                       {@code RetryMode} == ${code ON}
     * @param testContext                    testNg {@link ITestContext}
     */
    @BeforeTest
    @BeforeClass
    @Parameters({
            "repeatMode",
            "testDurationInSeconds",
            "invocationCount",
            "delayBetweenIterationInSeconds",
            "timeoutInSeconds",
            "slowdownDurationInSeconds",
            "retryMode",
            "recoveryClasses",
            "harCollectionType"
    })
    public void autouiInternalSetParameters(
            RepeatMode repeatMode,
            long testDurationInSeconds,
            long invocationCount,
            long delayBetweenIterationInSeconds,
            long timeoutInSeconds,
            long slowdownDurationInSeconds,
            RetryMode retryMode,
            String recoveryClasses,
            ITestContext testContext,
            String harCollectionType) {
        System.setProperty("org.uncommons.reportng.escape-output", "false");
        TestObject testObject = BaseTest.getTestObject();
        testObject.setTestContext(testContext);
        testObject.setRepeatMode(repeatMode);
        testObject.setTestDurationInSeconds(testDurationInSeconds);
        testObject.setInvocationCount(invocationCount);
        testObject.setDelayBetweenIterationInSeconds(delayBetweenIterationInSeconds);
        testObject.setTimeoutInSeconds(timeoutInSeconds);
        testObject.setSlowdownDurationInSeconds(slowdownDurationInSeconds);
        testObject.setRetryMode(retryMode);
        testObject.setRecoveryManager(new RecoveryManager(recoveryClasses));

        if (harCollectionType != null)
            testObject.setHarType(harCollectionType);
        else
            testObject.setHarType(HarType.getDefault().toString());
    }
}
