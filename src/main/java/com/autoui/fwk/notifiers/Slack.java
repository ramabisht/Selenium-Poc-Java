package com.autoui.fwk.notifiers;


import com.autoui.fwk.core.BaseTest;
import com.autoui.fwk.reporting.Logger;
import com.autoui.fwk.rest.Client;
import org.testng.ITestResult;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Slack {

    private static final Logger _logger = Logger.getLogger(Slack.class);

    public static void postRequestToSlack(String webHookUrl, String body) {
        Client client = new Client();
        _logger.info("Posting slack message to url :" + webHookUrl + ", content :" + body);
        client.validatePostResponseCode(client.postRequestByUrlAndHeadersAndBodyContent(webHookUrl, client.getDefaultHeader(), body));
    }

    public static void postRequestToSlack(String webHookUrl, HashMap<String, String> body) {
        Client client = new Client();
        _logger.info("Posting slack message to url :" + webHookUrl + ", content :" + body);
        new Client().validatePostResponseCode(client.postRequestByUrlAndHeadersAndBodyObject(webHookUrl, client.getDefaultHeader(), (Object) body));
    }

    public static String getSlackText(HashMap<String, Object> testData) {
        int passTest = ((List) testData.get("Pass")).size();
        int failTest = ((List) testData.get("Fail")).size();
        int skipTest = ((List) testData.get("Skip")).size();
        int totalTest = passTest + failTest + skipTest;
        float passPercentage = ((float) passTest / (float) totalTest) * 100;
        float failPercentage = ((float) failTest / (float) totalTest) * 100;
        float skipPercentage = ((float) skipTest / (float) totalTest) * 100;
        String outputDirectory = "", suiteName = "";
        List<String> failedTests = new ArrayList<>();
        List<String> skippedTests = new ArrayList<>();

        _logger.info("Total test count :" + totalTest + ", Pass test :" + passTest + ", Failed Test :" + failTest + ", Skip test :" + skipTest);
        _logger.info("Pass test % " + passPercentage + ", Failed Test % " + failPercentage + ", Skip test % " + skipPercentage);

        for (Map.Entry<String, Object> eachData : testData.entrySet()) {
            _logger.info("Status :" + eachData.getKey());
            for (Object testResult : (ArrayList) eachData.getValue()) {
                HashMap<String, Object> resultMap = (HashMap) testResult;
                for (Map.Entry<String, Object> eachTestData : resultMap.entrySet()) {
                    _logger.info("Test Name :" + eachTestData.getKey());
                    ITestResult result = (ITestResult) eachTestData.getValue();
                    if (eachData.getKey().equals("Fail")) {
                        failedTests.add(result.getTestContext().getName() + "." + result.getName());
                    } else if (eachData.getKey().equals("Skip")) {
                        skippedTests.add(result.getTestContext().getName() + "." + result.getName());
                    }

                    outputDirectory = result.getTestContext().getOutputDirectory();
                    suiteName = result.getTestContext().getSuite().getName();

                    /*
                    _logger.info("Failed Test Name:" + result.getName() + "Name " + result.getTestName()
                            + "Name " + result.getTestContext().getName() + ", Status " + result.getStatus()
                            + ", Start Time: " + result.getStartMillis() + ", End time " + result.getEndMillis() +
                            + ", " + result.getHost() + ", " + result.getInstanceName() + ", " + result.getAttributeNames().toString()
                            + ", " + result.getTestContext().getHost() +
                            ", " + result.getTestContext().getOutputDirectory() + ", "
                            + result.getTestContext().getStartDate().toString() + ", "
                             + result.getTestContext().getEndDate().toString());
                    */
                }
            }
        }


        String slackText = "* Result for Suite :" + suiteName + "* \n";

        slackText += "Instance url: <" + BaseTest.getTestObject().getBaseUrl() + ">\n";
        slackText += "Total Test count :" + totalTest + "\n";

        if (passTest > 0) {
            slackText += "Pass Test count :" + passTest + " (" + passPercentage + "%)" + "\n";
        } else {
            slackText += "Pass Test count : 0 (0.0%)" + "\n";
        }

        if (failTest > 0) {
            slackText += "Fail Test count :" + failTest + " (" + failPercentage + "%)" + "\n";
            slackText += "Failed Test list [";
            for (String testName : failedTests) {
                slackText += testName + ",";
            }
            slackText += "]\n";
        } else {
            slackText += "Fail Test count : 0 (0.0%)" + "\n";
        }

        if (skipTest > 0) {
            slackText += "Skip Test count :" + skipTest + " (" + skipPercentage + "% )" + "\n";
            slackText += "Skipped Test list [";
            for (String testName : skippedTests) {
                slackText += testName + ",";
            }
            slackText += "]\n";
        } else {
            slackText += "Skip Test count : 0 (0.0%)" + "\n";
        }

        //slackText += "Output directory [" + outputDirectory + "]\n";
        slackText += "*Ends*";

        _logger.info("Text formed :" + slackText);
        return slackText;
    }
}
