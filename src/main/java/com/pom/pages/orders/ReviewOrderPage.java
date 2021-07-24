package com.pom.pages.orders;

import com.autoui.fwk.annotations.Action;
import com.autoui.fwk.core.PageObject;
import com.autoui.fwk.reporting.Logger;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.testng.Assert;

public class ReviewOrderPage extends PageObject {

    @Override
    public PageValidation pageValidation() {
        return null;
    }

    private static final String SUBMIT_ORDER_STATUS_XPATH = "//div[@role = 'dialog']//h3[contains(text(),'Order Submitted')]";
    private static final String SUBMIT_ORDER_VALUE_CSS = "[id$='order-number'],#order-submitted-number";
    private static final String GO_TO_SERVICE_CATALOG_CSS = "#order-submitted-modal_carbon-button";

    private static final Logger _logger = Logger.getLogger(OrderDetailsPageView.class);

    @FindBy(xpath = SUBMIT_ORDER_STATUS_XPATH)
    private WebElement orderStatus;

    @Action
    public boolean verifyOrderIsSubmitted() {
        return isElementFound(orderStatus) && orderStatus.isEnabled();
    }

    @FindBy(css = SUBMIT_ORDER_VALUE_CSS)
    private WebElement orderNumber;

    @Action
    public boolean isOderNumberPresent() {
        return isElementFound(orderNumber) && orderNumber.isEnabled();
    }

    @Action
    public String getOrderNumber() {
        return orderNumber.getText();
    }

    @FindBy(css = GO_TO_SERVICE_CATALOG_CSS)
    private WebElement serviceCatalogNavigation;

    @Action
    public boolean navigateServiceCatalogPresent(){
        Assert.assertTrue(isClickableElementFound(serviceCatalogNavigation), "navigate assertion");
        return isElementFound(serviceCatalogNavigation) && isClickableElementFound(serviceCatalogNavigation);
    }

    @Action
    public boolean clickOnServiceCatalogPresent(){
        if (navigateServiceCatalogPresent()){
            _logger.info("Navigate to Service catalog is present");
            serviceCatalogNavigation.click();
            return true;
        }
    return false;
    }


}
