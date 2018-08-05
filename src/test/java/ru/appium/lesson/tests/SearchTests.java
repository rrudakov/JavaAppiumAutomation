package ru.appium.lesson.tests;

import org.junit.Test;

import ru.appium.lesson.lib.CoreTestCase;
import ru.appium.lesson.lib.ui.ArticlePageObject;
import ru.appium.lesson.lib.ui.NavigationUI;
import ru.appium.lesson.lib.ui.SearchPageObject;
import ru.appium.lesson.lib.ui.WelcomePageObject;

public class SearchTests extends CoreTestCase {
  private WelcomePageObject welcome;
  private SearchPageObject search;
  private ArticlePageObject article;
  private NavigationUI navigation;

  @Override
  protected void setUp() throws Exception {
    super.setUp();
    this.welcome = new WelcomePageObject(this.driver);
    this.search = new SearchPageObject(this.driver);
    this.article = new ArticlePageObject(this.driver);
    this.navigation = new NavigationUI(this.driver);
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
    String placeholder = this.search.getSearchInputPlaceholder();

    assertEquals("Wrong placeholder in Search input", "Search…", placeholder);
  }

  @Test
  public void testSearchFewArticles() {
    this.welcome.skipWelcome();
    this.search.initSearchInput();
    this.search.typeSearchLine("Java");
    int amountOfSearchResults = this.search.getAmountOfFoundArticles();
    assertTrue("Too few search results", amountOfSearchResults > 1);
    this.navigation.clickBackButton();
    this.search.assertThereIsNoResultOfSearch();
    ;
  }

  @Test
  public void testSearchFewArticlesAndCheckResults() {
    String searchText = "Test";
    this.welcome.skipWelcome();
    this.search.initSearchInput();
    this.search.typeSearchLine(searchText);
    int searchResultCounter = this.search.getAmountOfFoundArticles();
    assertTrue("Too few search results", searchResultCounter > 1);

    for (String title : this.search.searchResultTitles()) {
      assertTrue(
          String.format("Search result doesn't contains string '%s'", searchText),
          title.contains(searchText));
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
