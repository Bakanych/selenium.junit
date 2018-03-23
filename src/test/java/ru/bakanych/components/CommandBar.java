package ru.bakanych.components;

import org.openqa.selenium.ElementNotVisibleException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;
import ru.bakanych.model.SelectorState;
import ru.yandex.qatools.htmlelements.element.HtmlElement;


@FindBy(name = "commandBar")
public class CommandBar extends HtmlElement {

    @FindBy(xpath = ".//i[text()='delete']")
    private HtmlElement deleteButton;

    @FindBy(xpath = ".//i[contains(.,'check_box')]")
    private HtmlElement selector;

    public int getSelectedCount(WebDriver driver){
        String buttonText = ((JavascriptExecutor) driver)
                .executeScript(
                "return $(arguments[0]).parent().clone().children().remove().end().text()", deleteButton)
                .toString();
        return Integer.parseInt(buttonText);
    }
    public void delete() {
        if (!deleteButton.isDisplayed())
            throw new ElementNotVisibleException("delete button is not visible");

        deleteButton.click();
    }

    public SelectorState getSelectorState(){
        switch (selector.getText())
        {
            case "check_box_outline_blank":
                return SelectorState.NONE;
            case "check_box" :
                return SelectorState.ALL;
            case "indeterminate_check_box" :
            default:
                return SelectorState.INDETERMINATE;
        }
    }
    public CommandBar selectAll(){
        SelectorState state = getSelectorState();
        switch (state)
        {
            case ALL:
                break;
            case NONE:
                selector.click();
            case INDETERMINATE:
                selector.click();
                selector.click();
        }
        return this;
    }

    public CommandBar selectNone(){
        SelectorState state = getSelectorState();
        switch (state)
        {
            case ALL:
                selector.click();
            case NONE:
                break;
            case INDETERMINATE:
                selector.click();
        }
        return this;
    }

}
