package com.pom.steps.orders;

import com.autoui.fwk.annotations.Pages;
import com.autoui.fwk.annotations.Step;
import com.autoui.fwk.reporting.Logger;
import com.pom.pages.orders.OrderDetailsPageView;
import com.pom.pages.orders.PlaceOrderPageView;
import com.pom.steps.AbstractHomeSteps;
import org.testng.Assert;

public class OrderDetailsSteps extends AbstractHomeSteps  {

    private static final Logger _logger = Logger.getLogger(OrderDetailsSteps.class);

    // Pages ----------------------------------------------
    @Pages
    private OrderDetailsPageView orderDetailsPageView;

    @Pages
    private PlaceOrderPageView placeOrderPageView;


    // Steps ----------------------------------------------
    @Step
    public void fillOrderParameterDetailsMainParam(String providerName) {
        orderDetailsPageView.fillMainParameterPage(providerName);
        Assert.assertTrue(placeOrderPageView.clickOnNextButton(),"Next button enabled post filling Main parameters");
    }

    @Step
    public void fillOrderParameterDetailsAdditionalParam(String providerName) {
        orderDetailsPageView.fillAdditionalParameterPage(providerName);
    }

    @Step
    public  void clickOnSubmitButton(){
        Assert.assertTrue(placeOrderPageView.clickOnSubmitButton(),"Submit button enabled post filling Additional parameters");
    }
}
