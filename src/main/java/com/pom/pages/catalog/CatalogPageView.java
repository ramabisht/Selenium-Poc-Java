package com.pom.pages.catalog;

import com.automacent.fwk.annotations.Action;
import com.automacent.fwk.annotations.Step;
import com.automacent.fwk.core.BaseTest;
import com.automacent.fwk.core.PageObject;
import com.automacent.fwk.reporting.Logger;
import com.pom.utils.LoadData;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.testng.Assert;

import java.util.List;

public class CatalogPageView extends PageObject {

    private static final String CATALOG_PAGE_TITLE = "h1.ibm--page-header__title[title~='Catalog']";
    private static final String ALL_CATEGORY = "ul.cb--selectable-list";
    private static final String CATEGORIES_LIST_XPATH = "//ul[@class='cb--selectable-list']/li/a";
    private static final String GET_CATEGORY_VALUE_XPATH = "//child::li/a";
    private static final String PROVIDER_CONTAINER_CSS = ".provider-internal-container";
    private static final String PROVIDER_LIST_XPATH = "//span[@class = 'bx--checkbox-label-text']";
    private static final String PROVIDER_TO_BE_CLICKED = "//span[@class = 'bx--checkbox-label-text'";
    private static final String CARD_SERVICE_TITLE = ".card-service-title";


    private static Logger _logger = Logger.getLogger(CatalogPageView.class);

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
                    //Change the param for Login page for title and URL accordingly
                    LoadData loadData = new LoadData();
                    Assert.assertEquals(driver.getTitle(), loadData.getParamValue(loadData.loadApplicationTitle(), "catalogTitle"), "Page title validation failed");
                    Assert.assertEquals(driver.getCurrentUrl(), BaseTest.getTestObject().getBaseUrl() +
                            loadData.getParamValue(loadData.loadApplicationUrl(), "catalogPageUrl"), "Page title validation failed");
                } catch (Exception ex) {
                    _logger.info("Exception:" + ex);
                }
            }
        };
    }


    @FindBy(css = CATALOG_PAGE_TITLE)
    private WebElement catalogPageTile;

    @Action
    public boolean isCatalogTilePresent() {
        return isElementFound(catalogPageTile) && catalogPageTile.isDisplayed();
    }

    //---------------------------------- Select required category ------------------------------------
    @FindBy(css = ALL_CATEGORY)
    private WebElement allCategoryList;

    @Action
    public boolean isCategoryListPresent(String allCategory) {
       return isElementFound(allCategoryList) && (allCategoryList.getText()).contains(allCategory);
    }

    @FindBy(xpath = CATEGORIES_LIST_XPATH)
    private List<WebElement> categoryCount;

    @Action
    public Boolean verifyCategoriesLoaded() {
        return categoryCount.size() > 0 ? true : false;
    }

    //---------------------------------- Click on the required category ------------------------------------
    @FindBy(xpath = GET_CATEGORY_VALUE_XPATH)
    private List<WebElement> categoryList;

    @Action
    public boolean clickOnCategory(String categoryToBeSelected) {
        for (WebElement element : categoryList) {
            if (isElementFound(element) && element.getText().equals(categoryToBeSelected) &&
                    element.isDisplayed() && element.isEnabled()) {
                      element.click();
                      return true;
            }
        }
        return false;
    }

    //---------------------------------- Select required provider VRA ------------------------------------
    @FindBy(css = PROVIDER_CONTAINER_CSS)
    private WebElement providerContainer;

    @Action
    public boolean isProviderListDisplayed() {
        return isElementFound(providerContainer) && providerContainer.isEnabled();
    }

    @FindBy(xpath = PROVIDER_LIST_XPATH)
    private List<WebElement> allProviderList;

    @Action
    private WebElement verifyProviderPresent(String providerName) {
        WebElement providerToBeClicked = null;
        for (WebElement element : allProviderList) {
            if (element.getText().contains(providerName) && isElementFound(element)) {
                _logger.info("Provider Found :" + providerName);
                providerToBeClicked = element;
                break;
            }
        }
        Assert.assertNotNull(providerToBeClicked, "Found the provider :" + providerName);
        return providerToBeClicked;
    }

    @Action
    public boolean clickOnProvider(String providerName) {
        WebElement providerToClick = verifyProviderPresent(providerName);
        if (isElementFound(By.xpath(PROVIDER_TO_BE_CLICKED + " and text() =' " + providerToClick.getText() + " ']"))) {
            driver.findElement(By.xpath(PROVIDER_TO_BE_CLICKED + " and text() =' " + providerToClick.getText() + " ']")).click();
            return true;
        } else
            return false;
    }

    //-----------------------------------------------------Select required blueprint name here--------------------------------------------------------------------------------------
    @FindBy(css = CARD_SERVICE_TITLE)
    private List<WebElement> cardIndexValue;

    /*
    @Action
    public int getIndexOfService(String bluePrintName) {
        int indexValue = -1;
        for (WebElement element : cardIndexValue) {
            if (element.getText().equals(bluePrintName) && isElementFound(element)) {
                indexValue = cardIndexValue.indexOf(element);
            }
        }
        return indexValue;
    }*/

    @Action
    public boolean clickOnService(String bluePrintName) {
        for (WebElement element : cardIndexValue) {
            if (element.getText().equals(bluePrintName) && isElementFound(element) &&
                    isClickableElementFound(element)) {
               element.click();
               return true;
            }
        }
        return false;
    }

}
