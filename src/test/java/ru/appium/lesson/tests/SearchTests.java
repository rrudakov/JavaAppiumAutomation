package ru.appium.lesson.tests;

import org.hamcrest.CoreMatchers;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import ru.appium.lesson.lib.CoreTestCase;
import ru.appium.lesson.lib.ui.ArticlePageObject;
import ru.appium.lesson.lib.ui.SearchPageObject;
import ru.appium.lesson.lib.ui.WelcomePageObject;

public class SearchTests extends CoreTestCase {
  private WelcomePageObject welcome;
  private SearchPageObject search;
  private ArticlePageObject article;

  @Override
  protected void setUp() throws Exception {
    super.setUp();
    this.welcome = new WelcomePageObject(driver);
    this.search = new SearchPageObject(this.driver);
    this.article = new ArticlePageObject(driver);
  }

  @Test
  public void testSearch() {
    this.welcome.skipWelcome();
    this.search.initSearchInput();
    this.search.typeSearchLine("Java");
    this.search.waitSearchResult("Object-oriented programming language");
  }

  @Test
  public void testCancelSearch() {
    this.welcome.skipWelcome();
    this.search.initSearchInput();
    this.search.typeSearchLine("Java");
    this.search.waitForCancelButtonToAppear();
    this.search.clickCancelSearch();
    this.search.waitForCancelButtonToDisappear();
  }

  @Test
  public void testAmountOfNonEmptySearch() {
    String searchText = "Linkin park discograghy";
    this.welcome.skipWelcome();
    this.search.initSearchInput();
    this.search.typeSearchLine(searchText);
    int amountOfSearchResults = this.search.getAmountOfFoundArticles();

    assertTrue("Too few search results", amountOfSearchResults > 0);
  }

  @Test
  public void testAmountOfEmptySearch() {
    String searchText = "lksjdfkajhsdlkfjhasd";
    this.welcome.skipWelcome();
    this.search.initSearchInput();
    this.search.typeSearchLine(searchText);
    this.search.waitForEmptyResultsLabel();
    this.search.assertThereIsNoResultOfSearch();
  }

  @Test
  public void testCheckSearchPlaceholder() {
    this.welcome.skipWelcome();
    this.search.initSearchInput();

    WebElement searchInputElement =
        this.mainPage.waitForElementPresent(SEARCH_INPUT_BY_ID, "Can't find 'Search' input");

    String placeholder = searchInputElement.getAttribute("text");

    assertEquals("Wrong placeholder in Search input", "Search…", placeholder);
  }

  @Test
  public void testSearchFewArticles() {
    this.welcome.skipWelcome();
    this.search.initSearchInput();
    this.search.typeSearchLine("Java");
    int amountOfSearchResults = this.search.getAmountOfFoundArticles();

    assertTrue("Too few search results", amountOfSearchResults > 1);

    // В новой версии приложения нет крестика в поле ввода 'Search...' поэтому я нажимаю кнопку со
    // стрелочкой
    this.mainPage.waitForElementAndClick(BACK_BUTTON, "Can't find 'Back' button");
    this.search.assertThereIsNoResultOfSearch();
    ;
  }

  @Test
  public void testSearchFewArticlesAndCheckResults() {
    String searchText = "Test";
    this.welcome.skipWelcome();
    this.search.initSearchInput();
    this.search.typeSearchLine(searchText);
    List<WebElement> resultElements =
        this.mainPage.waitForElementsPresent(SEARCH_RESULTS, "No search results");

    assertTrue("Too few search results", resultElements.size() > 1);

    for (WebElement result : resultElements) {
      WebElement titleElement = result.findElement(By.id("org.wikipedia:id/page_list_item_title"));
      String title = titleElement.getAttribute("text");

      assertThat(
          String.format("Search result doesn't contains string '%s'", searchText),
          title,
          CoreMatchers.containsString(searchText));
    }
  }

  @Test
  public void testAssertElementPresent() {
    String searchText = "Java";
    this.welcome.skipWelcome();
    this.search.initSearchInput();
    this.search.typeSearchLine(searchText);
    this.search.clickByArticleWithSubstring("Object-oriented programming language");
    this.article.waitForTitleElement();
  }
}
