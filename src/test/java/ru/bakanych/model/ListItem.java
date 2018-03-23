package ru.bakanych.model;

import org.openqa.selenium.support.FindBy;
import ru.yandex.qatools.htmlelements.annotations.Name;
import ru.yandex.qatools.htmlelements.element.HtmlElement;

@Name("List item")
@FindBy(xpath = ".")
public final class ListItem extends HtmlElement {

    @FindBy(tagName = "input")
    private HtmlElement checkbox;

    @FindBy(xpath = "./td[2]")
    private HtmlElement subject;

    @FindBy(tagName = "label")
    private HtmlElement label;

     public String getName(){
         return label.getText();
     }
     public String getSubject(){
         return subject.getText();
     }
     public Boolean getSelected(){
         return checkbox.isSelected();
     }

     public void click() {
         label.click();
     }
}
