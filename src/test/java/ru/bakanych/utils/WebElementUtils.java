package ru.bakanych.utils;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.internal.WrapsDriver;
import org.openqa.selenium.internal.WrapsElement;

public final class WebElementUtils {

    public static <T> T ExecuteJsOnSelf(WebElement element, String js){

        return (T)((JavascriptExecutor) toWebDriver(element)).executeScript(js, element);
    }

    public static WebDriver toWebDriver(WebElement element){
        WebDriver webDriver;
        try {
            if (WrapsElement.class.isAssignableFrom(element.getClass()))
                webDriver = ((WrapsDriver) ((WrapsElement) element).getWrappedElement()).getWrappedDriver();
            else
                webDriver = ((WrapsDriver) element).getWrappedDriver();
        } catch (ClassCastException e){
            webDriver = ((WrapsDriver)element.findElement(By.xpath("."))).getWrappedDriver();
        }

        return webDriver;
    }
}
