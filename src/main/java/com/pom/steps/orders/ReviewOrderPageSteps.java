package com.pom.steps.orders;

import com.autoui.fwk.annotations.Pages;
import com.autoui.fwk.annotations.Step;
import com.autoui.fwk.reporting.Logger;
import com.pom.pages.orders.OrderDetailsPageView;
import com.pom.pages.orders.ReviewOrderPage;
import com.pom.steps.AbstractHomeSteps;
import org.testng.Assert;

public class ReviewOrderPageSteps extends AbstractHomeSteps {

    private static final Logger _logger = Logger.getLogger(OrderDetailsPageView.class);

    // Pages ----------------------------------------------
    @Pages
    private ReviewOrderPage reviewOrderPage;

    // Steps ----------------------------------------------

    @Step
    public void verifyOrderSubmitMessage(){
        Assert.assertTrue(reviewOrderPage.verifyOrderIsSubmitted(), "Order Submitted message is displayed");
    }

    @Step
    public String getOrderNumber(){
        Assert.assertTrue(reviewOrderPage.isOderNumberPresent(), "Submitted order number is displayed");
         String orderId = reviewOrderPage.getOrderNumber();
         _logger.info("Submitted OrderId is "+ orderId);
        return orderId;
    }

    @Step
    public void navigateToCatalogPage(){
        Assert.assertTrue(reviewOrderPage.clickOnServiceCatalogPresent());
    }


    @Step
    public String submittedOrderNumber(){
        verifyOrderSubmitMessage();
        String orderId =  getOrderNumber();
       // navigateToCatalogPage();
        return orderId;
    }

    @Step
    public void navigateToCatalogAfterOrderSubmission() {
        navigateToCatalogPage();
    }


}

