package ru.bakanych.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.FindBy;
import ru.bakanych.components.CommandBar;
import ru.bakanych.components.HtmlList;
import ru.bakanych.components.Paginator;
import ru.yandex.qatools.htmlelements.element.HtmlElement;

/**
 * Sample page
 */
public class HomePage extends Page {

  @FindBy(name = "addButton")
  @CacheLookup
  private HtmlElement addButton;

  @CacheLookup
  public CommandBar commandBar;

  @CacheLookup
  public Paginator paginator;

  @CacheLookup
  private HtmlList data;


  public HomePage(WebDriver webDriver) {
    super(webDriver);
  }


  public HomePage addItem(){
    addButton.click();
    return this;
  }

  public HomePage selectItem(int index)
  {
    this.data.getItems().get(index).click();
    return this;
  }

  public HomePage deleteItem(int index)
  {
    return this
            .selectItem(index)
            .deleteSelectedItems();
  }
  public HomePage deleteSelectedItems ()
  {
    this.commandBar.delete();
    return this;
  }
}
