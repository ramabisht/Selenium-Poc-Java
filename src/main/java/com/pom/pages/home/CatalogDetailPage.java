package com.pom.pages.home;

import com.automacent.fwk.annotations.Action;
import com.automacent.fwk.annotations.Step;
import com.automacent.fwk.core.PageObject;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;
import org.testng.Assert;

public class CatalogDetailPage extends PageObject {
    private static final String SERVICENAMEECSS="h1.ibm--page-header__title" ;
    private static final String CONFIGURESERIVECSS="h1.ibm--page-header__title" ;


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
    @FindBy(css=SERVICENAMEECSS)
    private WebElement serviceNameTitle;

    @Action
    public boolean verifyServiceNamePresent(String bluePrint){
     return (serviceNameTitle.getText().equals(bluePrint));
    }

    //-----------------------------------click on the configure button---------------------------------

    @FindBy(css=CONFIGURESERIVECSS)
    private WebElement configureServiceButton;

    @Action
    public boolean verifyConfigureButtonVisible(){
        return isClickableElementFound(configureServiceButton);  // check here for is clickable of the button
    }

    @Action
    public void clickOnConfigureService(){
        configureServiceButton.click();
    }
}
