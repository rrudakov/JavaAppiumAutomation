package ru.appium.lesson.lib.ui.ios;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import io.appium.java_client.AppiumDriver;
import ru.appium.lesson.lib.ui.SearchPageObject;

public class IOSSearchPageObject extends SearchPageObject {
  static {
    SEARCH_INIT = By.xpath("//XCUIElementTypeSearchField[@name='Search Wikipedia']");
    SEARCH_INPUT = By.xpath("//XCUIElementTypeSearchField[not(@name)]");
    // SEARCH_INPUT = By.xpath("//XCUIElementTypeSearchField[@value='Search Wikipedia']");
    SEARCH_INPUT_BY_ID = By.xpath("//XCUIElementTypeSearchField[not(@name)]");
    SEARCH_CANCEL_BUTTON = By.id("Close");
    SEARCH_RESULTS = By.xpath("//XCUIElementTypeLink");
    SEARCH_EMPTY_RESULT_LABEL = By.xpath("//XCUIElementTypeStaticText[@name='No results found']");
    SEARCH_RESULT_BY_SUBSTRING_TPL = "//XCUIElementTypeLink[contains(@name, '{SUBSTRING}')]";
    SEARCH_RESULT_BY_TITLE_AND_DESC_TPL =
        "//XCUIElementTypeLink[contains(@name, '{TITLE}') and contains(@name, '{DESCRIPTION}')]";
  }

  public IOSSearchPageObject(AppiumDriver<WebElement> driver) {
    super(driver);
  }
}
