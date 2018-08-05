package ru.appium.lesson.lib.ui;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import io.appium.java_client.AppiumDriver;

public class MyListsPageObject extends MainPageObject {
  private static final String ARTICLE_BY_TITLE_TPL =
      "//*[@resource-id='org.wikipedia:id/page_list_item_title' and @text='{TITLE}']";

  public MyListsPageObject(AppiumDriver<WebElement> driver) {
    super(driver);
  }

  private static By getSavedArticleXpathByTitle(String articleTitle) {
    return By.xpath(ARTICLE_BY_TITLE_TPL.replace("{TITLE}", articleTitle));
  }

  public void openFolderByName(String nameOfFolder) {
    this.waitForElementAndClick(
        this.byText(nameOfFolder),
        String.format("Can't find saved list with name '%s'", nameOfFolder));
  }

  public void waitForArticleToAppearByTitle(String articleTitle) {
    this.waitForElementPresent(
        getSavedArticleXpathByTitle(articleTitle),
        String.format("Can't find article by name '%s'", articleTitle));
  }

  public void swipeByArticleToDelete(String articleTitle) {
    this.waitForArticleToAppearByTitle(articleTitle);
    this.swipeElementToLeft(this.byText(articleTitle), "Can't find saved article");
  }

  public void waitForArticleToDisappearByTitle(String articleTitle) {
    this.waitForElementNotPresent(
        getSavedArticleXpathByTitle(articleTitle), "Can't delete saved article", 15);
  }

  public void openArticleFromSavedFolderByTitle(String articleTitle) {
    this.waitForElementAndClick(
        this.byText(articleTitle),
        String.format("Can't find saved article with title '%s'", articleTitle));
  }
}
