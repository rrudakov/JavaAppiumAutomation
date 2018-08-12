package ru.appium.lesson.lib.ui.android;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import io.appium.java_client.AppiumDriver;
import ru.appium.lesson.lib.ui.SearchPageObject;

public class AndroidSearchPageObject extends SearchPageObject {
  static {
    SEARCH_INIT = By.xpath("//*[contains(@text, 'Search Wikipedia')]");
    SEARCH_INPUT = By.xpath("//*[contains(@text, 'Search…')]");
    SEARCH_INPUT_BY_ID = By.id("org.wikipedia:id/search_src_text");
    SEARCH_CANCEL_BUTTON = By.id("org.wikipedia:id/search_close_btn");
    SEARCH_RESULTS =
        By.xpath(
            "//*[@resource-id='org.wikipedia:id/search_results_list']//*[@resource-id='org.wikipedia:id/page_list_item_title']");
    SEARCH_RESULT_BY_SUBSTRING_TPL =
        "//*[@resource-id='org.wikipedia:id/search_results_list']//*[@text='{SUBSTRING}']";
    SEARCH_RESULT_BY_TITLE_AND_DESC_TPL =
        "//*[./*[@resource-id='org.wikipedia:id/page_list_item_title' and @text='{TITLE}'] and ./*[@resource-id='org.wikipedia:id/page_list_item_description' and @text='{DESCRIPTION}']]";
    SEARCH_EMPTY_RESULT_LABEL = By.xpath("//*[@text='No results found']");
  }

  public AndroidSearchPageObject(AppiumDriver<WebElement> driver) {
    super(driver);
  }
}
