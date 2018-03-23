package ru.bakanych.pages;

import org.openqa.selenium.WebDriver;
import ru.yandex.qatools.htmlelements.loader.HtmlElementLoader;

/**
 * Abstract class representation of a Page in the UI. Page object pattern
 */
public abstract class Page {

  protected WebDriver driver;

  public Page(final WebDriver driver) {
    this.driver = driver;
    HtmlElementLoader.populatePageObject(this, driver);
  }
}
