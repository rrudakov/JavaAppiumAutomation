package ru.appium.lesson.lib.ui;

import java.util.List;
import java.util.stream.Collectors;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import io.appium.java_client.AppiumDriver;
import ru.appium.lesson.lib.Platform;

public abstract class SearchPageObject extends MainPageObject {
  protected static By SEARCH_INIT,
      SEARCH_INPUT,
      SEARCH_INPUT_BY_ID,
      SEARCH_CANCEL_BUTTON,
      SEARCH_RESULTS,
      SEARCH_EMPTY_RESULT_LABEL;
  protected static String SEARCH_RESULT_BY_SUBSTRING_TPL, SEARCH_RESULT_BY_TITLE_AND_DESC_TPL;

  public SearchPageObject(AppiumDriver<WebElement> driver) {
    super(driver);
  }

  /** Fix xpath by template. */
  private static By getResultSearchLocator(String substring) {
    return By.xpath(SEARCH_RESULT_BY_SUBSTRING_TPL.replace("{SUBSTRING}", substring));
  }

  private static By getResultSearchLocator(String title, String description) {
    return By.xpath(
        SEARCH_RESULT_BY_TITLE_AND_DESC_TPL
            .replace("{TITLE}", title)
            .replace("{DESCRIPTION}", description));
  }

  public void initSearchInput() {
    this.waitForElementAndClick(SEARCH_INIT, "Can't find and click search init element");
    this.waitForElementPresent(SEARCH_INPUT, "Can't find search input after clicking search init");
  }

  public void typeSearchLine(String searchText) {
    this.waitForElementAndClear(SEARCH_INPUT, "Can't find and click search input");
    this.waitForElementAndSendKeys(SEARCH_INPUT, searchText, "Can't find and type search input");
  }

  public void waitSearchResult(String substring) {
    this.waitForElementPresent(
        getResultSearchLocator(substring),
        String.format("Can't find '%s' in search result", substring),
        15);
  }

  public void waitForElementByTitleAndDescription(String title, String description) {
    this.waitForElementPresent(
        getResultSearchLocator(title, description),
        String.format(
            "Can't find element with title '%s' and description '%s'", title, description),
        15);
  }

  public void clickByArticleWithSubstring(String substring) {
    this.waitForElementAndClick(
        getResultSearchLocator(substring),
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
    this.waitForElementPresent(SEARCH_EMPTY_RESULT_LABEL, "Can't find empty results label", 15);
  }

  public void assertThereIsNoResultOfSearch() {
    this.assertElementNotPresent(SEARCH_RESULTS, "We supposed not to find any results");
  }

  public String getSearchInputPlaceholder() {
    return this.waitForElementPresent(SEARCH_INPUT_BY_ID, "Can't find 'Search' input")
        .getAttribute("text");
  }

  public List<String> searchResultTitles() {
    List<WebElement> titleElements =
        this.waitForElementsPresent(SEARCH_RESULTS, "Can't find anything by the request");
    return titleElements.stream()
        .map(
            title ->
                Platform.getInstance().isAndroid()
                    ? title.getAttribute("text")
                    : title.getAttribute("name"))
        .collect(Collectors.toList());
  }
}
