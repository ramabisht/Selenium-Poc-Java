package com.mcmp.tests.orderIntegration.vra.e2e;

import com.autoui.fwk.annotations.Step;
import com.autoui.fwk.annotations.Steps;
import com.autoui.fwk.utils.ThreadUtils;
import com.mcmp.tests.LoginTest;
import com.pom.pages.catalog.CatalogPageView;
import com.pom.steps.approveOrder.ApproveOrderSteps;
import com.pom.steps.orderedServices.AllServicesPageSteps;
import com.pom.steps.orders.OrderDetailsSteps;
import com.pom.steps.catalog.CatalogDetailPageSteps;
import com.pom.steps.catalog.CatalogPageSteps;
import com.pom.steps.home.HomePageSteps;
import com.pom.steps.orders.ReviewOrderPageSteps;
import com.pom.utils.LoadData;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import org.testng.annotations.*;

import java.util.LinkedHashMap;

public class e2eCentOS77CompositeVRA82 extends LoginTest {

    //----------Read these values from the test json
    //-----------Test Specific constants
    private static final String ENTERPRISE_MARKET = "Enterprise Marketplace";
    private static final String CATALOG_PAGE = "Catalog";
    private static final String APPROVE_ORDERS_PAGE = "Approve Orders";
    private static final String ORDERED_SERVICES_PAGE = "Ordered Services";
    private static final String EXPECTED_ORDER_STATUS = "Completed";
    private static final String APPROVAL_MESSAGE =  "Approval Processed";
    private static LinkedHashMap<String, Object> loadTestData;


    @Steps
    private HomePageSteps homePageSteps;

    @Steps
    private CatalogPageSteps catalogPageSteps;

    @Steps
    private CatalogDetailPageSteps catalogDetailPageSteps;

    @Steps
    private OrderDetailsSteps orderDetailsSteps;

    @Steps
    private LoadData loadData;

    @Steps
    private ReviewOrderPageSteps reviewOrderPageSteps;

    @Steps
    private ApproveOrderSteps approveOrderSteps;

    @Steps
    private AllServicesPageSteps allServicesPageSteps;

    @Steps
    private CatalogPageView catalogPageView ;

    @BeforeClass
    public void verifyAndOpenMenu() {
        loadTestData = loadData.loadTestDataFile("VRA");
        homePageSteps.clickHamburgerButton();
        homePageSteps.clickOnMenuItem(ENTERPRISE_MARKET);
    }

    /*@Test
    public void testApprovalOrder() {
        //homePageSteps.clickOnSubMenuItem(APPROVE_ORDERS_PAGE);
        String orderId = "K64WGW2YH1";
        String serviceInstanceName = "TestAutomationcetos90";
        String orderApprovedMessage = "Approval Processed";
        //approveOrderSteps.approvePendingOrder(orderApprovedMessage, orderId);
        //approveOrderSteps.verifyProvisioningIsCompleted(EXPECTED_ORDER_STATUS,orderId);
        homePageSteps.clickOnSubMenuItem(ORDERED_SERVICES_PAGE);
        allServicesPageSteps.deleteServiceInstance(serviceInstanceName) ;
    }*/

   @Test(priority = 0, description = "Navigate VRA Service Page.", testName = "VRA82: CentOS77 - Navigate VRA Service Page")
    @Severity(SeverityLevel.CRITICAL)
    //@Repeat
    public void openServiceTemplate() {
        homePageSteps.clickOnSubMenuItem(CATALOG_PAGE);
        catalogPageSteps.confirmCatalogPageIsLoaded();
        ThreadUtils.sleepFor(10);
        catalogPageSteps.selectCategory((String) loadData.getParamValue(loadTestData, "Category"));
        catalogPageSteps.selectProvider((String) loadData.getParamValue(loadTestData, "provider"));
    }

    @Test(dependsOnMethods = {"openServiceTemplate"}, priority = 0, description = "Verify Provisioning of service.", testName = "VRA82: CentOS77 - Verify Composite-Main Parameters page")
    @Severity(SeverityLevel.CRITICAL)
    //@Repeat
    @Parameters()
    public void configureVRAService() {
        catalogPageSteps.selectServiceTemplate((String) loadData.getParamValue(loadTestData, "bluePrintName"));
        catalogDetailPageSteps.configureService((String) loadData.getParamValue(loadTestData, "bluePrintName"));
        String serviceInstanceName =  orderDetailsSteps.fillOrderParameterDetailsMainParam((String) loadData.getParamValue(loadTestData, "provider"));
        orderDetailsSteps.fillOrderParameterDetailsAdditionalParam((String) loadData.getParamValue(loadTestData, "provider"));
        orderDetailsSteps.clickOnSubmitButton();
        String orderId = reviewOrderPageSteps.getOrderNumber();
        reviewOrderPageSteps.navigateToCatalogAfterOrderSubmission();
        catalogPageSteps.switchDefaultContent();
        homePageSteps.clickHamburgerButton();
        homePageSteps.clickOnMenuItem(ENTERPRISE_MARKET);
        homePageSteps.clickOnSubMenuItem(APPROVE_ORDERS_PAGE);
        catalogPageView.switchToIFrame();
        approveOrderSteps.approvePendingOrder(APPROVAL_MESSAGE, orderId);
        approveOrderSteps.verifyProvisioningIsCompleted(EXPECTED_ORDER_STATUS,orderId);
        catalogPageSteps.switchToDefaultContent();
        homePageSteps.clickHamburgerButton();
        homePageSteps.clickOnMenuItem(ENTERPRISE_MARKET);
        homePageSteps.clickOnSubMenuItem(ORDERED_SERVICES_PAGE);
        catalogPageView.switchToIFrame();
        allServicesPageSteps.deleteServiceInstance(serviceInstanceName) ;
   }
}
