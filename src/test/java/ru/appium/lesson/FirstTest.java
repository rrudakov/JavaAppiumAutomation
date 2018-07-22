package ru.appium.lesson;

import java.net.URL;
import java.util.List;

import org.hamcrest.CoreMatchers;
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

    // Locators
    private static final By SKIP_BUTTON = By.id("org.wikipedia:id/fragment_onboarding_skip_button");
    private static final By SEARCH_INIT = By.xpath("//*[contains(@text, 'Search Wikipedia')]");
    private static final By SEARCH_INPUT_BY_ID = By.id("org.wikipedia:id/search_src_text");
    private static final By SEARCH_INPUT = By.xpath("//*[contains(@text, 'Search…')]");
    private static final By BACK_BUTTON = By.xpath("//*[@resource-id='org.wikipedia:id/search_toolbar']/*[@class='android.widget.ImageButton']");
    private static final By RESULT_JAVA = By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_container']//*[@text='Object-oriented programming language']");
    private static final By ARTICLE_TITLE = By.id("org.wikipedia:id/view_page_title_text");
    private static final By SEARCH_RESULTS = By.xpath("//*[@resource-id='org.wikipedia:id/search_results_list']/*[@resource-id='org.wikipedia:id/page_list_item_container']");

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

    @Test
    public void shouldAnswerWithTrue() {
        waitForElementAndClick(SKIP_BUTTON, "Can't find skip button", DEFAULT_TIMEOUT);
        waitForElementAndClick(SEARCH_INIT, "Can't find 'Search wikipedia' input", DEFAULT_TIMEOUT);
        waitForElementAndSendKeys(SEARCH_INPUT, "Java", "Can't find 'Search' input", DEFAULT_TIMEOUT);
        waitForElementPresent(RESULT_JAVA, "Can't find 'Object-oriented programming language' topic by 'Java'", 15);
    }

    @Test
    public void testCancelSearch() {
        waitForElementAndClick(SKIP_BUTTON, "Can't find skip button", DEFAULT_TIMEOUT);
        waitForElementAndClick(SEARCH_INIT, "Can't find 'Search wikipedia' input", DEFAULT_TIMEOUT);
        waitForElementAndSendKeys(SEARCH_INPUT, "Java", "Can't find 'Search' input", DEFAULT_TIMEOUT);
        waitForElementAndClear(SEARCH_INPUT_BY_ID, "Can't find 'Search' input", DEFAULT_TIMEOUT);

        // В новой версии приложения нет крестика в поле ввода 'Search...' поэтому я нажимаю кнопку со стрелочкой
        waitForElementAndClick(BACK_BUTTON, "Can't find 'Back' button", DEFAULT_TIMEOUT);

        // Кнопка со стрелочкой имеет тот же xpath, что и кнопка с лупой, поэтому ждем, когда пропадет поле ввода по id
        waitForElementNotPresent(SEARCH_INPUT_BY_ID, "Search src input still present on the page", 15);
    }

    @Test
    public void testCompareArticleTitle() {
        waitForElementAndClick(SKIP_BUTTON, "Can't find skip button", DEFAULT_TIMEOUT);
        waitForElementAndClick(SEARCH_INIT, "Can't find 'Search wikipedia' input", DEFAULT_TIMEOUT);
        waitForElementAndSendKeys(SEARCH_INPUT, "Java", "Can't find 'Search' input", DEFAULT_TIMEOUT);
        waitForElementAndClick(RESULT_JAVA, "Can't find 'Object-oriented programming language' topic by 'Java'", DEFAULT_TIMEOUT);

        WebElement titleElement = waitForElementPresent(ARTICLE_TITLE, "Can't find article title", 15);

        String title = titleElement.getAttribute("text");

        Assert.assertEquals("We see unexpected title", "Java (programming language)", title);
    }

    @Test
    public void testCheckSearchPlaceholder() {
        waitForElementAndClick(SKIP_BUTTON, "Can't find skip button", DEFAULT_TIMEOUT);
        waitForElementAndClick(SEARCH_INIT, "Can't find 'Search wikipedia' input", DEFAULT_TIMEOUT);

        WebElement searchInputElement = waitForElementPresent(SEARCH_INPUT_BY_ID, "Can't find 'Search' input", DEFAULT_TIMEOUT);

        String placeholder = searchInputElement.getAttribute("text");

        Assert.assertEquals("Wrong placeholder in Search input", "Search…", placeholder);
    }

    @Test
    public void testSearchFewArticles() {
        waitForElementAndClick(SKIP_BUTTON, "Can't find skip button", DEFAULT_TIMEOUT);
        waitForElementAndClick(SEARCH_INIT, "Can't find 'Search wikipedia' input", DEFAULT_TIMEOUT);
        waitForElementAndSendKeys(SEARCH_INPUT, "Java", "Can't find 'Search' input", DEFAULT_TIMEOUT);
        List<WebElement> resultElements = waitForElementsPresent(SEARCH_RESULTS, "No search results", DEFAULT_TIMEOUT);

        Assert.assertTrue("Too few search results", resultElements.size() > 1);

        // В новой версии приложения нет крестика в поле ввода 'Search...' поэтому я нажимаю кнопку со стрелочкой
        waitForElementAndClick(BACK_BUTTON, "Can't find 'Back' button", DEFAULT_TIMEOUT);

        waitForElementNotPresent(SEARCH_RESULTS, "Search results hasn't disappeared", DEFAULT_TIMEOUT);

    }

    @Test
    public void testSearchFewArticlesAndCheckResults() {
        String searchString = "Test";

        waitForElementAndClick(SKIP_BUTTON, "Can't find skip button", DEFAULT_TIMEOUT);
        waitForElementAndClick(SEARCH_INIT, "Can't find 'Search wikipedia' input", DEFAULT_TIMEOUT);
        waitForElementAndSendKeys(SEARCH_INPUT, searchString, "Can't find 'Search' input", DEFAULT_TIMEOUT);
        List<WebElement> resultElements = waitForElementsPresent(SEARCH_RESULTS, "No search results", DEFAULT_TIMEOUT);

        Assert.assertTrue("Too few search results", resultElements.size() > 1);

        for (WebElement result : resultElements) {
            WebElement titleElement = result.findElement(By.id("org.wikipedia:id/page_list_item_title"));
            String title = titleElement.getAttribute("text");

            Assert.assertThat(String.format("Search result doesn't contains string '%s'", searchString),
                              title,
                              CoreMatchers.containsString(searchString));
        }

    }

    private WebElement waitForElementPresent(By locator, String errorMessage, long timeOutInSeconds) {
        WebDriverWait wait = new WebDriverWait(driver, timeOutInSeconds);
        wait.withMessage(errorMessage + "\n");
        return wait.until(ExpectedConditions.presenceOfElementLocated(locator));
    }

    private WebElement waitForElementPresent(By locator, String errorMessage) {
        return waitForElementPresent(locator, errorMessage, DEFAULT_TIMEOUT);
    }

    private List<WebElement> waitForElementsPresent(By locator, String errorMessage, long timeOutInSeconds) {
        WebDriverWait wait = new WebDriverWait(driver, timeOutInSeconds);
        wait.withMessage(errorMessage + "\n");
        return wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(locator));
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
