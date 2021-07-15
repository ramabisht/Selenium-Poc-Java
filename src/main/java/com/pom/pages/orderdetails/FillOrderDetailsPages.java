package com.pom.pages.orderdetails;

import com.automacent.fwk.annotations.Action;
import com.automacent.fwk.annotations.Step;
import com.automacent.fwk.annotations.Steps;
import com.automacent.fwk.core.BaseTest;
import com.automacent.fwk.core.PageObject;
import com.automacent.fwk.reporting.Logger;
import com.pom.pages.pageobjects.PlaceOrderPage;
import com.pom.utils.LoadData;
import org.json.simple.JSONObject;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.testng.Assert;
import java.util.HashMap;
import java.util.Map;



public class FillOrderDetailsPages extends PageObject {


    private static final String ORDER_DETAIL_TITLE_CSS = "h1.ibm--page-header__title" ;
    private static final String ORDERPARAMETERS = "Order Parameters";
    private static final String MAINPARAMETER = "Main Parameters";
    private static final String ADDITIONALPARAMETER = "Additional Parameters";
    private static final Logger _logger = Logger.getLogger(FillOrderDetailsPages.class);

    @Override
    public PageValidation pageValidation() {
        return new PageValidation() {
            @Override
            public void validate() {
                validateOrderPageLoaded();
            }

            @Step
            public void validateOrderPageLoaded() {
                Assert.assertTrue(isOrderPageHeaderPresent(), "Is place order page heading is present");

            }
        };
    }

    @Steps
    private LoadData loadData;
    @Steps
    private PlaceOrderPage placeOrderPage ;
    @Steps
    private FillOrderDetailsData fillOrderDetailsData;

    @FindBy(css=ORDER_DETAIL_TITLE_CSS)
    private WebElement pageHeader;

    @Action
    public boolean isOrderPageHeaderPresent(){
        return pageHeader.isDisplayed();
    }

    @Action
    public String isPageTitleSameAsServiceName(){ //put assert here for String bluePrint name
        return pageHeader.getAttribute("title");
    }

    // after that add here the main and additional parameter header validation logic while calling in steps

    /*---------------------------------------------------Handling diffrent type of data filling in oderds---------------------------*/


    /*-------------------------------Fill oder detail for main parameter page---------------------------------------*/
    @Action
    public JSONObject loadTestData(String providerName){
        return loadData.loadTestDataFile(providerName, BaseTest.getTestObject().getTestName());
    }

    @Action
    public Object getOrderParameters(String providerName){
        return  loadData.getParamValue(loadTestData(providerName) , ORDERPARAMETERS);
    }

    @Action
    public void fillMainParameterPage(String providerName) {
        try {
        _logger.info("Starting filling values in main parameter page");
        boolean verifyMainParaPage = placeOrderPage.verifyLandedToMainParaPage();
        if (verifyMainParaPage) {
                _logger.info("inside if condition");
            ((HashMap)((HashMap) getOrderParameters(providerName)).get(MAINPARAMETER)).forEach((key, value) -> {
                    _logger.info("Filling details for " + key + " parameter in Main parameter page " + "to value "+ value);
                    Map<String, Object> mainParamsValue = (Map<String, Object>) value;
                        String paramId = (String) mainParamsValue.get("id");
                        String paramType = (String) mainParamsValue.get("type");
                        String paramValue = (String) mainParamsValue.get("value");
                        _logger.info("Outer Param id :" + paramId + ", Param type :" + paramType + ", Param Value :" + paramValue);
                        fillOrderDetailsData.fillOrderDetails(paramId, paramValue, paramType);
                });
            }
        }
        catch (Exception ex) {
            _logger.info("Exception:" + ex);
        }
    }

    // Put logic to click on the next button in the steps

    /*-------------------------------Fill oder detail for Additional parameter page---------------------------------------*/
    @Action
    public void fillAdditionalParameterPage(String providerName) {
        boolean verifyAdditionalParaPage = placeOrderPage.verifyLandedToAdditionalParaPage();
        if (verifyAdditionalParaPage) {
            try {
                ((HashMap)((HashMap) getOrderParameters(providerName)).get(ADDITIONALPARAMETER)).forEach((key, value) -> {
                    _logger.info("Filling details for " + key + "parameter in Additional parameter page");
                    Map<String, Object> additionalParamKey = (Map<String, Object>) value;
                    if((String) additionalParamKey.get("id") != null) {
                        String paramId = "", paramType = "", paramValue = "";
                        paramId = (String) additionalParamKey.get("id");
                        paramType = (String) additionalParamKey.get("type");
                        paramValue = (String) additionalParamKey.get("value");
                        _logger.info("Filling details for " + key + "parameter in Additional parameters page" + "to value "+ paramValue);
                        //System.out.println("Outer Param id :" + paramId + ", Param type :" + paramType + ", Param Value :" + paramValue);
                        fillOrderDetailsData.fillOrderDetails(paramId, paramType,paramValue);
                        placeOrderPage.clickOnNextButton();
                    } else {
                        additionalParamKey.forEach((parkey, parValue) -> {
                            String paramId = "", paramType = "", paramValue = "";
                            Map<String, Object> additionalParamValue = (Map<String, Object>) parValue;
                            //_logger.info("Filling details for " + parkey + " parameter in Additional parameters page");
                            paramId = (String) additionalParamValue.get("id");
                            paramType = (String) additionalParamValue.get("type");
                            paramValue = (String) additionalParamValue.get("value");
                            _logger.info("Filling details for " + parkey + " parameter in Additional parameters page" + "to value "+ paramValue);
                            fillOrderDetailsData.fillOrderDetails(paramId, paramType,paramValue);
                            //System.out.println("Outer Param id :" + paramId + ", Param type :" + paramType + ", Param Value :" + paramValue);
                        });
                        placeOrderPage.clickOnNextButton();
                    }
                });
            } catch (Exception ex) {
                _logger.info("Exception:" + ex);
            }
        }
    }
    }


    // Put logic to click on the next button in the steps

    /*-------------------------------Put logic here for review order---------------------------------------*/

    // Put logic to click on the Submit button in the steps


