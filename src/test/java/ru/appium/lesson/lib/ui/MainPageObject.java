package ru.appium.lesson.lib.ui;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.TouchAction;

public class MainPageObject {
  private static final int DEFAULT_TIMEOUT = 10;
  protected AppiumDriver<WebElement> driver;

  public MainPageObject(AppiumDriver<WebElement> driver) {
    this.driver = driver;
  }

  protected void assertElementPresent(By locator, String errorMessage) {
    int amountOfElements = this.getAmountOfElements(locator);

    if (amountOfElements == 0) {
      throw new AssertionError(
          String.format(
              "An element %s should not be present. %s", locator.toString(), errorMessage));
    }
  }

  // Этот метод я изначально написал для задания, testAssertElementPresent всего лишь тест, который
  // использует этот метод
  protected boolean isElementCurrentlyVisible(By locator) {
    try {
      WebElement element = waitForElementPresent(locator, "Ignore error", 0);
      return element.isDisplayed();
    } catch (TimeoutException e) {
      return false;
    }
  }

  protected WebElement waitForElementPresent(By locator, String errorMessage, long timeOutInSeconds) {
    WebDriverWait wait = new WebDriverWait(driver, timeOutInSeconds);
    wait.withMessage(errorMessage + "\n");
    return wait.until(ExpectedConditions.presenceOfElementLocated(locator));
  }

  protected WebElement waitForElementPresent(By locator, String errorMessage) {
    return waitForElementPresent(locator, errorMessage, DEFAULT_TIMEOUT);
  }

  protected List<WebElement> waitForElementsPresent(
      By locator, String errorMessage, long timeOutInSeconds) {
    WebDriverWait wait = new WebDriverWait(driver, timeOutInSeconds);
    wait.withMessage(errorMessage + "\n");
    return wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(locator));
  }

  protected List<WebElement> waitForElementsPresent(By locator, String errorMessage) {
    return this.waitForElementsPresent(locator, errorMessage, DEFAULT_TIMEOUT);
  }

  protected WebElement waitForElementAndClick(By locator, String errorMessage, long timeOutInSeconds) {
    WebElement element = waitForElementPresent(locator, errorMessage, timeOutInSeconds);
    element.click();
    return element;
  }

  protected WebElement waitForElementAndClick(By locator, String errorMessage) {
    return this.waitForElementAndClick(locator, errorMessage, DEFAULT_TIMEOUT);
  }

  protected WebElement waitForElementAndSendKeys(
      By locator, String value, String errorMessage, long timeOutInSedonds) {
    WebElement element = waitForElementPresent(locator, errorMessage, timeOutInSedonds);
    element.sendKeys(value);
    return element;
  }

  protected WebElement waitForElementAndSendKeys(By locator, String value, String errorMessage) {
    return this.waitForElementAndSendKeys(locator, value, errorMessage, DEFAULT_TIMEOUT);
  }

  protected boolean waitForElementNotPresent(By locator, String errorMessage, long timeOutInSeconds) {
    WebDriverWait wait = new WebDriverWait(driver, timeOutInSeconds);
    wait.withMessage(errorMessage + "\n");
    return wait.until(ExpectedConditions.invisibilityOfElementLocated(locator));
  }

  protected boolean waitForElementNotPresent(By locator, String errorMessage) {
    return this.waitForElementNotPresent(locator, errorMessage, DEFAULT_TIMEOUT);
  }

  protected WebElement waitForElementAndClear(By locator, String errorMessage, long timeOutInSeconds) {
    WebElement element = waitForElementPresent(locator, errorMessage);
    element.clear();
    return element;
  }

  protected WebElement waitForElementAndClear(By locator, String errorMessage) {
    return this.waitForElementAndClear(locator, errorMessage, DEFAULT_TIMEOUT);
  }

  protected void swipeUp(int timeOfSwipe) {
    TouchAction action = new TouchAction(driver);
    Dimension size = driver.manage().window().getSize();
    int x = size.getWidth() / 2;
    int y_start = (int) (size.getHeight() * 0.8);
    int y_end = (int) (size.getHeight() * 0.2);

    action.press(x, y_start).waitAction(timeOfSwipe).moveTo(x, y_end).release().perform();
  }

  protected void swipeUpQuick() {
    this.swipeUp(200);
  }

  protected void swipeUpToFindElement(By locator, String errorMessage, int maxSwipes) {
    int alreadySwiped = 0;

    while (driver.findElements(locator).size() == 0) {
      if (alreadySwiped > maxSwipes) {
        this.waitForElementsPresent(
            locator, String.format("Can't find element by swiping up.\n%s", errorMessage), 0);
        return;
      }

      this.swipeUpQuick();
      alreadySwiped++;
    }
  }

  protected void swipeElementToLeft(By locator, String errorMessage) {
    WebElement element = waitForElementPresent(locator, errorMessage);
    int middleY = element.getLocation().getY() + element.getSize().getHeight() / 2;
    int leftX = element.getLocation().getX();
    int rightX = leftX + element.getSize().getWidth();
    System.out.printf("MIddleY: %d, RightX: %d, LeftX: %d\n", middleY, rightX, leftX);
    TouchAction action = new TouchAction(driver);
    action.press(rightX, middleY).waitAction(800).moveTo(leftX, middleY).release().perform();
  }

  protected int getAmountOfElements(By locator) {
    List<WebElement> elements = driver.findElements(locator);
    return elements.size();
  }

  protected void assertElementNotPresent(By locator, String errorMessage) {
    int amountOfElements = this.getAmountOfElements(locator);

    if (amountOfElements > 0) {
      throw new AssertionError(
          String.format(
              "An element %s supposed to be present. %s", locator.toString(), errorMessage));
    }
  }

  protected String waitForElementAndGetAttribure(
      By locator, String attribureName, String errorMessage, long timeOutInSecond) {
    WebElement element = this.waitForElementPresent(locator, errorMessage, timeOutInSecond);
    return element.getAttribute(attribureName);
  }

  protected String waitForElementAndGetAttribure(
      By locator, String attribureName, String errorMessage) {
    return this.waitForElementAndGetAttribure(
        locator, attribureName, errorMessage, DEFAULT_TIMEOUT);
  }

  protected By byText(String elementText) {
    return By.xpath(String.format("//*[@text='%s']", elementText));
  }
}