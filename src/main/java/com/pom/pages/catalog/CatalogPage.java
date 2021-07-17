package com.pom.pages.catalog;

import com.automacent.fwk.annotations.Action;
import com.automacent.fwk.annotations.Step;
import com.automacent.fwk.core.PageObject;
import com.automacent.fwk.reporting.Logger;
import com.pom.steps.login.LoginSteps;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.testng.Assert;

import java.util.List;

public class CatalogPage extends PageObject {

    private static final String CATALOGPAGETITLE = "h1.ibm--page-header__title[title~='Catalog']";
    public static final String IFRAME = "mcmp-iframe";
    private static final String ALLCATEGORY = "ul.cb--selectable-list";
    private static final String CATEGORYLISTXPATH = "//ul[@class='cb--selectable-list']/li/a";
    private static final String GETCATEGORYVALUEXPATH = "//child::li/a";
    private static final String PROVIDERCONTAINERCSS = ".provider-internal-container";
    private static final String PROVIDERLISTXPATH = "//span[@class = 'bx--checkbox-label-text']";
    private static final String PROVIDERTOBECLICKED = "//span[@class = 'bx--checkbox-label-text'";
    private static final String CARDSERVICETITLE = ".card-service-title";
    private static Logger _logger = Logger.getLogger(LoginSteps.class);

    @Override
    public PageValidation pageValidation() {
        return new PageValidation() {
            @Override
            public void validate() {
                validateCatalogPageLoaded();
            }

            @Step
            public void validateCatalogPageLoaded() {
                try {
                    switchIframe();
                    Assert.assertTrue(isCatalogTilePresent(), "Is Catalog heading has been displayed");
                } catch (Exception ex) {
                    _logger.info("Exception:" + ex);
                }
            }
        };
    }

    //---------------------------------- validate heading ------------------------------------
    // @FindBy(id = IFRAME)
    // private WebElement iframe;

   /* @Action
    public void switchIframe() {
        try {
            isElementFound(iframe);
            switchIframe();
            driver.switchTo().frame(IFRAME);
        }
        catch (Exception ex){
            _logger.info("Exception trace:"+ ex);
        }
    }*/

    @Action
    public void switchIframe() {
        _logger.info("Switching iframe");
        driver.switchTo().frame(IFRAME);
    }

    @FindBy(css = CATALOGPAGETITLE)
    private WebElement catalogPageTile;

    @Action
    public boolean isCatalogTilePresent() {

        isElementFound(catalogPageTile);
        return catalogPageTile.isDisplayed();
    }

    //---------------------------------- Select required category ------------------------------------
    @FindBy(css = ALLCATEGORY)
    private WebElement allCategoryList;

    @Action
    public boolean isCategoryListPresent(String allCatagory) { //fill allCatagory value is page steps
        isElementFound(allCategoryList);
        return (allCategoryList.getText()).contains(allCatagory);
    }

    @FindBy(xpath = CATEGORYLISTXPATH)
    private List<WebElement> categoryCount;

    @Action
    public Boolean verifyCategoriesLoaded() {
        Boolean catogaryListSize = false;
        if (categoryCount.size() > 0) {
            catogaryListSize = true;
        }
        return catogaryListSize;
    }

    //---------------------------------- Click on the required catagory ------------------------------------
    @FindBy(xpath = GETCATEGORYVALUEXPATH)
    private List<WebElement> categoryList;


    //----- to do action //put logic here for clickable
    @Action
    public void clickOnCategory(String categoryToBeSelected) { //fill catagoryToBeSelected as : Compute value in test case level
        for (WebElement element : categoryList) {
            if (element.getText().equals(categoryToBeSelected)) {
                if (element.isDisplayed() && element.isEnabled()) {
                    element.click();
                }
            }
        }
    }

    //---------------------------------- Select required provider VRA ------------------------------------ also cjheck how to put wait here
    @FindBy(css = PROVIDERCONTAINERCSS)
    private WebElement providerContainer;

    @Action
    public boolean isProviderListDisplayed() {
        //return (providerContainer.isEnabled() && providerContainer.isDisplayed());
        return (providerContainer.isEnabled());
    }

    @FindBy(xpath = PROVIDERLISTXPATH)
    private List<WebElement> allProviderList;

    @Action
    public WebElement verifyProviderPresent(String providerName) { //fill value is steps page : provider value is test case level
        WebElement providerToBeClicked = null;
        for (WebElement element : allProviderList) {
            if (element.getText().contains(providerName)) {
                _logger.info("Provider Found" + providerName);
                providerToBeClicked = element;
            }
        }
        _logger.info("providerToBeClicked:" + providerToBeClicked);
        return providerToBeClicked;
    }

    @Action
    public void clickOnTheProvider(String providerName) {
        _logger.info("click on the provider name:" + providerName);
        WebElement providerToClick = verifyProviderPresent(providerName);
        if (isElementFound(driver.findElement(By.xpath(PROVIDERTOBECLICKED + " and text() =' " + providerToClick.getText() + " ']")))) {
            _logger.info("provider is present");
            driver.findElement(By.xpath(PROVIDERTOBECLICKED + " and text() =' " + providerToClick.getText() + " ']")).click();
        }
    }

    //-----------------------------------------------------Select required blueprint name here--------------------------------------------------------------------------------------
    @FindBy(css = CARDSERVICETITLE)
    private List<WebElement> cardIndexValue;

    @Action
    public Integer verifyServicePresent(String bluePrintName) {
        int indexValue = 0;
        for (WebElement element : cardIndexValue) {
            if (element.getText().equals(bluePrintName)) {
                indexValue = cardIndexValue.indexOf(element);

            }
        }
        return indexValue;
    }

    @Action
    public void clickOnService(String bluePrintName) { // check for calling the function again and again
        int indexValue = verifyServicePresent(bluePrintName);
        if (isClickableElementFound(cardIndexValue.get(indexValue))) {
            cardIndexValue.get(indexValue).click();

        }

    }

}
