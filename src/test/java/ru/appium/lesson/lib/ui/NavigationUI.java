package ru.appium.lesson.lib.ui;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import io.appium.java_client.AppiumDriver;

public class NavigationUI extends MainPageObject {
  private static final By
      SAVED_ITEMS =
          By.xpath(
              "//*[@resource-id='org.wikipedia:id/fragment_main_nav_tab_layout']//android.widget.FrameLayout[2]"),
      BACK_BUTTON =
          By.xpath(
              "//*[@resource-id='org.wikipedia:id/search_toolbar']/android.widget.ImageButton");

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
