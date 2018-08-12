package ru.appium.lesson.lib.ui;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import io.appium.java_client.AppiumDriver;

public abstract class WelcomePageObject extends MainPageObject {
  protected static By SKIP_BUTTON,
      LEARN_MORE_LINK,
      NEW_WAYS_TO_EXPLORE_TEXT,
      ADD_LANG_LINK,
      COLLECTED_DATA_LINK,
      GET_STARTED_BUTTON,
      NEXT;

  public WelcomePageObject(AppiumDriver<WebElement> driver) {
    super(driver);
  }

  public void skipWelcome() {
    this.waitForElementAndClick(SKIP_BUTTON, "Can't find skip button");
  }

  public void waitForLearnMoreLink() {
    this.waitForElementsPresent(LEARN_MORE_LINK, "Can't find 'Learn more about wikipedia' link");
  }

  public void waitForNewWayToExplore() {
    this.waitForElementsPresent(NEW_WAYS_TO_EXPLORE_TEXT, "Can't find 'New way to explore' link");
  }

  public void waitForAddOrEditPreferredLangText() {
    this.waitForElementsPresent(ADD_LANG_LINK, "Can't find 'Add or edit preferred languages' link");
  }

  public void waitForLearnMoreAboutDataCollectedText() {
    this.waitForElementsPresent(
        COLLECTED_DATA_LINK, "Can't find 'Learn more about data collected' link");
  }

  public void clickGetStartedButton() {
    this.waitForElementAndClick(GET_STARTED_BUTTON, "Can't find and click 'Get started' link");
  }

  public void clickNextButton() {
    this.waitForElementAndClick(NEXT, "Can't find 'Next' link");
  }
}
