package ru.appium.lesson;

import java.net.URL;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;

/**
 * First test using appium.
 */
public class FirstTest {

    private AppiumDriver<MobileElement> driver;
    private static final int DEFAULT_TIMEOUT = 5;

    @Before
    public void setUp() throws Exception {
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("platformName", "Android");
        capabilities.setCapability("deviceName", "HUAWEI P9 lite");
        capabilities.setCapability("platformVersion", "7.0");
        capabilities.setCapability("automationName", "Appium");
        capabilities.setCapability("appPackage", "org.wikipedia");
        capabilities.setCapability("appActivity", ".main.MainActivity");
        capabilities.setCapability("app", "/home/rrudakov/Work/appium-lesson/apks/org.wikipedia.apk");

        driver = new AndroidDriver<>(new URL("http://127.0.0.1:4723/wd/hub"), capabilities);
    }

    @After
    public void tearDown() {
        driver.quit();
    }

    /**
     * Rigorous Test :-)
     */
    @Test
    public void shouldAnswerWithTrue() {
        waitForElementAndClick(By.id("org.wikipedia:id/fragment_onboarding_skip_button"),
                               "Can't find skip button",
                               DEFAULT_TIMEOUT);

        waitForElementAndClick(By.xpath("//*[contains(@text, 'Search Wikipedia')]"),
                               "Can't find 'Search wikipedia' input",
                               DEFAULT_TIMEOUT);

        waitForElementAndSendKeys(By.xpath("//*[contains(@text, 'Search…')]"),
                                  "Java",
                                  "Can't find 'Search' input",
                                  DEFAULT_TIMEOUT);

        waitForElementPresent(By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_container']//*[@text='Object-oriented programming language']"),
                              "Can't find 'Object-oriented programming language' topic by 'Java'",
                              15);
    }

    @Test
    public void testCancelSearch() {
        waitForElementAndClick(By.id("org.wikipedia:id/fragment_onboarding_skip_button"),
                               "Can't find skip button",
                               DEFAULT_TIMEOUT);

        waitForElementAndClick(By.id("org.wikipedia:id/search_container"),
                               "Can't find 'Search wikipedia' input",
                               DEFAULT_TIMEOUT);

        waitForElementAndSendKeys(By.xpath("//*[contains(@text, 'Search…')]"),
                                  "Java",
                                  "Can't find 'Search' input",
                                  DEFAULT_TIMEOUT);

        waitForElementAndClear(By.id("org.wikipedia:id/search_src_text"),
                               "Can't find 'Search' input",
                               DEFAULT_TIMEOUT);

        // В новой версии приложения нет крестика в поле ввода 'Search...' поэтому я нажимаю кнопку со стрелочкой
        waitForElementAndClick(By.xpath("//*[@resource-id='org.wikipedia:id/search_toolbar']/*[@class='android.widget.ImageButton']"),
                               "Can't find 'Back' button",
                               DEFAULT_TIMEOUT);

        // Кнопка со стрелочкой имеет тот же xpath, что и кнопка с лупой, поэтому ждем, когда пропадет поле ввода по id
        waitForElementNotPresent(By.id("org.wikipedia:id/search_src_text"),
                                 "Search src input still present on the page",
                                 15);
    }

    @Test
    public void testCompareArticleTitle() {
        waitForElementAndClick(By.id("org.wikipedia:id/fragment_onboarding_skip_button"),
                               "Can't find skip button",
                               DEFAULT_TIMEOUT);

        waitForElementAndClick(By.id("org.wikipedia:id/search_container"),
                               "Can't find 'Search wikipedia' input",
                               DEFAULT_TIMEOUT);

        waitForElementAndSendKeys(By.xpath("//*[contains(@text, 'Search…')]"),
                                  "Java",
                                  "Can't find 'Search' input",
                                  DEFAULT_TIMEOUT);

        waitForElementAndClick(By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_container']//*[@text='Object-oriented programming language']"),
                               "Can't find 'Object-oriented programming language' topic by 'Java'",
                               DEFAULT_TIMEOUT);

        WebElement titleElement = waitForElementPresent(By.id("org.wikipedia:id/view_page_title_text"),
                                                        "Can't find article title",
                                                        15);

        String title = titleElement.getAttribute("text");

        Assert.assertEquals("We see unexpected title",
                            "Java (programming language)",
                            title);
    }

    private WebElement waitForElementPresent(By locator, String errorMessage, long timeOutInSeconds) {
        WebDriverWait wait = new WebDriverWait(driver, timeOutInSeconds);
        wait.withMessage(errorMessage + "\n");
        return wait.until(ExpectedConditions.presenceOfElementLocated(locator));
    }

    private WebElement waitForElementPresent(By locator, String errorMessage) {
        return waitForElementPresent(locator, errorMessage, DEFAULT_TIMEOUT);
    }

    private WebElement waitForElementAndClick(By locator, String errorMessage, long timeOutInSeconds) {
        WebElement element = waitForElementPresent(locator, errorMessage, timeOutInSeconds);
        element.click();
        return element;
    }

    private WebElement waitForElementAndSendKeys(By locator, String value, String errorMessage, long timeOutInSedonds) {
        WebElement element = waitForElementPresent(locator, errorMessage, timeOutInSedonds);
        element.sendKeys(value);
        return element;
    }

    private boolean waitForElementNotPresent(By locator, String errorMessage, long timeOutInSeconds) {
        WebDriverWait wait = new WebDriverWait(driver, timeOutInSeconds);
        wait.withMessage(errorMessage + "\n");
        return wait.until(ExpectedConditions.invisibilityOfElementLocated(locator));
    }

    private WebElement waitForElementAndClear(By locator, String errorMessage, long timeOutInSeconds) {
        WebElement element = waitForElementPresent(locator, errorMessage);
        element.clear();
        return element;
    }
}
