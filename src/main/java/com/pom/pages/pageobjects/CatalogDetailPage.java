package com.pom.pages.pageobjects;

import com.automacent.fwk.annotations.Action;
import com.automacent.fwk.annotations.Step;
import com.automacent.fwk.core.PageObject;
import com.automacent.fwk.reporting.Logger;
import com.pom.steps.login.LoginSteps;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class CatalogDetailPage extends PageObject {

    private static final String SERVICENAMEECSS = "h1.ibm--page-header__title";
    private static final String CONFIGURESERIVECSS = "button#configure-service";
    private static Logger _logger = Logger.getLogger(LoginSteps.class);

    @Override
    public PageValidation pageValidation() {
        return new PageValidation() {
            @Override
            public void validate() {
                validateCatalogDetailPageLoaded();
            }

            @Step
            public void validateCatalogDetailPageLoaded() {
                //Assert.assertTrue(verifyServiceNamePresent(String bluePrint), "UserName Field on Login Page is loaded"); //how to fix this problem
            }
        };
    }

    //-------------------------------validate catalog detail page header-----------------------------
    @FindBy(css = SERVICENAMEECSS)
    private WebElement serviceNameTitle;

    @Action
    public boolean verifyServiceNamePresent(String bluePrint) {
        _logger.info("Verify page heading is" + bluePrint);
        return (serviceNameTitle.getText().equals(bluePrint));
    }

    //-----------------------------------click on the configure button---------------------------------

    @FindBy(css = CONFIGURESERIVECSS)
    private WebElement configureServiceButton;

    @Action
    public boolean verifyConfigureButtonVisible() {
        return isClickableElementFound(configureServiceButton);
    }

    @Action
    public void clickOnConfigureService() {
        _logger.info("Click on the configure button");
        configureServiceButton.click();
    }
}
