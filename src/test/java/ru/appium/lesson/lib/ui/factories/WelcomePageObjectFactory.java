package ru.appium.lesson.lib.ui.factories;

import org.openqa.selenium.WebElement;

import io.appium.java_client.AppiumDriver;
import ru.appium.lesson.lib.Platform;
import ru.appium.lesson.lib.ui.WelcomePageObject;
import ru.appium.lesson.lib.ui.android.AndroidWelcomePageObject;
import ru.appium.lesson.lib.ui.ios.IOSWelcomePageObject;

public class WelcomePageObjectFactory {
  public static WelcomePageObject get(AppiumDriver<WebElement> driver) {
    if (Platform.getInstance().isAndroid()) return new AndroidWelcomePageObject(driver);
    else return new IOSWelcomePageObject(driver);
  }
}
