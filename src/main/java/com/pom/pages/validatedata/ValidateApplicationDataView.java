package com.pom.pages.validatedata;

import com.autoui.fwk.annotations.Action;
import com.autoui.fwk.annotations.Steps;
import com.autoui.fwk.core.PageObject;
import com.autoui.fwk.reporting.Logger;
import com.pom.pages.orders.PlaceOrderPageView;

public class ValidateApplicationDataView extends PageObject {

    @Override
    public PageValidation pageValidation() {
        return null;
    }


    private static final Logger _logger = Logger.getLogger(ValidateApplicationDataView.class);

    @Steps
    private PlaceOrderPageView placeOrderPageView;

    // Actions--------------------------------------------------------------
    @Action
    public void validateReviewOrderPage() {
        boolean verifyReviewOrderPage = placeOrderPageView.verifyLandedToAdditionalParameterPage();
        if (verifyReviewOrderPage) {

        }
    }

}
