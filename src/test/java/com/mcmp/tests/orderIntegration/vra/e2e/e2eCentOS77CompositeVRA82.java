package com.mcmp.tests.orderIntegration.vra.e2e;

import com.automacent.fwk.annotations.Repeat;
import com.automacent.fwk.annotations.Steps;
import com.automacent.fwk.utils.ThreadUtils;
import com.mcmp.tests.LoginTest;
import com.pom.steps.orders.OrderDetailsSteps;
import com.pom.steps.catalog.CatalogDetailPageSteps;
import com.pom.steps.catalog.CatalogPageSteps;
import com.pom.steps.home.HomePageSteps;
import com.pom.utils.LoadData;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import org.testng.annotations.*;

public class e2eCentOS77CompositeVRA82 extends LoginTest {

    //----------Read these values from the test json
    //-----------Test Specific constants
    private static final String ENTERPRISE_MARKET = "Enterprise Marketplace";
    private static final String CATALOG_PAGE = "Catalog";
    private static final String APPROVE_ORDERS_PAGE = "Approve Orders";
    private static final String ORDERED_SERVICES_PAGE = "Ordered Services";

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


    @BeforeClass
    public void verifyAndOpenMenu() {
        homePageSteps.clickHamburgerButton();
        homePageSteps.clickOnMenuItem(ENTERPRISE_MARKET);

    }

    @Test(priority = 0, description = "Navigate VRA Service Page.", testName = "VRA82: CentOS77 - Navigate VRA Service Page")
    @Severity(SeverityLevel.CRITICAL)
    //@Repeat
    public void openServiceTemplate() {
        homePageSteps.clickOnSubMenuItem(CATALOG_PAGE);
        catalogPageSteps.confirmCatalogPageIsLoaded();
        ThreadUtils.sleepFor(10);
        catalogPageSteps.selectCategory((String) loadData.getParamValue(loadData.loadTestDataFile("VRA"), "Category"));
        catalogPageSteps.selectProvider((String) loadData.getParamValue(loadData.loadTestDataFile("VRA"), "provider"));
    }

    @Test(dependsOnMethods = {"openServiceTemplate"}, priority = 0, description = "Verify fields on Main Parameters page.", testName = "VRA82: CentOS77 - Verify Composite-Main Parameters page")
    @Severity(SeverityLevel.CRITICAL)
    //@Repeat
    @Parameters()
    public void configureVRAService() {
        catalogPageSteps.selectServiceTemplate((String) loadData.getParamValue(loadData.loadTestDataFile("VRA"), "bluePrintName"));
        catalogDetailPageSteps.configureService((String) loadData.getParamValue(loadData.loadTestDataFile("VRA"), "bluePrintName"));
        orderDetailsSteps.fillOrderParameterDetailsMainParam((String) loadData.getParamValue(loadData.loadTestDataFile("VRA"), "provider"));
        orderDetailsSteps.fillOrderParameterDetailsAdditionalParam((String) loadData.getParamValue(loadData.loadTestDataFile("VRA"), "provider"));
        orderDetailsSteps.clickOnSubmitButton();
        // click on hamburger button and then click on approve order
        homePageSteps.clickOnSubMenuItem(APPROVE_ORDERS_PAGE);

    }


}
