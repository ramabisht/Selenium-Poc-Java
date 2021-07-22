package com.pom.pages.approveOrders;

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

public class PendingApprovalPageView extends PageObject {

    private static final Logger _logger = Logger.getLogger(OrderDetailsPageView.class);
    private static final String PENDING_APPROVAL_CSS = "a#tab-control-pending_approval_tab";
    private static final String SEARCH_BOX_CSS = "input#search__input-orders-search";
    private static final String ORDER_ID_CSS = ".order-items a";
    private static final String APPROVE_BUTTON_CSS = "button#order_approve_button";
    private static final String ORDER_APPROVAL_FLOW_XPATH = "//h2[contains(text(),'Order Approval Flow')]";
    private static final String FINANCIAL_APPROVAL_XPATH = "label[for='checkbox-financial']";
    private static final String TECHNICAL_APPROVAL_XPATH = "label[for='checkbox-technical']";
    private static final String APPROVE_ORDER_CSS = "#order_details_approval_approve";

    public PageValidation pageValidation() {
        return new PageValidation() {
            @Override
            public void validate() {
                validateOrderApprovalPageLoaded();
            }

            @Step
            public void validateOrderApprovalPageLoaded() {
                LoadData loadData = new LoadData();
                Assert.assertEquals(driver.getTitle(), loadData.getParamValue(loadData.loadApplicationTitle(), "orderedServices"),
                        "Page title validation failed");
                Assert.assertEquals(driver.getCurrentUrl(), BaseTest.getTestObject().getBaseUrl() +
                        loadData.getParamValue(loadData.loadApplicationUrl(), "ordersPageUrl"), "Page title validation failed");
                _logger.info("Ordered Services page load validation completed...");
            }
        };
    }

    @FindBy(className = PENDING_APPROVAL_CSS)
    private WebElement pendingApprovalTab;

    @Action
    public boolean isPendingApprovalTabPresent() {
        return isElementFound(pendingApprovalTab) && pendingApprovalTab.isDisplayed();
    }

    @FindBy(className = SEARCH_BOX_CSS)
    private WebElement searchBox;

    @Action
    public boolean isSearchBoxPresent() {
        return isElementFound(searchBox) && searchBox.isDisplayed();
    }

    @Action
    public boolean searchForOrder(String orderId) {
        if (isSearchBoxPresent()) {
            searchBox.clear();
            searchBox.sendKeys(orderId);
            _logger.info("Searching for " + orderId + " in Pending Approvals");
            return true;
        }
        return false;
    }

    @FindBy(className = ORDER_ID_CSS)
    private WebElement displayOrderId;

    @Action
    public boolean OrderIdIsDisplayed() {
        return isElementFound(searchBox) && searchBox.isDisplayed();
    }

    @Action
    public String getOrderIdIsDisplayed() {
        return displayOrderId.getText();
    }

    @FindBy(className = APPROVE_BUTTON_CSS)
    private WebElement approveButton;

    @Action
    public boolean isApproveButtonEnabled() {
        return isElementFound(approveButton) && isClickableElementFound(approveButton);
    }

    @Action
    public boolean clickOnApproveButton() {
        if (isApproveButtonEnabled()) {
            approveButton.click();
            return true;
        }
        return false;
    }

    @FindBy(xpath = ORDER_APPROVAL_FLOW_XPATH)
    private WebElement orderApprovalFlow;

    @Action
    public boolean isOrderApprovalFlowDisplayed() {
        return isElementFound(orderApprovalFlow) && orderApprovalFlow.isDisplayed();
    }

    @FindBy(xpath = FINANCIAL_APPROVAL_XPATH)
    private WebElement financialApproval;

    @Action
    public boolean isFinancialApprovalEnabled() {
        return isElementFound(financialApproval) && isClickableElementFound(financialApproval);
    }

    @Action
    public boolean clickOnFinancialApproval() {
        if (isFinancialApprovalEnabled()) {
            financialApproval.click();
            return true;
        }
        return false;
    }

    @FindBy(xpath = TECHNICAL_APPROVAL_XPATH)
    private WebElement technicalApproval;

    @Action
    public boolean isTechnicalApprovalEnabled() {
        return isElementFound(technicalApproval) && isClickableElementFound(technicalApproval);
    }

    @Action
    public boolean clickOnTechnicalApproval() {
        if (isTechnicalApprovalEnabled()) {
            technicalApproval.click();
            return true;
        }
        return false;
    }

    @FindBy(xpath = APPROVE_ORDER_CSS)
    private WebElement approveOrderDetail;

    @Action
    public boolean isApproveOrderDetailEnabled() {
        return isElementFound(approveOrderDetail) && isClickableElementFound(approveOrderDetail);
    }

    @Action
    public boolean clickOnApproveOrderDetail() {
        if (isApproveOrderDetailEnabled()) {
            technicalApproval.click();
            return true;
        }
        return false;
    }
}
