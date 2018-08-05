package ru.appium.lesson.lib.ui;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import io.appium.java_client.AppiumDriver;

public class WelcomePageObject extends MainPageObject {
  private static final By SKIP_BUTTON = By.id("org.wikipedia:id/fragment_onboarding_skip_button");

  public WelcomePageObject(AppiumDriver<WebElement> driver) {
    super(driver);
  }

  public void skipWelcome() {
    this.waitForElementAndClick(SKIP_BUTTON, "Can't find skip button");
  }
}
