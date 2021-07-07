package com.pom.pages.home;
import com.automacent.fwk.annotations.Action;
import com.automacent.fwk.annotations.Step;
import com.automacent.fwk.core.PageObject;
import com.sun.glass.ui.Size;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.testng.Assert;

import java.util.List;

public class CatalogPage extends PageObject {

    private static final String CATALOGPAGETITLE = "h1.ibm--page-header__title[title~='Catalog']";
    private static final String IFRAME = "mcmp-iframe";
    private static final String ALLCATEGORY = "ul.cb--selectable-list";
    private static final String CATEGORYLISTXPATH = "//ul[@class='cb--selectable-list']/li/a";
    private static final String GETCATEGORYVALUEXPATH = "//child::li/a";
    private static final String PROVIDERCONTAINERCSS = ".provider-internal-container";
    private static final String PROVIDERLISTCSS = ".provider-internal-container";
    private static final String PROVIDERTOBECLICKED = "//span[@class = 'bx--checkbox-label-text";
    private static final String CARDSERVICETITLE  = ".card-service-title" ;

    @Override
    public PageValidation pageValidation() {
        return new PageValidation() {
            @Override
            public void validate() {
                validateCatalogPageLoaded();
            }

            @Step
            public void validateCatalogPageLoaded() {
                Assert.assertTrue(isCatalogTilePresent(), "UserName Field on Login Page is loaded");
            }
        };
    }

    //---------------------------------- validate heading ------------------------------------
    @FindBy(css = CATALOGPAGETITLE)
    private WebElement catalogPageTile;

    @Action
    public boolean isCatalogTilePresent() {
        isElementFound(catalogPageTile);
        return catalogPageTile.isDisplayed();
    }

    @Action
    public void switchIframe() {
        driver.switchTo().frame(IFRAME);
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
    public Integer VerifyCategoryIsloaded(Integer expectedNumberOfCatogary) { //fill allCatagory value is page steps
        return categoryCount.size();
    }

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
   @FindBy(css=PROVIDERCONTAINERCSS)
    private WebElement providerContainer;

    @Action
    public boolean verifyProviderListDisplayed(){
        return (providerContainer.isEnabled() && providerContainer.isDisplayed());
    }

    @FindBy(xpath = PROVIDERLISTCSS )
    private List<WebElement> allProviderList;

    @Action
    public WebElement VerifyProviderPresent(String providerName){ //fill value is steps page : provider value is test case level
        WebElement providerToBeClicked = null;
        for (WebElement element : allProviderList) {
            if (element.getText().equals(providerName)) {
                providerToBeClicked=element;
            }

        }
        return providerToBeClicked;
    }

    @Action
    public void clickOnProvider(String providerName){
        if(isElementFound(driver.findElement(By.xpath(PROVIDERTOBECLICKED+ " and text() ='" + VerifyProviderPresent(providerName).getText()+ "']")))){ //-- check if providerName can be made as instance varriable
            driver.findElement(By.xpath(PROVIDERTOBECLICKED+ " and text() ='" + VerifyProviderPresent(providerName).getText()+ "']")).click();
        }
    }

    //-----------------------------------------------------Select required blueprint name here--------------------------------------------------------------------------------------
    @FindBy(css=CARDSERVICETITLE)
    private List<WebElement> cardIndexValue;

    @Action
    public Integer VerifyServicePresent(String bluePrintName){
        int indexValue = 0;
        for (WebElement element:cardIndexValue){
            if(element.getText().equals(bluePrintName)){
                indexValue = cardIndexValue.indexOf(element);

            }
        }
       return  indexValue;
    }

    @Action
    public void ClickOnService(String bluePrintName){ // check for calling the function again and again
        if ((cardIndexValue.get(VerifyServicePresent(bluePrintName)).isEnabled())){
            cardIndexValue.get(VerifyServicePresent(bluePrintName)).click();
        }

    }

}
