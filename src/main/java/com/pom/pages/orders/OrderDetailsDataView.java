package com.pom.pages.orders;

import com.automacent.fwk.annotations.Action;
import com.automacent.fwk.core.PageObject;
import com.automacent.fwk.reporting.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

import java.util.List;

public class OrderDetailsDataView extends PageObject {

    private static final Logger _logger = Logger.getLogger(OrderDetailsPageView.class);

    @Override
    public PageValidation pageValidation() {
        return null;
    }

    // Actions--------------------------------------------------------------

    @Action
    private void fillTextBox(String elementId, String elementValue) {
        _logger.info("Filling text box by Id :" + elementId + ", value:" + elementValue);
        Assert.assertTrue(isElementFound(By.id(elementId)), "Text Box not found by Id " + elementId);
        WebElement textBoxElement = driver.findElement(By.id(elementId));
        textBoxElement.clear();
        textBoxElement.sendKeys(elementValue);
        Assert.assertEquals(textBoxElement.getText(), elementValue, "Text box value entered is :" + elementValue);
    }

    @Action
    private void selectCheckBox(String elementId, String elementValue) {
        _logger.info("Clicking on the button by Id :" + elementId + ", value:" + elementValue);
        Assert.assertTrue(isElementFound(By.id(elementId)), "Found the button element by Id " + elementId);
        WebElement buttonElement = driver.findElement(By.id(elementId));
        Assert.assertTrue(isClickableElementFound(buttonElement), "Found the clickable button element by Id " + elementId);
        buttonElement.click();
        _logger.info("Clicked on the button by Id:" + elementId);
    }

    @Action
    private void selectRadioButton(String elementId, String elementValue) {
        _logger.info("Clicking on the radio button by Id :" + elementId + ", value:" + elementValue);
        Assert.assertTrue(isElementFound(By.id(elementId)), "Found the radio button element by Id " + elementId);
        WebElement radioButtonElement = driver.findElement(By.cssSelector("[id=\"" + elementId + "\"] ~ label span"));
        Assert.assertTrue(isClickableElementFound(radioButtonElement), "Found the clickable radio button element by Id " + elementId);
        radioButtonElement.click();
        _logger.info("Clicked on the radio button by Id:" + elementId);
    }

    @Action
    private void selectFromDropDown(String elementId, String elementValue) {
        _logger.info("Selecting the drop down by Id :" + elementId + ", value:" + elementValue);
        Assert.assertTrue(isElementFound(By.id(elementId)), "Found the drop down element by Id " + elementId);
        WebElement dropDownElement = driver.findElement(By.xpath("//*[@id='" + elementId + "' or @id = '" + elementId.toLowerCase() + "']/div"));
        Assert.assertTrue(isClickableElementFound(dropDownElement), "Found the clickable drop down element by Id " + elementId);
        dropDownElement.click();
        _logger.info("Drop down expanded");

        Assert.assertTrue(isElementFound(By.xpath("//*[@id='" + elementId + "' or @id = '" + elementId.toLowerCase() + "']//ul//li")),
                "Drop down options enabled:");

        List<WebElement> findDropDownElement = driver.findElements(By.xpath("//*[@id='" + elementId + "' or @id = '" + elementId.toLowerCase() + "']//ul//li"));
        for (WebElement element : findDropDownElement) {
            if (element.getText().equals(elementValue)) {
                Assert.assertTrue(isClickableElementFound(element), "Found the provided options form Drop down " + elementValue);
                element.click();
                _logger.info("Selected the option " + element.getText() + " from drop down.");
            }

        }
    }

    @Action
    public void fillOrderDetails(String elementId, String elementValue, String elementType) {
        _logger.info("Filling order details from Type " + elementType + ", ID " + elementId + ", value " + elementValue);
        if (elementType.equals("Textbox")) {
            fillTextBox(elementId, elementValue);
        } else if (elementType.equals("Dropdown")) {
            selectFromDropDown(elementId, elementValue);
        } else if (elementType.equals("CheckBox")) {
            selectCheckBox(elementId, elementValue);
        } else if (elementType.equals("RadioButton")) {
            selectRadioButton(elementId, elementValue);
        } else {
            _logger.error("Invalid/Unsupported element type provided :" + elementType + " , supported options are : [Textbox, Dropdown, CheckBox, RadioButton]");
        }
    }

}




