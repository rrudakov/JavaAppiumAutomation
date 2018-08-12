package ru.appium.lesson.lib.ui.factories;

import org.openqa.selenium.WebElement;

import io.appium.java_client.AppiumDriver;
import ru.appium.lesson.lib.Platform;
import ru.appium.lesson.lib.ui.ArticlePageObject;
import ru.appium.lesson.lib.ui.android.AndroidArticlePageObject;
import ru.appium.lesson.lib.ui.ios.IOSArticlePageObject;

public class ArticlePageObjectFactory {
  public static ArticlePageObject get(AppiumDriver<WebElement> driver) {
    if (Platform.getInstance().isAndroid()) return new AndroidArticlePageObject(driver);
    else return new IOSArticlePageObject(driver);
  }
}
