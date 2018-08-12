package ru.appium.lesson.lib.ui.ios;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import io.appium.java_client.AppiumDriver;
import ru.appium.lesson.lib.ui.ArticlePageObject;

public class IOSArticlePageObject extends ArticlePageObject {
  static {
    ARTICLE_TITLE = By.id("Java (programming language)");
    FOOTER_ELEMENT = By.id("View article in browser");
    OPTIONS_ADD_TO_MY_LIST = By.id("Save for later");
    CLOSE_BUTTON = By.id("Back");
    CLOSE_SYNC_BUTTON = By.id("places auth close");
  }

  public IOSArticlePageObject(AppiumDriver<WebElement> driver) {
    super(driver);
  }
}
