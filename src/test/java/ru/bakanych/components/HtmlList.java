package ru.bakanych.components;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.FindBy;
import ru.bakanych.model.ListItem;
import ru.yandex.qatools.htmlelements.annotations.Name;
import ru.yandex.qatools.htmlelements.element.HtmlElement;
import ru.yandex.qatools.htmlelements.loader.HtmlElementLoader;

import java.util.ArrayList;
import java.util.List;

@Name("Items area")
@FindBy(tagName = "table")
public class HtmlList extends HtmlElement {

    @FindBy(xpath = ".//tr")
    private List<HtmlElement> tableRows;

    public List<ListItem> getItems(){
        List<ListItem> pageItems = new ArrayList<>();
        for (WebElement row : tableRows) {
            pageItems.add(HtmlElementLoader.createHtmlElement(ListItem.class, row));
        }
        return  pageItems;
    }


}
