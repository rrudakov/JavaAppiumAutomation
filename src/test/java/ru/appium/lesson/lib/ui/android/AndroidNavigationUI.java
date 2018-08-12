package ru.appium.lesson.lib.ui.android;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import io.appium.java_client.AppiumDriver;
import ru.appium.lesson.lib.ui.NavigationUI;

public class AndroidNavigationUI extends NavigationUI {
  static {
    SAVED_ITEMS =
        By.xpath(
            "//*[@resource-id='org.wikipedia:id/fragment_main_nav_tab_layout']//android.widget.FrameLayout[2]");
    BACK_BUTTON =
        By.xpath("//*[@resource-id='org.wikipedia:id/search_toolbar']/android.widget.ImageButton");
  }

  public AndroidNavigationUI(AppiumDriver<WebElement> driver) {
    super(driver);
  }
}
