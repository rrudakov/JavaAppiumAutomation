package ru.appium.lesson.lib.ui.android;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import io.appium.java_client.AppiumDriver;
import ru.appium.lesson.lib.ui.ArticlePageObject;

public class AndroidArticlePageObject extends ArticlePageObject {
  static {
    ARTICLE_TITLE = By.id("org.wikipedia:id/view_page_title_text");
    FOOTER_ELEMENT = By.xpath("//*[@text='View page in browser']");
    MORE_OPTIONS = By.xpath("//android.widget.ImageView[@content-desc='More options']");
    GOT_IT = By.id("org.wikipedia:id/onboarding_button");
    CREATE_LIST = By.xpath("//*[@resource-id='org.wikipedia:id/create_button']");
    LIST_NAME_INPUT = By.id("org.wikipedia:id/text_input");
    OK_BUTTON = By.id("android:id/button1");
    CLOSE_BUTTON = By.xpath("//android.widget.ImageButton[@content-desc='Navigate up']");
  }

  public AndroidArticlePageObject(AppiumDriver<WebElement> driver) {
    super(driver);
  }
}
