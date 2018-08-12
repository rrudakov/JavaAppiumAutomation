package ru.appium.lesson.lib.ui.ios;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import io.appium.java_client.AppiumDriver;
import ru.appium.lesson.lib.ui.NavigationUI;

public class IOSNavigationUI extends NavigationUI {
  static {
    SAVED_ITEMS = By.id("Saved");
    BACK_BUTTON = By.id("Close");
  }

  public IOSNavigationUI(AppiumDriver<WebElement> driver) {
    super(driver);
  }
}
