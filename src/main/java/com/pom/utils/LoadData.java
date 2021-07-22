package com.pom.utils;

import com.autoui.fwk.annotations.Step;
import com.autoui.fwk.core.BaseTest;
import com.autoui.fwk.core.WebTestSteps;
import com.autoui.fwk.reporting.Logger;
import com.autoui.fwk.utils.JacksonUtils;
import com.autoui.fwk.utils.JsonUtils;
import com.autoui.fwk.utils.PropertyUtils;
import org.json.simple.JSONObject;

import java.util.LinkedHashMap;
import java.util.Properties;

public class LoadData extends WebTestSteps {

    private static Logger _logger = Logger.getLogger(LoadData.class);
    private static Properties properties = null;
    private static JsonUtils jsonUtils = new JsonUtils();
    private static JacksonUtils jacksonUtils = new JacksonUtils();
    private static final String TEST_DATA_PROPERTIES = "testdata.properties";
    private static final String RESOURCES = "\\src\\test\\resources\\";
    private static final String MCMP = "mcmp";
    private static final String APPLICATION_URL_DATA = "application.url";
    private static final String APPLICATION_TITLE_DATA = "application.title";
    private static final String MCMP_UI_DATA = "mcmp.ui.data";

    private void loadPropertyData() {
        properties = PropertyUtils.readProperties(System.getProperty("user.dir") + RESOURCES + TEST_DATA_PROPERTIES);
        _logger.info("Loaded the " + TEST_DATA_PROPERTIES + " file property data");
    }

    @Step
    public JSONObject loadApplicationUrl() {
        loadPropertyData();
        _logger.info("Loading the " + APPLICATION_URL_DATA + " data");
        return jsonUtils.readJsonFromFile(System.getProperty("user.dir") + RESOURCES + MCMP + "\\" + properties.getProperty(APPLICATION_URL_DATA));
    }

    @Step
    public JSONObject loadApplicationTitle() {
        loadPropertyData();
        _logger.info("Loading the " + APPLICATION_TITLE_DATA + " data;");
        return jsonUtils.readJsonFromFile(System.getProperty("user.dir") + RESOURCES + MCMP + "\\" + properties.getProperty(APPLICATION_TITLE_DATA));
    }

    @Step
    public JSONObject loadMCMPUIData() {
        loadPropertyData();
        _logger.info("Loading the " + MCMP_UI_DATA + " data;");
        return jsonUtils.readJsonFromFile(System.getProperty("user.dir") + RESOURCES + MCMP + "\\" + properties.getProperty(MCMP_UI_DATA));
    }

    @Step
    public JSONObject loadTestDataFile(String folderName, String testName) {
        _logger.info("Loading the test data under folder " + folderName + " for Test " + BaseTest.getTestObject().getTestName());
        return jsonUtils.readJsonFromFile(System.getProperty("user.dir") + RESOURCES + MCMP + "\\" + folderName + "\\" + testName + ".json");
    }


    @Step
    public LinkedHashMap<String, Object> loadTestDataFile(String folderName) {
        _logger.info("Loading the test data under folder " + folderName );
        String testNameOutput =  BaseTest.getTestObject().getTestName().split("\\.")[( BaseTest.getTestObject().getTestName().split("\\.")).length - 1];
        return jacksonUtils.getJsonMapFromfile(System.getProperty("user.dir") + RESOURCES + MCMP + "\\" + folderName + "\\" + testNameOutput + ".json");
    }

    @Step
    public Object getParamValue(JSONObject jsonObject, String paramName) {
        _logger.info("Returning value for params :" + paramName + " as [" + jsonObject.get(paramName) + "]");
        return jsonObject.get(paramName);
    }

    @Step
    public Object getParamValue(LinkedHashMap<String, Object> mapObject, String paramName) {
        _logger.info("Returning value for params :" + paramName + " as [" + mapObject.get(paramName) + "]");
        return mapObject.get(paramName);
    }

}
