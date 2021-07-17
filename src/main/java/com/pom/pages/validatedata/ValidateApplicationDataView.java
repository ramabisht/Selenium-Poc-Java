package com.pom.pages.validatedata;

import com.automacent.fwk.annotations.Action;
import com.automacent.fwk.annotations.Steps;
import com.automacent.fwk.core.PageObject;
import com.pom.pages.orders.PlaceOrderPageView;

public class ValidateApplicationDataView extends PageObject {

    @Override
    public PageValidation pageValidation() {
        return null;
    }

    @Steps
    private PlaceOrderPageView placeOrderPageView;

    @Action
    public void validateReviewOrderPage() {
        boolean verifyReviewOrderPage = placeOrderPageView.verifyLandedToAdditionalParaPage();
        if (verifyReviewOrderPage) {

        }
    }

}
