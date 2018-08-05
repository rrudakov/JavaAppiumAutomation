package ru.appium.lesson.lib.ui;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import io.appium.java_client.AppiumDriver;

public class SearchPageObject extends MainPageObject {
  private static final By SEARCH_INIT = By.xpath("//*[contains(@text, 'Search Wikipedia')]"),
      SEARCH_INPUT = By.xpath("//*[contains(@text, 'Search…')]"),
      SEARCH_INPUT_BY_ID = By.id("org.wikipedia:id/search_src_text"),
      SEARCH_CANCEL_BUTTON = By.id("org.wikipedia:id/search_close_btn"),
      SEARCH_RESULTS =
          By.xpath(
              "//*[@resource-id='org.wikipedia:id/search_results_list']/*[@resource-id='org.wikipedia:id/page_list_item_container']");;
  private static final String SEARCH_RESULT_BY_SUBSTRING_TPL =
      "//*[@resource-id='org.wikipedia:id/page_list_item_container']//*[@text='{SUBSTRING}']";

  public SearchPageObject(AppiumDriver<WebElement> driver) {
    super(driver);
  }

  private static By getResulSearchLocator(String substring) {
    return By.xpath(SEARCH_RESULT_BY_SUBSTRING_TPL.replace("{SUBSTRING}", substring));
  }

  public void initSearchInput() {
    this.waitForElementAndClick(SEARCH_INIT, "Can't find and click search init element");
    this.waitForElementPresent(SEARCH_INPUT, "Can't find search input after clicking search init");
  }

  public void typeSearchLine(String searchText) {
    this.waitForElementAndSendKeys(SEARCH_INPUT, searchText, "Can't find and type search input");
  }

  public void waitSearchResult(String substring) {
    this.waitForElementPresent(
        getResulSearchLocator(substring),
        String.format("Can't find '%s' in search result", substring),
        15);
  }

  public void clickByArticleWithSubstring(String substring) {
    this.waitForElementAndClick(
        getResulSearchLocator(substring),
        String.format("Can't find and click '%s' in search result", substring),
        15);
  }

  public void waitForCancelButtonToAppear() {
    this.waitForElementsPresent(SEARCH_CANCEL_BUTTON, "Can't find search cancel button");
  }

  public void waitForCancelButtonToDisappear() {
    this.waitForElementNotPresent(SEARCH_CANCEL_BUTTON, "Search cancel button not disappear");
  }

  public void clickCancelSearch() {
    this.waitForElementAndClick(SEARCH_CANCEL_BUTTON, "Can't find and click search cancel button");
  }

  public int getAmountOfFoundArticles() {
    this.waitForElementPresent(SEARCH_RESULTS, "Can't find anything by the request", 15);
    return this.getAmountOfElements(SEARCH_RESULTS);
  }

  public void waitForEmptyResultsLabel() {
    this.waitForElementPresent(
        this.byText("No results found"), "Can't find empty results label", 15);
  }

  public void assertThereIsNoResultOfSearch() {
    this.assertElementNotPresent(SEARCH_RESULTS, "We supposed not to find any results");
  }
}
