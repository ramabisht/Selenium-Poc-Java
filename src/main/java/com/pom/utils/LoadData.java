package com.pom.utils;

import com.automacent.fwk.annotations.Step;
import com.automacent.fwk.core.WebTestSteps;
import com.automacent.fwk.reporting.Logger;
import com.automacent.fwk.utils.JsonUtils;
import com.automacent.fwk.utils.PropertyUtils;
import org.json.simple.JSONObject;

import java.util.Properties;

public class LoadData extends WebTestSteps {

    private static Logger _logger = Logger.getLogger(LoadData.class);

    private static Properties properties = null;

    private static JsonUtils jsonUtils = new JsonUtils();

   private void loadPropertyData(){
        properties = PropertyUtils.readProperties(System.getProperty("user.dir") + "\\src\\test\\resources\\testdata.properties");
        _logger.info("Loaded the property data;");
    }

    @Step
    public void loadApplicationUrl(){
        loadPropertyData();
        JSONObject jsonObject = jsonUtils.readJsonFromFile(System.getProperty("user.dir") + "\\src\\test\\resources\\mcmp\\"+ properties.getProperty("application.url"));
        _logger.info("loaded the application Url");
        _logger.info("Launchpad url:" + jsonObject.get("launchpadUrl"));
    }
}
