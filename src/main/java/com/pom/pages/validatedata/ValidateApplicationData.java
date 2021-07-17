package com.pom.pages.validatedata;

import com.automacent.fwk.annotations.Action;
import com.automacent.fwk.annotations.Steps;
import com.automacent.fwk.core.PageObject;
import com.pom.pages.orders.PlaceOrderPage;

public class ValidateApplicationData extends PageObject {

    @Override
    public PageValidation pageValidation() {
        return null;
    }

    @Steps
    private PlaceOrderPage placeOrderPage;

    @Action
    public void validateReviewOrderPage() {
        boolean verifyReviewOrderPage = placeOrderPage.verifyLandedToAdditionalParaPage();
        if (verifyReviewOrderPage) {

        }
    }

}
