package com.pom.pages.catalog;

import com.autoui.fwk.annotations.Action;
import com.autoui.fwk.annotations.Step;
import com.autoui.fwk.core.BaseTest;
import com.autoui.fwk.core.PageObject;
import com.autoui.fwk.reporting.Logger;
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
    //private static final String PROVIDER_TO_BE_CLICKED = "//span[@class = 'bx--checkbox-label-text'";
    private static final String PROVIDER_TO_BE_CLICKED = "//input[@name='%s']";
    private static final String CARD_SERVICE_TITLE = ".card-service-title";
    private static final String MCMP_IFRAME_ID = "iframe#mcmp-iframe";


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
                LoadData loadData = new LoadData();
                Assert.assertEquals(driver.getTitle(), loadData.getParamValue(loadData.loadApplicationTitle(), "catalogTitle"),
                        "Page title validation failed");
                Assert.assertEquals(driver.getCurrentUrl(), BaseTest.getTestObject().getBaseUrl() +
                        loadData.getParamValue(loadData.loadApplicationUrl(), "catalogPageUrl"), "Page title validation failed");
                _logger.info("Catalog page load validation completed...");
            }
        };
    }


    @FindBy(css = CATALOG_PAGE_TITLE)
    private WebElement catalogPageTile;

    @Action
    public boolean isCatalogTilePresent() {
        return isElementFound(catalogPageTile) && catalogPageTile.isDisplayed();
    }

    public boolean switchToCatalogIFrame() {
        if (isElementFound(driver.findElement(By.id(MCMP_IFRAME_ID)))) {
            driver.switchTo().frame(driver.findElement(By.id(MCMP_IFRAME_ID)));
            return true;
        }
        return false;
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
    public boolean verifyProviderPresent(String providerName) {
        for (WebElement element : allProviderList) {
            _logger.info("Iterating from provider :" + element.getText());
            if (element.getText().contains(providerName) && isElementFound(element)
                    && isClickableElementFound(element)) {
                _logger.info("Provider Found :" + element.getText());
                element.click();
                return true;
            }
        }
        return false;
    }

    /*
    @Action
    public boolean clickOnProvider(String providerName) {
        WebElement providerToClick = verifyProviderPresent(providerName);

        _logger.info("Provider element found xPath trying is :" + PROVIDER_TO_BE_CLICKED + " and text() =' " + providerToClick.getText() + " ']");
        if (providerToClick != null
                && isElementFound(By.xpath(PROVIDER_TO_BE_CLICKED + " and text() =' " + providerToClick.getText() + " ']"))) {
            driver.findElement(By.xpath(PROVIDER_TO_BE_CLICKED + " and text() =' " + providerToClick.getText() + " ']")).click();
            return true;
        } else
            return false;


        if(isElementFound(By.xpath(String.format(PROVIDER_TO_BE_CLICKED, providerName))) &&
        isClickableElementFound(driver.findElement(By.xpath(String.format(PROVIDER_TO_BE_CLICKED, providerName))))){
            driver.findElement(By.xpath(String.format(PROVIDER_TO_BE_CLICKED, providerName))).click();
            return true;
        }

        return false;
    }*/

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
