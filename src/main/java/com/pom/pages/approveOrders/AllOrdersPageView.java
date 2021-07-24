package com.pom.pages.approveOrders;

import com.autoui.fwk.annotations.Action;
import com.autoui.fwk.annotations.Pages;
import com.autoui.fwk.core.PageObject;
import com.autoui.fwk.reporting.Logger;
import com.pom.pages.orders.OrderDetailsPageView;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class AllOrdersPageView extends PageObject {

    private static final Logger _logger = Logger.getLogger(OrderDetailsPageView.class);
    private static final String ALL_ORDERS_CSS = "a#tab-control-all_orders_tab";
    private static final String NO_ORDER_FOUND_XPATH = "//p[@class = 'bx--inline-notification__subtitle']/span";
    private static final String ORDER_STATUS_CSS = ".bx--table-body tr td:nth-child(3) span"; //correct this xpath get the value based on the olum header //*[contains(text(),'Status')] extend this one


    @Override
    public PageValidation pageValidation() {
        return null;
    }

    @Pages
    private PendingApprovalPageView pendingApprovalPageView;

    @FindBy(className = ALL_ORDERS_CSS)
    private WebElement allOrdersTab;

    @Action
    private boolean isAllOrdersTabIsPresent(){
        return isElementFound(allOrdersTab) && isClickableElementFound(allOrdersTab);
    }

    @Action
    public boolean clickOnAllOrdersTab(){
        if(isAllOrdersTabIsPresent()){
            _logger.info("All Orders Tab is visible");
            allOrdersTab.click();
            _logger.info("clicked on All Orders Tab");
            return true ;
        }
        return false ;
    }
    //search for orders use the same locator same as pending approval tab and search for the service name there

    //check if no order found error is there

    @Action
    public boolean NO_ORDER_FOUND_XPATH() {
        return isElementFound(allOrdersTab) && allOrdersTab.isDisplayed();
    }


    @FindBy(className = NO_ORDER_FOUND_XPATH)
    private WebElement noOrderFound;

    @Action
    private boolean isNoOrderFoundPresent() {
        return isElementFound(noOrderFound) && noOrderFound.isDisplayed();
    }
    //Validate with above messages (message == "No Orders Found" || message == "No Pending Orders") {
    @Action
    public String getTextNoOrderFoundStatus() {
        String searchOrderStatus = new String("");
        if (isNoOrderFoundPresent()) {
            searchOrderStatus = noOrderFound.getText();
            _logger.info("After search order status returned is "+ searchOrderStatus);
            return searchOrderStatus;
        }
        return searchOrderStatus;
    }

    @FindBy(className = ORDER_STATUS_CSS)
    private WebElement oderStatus;

    @Action
    public boolean orderStatusAfterApproval() {
        return isElementFound(oderStatus) && oderStatus.isDisplayed();
    }

    @Action
    public boolean verifyOrderIsCompleted(String expectedOrderStatus, String orderId){
        String finalOrderStatus  = new String("");
        int maxCount = 20;
        int counter = 0 ;
        while (counter<=maxCount){
            finalOrderStatus = oderStatus.getText();
            if (finalOrderStatus.equals(expectedOrderStatus)){
                _logger.info("Order has been completed with expected status as "+ finalOrderStatus);
                return true;
            }
            else{
                pendingApprovalPageView.searchForOrderInPage(orderId);
                _logger.info("Waiting for "+ finalOrderStatus + " to be " + expectedOrderStatus);
                counter+=1;

            }

        }
        return false;
    }

}
