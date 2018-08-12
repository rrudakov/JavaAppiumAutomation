package ru.appium.lesson.lib;

import org.openqa.selenium.ScreenOrientation;
import org.openqa.selenium.WebElement;

import io.appium.java_client.AppiumDriver;
import junit.framework.TestCase;

public class CoreTestCase extends TestCase {
  protected AppiumDriver<WebElement> driver;
  protected Platform platform;

  @Override
  protected void setUp() throws Exception {
    super.setUp();
    this.driver = Platform.getInstance().getDriver();
    this.rotateScreenPortrait();
  }

  @Override
  protected void tearDown() throws Exception {
    driver.quit();
    super.tearDown();
  }

  protected void rotateScreenPortrait() {
    this.driver.rotate(ScreenOrientation.PORTRAIT);
  }

  protected void rotateScreenLandscape() {
    this.driver.rotate(ScreenOrientation.LANDSCAPE);
  }

  protected void backgroundApp(int seconds) {
    this.driver.runAppInBackground(seconds);
  }
}
