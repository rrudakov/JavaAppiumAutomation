package ru.appium.lesson.lib.ui;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import io.appium.java_client.AppiumDriver;
import ru.appium.lesson.lib.Platform;

public abstract class ArticlePageObject extends MainPageObject {
  protected static By ARTICLE_TITLE,
      FOOTER_ELEMENT,
      MORE_OPTIONS,
      OPTIONS_ADD_TO_MY_LIST,
      GOT_IT,
      CREATE_LIST,
      LIST_NAME_INPUT,
      OK_BUTTON,
      CLOSE_BUTTON,
      CLOSE_SYNC_BUTTON;

  public ArticlePageObject(AppiumDriver<WebElement> driver) {
    super(driver);
  }

  public WebElement waitForTitleElement() {
    return this.waitForElementPresent(ARTICLE_TITLE, "Can't find article title on page", 15);
  }

  public String getArticleTitle() {
    WebElement titleElement = this.waitForTitleElement();
    if (Platform.getInstance().isAndroid()) return titleElement.getAttribute("text");
    else return titleElement.getAttribute("name");
  }

  public void swipeToFooter() {
    String error = "Can't find the end of article";
    if (Platform.getInstance().isAndroid()) this.swipeUpToFindElement(FOOTER_ELEMENT, error, 40);
    else this.swipeTillElementAppear(FOOTER_ELEMENT, error, 40);
  }

  public void addArticleToMyList(String nameOfFolder) {
    this.waitForElementAndClick(MORE_OPTIONS, "Can't find 'More options' button");
    this.waitForElementsPresent(
        this.byText("Add to reading list"), "Can't find 'Add to reading list' item");
    this.waitForElementAndClick(
        this.byText("Add to reading list"), "Can't find 'Add to reading list' item");
    this.waitForElementAndClick(GOT_IT, "Can't find 'Got it!' button");
    this.waitForElementAndClick(CREATE_LIST, "Can't find 'Create list' button");
    this.waitForElementAndClear(LIST_NAME_INPUT, "Can't find 'List name' input field");
    this.waitForElementAndSendKeys(
        LIST_NAME_INPUT,
        nameOfFolder,
        String.format("Can't fill in 'List name' input with text '%s'", nameOfFolder));
    this.waitForElementAndClick(OK_BUTTON, "Can't find 'OK' button");
  }

  public void closeArticle() {
    this.waitForElementAndClick(CLOSE_BUTTON, "Can't find 'Close' button");
  }

  public void addArticleToMySaved() {
    this.waitForElementAndClick(OPTIONS_ADD_TO_MY_LIST, "Can't find save to by list button");
    this.waitForElementAndClick(CLOSE_SYNC_BUTTON, "Can't find close sync button");
  }

  public void addArticleToExistingList(String nameOfFolder) {
    this.waitForElementAndClick(MORE_OPTIONS, "Can't find 'More options' button");
    this.waitForElementsPresent(
        this.byText("Add to reading list"), "Can't find 'Add to reading list' item");
    this.waitForElementAndClick(
        this.byText("Add to reading list"), "Can't find 'Add to reading list' item");
    this.waitForElementAndClick(
        this.byText(nameOfFolder),
        String.format("Can't find reading list with name '%s'", nameOfFolder));
  }
}
