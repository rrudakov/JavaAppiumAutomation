package ru.appium.lesson.lib.ui;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import io.appium.java_client.AppiumDriver;

public class ArticlePageObject extends MainPageObject {
  private static final By ARTICLE_TITLE = By.id("org.wikipedia:id/view_page_title_text"),
      FOOTER_ELEMENT = By.xpath("//*[@text='View page in browser']"),
      MORE_OPTIONS = By.xpath("//android.widget.ImageView[@content-desc='More options']"),
      GOT_IT = By.id("org.wikipedia:id/onboarding_button"),
      CREATE_LIST = By.xpath("//*[@resource-id='org.wikipedia:id/create_button']"),
      LIST_NAME_INPUT = By.id("org.wikipedia:id/text_input"),
      OK_BUTTON = By.id("android:id/button1"),
      CLOSE_BUTTON = By.xpath("//android.widget.ImageButton[@content-desc='Navigate up']");

  public ArticlePageObject(AppiumDriver<WebElement> driver) {
    super(driver);
  }

  public WebElement waitForTitleElement() {
    return this.waitForElementPresent(ARTICLE_TITLE, "Can't find article title on page", 15);
  }

  public String getArticleTitle() {
    return this.waitForTitleElement().getAttribute("text");
  }

  public void swipeToFooter() {
    this.swipeUpToFindElement(FOOTER_ELEMENT, "Can't find the end of article", 20);
  }

  public void addArticleToMyList(String nameOfFolder) {
    this.waitForElementAndClick(MORE_OPTIONS, "Can't find 'More options' button");
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
}
