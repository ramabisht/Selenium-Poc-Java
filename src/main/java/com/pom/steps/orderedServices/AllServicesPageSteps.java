package com.pom.steps.orderedServices;

import com.autoui.fwk.annotations.Pages;
import com.autoui.fwk.annotations.Step;
import com.autoui.fwk.reporting.Logger;
import com.pom.pages.orderedServices.AllServicesPageView;
import com.pom.pages.orders.OrderDetailsPageView;
import com.pom.steps.AbstractHomeSteps;
import org.testng.Assert;

public class AllServicesPageSteps extends AbstractHomeSteps {

    private static final Logger _logger = Logger.getLogger(OrderDetailsPageView.class);

    @Pages
    public AllServicesPageView allServicesPageView;

    @Step
    public void searchServiceInstanceName(String serviceInstanceName) {
        Assert.assertTrue(allServicesPageView.searchForOrderInOrderedServicesPage(serviceInstanceName), "Search for Service Instance");
    }

    @Step
    public  void clickInstanceActionIcon(){
        Assert.assertTrue(allServicesPageView.clickOnInstanceTableActionIcon(), "Click on instance table icon");
    }

    @Step
    public void clickDeleteServiceButton(){
        Assert.assertTrue(allServicesPageView.clickOnDeleteServiceButton(), "Click on Delete Service Button");
    }

    @Step
    public void clickOnDeleteServiceConfirm(){
        Assert.assertTrue(allServicesPageView.isDeleteServiceDialogIsPresent(),"Click delete service confirm check box");
    }

    @Step
    public void clickDeleteServiceConfirm(){
        Assert.assertTrue(allServicesPageView.clickOnDeleteServiceConfirmOk(), "Click on delete service Ok");
    }

    /********* Main module will be called in spec file  ********/

    @Step
    public void deleteServiceInstance(String serviceInstanceName){
        searchServiceInstanceName(serviceInstanceName);
        clickInstanceActionIcon();
        clickDeleteServiceButton();
        clickOnDeleteServiceConfirm();
        clickDeleteServiceConfirm();

    }
}
