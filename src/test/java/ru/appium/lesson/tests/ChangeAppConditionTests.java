package ru.appium.lesson.tests;

import org.junit.Test;

import ru.appium.lesson.lib.CoreTestCase;
import ru.appium.lesson.lib.ui.ArticlePageObject;
import ru.appium.lesson.lib.ui.SearchPageObject;
import ru.appium.lesson.lib.ui.WelcomePageObject;

public class ChangeAppConditionTests extends CoreTestCase {
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
  public void testChangeScreenOrientationOnSearchResult() {
    String searchText = "Java";
    String articleTitle = "Object-oriented programming language";
    this.welcome.skipWelcome();
    this.search.initSearchInput();
    this.search.typeSearchLine(searchText);
    this.search.clickByArticleWithSubstring(articleTitle);
    String titleBeforeRotation = this.article.getArticleTitle();

    this.rotateScreenLandscape();

    String titleAfterRotation = this.article.getArticleTitle();

    assertEquals(
        "Article title has been changed after rotation", titleBeforeRotation, titleAfterRotation);

    this.rotateScreenPortrait();

    String titleAfterSecondRotation = this.article.getArticleTitle();

    assertEquals(
        "Article title has been changed after second rotation",
        titleBeforeRotation,
        titleAfterSecondRotation);
  }

  @Test
  public void testCheckSearchArticleInBackground() {
    String searchText = "Java";
    this.welcome.skipWelcome();
    this.search.initSearchInput();
    this.search.typeSearchLine(searchText);
    this.search.waitSearchResult("Object-oriented programming language");
    this.backgroundApp(2);
    this.search.waitSearchResult("Object-oriented programming language");
  }
}
