package com.pom.steps.orders;

import com.automacent.fwk.annotations.Pages;
import com.automacent.fwk.annotations.Step;
import com.automacent.fwk.reporting.Logger;
import com.pom.pages.AbstractHomePageView;
import com.pom.pages.orders.FillOrderDetailsPagesView;
import com.pom.pages.orders.PlaceOrderPageView;

public class FillOrderDetailsSteps {

    private static final Logger _logger = Logger.getLogger(FillOrderDetailsSteps.class);

    @Pages
    private FillOrderDetailsPagesView fillOrderDetailsPagesView;
    @Pages
    private PlaceOrderPageView placeOrderPageView;

    @Step
    public void fillOrderParameterDetailsMainParam(String providerName) {
        fillOrderDetailsPagesView.fillMainParameterPage(providerName);
        placeOrderPageView.clickOnNextButton();
    }

    public void fillOrderParameterDetailsAdditionalParam(String providerName) {
        fillOrderDetailsPagesView.fillAdditionalParameterPage(providerName);
    }
}
