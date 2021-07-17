package com.pom.steps.orderdetails;

import com.automacent.fwk.annotations.Pages;
import com.automacent.fwk.annotations.Step;
import com.pom.pages.orderdetails.FillOrderDetailsPages;
import com.pom.pages.pageobjects.PlaceOrderPage;

public class FillOrderDetailsSteps {

    @Pages
    private FillOrderDetailsPages fillOrderDetailsPages;
    @Pages
    private PlaceOrderPage placeOrderPage;

    @Step
    public void fillOrderParameterDetailsMainParam(String providerName) {
        fillOrderDetailsPages.fillMainParameterPage(providerName);
        placeOrderPage.clickOnNextButton();
    }

    public void fillOrderParameterDetailsAdditionalParam(String providerName) {
        fillOrderDetailsPages.fillAdditionalParameterPage(providerName);
    }
}
