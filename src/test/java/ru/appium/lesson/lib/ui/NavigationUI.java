package ru.appium.lesson.lib.ui;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import io.appium.java_client.AppiumDriver;

public abstract class NavigationUI extends MainPageObject {
  protected static By SAVED_ITEMS, BACK_BUTTON;

  public NavigationUI(AppiumDriver<WebElement> driver) {
    super(driver);
  }

  public void clickMyLists() {
    this.waitForElementAndClick(SAVED_ITEMS, "Can't find saved items button");
  }

  public void clickBackButton() {
    this.waitForElementAndClick(BACK_BUTTON, "Can't find back button");
  }
}
