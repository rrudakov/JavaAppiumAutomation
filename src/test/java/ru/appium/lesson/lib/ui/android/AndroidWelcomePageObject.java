package ru.appium.lesson.lib.ui.android;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import io.appium.java_client.AppiumDriver;
import ru.appium.lesson.lib.ui.WelcomePageObject;

public class AndroidWelcomePageObject extends WelcomePageObject {
  static {
    SKIP_BUTTON = By.id("org.wikipedia:id/fragment_onboarding_skip_button");
    // From iOS tests, anyway it will be skipped
    LEARN_MORE_LINK = By.id("Learn more about Wikipedia");
    NEW_WAYS_TO_EXPLORE_TEXT = By.id("New ways to explore");
    ADD_LANG_LINK = By.id("Add or edit preferred languages");
    COLLECTED_DATA_LINK = By.id("Learn more about data collected");
    GET_STARTED_BUTTON = By.id("Get started");
    NEXT = By.id("Next");
  }

  public AndroidWelcomePageObject(AppiumDriver<WebElement> driver) {
    super(driver);
  }
}
