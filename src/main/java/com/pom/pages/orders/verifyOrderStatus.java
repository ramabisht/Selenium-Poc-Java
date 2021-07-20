package com.pom.pages.orders;

import com.automacent.fwk.annotations.Action;
import com.automacent.fwk.core.PageObject;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class verifyOrderStatus extends PageObject {
    @Override
    public PageValidation pageValidation() {
        return null;
    }

    private static final String SUBMIT_ORDER_STATUS_XPATH = "//div[@role = 'dialog']//h3[contains(text(),'Order Submitted')]";
    private static final String SUBMIT_ORDER_VALUE_CSS = "[id$='order-number'],#order-submitted-number"  ;

    @FindBy(xpath = SUBMIT_ORDER_STATUS_XPATH)
       private WebElement orderStatus;

    @Action
    public boolean verifyOrderIsSubmitted(){
     return isElementFound(orderStatus) && orderStatus.isEnabled();
    }

    @FindBy(className = SUBMIT_ORDER_VALUE_CSS)
        private WebElement orderNumber;

    @Action
    public boolean verifyOderNumber(){
        return isElementFound(orderNumber) && orderNumber.isEnabled();
    }

    @Action
    public String getOrderNumber(){
       return  orderNumber.getText();
    }
}
