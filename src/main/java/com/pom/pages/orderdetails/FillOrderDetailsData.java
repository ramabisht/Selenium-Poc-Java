package com.pom.pages.orderdetails;
import com.automacent.fwk.annotations.Action;
import com.automacent.fwk.core.PageObject;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.List;

public class FillOrderDetailsData extends PageObject {

    private static final com.automacent.fwk.reporting.Logger _logger = com.automacent.fwk.reporting.Logger.getLogger(FillOrderDetailsPages.class);

    @Override
    public PageValidation pageValidation() {

        return null;
    }

    @Action
    public void fillValueForText(String elementId, String elementValue ) {
        _logger.info("stating the text function");
        WebElement idElement = driver.findElement(By.id(elementId));
            isElementFound(idElement);
            idElement.clear();
            idElement.sendKeys(elementValue);
            _logger.info("Value entered is"+ elementValue);
    }
    @Action
    public void fillValueForButtons(String elementId, String elementValue){
        _logger.info("stating the Buttons function");
        WebElement idElement=driver.findElement(By.id(elementId));
            isClickableElementFound(idElement);
            idElement.click();
            _logger.info("Clicked on "+ elementValue);
    }

    @Action
    public void fillValueForDropDown(String elementId, String elementValue) {
        _logger.info("stating the dropDown function");
        WebElement idElement = driver.findElement(By.xpath("//*[@id='" + elementId + "' or @id = '" + elementId.toLowerCase() + "']/div"));
        if (isElementFound(idElement) & isClickableElementFound(idElement)) {
            idElement.click();
            List<WebElement> findDropDownElement = driver.findElements(By.xpath("//*[@id='" + elementId + "' or @id = '" + elementId.toLowerCase() + "']//ul//li"));
            for (WebElement element : findDropDownElement) {
                if (element.getText().equals(elementValue) & isClickableElementFound(element)) {
                    element.click();
                    _logger.info("Value chose from the drop down is " + elementValue);
                }
            }
        }
    }

    @Action
    public void fillOrderDetails(String elementId, String elementValue, String elementType){
        _logger.info("starting the fillorderdetail function"+elementType+" "+elementType.length());
        if (elementType.trim().contains("Textbox")){
            _logger.info("inside the fill fucntion");
            fillValueForText(elementId, elementValue);
        }
        else if (elementType.equals("Dropdown")){
            _logger.info("inside the fill fucntion");
            fillValueForDropDown(elementId, elementValue);
        }

        else if (elementType.equals("RadioButton")|| elementType.equals("CheckBox"))
            _logger.info("inside the fill fucntion");{
            fillValueForButtons(elementId, elementValue);
        }

    }

    }




