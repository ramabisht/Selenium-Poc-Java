package com.pom.pages.orders;

import com.automacent.fwk.annotations.Action;
import com.automacent.fwk.annotations.Step;
import com.automacent.fwk.annotations.Steps;
import com.automacent.fwk.core.BaseTest;
import com.automacent.fwk.core.PageObject;
import com.automacent.fwk.reporting.Logger;
import com.pom.utils.LoadData;
import org.json.simple.JSONObject;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.testng.Assert;

import java.util.HashMap;
import java.util.Map;


public class OrderDetailsPageView extends PageObject {


    private static final String ORDER_DETAILS_TITLE_CSS = "h1.ibm--page-header__title";
    private static final String ORDER_PARAMETERS = "Order Parameters";
    private static final String MAIN_PARAMETERS = "Main Parameters";
    private static final String ADDITIONAL_PARAMETERS = "Additional Parameters";
    private static final Logger _logger = Logger.getLogger(OrderDetailsPageView.class);

    @Override
    public PageValidation pageValidation() {
        return new PageValidation() {
            @Override
            public void validate() {
                validateOrderPageLoaded();
            }

            @Step
            public void validateOrderPageLoaded() {
                //Change the param for Login page for title and URL accordingly
                LoadData loadData = new LoadData();
                Assert.assertEquals(driver.getTitle(), loadData.getParamValue(loadData.loadApplicationTitle(), "launchpadTitle"), "Page title validation failed");
                Assert.assertEquals(driver.getCurrentUrl(), BaseTest.getTestObject().getBaseUrl() +
                        loadData.getParamValue(loadData.loadApplicationUrl(), "launchpadUrl"), "Page title validation failed");

                Assert.assertTrue(isOrderPageHeaderPresent(), "Is place order page heading is present");

            }
        };
    }

    @Steps
    private LoadData loadData;

    @Steps
    private PlaceOrderPageView placeOrderPageView;

    @Steps
    private OrderDetailsDataView orderDetailsDataView;

    @FindBy(css = ORDER_DETAILS_TITLE_CSS)
    private WebElement pageHeader;

    @Action
    public boolean isOrderPageHeaderPresent() {
        return pageHeader.isDisplayed();
    }

    @Action
    public String isPageTitleSameAsServiceName() { //put assert here for String bluePrint name
        return pageHeader.getAttribute("title");
    }

    // after that add here the main and additional parameter header validation logic while calling in steps

    /*---------------------------------------------------Handling different type of data filling in orders---------------------------*/


    /*-------------------------------Fill oder detail for main parameter page---------------------------------------*/


    @Action
    public JSONObject loadTestData(String providerName) {
        return loadData.loadTestDataFile(providerName);
    }

    @Action
    public Object getOrderParameters(String providerName) {
        return loadData.getParamValue(loadTestData(providerName), ORDER_PARAMETERS);
    }

    @Action
    public void fillMainParameterPage(String providerName) {
        try {
            _logger.info("Starting filling values in main parameter page");
            Assert.assertTrue(placeOrderPageView.verifyLandedToMainParameterPage(), "Main Parameter page opened");
            ((HashMap) ((HashMap) getOrderParameters(providerName)).get(MAIN_PARAMETERS)).forEach((key, value) -> {
                _logger.info("Filling details for " + key + " parameter in Main parameter page to value " + value);
                Map<String, Object> mainParamsValue = (Map<String, Object>) value;
                String paramId = (String) mainParamsValue.get("id");
                String paramType = (String) mainParamsValue.get("type");
                String paramValue = (String) mainParamsValue.get("value");
                _logger.info("Outer Param id :" + paramId + ", Param type :" + paramType + ", Param Value :" + paramValue);
                orderDetailsDataView.fillOrderDetails(paramId, paramValue, paramType);
            });
        } catch (Exception ex) {
            _logger.error("Exception:" + ex);
            Assert.fail("Filling Main parameter page failed because of " + ex.getMessage());
        }
    }

    // Put logic to click on the next button in the steps

    /*-------------------------------Fill oder detail for Additional parameter page---------------------------------------*/
    @Action
    public void fillAdditionalParameterPage(String providerName) {
        Assert.assertTrue(placeOrderPageView.verifyLandedToAdditionalParameterPage(), "Additional parameter page opened");
        try {
            ((HashMap) ((HashMap) getOrderParameters(providerName)).get(ADDITIONAL_PARAMETERS)).forEach((key, value) -> {
                _logger.info("Filling details for " + key + "parameter in Additional parameter page");
                Map<String, Object> additionalParamKey = (Map<String, Object>) value;
                if (additionalParamKey.get("id") != null) {
                    String paramId = "", paramType = "", paramValue = "";
                    paramId = (String) additionalParamKey.get("id");
                    paramType = (String) additionalParamKey.get("type");
                    paramValue = (String) additionalParamKey.get("value");
                    _logger.info("Filling details for " + key + "parameter in Additional parameters page to value " + paramValue);
                    orderDetailsDataView.fillOrderDetails(paramId, paramType, paramValue);
                } else {
                    additionalParamKey.forEach((paramkey, parValue) -> {
                        Map<String, Object> additionalParamValue = (Map<String, Object>) parValue;
                        String paramId = "", paramType = "", paramValue = "";
                        paramId = (String) additionalParamValue.get("id");
                        paramType = (String) additionalParamValue.get("type");
                        paramValue = (String) additionalParamValue.get("value");
                        _logger.info("Filling details for " + paramkey + " parameter in Additional parameters page to value " + paramValue);
                        orderDetailsDataView.fillOrderDetails(paramId, paramType, paramValue);
                    });
                }
                Assert.assertTrue(placeOrderPageView.clickOnNextButton(), "Click on the next button post filling additional param for " + key);
            });
        } catch (Exception ex) {
            _logger.info("Exception:" + ex);
            Assert.fail("Filling Additional parameter page failed because of " + ex.getMessage());
        }
    }
}


// Put logic to click on the next button in the steps

/*-------------------------------Put logic here for review order---------------------------------------*/

// Put logic to click on the Submit button in the steps


