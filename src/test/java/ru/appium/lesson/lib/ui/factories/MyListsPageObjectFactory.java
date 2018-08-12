package ru.appium.lesson.lib.ui.factories;

import org.openqa.selenium.WebElement;

import io.appium.java_client.AppiumDriver;
import ru.appium.lesson.lib.Platform;
import ru.appium.lesson.lib.ui.MyListsPageObject;
import ru.appium.lesson.lib.ui.android.AndroidMyListsPageObject;
import ru.appium.lesson.lib.ui.ios.IOSMyListsPageObject;

public class MyListsPageObjectFactory {
  public static MyListsPageObject get(AppiumDriver<WebElement> driver) {
    if (Platform.getInstance().isAndroid()) return new AndroidMyListsPageObject(driver);
    else return new IOSMyListsPageObject(driver);
  }
}
