package ru.appium.lesson.tests;

import org.junit.Test;

import ru.appium.lesson.lib.CoreTestCase;
import ru.appium.lesson.lib.ui.ArticlePageObject;
import ru.appium.lesson.lib.ui.MyListsPageObject;
import ru.appium.lesson.lib.ui.NavigationUI;
import ru.appium.lesson.lib.ui.SearchPageObject;
import ru.appium.lesson.lib.ui.WelcomePageObject;

public class MyListsTests extends CoreTestCase {
  private WelcomePageObject welcome;
  private SearchPageObject search;
  private ArticlePageObject article;
  private NavigationUI navigation;
  private MyListsPageObject myLists;

  @Override
  protected void setUp() throws Exception {
    super.setUp();
    this.welcome = new WelcomePageObject(driver);
    this.search = new SearchPageObject(this.driver);
    this.article = new ArticlePageObject(driver);
    this.navigation = new NavigationUI(driver);
    this.myLists = new MyListsPageObject(driver);
  }

  @Test
  public void testSaveFirstArticleToMyList() {
    String searchText = "Java";
    String listName = "Learning programming";
    String articleTitleSearch = "Object-oriented programming language";
    String articleTitle = "Java (programming language)";

    this.welcome.skipWelcome();
    this.search.initSearchInput();
    this.search.typeSearchLine(searchText);
    this.search.clickByArticleWithSubstring(articleTitleSearch);
    this.article.waitForTitleElement();
    this.article.addArticleToMyList(listName);
    this.article.closeArticle();
    this.navigation.clickMyLists();
    this.myLists.openFolderByName(listName);
    this.myLists.swipeByArticleToDelete(articleTitle);
    this.myLists.waitForArticleToDisappearByTitle(articleTitle);
  }

  @Test
  public void testSaveTwoArticles() {
    String listName = "Test articles";
    String firstArticleTitle = "Java (programming language)";
    String secondArticleTitle = "Appium";
    String firstSearchText = "Java";
    String secondSearchText = "Appium";

    this.welcome.skipWelcome();
    this.search.initSearchInput();
    this.search.typeSearchLine(firstSearchText);
    this.search.clickByArticleWithSubstring("Object-oriented programming language");
    this.article.waitForTitleElement();
    this.article.addArticleToMyList(listName);
    this.article.closeArticle();

    this.search.initSearchInput();
    this.search.typeSearchLine(secondSearchText);
    this.search.clickByArticleWithSubstring("Appium");
    this.article.waitForTitleElement();
    this.article.addArticleToExistingList(listName);
    this.article.closeArticle();

    this.navigation.clickMyLists();
    this.myLists.openFolderByName(listName);
    this.myLists.swipeByArticleToDelete(firstArticleTitle);
    this.myLists.openArticleFromSavedFolderByTitle(secondArticleTitle);
    String title = this.article.getArticleTitle();
    assertEquals("Wrong article title", secondArticleTitle, title);
  }
}
