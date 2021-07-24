package com.pom.pages.orderedServices;

import com.autoui.fwk.annotations.Action;
import com.autoui.fwk.annotations.Step;
import com.autoui.fwk.core.BaseTest;
import com.autoui.fwk.core.PageObject;
import com.autoui.fwk.reporting.Logger;
import com.pom.pages.orders.OrderDetailsPageView;
import com.pom.utils.LoadData;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.testng.Assert;

public class AllServicesPageView extends PageObject {

    private static final Logger _logger = Logger.getLogger(OrderDetailsPageView.class);
    private static final String SEARCH_BOX_CSS ="input#data-table-search__input";
    private static final String SERVICE_NOT_FOUND_CSS =".bx--table-row.bx--table-row-empty--default"; // if this locator is present means there is ot service found and get test and check with "No Data Available"
    private static final String INSTANCE_TABLE_ACTION_ICON_CSS =".bx--table-overflow";
    //private static final String SERVICE_INSTANCE_LOADED_XPATH ="//span[contains(text(),"+"'"+String orderId+"')]";
    private static final String DELETE_SERVICE_CSS ="//button[@class='bx--overflow-menu-options__btn' and normalize-space(.)='Delete Service']";
    private static final String DELETE_SERVICE_DIALOG_CSS ="//div[@role = 'dialog']//h2[contains(text(),'Delete Service')]";
    private static final String DELETE_SERVICE_CONFIRM_CHECK_BOX_CSS ="#checkbox-inventory-listing_action-modal_ConfirmDeleteServiceChecked ~ .bx--checkbox-label";
    private static final String DELETE_SERVICE_CONFIRM_OK_CSS ="#inventory-listing_action-modal_carbon-button_ok";
    @Override
    public PageValidation pageValidation() {
        return new PageValidation() {
            @Override
            public void validate() {
                validateOrderedServicesPageLoaded();
            }

            @Step
            public void validateOrderedServicesPageLoaded() {
                LoadData loadData = new LoadData();
                Assert.assertEquals(driver.getTitle(), loadData.getParamValue(loadData.loadApplicationTitle(), "orderedServicesAllServices"),
                        "Page title validation failed");
                Assert.assertEquals(driver.getCurrentUrl(), BaseTest.getTestObject().getBaseUrl() +
                        loadData.getParamValue(loadData.loadApplicationUrl(), "inventoryPageUrl"), "Page title validation failed");
                _logger.info("Ordered Services page load validation completed...");
            }
        };
    }
    // get the service name from the input what we are providing service Id name either from the fill order detail function  or return that value fro the function from the

    @FindBy(className = SEARCH_BOX_CSS)
    private WebElement searchBox;

    @Action
    private boolean isSearchBoxPresentInOrderedServicesPage() {
        return isElementFound(searchBox) && searchBox.isDisplayed();
    }

    @Action
    public boolean searchForOrderInOrderedServicesPage(String serviceInstanceName) {
        if (isSearchBoxPresentInOrderedServicesPage()) {
            searchBox.clear();
            searchBox.sendKeys(serviceInstanceName);
            _logger.info("Searching for " + serviceInstanceName + " in Order Services page");
            return true;
        }
        return false;
    }
    // if this locator is present the service instance is not visible
    @FindBy(className = SERVICE_NOT_FOUND_CSS)
    private WebElement serviceNotFound;

    @Action
    public boolean isServiceNotFoundErrorPresent() {
        return isElementFound(serviceNotFound) && serviceNotFound.isDisplayed();
    }
// write code here to check if the correct order is listed

    @FindBy(className = INSTANCE_TABLE_ACTION_ICON_CSS)
    private WebElement instanceTableActionIcon;

    @Action
    private boolean isInstanceTableActionIconPresent() {
        return isElementFound(instanceTableActionIcon) && isClickableElementFound(instanceTableActionIcon);
    }

    @Action
    public boolean clickOnInstanceTableActionIcon(){
        if (isInstanceTableActionIconPresent()){
            _logger.info("Verify instance table icon is displayed");
            instanceTableActionIcon.click();
            return true ;
        }
        return false ;
    }

    @FindBy(className = DELETE_SERVICE_CSS)
    private WebElement deleteService;

    @Action
    private boolean isDeleteServiceButtonIsPresent(){
        return isElementFound(instanceTableActionIcon) && isClickableElementFound(instanceTableActionIcon);
    }

    @Action
    public boolean clickOnDeleteServiceButton(){
        if (isDeleteServiceButtonIsPresent()){
            _logger.info("Delete button is displayed");
            deleteService.click();
            return true;

        }
        return false;
    }

    @FindBy(className = DELETE_SERVICE_DIALOG_CSS)
    private WebElement deleteServiceDialog;

    @Action
    public boolean isDeleteServiceDialogIsPresent(){
        return isElementFound(deleteServiceDialog) && deleteServiceDialog.isDisplayed();
    }

    @FindBy(className = DELETE_SERVICE_CONFIRM_CHECK_BOX_CSS)
    private WebElement deleteServiceConfirmCheckBox;

    @Action
    private boolean isDeleteServiceConfirmCheckBoxIsPresent(){
        return isElementFound(deleteServiceConfirmCheckBox) && isClickableElementFound(deleteServiceConfirmCheckBox);
    }

    @Action
    public boolean clickOnDeleteServiceConfirmCheckBox(){
        if (isDeleteServiceConfirmCheckBoxIsPresent()){
            _logger.info("Delete Service Confirmation checkbox is visible");
            deleteService.click();
            return true;

        }
        return false;
    }

    @FindBy(className = DELETE_SERVICE_CONFIRM_OK_CSS)
    private WebElement deleteServiceConfirmOk;

    @Action
    private boolean isDeleteServiceConfirmOkIsPresent(){
        return isElementFound(deleteServiceConfirmOk) && isClickableElementFound(deleteServiceConfirmOk);
    }

    @Action
    public boolean clickOnDeleteServiceConfirmOk(){
        if (isDeleteServiceConfirmOkIsPresent()){
            _logger.info("Delete confirm OK button is present");
            deleteService.click();
            return true;

        }
        return false;
    }

}
