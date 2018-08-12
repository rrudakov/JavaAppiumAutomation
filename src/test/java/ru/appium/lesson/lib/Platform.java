package ru.appium.lesson.lib;

import java.net.URL;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;

public class Platform {
  private static final String PLATFORM_IOS = "ios";
  private static final String PLATFORM_ANDROID = "android";
  private static String APPIUM_URL = "http://127.0.0.1:4723/wd/hub";

  private static Platform instance;

  public static Platform getInstance() {
    if (instance == null) {
      instance = new Platform();
    }
    return instance;
  }

  public AppiumDriver<WebElement> getDriver() throws Exception {
    URL url = new URL(APPIUM_URL);
    if (this.isAndroid()) return new AndroidDriver<>(url, this.getAndroidDesiredCapabilities());
    else if (this.isIOS()) return new IOSDriver<>(url, this.getIOSDesiredCapabilities());
    else
      throw new Exception(
          String.format(
              "Can't detect type of the driver. Platform value: %s", this.getPlatformVar()));
  }

  private String getPlatformVar() {
    return System.getenv("PLATFORM");
  }

  private boolean isPlatform(String myPlatform) {
    return myPlatform.equalsIgnoreCase(this.getPlatformVar());
  }

  public boolean isAndroid() {
    return this.isPlatform(PLATFORM_ANDROID);
  }

  public boolean isIOS() {
    return this.isPlatform(PLATFORM_IOS);
  }

  private DesiredCapabilities getAndroidDesiredCapabilities() {
    DesiredCapabilities capabilities = new DesiredCapabilities();
    capabilities.setCapability("platformName", "Android");
    capabilities.setCapability("deviceName", "HUAWEI P9 lite");
    capabilities.setCapability("platformVersion", "7.0");
    capabilities.setCapability("automationName", "Appium");
    capabilities.setCapability("appPackage", "org.wikipedia");
    capabilities.setCapability("appActivity", ".main.MainActivity");
    capabilities.setCapability("app", "/home/rrudakov/Work/appium-lesson/apks/org.wikipedia.apk");
    return capabilities;
  }

  private DesiredCapabilities getIOSDesiredCapabilities() {
    DesiredCapabilities capabilities = new DesiredCapabilities();
    capabilities.setCapability("platformName", "iOS");
    capabilities.setCapability("deviceName", "iPhone SE");
    capabilities.setCapability("platformVersion", "11.4");
    capabilities.setCapability(
        "app", "/Users/administrator/Work/JavaAppiumAutomation/apks/Wikipedia.app");

    return capabilities;
  }
}
