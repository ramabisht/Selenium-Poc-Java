package com.mcmp.tests.orderIntegration.vra.e2e;

import com.automacent.fwk.annotations.Repeat;
import com.automacent.fwk.annotations.Steps;
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
        homePageSteps.verifyHamburgerButton();
        homePageSteps.clickHamburgerButton();
    }

    @Test(priority = 0, description = "Navigate VRA Service Page.", testName = "VRA82: CentOS77 - Navigate VRA Service Page")
    @Severity(SeverityLevel.CRITICAL)
    //@Repeat
    @Parameters({"Category", "Provider"})
    public void openServiceTemplate(String category, String provider) {
        homePageSteps.clickOnMenuItem(ENTERPRISE_MARKET);
        homePageSteps.clickOnSubMenuItem(CATALOG_PAGE);
        catalogPageSteps.selectCategory(category);
        catalogPageSteps.selectProvider(provider);
    }

    @Test(priority = 0, description = "Verify fields on Main Parameters page.", testName = "VRA82: CentOS77 - Verify Composite-Main Parameters page")
    @Severity(SeverityLevel.CRITICAL)
    //@Repeat
    @Parameters({"Provider", "BluePrintName"})
    public void configureVRAService(String provider, String bluePrintName) {
        catalogPageSteps.selectServiceTemplate(bluePrintName);
        catalogDetailPageSteps.configureService(bluePrintName);
        orderDetailsSteps.fillOrderParameterDetailsMainParam(provider);
        orderDetailsSteps.fillOrderParameterDetailsAdditionalParam(provider);
    }


    @AfterClass
    public void logoutFromApplication() {
    }
}
