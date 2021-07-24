package com.pom.steps.approveOrder;

import com.autoui.fwk.annotations.Pages;
import com.autoui.fwk.annotations.Step;
import com.autoui.fwk.reporting.Logger;
import com.autoui.fwk.utils.ThreadUtils;
import com.pom.pages.approveOrders.AllOrdersPageView;
import com.pom.pages.approveOrders.PendingApprovalPageView;
import com.pom.pages.catalog.CatalogPageView;
import com.pom.pages.orders.OrderDetailsPageView;
import com.pom.steps.AbstractHomeSteps;
import com.pom.steps.catalog.CatalogPageSteps;
import com.pom.steps.orders.ReviewOrderPageSteps;
import org.testng.Assert;

public class ApproveOrderSteps extends AbstractHomeSteps {
    private static final Logger _logger = Logger.getLogger(OrderDetailsPageView.class);
    @Pages
    private PendingApprovalPageView pendingApprovalPageView;

    @Pages
    private AllOrdersPageView allOrdersPageView;

    @Pages
    private ReviewOrderPageSteps reviewOrderPageSteps;

    @Pages
    private CatalogPageView catalogPageView;

    /******Steps to Approve Pending Order *********/

    @Step
    public void verifyLandedOnPendingApprovalTab(){
        Assert.assertTrue(pendingApprovalPageView.isPendingApprovalTabPresent());
    }

    @Step
    public void searchForOrderId(String orderId){
        Assert.assertTrue(pendingApprovalPageView.searchForOrderInPage(orderId));

    }

    @Step
    public void verifyOrderIdIsDisplayedAfterSearch(String orderId){
        _logger.info("Verify the displed order id is same as "+ orderId);
        Assert.assertTrue(pendingApprovalPageView.OrderIdIsDisplayed());
        Assert.assertEquals(pendingApprovalPageView.getOrderIdIsDisplayed(), orderId, "OrderId "+orderId+" is present after search for pending approval");
    }

    @Step
    public void clickOnTheApproveButton(){
        Assert.assertTrue(pendingApprovalPageView.clickOnApproveButton(), "Clicked on Approve Button");
    }

    @Step
    public void verifyOrderApprovalDialogueIsDisplayed(){
        Assert.assertTrue(pendingApprovalPageView.isOrderApprovalFlowDisplayed(), "Order approval dialogue is displayed");
    }

    @Step
    public void clickOnFinancialAndTechnicalApproval(){
        Assert.assertTrue(pendingApprovalPageView.clickOnFinancialApproval(),"Clicked on Financial Approval checkbox");
        Assert.assertTrue(pendingApprovalPageView.clickOnTechnicalApproval(),"Clicked on Technical Approval checkbox");
    }

    @Step
    public void approveOrderInDialogue(){
        Assert.assertTrue(pendingApprovalPageView.clickOnApproveOrderDetail(),"Clicked on Approve Orders");
    }

    @Step
    public void validateApproveSuccessMessage(String orderApprovedMessage){ //orderApproved =  Approval Processed from mcmp file
        Assert.assertEquals(pendingApprovalPageView.verifyApproveSuccessMessage(), orderApprovedMessage, "Order has been approved");
    }

    @Step
    public void clickOnOKButtonInDialogue(){
        Assert.assertTrue(pendingApprovalPageView.clickOnApproveOKButton(), "Clicked on approve Ok button");
    }

    /****** Steps to check service is provisioned Order *********/

    @Step
    public void navigateToAllOrdersTab(){
        Assert.assertTrue(allOrdersPageView.clickOnAllOrdersTab(), "Navigated to All orders Tab");
    }

    @Step
    public void validateOrderProvisioningCompleted(){
        Assert.assertTrue(allOrdersPageView.orderStatusAfterApproval(), "Order Status is visible");
    }

    @Step
    public void validateProvisioningIsCompleted(String expectedOrderStatus, String orderId){
        Assert.assertTrue(allOrdersPageView.verifyOrderIsCompleted(expectedOrderStatus,orderId), "Provisioning has been completed");
    }


    /**** Method will be called in spec file for pending order approval ****/

    @Step
    public void approvePendingOrder(String orderApprovedMessage, String orderId){
        _logger.info("Approve pending order in Approve Orders Page");
        //String orderId = reviewOrderPageSteps.submittedOrderNumber(); paas thi value in the test case level
        //verifyLandedOnPendingApprovalTab();
        //Assert.assertTrue(catalogPageView.switchToCatalogIFrame(), "switch iframe");
        _logger.info("Searching for order in pending approval page");
        ThreadUtils.sleepFor(50);
        searchForOrderId(orderId);
        verifyOrderIdIsDisplayedAfterSearch(orderId);
        clickOnTheApproveButton();
        verifyOrderApprovalDialogueIsDisplayed();
        clickOnFinancialAndTechnicalApproval();
        approveOrderInDialogue();
        validateApproveSuccessMessage(orderApprovedMessage);
        clickOnOKButtonInDialogue();
    }

    @Step
    public void verifyProvisioningIsCompleted(String expectedOrderStatus, String orderId){
        _logger.info("Navigate to all orders tab");
        navigateToAllOrdersTab();
        _logger.info("Searching for order in All Services approval page");
        searchForOrderId(orderId);
        //if(allOrdersPageView.gTextNoOrderFoundStatus()) //after the first search check if no rodr fiumd ,essgae is diapleyed can impletemt later
        validateProvisioningIsCompleted(expectedOrderStatus,orderId);
    }

}
