package ru.appium.lesson.lib.ui.factories;

import org.openqa.selenium.WebElement;

import io.appium.java_client.AppiumDriver;
import ru.appium.lesson.lib.Platform;
import ru.appium.lesson.lib.ui.NavigationUI;
import ru.appium.lesson.lib.ui.android.AndroidNavigationUI;
import ru.appium.lesson.lib.ui.ios.IOSNavigationUI;

public class NavigationUIFactory {
  public static NavigationUI get(AppiumDriver<WebElement> driver) {
    if (Platform.getInstance().isAndroid()) return new AndroidNavigationUI(driver);
    else return new IOSNavigationUI(driver);
  }
}
