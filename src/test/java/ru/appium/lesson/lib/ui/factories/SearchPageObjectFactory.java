package ru.appium.lesson.lib.ui.factories;

import org.openqa.selenium.WebElement;

import io.appium.java_client.AppiumDriver;
import ru.appium.lesson.lib.Platform;
import ru.appium.lesson.lib.ui.SearchPageObject;
import ru.appium.lesson.lib.ui.android.AndroidSearchPageObject;
import ru.appium.lesson.lib.ui.ios.IOSSearchPageObject;

public class SearchPageObjectFactory {
  public static SearchPageObject get(AppiumDriver<WebElement> driver) {
    if (Platform.getInstance().isAndroid()) return new AndroidSearchPageObject(driver);
    else return new IOSSearchPageObject(driver);
  }
}
