package ru.appium.lesson.lib.ui.android;

import org.openqa.selenium.WebElement;

import io.appium.java_client.AppiumDriver;
import ru.appium.lesson.lib.ui.MyListsPageObject;

public class AndroidMyListsPageObject extends MyListsPageObject {
  static {
    ARTICLE_BY_TITLE_TPL =
        "//*[@resource-id='org.wikipedia:id/page_list_item_title' and @text='{TITLE}']";
  }

  public AndroidMyListsPageObject(AppiumDriver<WebElement> driver) {
    super(driver);
  }
}
