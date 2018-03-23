package ru.bakanych.components;

import org.openqa.selenium.ElementNotInteractableException;
import org.openqa.selenium.ElementNotVisibleException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import ru.yandex.qatools.htmlelements.element.HtmlElement;

import java.util.List;

@FindBy(css = ".pagination")
public class Paginator extends HtmlElement {

    @FindBy(xpath = "(.//li)[1]")
    private HtmlElement prev;

    @FindBy(xpath = "(.//li)[2]")
    private HtmlElement pageText;

    @FindBy(xpath = "(.//li)[3]")
    private HtmlElement next;


    public int firstElementIndex(){
        return Integer.parseInt(
                parsePagiText()[0].split("-")[0]);
    }

    public int lastElementIndex(){
        return Integer.parseInt(
                parsePagiText()[0].split("-")[1]);
    }

    public int elementsCount(){
        return Integer.parseInt(
                parsePagiText()[1]);
    }

    public void prevPage() throws ElementNotInteractableException{
        if (this.prev.getAttribute("class").contains("disabled"))
            throw new ElementNotInteractableException("prev button is disabled");

        this.prev.click();
    }

    public void nextPage(){
        if (this.next.getAttribute("class").contains("disabled"))
            throw new ElementNotInteractableException("next button is disabled");
        this.next.click();
    }

    private String[] parsePagiText()
    {
        return pageText.getText().split(" of ");
    }



}
