package ru.bakanych;

import org.junit.jupiter.api.*;
import org.openqa.selenium.ElementNotInteractableException;
import ru.bakanych.pages.HomePage;
import static org.junit.jupiter.api.Assertions.*;

class PaginatedListTest extends JUnitTestBase {

  final static String PAGINATION_TAG = "pagination";
  private int pagesCount = 0;

  private HomePage app;
  @BeforeEach
  void initPageObjects() {
    driver.get(baseUrl);
    this.app = new HomePage(driver);
    this.pagesCount = app.paginator.elementsCount() / app.paginator.lastElementIndex();
  }

  @Test
  @Tag(PAGINATION_TAG)
  @DisplayName("Paginator should increase total after adding item")
  void testIncrementTotal(){
    int initialCount = app.paginator.elementsCount();
    app.addItem();
    assertEquals(initialCount+1, app.paginator.elementsCount());
  }

  @Test
  @Tag(PAGINATION_TAG)
  @DisplayName("Paginator should decrease total after deleting item")
  void testDecrementTotal(){
    int initialCount = app.paginator.elementsCount();
    app.deleteItem(1);
    assertEquals(initialCount-1, app.paginator.elementsCount());
  }

  @Nested
  @DisplayName("Paginator should display correct page info")
  @Tag(PAGINATION_TAG)
  class PaginatorPageInfo{
    @Test
    @DisplayName("on load")
    void testPaginatorPageInfoOnLoad() {
      assertAll(() -> {
        assertEquals(1, app.paginator.firstElementIndex());
        assertEquals(10, app.paginator.lastElementIndex());
        assertEquals(100, app.paginator.elementsCount());
      });
    }

    @Test
    @DisplayName("on next")
    void testPaginatorPageInfoOnNext(){
      app.paginator.nextPage();

      assertAll(()->{
        assertEquals(11, app.paginator.firstElementIndex());
        assertEquals(20, app.paginator.lastElementIndex());
        assertEquals(100, app.paginator.elementsCount());
      });
    }

    @Test
    @DisplayName("on element add")
    void testPaginatorPageInfoOnAdd(){
      for (int i = 0; i < pagesCount-1; i++) {
        app.paginator.nextPage();
      }

      app.addItem();
      app.paginator.nextPage();

      assertAll(()->{
        assertEquals(101, app.paginator.firstElementIndex());
        assertEquals(101, app.paginator.lastElementIndex());
        assertEquals(101, app.paginator.elementsCount());
      });
    }

    @Test
    @DisplayName("on element delete")
    void testPaginatorPageInfoOnDelete(){
      app.deleteItem(0);

      assertAll(()->{
        assertEquals(1, app.paginator.firstElementIndex());
        assertEquals(10, app.paginator.lastElementIndex());
        assertEquals(99, app.paginator.elementsCount());
      });
    }
  }
  @Nested
  @DisplayName("Paginator should disable prev button for the first page")
  @Tag(PAGINATION_TAG)
  class PaginatorPrev{

    @Test
    @DisplayName("on load")
    void testDisablePrevButtonOnLoad(){
      assertThrows(ElementNotInteractableException.class, ()->
              app.paginator.prevPage());
    }


    @Test
    @DisplayName("when return")
    void testDisablePrevButtonOnReturn(){
      app.paginator.nextPage();
      app.paginator.prevPage();
      assertThrows(ElementNotInteractableException.class, ()->
              app.paginator.prevPage());
    }
  }

  @Nested
  @DisplayName("Delete button should display number of selected elements on page")
  class DeleteButtonSelectedCount{
    @Test
    @DisplayName("no selected elements")
    void testNoSelected(){
      assertEquals(0, app.commandBar.getSelectedCount(driver));
    }

    @Test
    @DisplayName("one selected element")
    void testOneSelected(){
      app.selectItem(0);
      assertEquals(1, app.commandBar.getSelectedCount(driver));
    }

    @Test
    @DisplayName("all selected elements")
    void testAllSelected(){
      app.commandBar.selectAll();
      assertEquals(10, app.commandBar.getSelectedCount(driver));
    }

    @Test
    @DisplayName("multi page selection")
    void testMultiPageSelection(){
      app.selectItem(0);
      assertEquals(1, app.commandBar.getSelectedCount(driver));
      app.paginator.nextPage();
      app.selectItem(0);
      app.selectItem(1);
      assertEquals(2, app.commandBar.getSelectedCount(driver));
      app.paginator.prevPage();
      assertEquals(1, app.commandBar.getSelectedCount(driver));
    }
  }

  @Test
  @DisplayName("should delete only elements selected on current page")
  void testDeleteElementsOnPage(){
    app.commandBar.selectAll();
    app.paginator.nextPage();
    app.commandBar.selectAll();
    app.deleteSelectedItems();
    assertEquals(90, app.paginator.elementsCount());
    app.paginator.prevPage();
    app.deleteSelectedItems();
    assertEquals(80, app.paginator.elementsCount());
  }

  @Test
  @DisplayName("Paginator should disable next button for the last page")
  @Tag(PAGINATION_TAG)
  void testDisableNextButton(){
    for (int i = 0; i < pagesCount-1; i++) {
      app.paginator.nextPage();
    }
    assertThrows(ElementNotInteractableException.class, ()->
      app.paginator.nextPage());
  }

}
