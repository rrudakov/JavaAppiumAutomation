package ru.appium.lesson.lib.ui.ios;

import org.openqa.selenium.WebElement;

import io.appium.java_client.AppiumDriver;
import ru.appium.lesson.lib.ui.MyListsPageObject;

public class IOSMyListsPageObject extends MyListsPageObject {
  static {
    ARTICLE_BY_TITLE_TPL = "//XCUIElementTypeLink[contains(@name, '{TITLE}')]";
    ARTICLE_BY_TITLE_PARENT_TPL = "//XCUIElementTypeLink[contains(@name, '{TITLE}')]/..";
  }

  public IOSMyListsPageObject(AppiumDriver<WebElement> driver) {
    super(driver);
  }
}
