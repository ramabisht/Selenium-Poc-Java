package com.pom.pages.catalog;

import com.automacent.fwk.annotations.Action;
import com.automacent.fwk.annotations.Step;
import com.automacent.fwk.core.BaseTest;
import com.automacent.fwk.core.PageObject;
import com.automacent.fwk.reporting.Logger;
import com.automacent.fwk.utils.ThreadUtils;
import com.pom.utils.LoadData;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.testng.Assert;

public class CatalogDetailPageView extends PageObject {

    // XPATH ------------------------------------------
    private static final String SERVICE_NAME_CSS = "h1.ibm--page-header__title";
    private static final String CONFIGURE_SERVICE_CSS = "button#configure-service";


    //Logger--------------------------------------------
    private static Logger _logger = Logger.getLogger(CatalogDetailPageView.class);

    //Page validation-----------------------------------
    @Override
    public PageValidation pageValidation() {
        return new PageValidation() {

            @Override
            public void validate() {
                validateCatalogDetailsPageLoaded();
            }

            @Step
            public void validateCatalogDetailsPageLoaded() {

                //Change the param for Login page for title and URL accordingly
                /*
                LoadData loadData = new LoadData();
                Assert.assertEquals(driver.getTitle(),  loadData.getParamValue(loadData.loadApplicationTitle(), "launchpadTitle"), "Page title validation failed");
                Assert.assertEquals(driver.getCurrentUrl(), BaseTest.getTestObject().getBaseUrl() +
                        loadData.getParamValue(loadData.loadApplicationUrl(), "launchpadUrl"), "Page title validation failed");
                 */
            }
        };
    }

    // Actions--------------------------------------------------------------

    //-------------------------------Validate catalog detail page header-----------------------------
    @FindBy(css = SERVICE_NAME_CSS)
    private WebElement blueprintName;

    @Action
    public String getBluePrintName() {
        ThreadUtils.sleepFor(5);
        Assert.assertTrue(isElementFound(blueprintName), "Blue print name element found.");
        return blueprintName.getText();
    }

    //-----------------------------------Click on the configure button---------------------------------

    @FindBy(css = CONFIGURE_SERVICE_CSS)
    private WebElement configureServiceButton;

    @Action
    public boolean clickOnConfigureService() {
        Assert.assertTrue(isClickableElementFound(configureServiceButton), "Configure service button is clickable.");
        configureServiceButton.click();
        return true;
    }
}
