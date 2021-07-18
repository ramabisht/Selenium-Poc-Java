package com.pom.pages.validatedata;

import com.automacent.fwk.annotations.Action;
import com.automacent.fwk.annotations.Steps;
import com.automacent.fwk.core.PageObject;
import com.automacent.fwk.reporting.Logger;
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
