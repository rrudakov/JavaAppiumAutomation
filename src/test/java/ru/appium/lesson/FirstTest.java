package ru.appium.lesson;

import java.net.URL;
import java.util.List;

import org.hamcrest.CoreMatchers;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.ScreenOrientation;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;

/** First test using appium. */
public class FirstTest {

  private AppiumDriver<MobileElement> driver;
  private static final int DEFAULT_TIMEOUT = 10;

  // Locators
  private static final By SKIP_BUTTON = By.id("org.wikipedia:id/fragment_onboarding_skip_button");
  private static final By SEARCH_INIT = By.xpath("//*[contains(@text, 'Search Wikipedia')]");
  private static final By SEARCH_INPUT_BY_ID = By.id("org.wikipedia:id/search_src_text");
  private static final By SEARCH_INPUT = By.xpath("//*[contains(@text, 'Search…')]");
  private static final By BACK_BUTTON =
      By.xpath(
          "//*[@resource-id='org.wikipedia:id/search_toolbar']/android.widget.ImageButton");
  private static final By RESULT_JAVA =
      By.xpath(
          "//*[@resource-id='org.wikipedia:id/page_list_item_container']//*[@text='Object-oriented programming language']");
  private static final By RESULT_APPIUM = By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_title' and @text='Appium']");
  private static final By ARTICLE_TITLE = By.id("org.wikipedia:id/view_page_title_text");
  private static final By ARTICLE_APPIUM_FOOTER = By.xpath("//*[@text='View page in browser']");
  private static final By SEARCH_RESULTS =
      By.xpath(
          "//*[@resource-id='org.wikipedia:id/search_results_list']/*[@resource-id='org.wikipedia:id/page_list_item_container']");
  private static final By MORE_OPTIONS = By.xpath("//android.widget.ImageView[@content-desc='More options']");
  private static final By GOT_IT = By.id("org.wikipedia:id/onboarding_button");
  private static final By CREATE_LIST = By.xpath("//*[@resource-id='org.wikipedia:id/create_button']");
  private static final By LIST_NAME_INPUT = By.id("org.wikipedia:id/text_input");
  private static final By OK_BUTTON = By.id("android:id/button1");
  private static final By CLOSE_BUTTON = By.xpath("//android.widget.ImageButton[@content-desc='Navigate up']");
  private static final By SAVED_ITEMS =
      By.xpath(
          "//*[@resource-id='org.wikipedia:id/fragment_main_nav_tab_layout']//android.widget.FrameLayout[2]");

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
    this.openAppAndSearch("Java");
    waitForElementPresent(
        RESULT_JAVA, "Can't find 'Object-oriented programming language' topic by 'Java'", 15);
  }

  @Test
  public void testCancelSearch() {
    this.openAppAndSearch("Java");
    waitForElementAndClear(SEARCH_INPUT_BY_ID, "Can't find 'Search' input", DEFAULT_TIMEOUT);

    // В новой версии приложения нет крестика в поле ввода 'Search...' поэтому я нажимаю кнопку со
    // стрелочкой
    waitForElementAndClick(BACK_BUTTON, "Can't find 'Back' button", DEFAULT_TIMEOUT);

    // Кнопка со стрелочкой имеет тот же xpath, что и кнопка с лупой, поэтому ждем, когда пропадет
    // поле ввода по id
    waitForElementNotPresent(SEARCH_INPUT_BY_ID, "Search src input still present on the page", 15);
  }

  @Test
  public void testCompareArticleTitle() {
    this.openAppAndSearch("Java");
    waitForElementAndClick(
        RESULT_JAVA,
        "Can't find 'Object-oriented programming language' topic by 'Java'",
        DEFAULT_TIMEOUT);

    WebElement titleElement = waitForElementPresent(ARTICLE_TITLE, "Can't find article title", 15);

    String title = titleElement.getAttribute("text");

    Assert.assertEquals("We see unexpected title", "Java (programming language)", title);
  }

  @Test
  public void testSwipeArticle() {
    this.openAppAndSearch("Appium");
    waitForElementAndClick(RESULT_APPIUM, "Can't find 'Appium' article", DEFAULT_TIMEOUT);
    waitForElementPresent(ARTICLE_TITLE, "Can't find article title", 15);
    swipeUpToFindElement(ARTICLE_APPIUM_FOOTER, "Can't find the end of the article", 20);
  }

  @Test
  public void testSaveFirstArticleToMyList() {
    String listName = "Learning programming";
    String articleTitle = "Java (programming language)";

    this.openAppAndSearch("Java");
    waitForElementAndClick(RESULT_JAVA, "Can't find 'Java' article", DEFAULT_TIMEOUT);
    waitForElementAndClick(MORE_OPTIONS, "Can't find 'More options' button", DEFAULT_TIMEOUT);
    waitForElementAndClick(this.byText("Add to reading list"), "Can't find 'Add to reading list' item", DEFAULT_TIMEOUT);
    waitForElementAndClick(GOT_IT, "Can't find 'Got it!' button", DEFAULT_TIMEOUT);
    waitForElementAndClick(CREATE_LIST, "Can't find 'Create list' button", DEFAULT_TIMEOUT);
    waitForElementAndClear(LIST_NAME_INPUT, "Can't find 'List name' input field", DEFAULT_TIMEOUT);
    waitForElementAndSendKeys(LIST_NAME_INPUT, listName, String.format("Can't fill in 'List name' input with text '%s'", listName), DEFAULT_TIMEOUT);
    waitForElementAndClick(OK_BUTTON, "Can't find 'OK' button", DEFAULT_TIMEOUT);
    waitForElementAndClick(CLOSE_BUTTON, "Can't find 'Close' button", DEFAULT_TIMEOUT);
    waitForElementAndClick(SAVED_ITEMS, "Can't find 'Saved items' button", DEFAULT_TIMEOUT);
    waitForElementAndClick(this.byText(listName), String.format("Can't find saved list with name '%s'", listName), DEFAULT_TIMEOUT);
    swipeElementToLeft(this.byText(articleTitle), "Can't find saved article");
    waitForElementNotPresent(By.xpath(String.format("//*[@resource-id='org.wikipedia:id/page_list_item_container'][//*[@text='%s']]", articleTitle)), "Can't delete saved article", DEFAULT_TIMEOUT);
  }

  @Test
  public void testAmountOfNonEmptySearch() {
    String searchText = "Linkin park discograghy";
    this.openAppAndSearch(searchText);
    waitForElementPresent(SEARCH_RESULTS, String.format("Can't find anything by request '%s'", searchText));
    int amountOfSearchResults = this.getAmountOfElements(SEARCH_RESULTS);

    Assert.assertTrue("Too few search results", amountOfSearchResults > 0);
  }

  @Test
  public void testAmountOfEmptySearch() {
    String searchText = "lksjdfkajhsdlkfjhasd";
    this.openAppAndSearch(searchText);
    waitForElementPresent(this.byText("No results found"), String.format("Can't find empty results label by text '%s'", searchText));
    this.assertElementNotPresent(SEARCH_RESULTS, String.format("We've found some results by request: %s", searchText));
  }

  @Test
  public void testChangeScreenOrientationOnSearchResult() {
    String searchText = "Java";
    this.openAppAndSearch(searchText);
    waitForElementAndClick(RESULT_JAVA, "Can't find 'Java' article", 15);

    String titleBeforeRotation = this.waitForElementAndGetAttribure(ARTICLE_TITLE, "text", "Can't find title of article", DEFAULT_TIMEOUT);

    driver.rotate(ScreenOrientation.LANDSCAPE);

    String titleAfterRotation = this.waitForElementAndGetAttribure(ARTICLE_TITLE, "text", "Can't find title of article", DEFAULT_TIMEOUT);

    Assert.assertEquals("Article title has been changed after rotation", titleBeforeRotation, titleAfterRotation);

    driver.rotate(ScreenOrientation.PORTRAIT);

    String titleAfterSecondRotation = this.waitForElementAndGetAttribure(ARTICLE_TITLE, "text", "Can't find title of article", DEFAULT_TIMEOUT);

    Assert.assertEquals("Article title has been changed after second rotation", titleBeforeRotation, titleAfterSecondRotation);
  }

  @Test
  public void testCheckSearchArticleInBackground() {
    String searchText = "Java";
    this.openAppAndSearch(searchText);
    waitForElementPresent(RESULT_JAVA, "Can't find 'Java' article", 15);

    driver.runAppInBackground(2);

    waitForElementPresent(RESULT_JAVA, "Can't find 'Java' article after returning from background", 15);
  }

  @Test
  public void testCheckSearchPlaceholder() {
    waitForElementAndClick(SKIP_BUTTON, "Can't find skip button", DEFAULT_TIMEOUT);
    waitForElementAndClick(SEARCH_INIT, "Can't find 'Search wikipedia' input", DEFAULT_TIMEOUT);

    WebElement searchInputElement =
        waitForElementPresent(SEARCH_INPUT_BY_ID, "Can't find 'Search' input", DEFAULT_TIMEOUT);

    String placeholder = searchInputElement.getAttribute("text");

    Assert.assertEquals("Wrong placeholder in Search input", "Search…", placeholder);
  }

  @Test
  public void testSearchFewArticles() {
    this.openAppAndSearch("Java");
    waitForElementsPresent(SEARCH_RESULTS, "No search results", DEFAULT_TIMEOUT);
    int amountOfSearchResults = this.getAmountOfElements(SEARCH_RESULTS);

    Assert.assertTrue("Too few search results", amountOfSearchResults > 1);

    // В новой версии приложения нет крестика в поле ввода 'Search...' поэтому я нажимаю кнопку со
    // стрелочкой
    waitForElementAndClick(BACK_BUTTON, "Can't find 'Back' button", DEFAULT_TIMEOUT);

    waitForElementNotPresent(SEARCH_RESULTS, "Search results hasn't disappeared", DEFAULT_TIMEOUT);
  }

  @Test
  public void testSearchFewArticlesAndCheckResults() {
    String searchString = "Test";

    this.openAppAndSearch(searchString);
    List<WebElement> resultElements =
        waitForElementsPresent(SEARCH_RESULTS, "No search results", DEFAULT_TIMEOUT);

    Assert.assertTrue("Too few search results", resultElements.size() > 1);

    for (WebElement result : resultElements) {
      WebElement titleElement = result.findElement(By.id("org.wikipedia:id/page_list_item_title"));
      String title = titleElement.getAttribute("text");

      Assert.assertThat(
          String.format("Search result doesn't contains string '%s'", searchString),
          title,
          CoreMatchers.containsString(searchString));
    }
  }

  private void openAppAndSearch(String searchText) {
    waitForElementAndClick(SKIP_BUTTON, "Can't find skip button", DEFAULT_TIMEOUT);
    waitForElementAndClick(SEARCH_INIT, "Can't find 'Search wikipedia' input", DEFAULT_TIMEOUT);
    waitForElementAndSendKeys(SEARCH_INPUT, searchText, "Can't find 'Search' input", DEFAULT_TIMEOUT);
  }

  private WebElement waitForElementPresent(By locator, String errorMessage, long timeOutInSeconds) {
    WebDriverWait wait = new WebDriverWait(driver, timeOutInSeconds);
    wait.withMessage(errorMessage + "\n");
    return wait.until(ExpectedConditions.presenceOfElementLocated(locator));
  }

  private WebElement waitForElementPresent(By locator, String errorMessage) {
    return waitForElementPresent(locator, errorMessage, DEFAULT_TIMEOUT);
  }

  private List<WebElement> waitForElementsPresent(
      By locator, String errorMessage, long timeOutInSeconds) {
    WebDriverWait wait = new WebDriverWait(driver, timeOutInSeconds);
    wait.withMessage(errorMessage + "\n");
    return wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(locator));
  }

  private WebElement waitForElementAndClick(
      By locator, String errorMessage, long timeOutInSeconds) {
    WebElement element = waitForElementPresent(locator, errorMessage, timeOutInSeconds);
    element.click();
    return element;
  }

  private WebElement waitForElementAndSendKeys(
      By locator, String value, String errorMessage, long timeOutInSedonds) {
    WebElement element = waitForElementPresent(locator, errorMessage, timeOutInSedonds);
    element.sendKeys(value);
    return element;
  }

  private boolean waitForElementNotPresent(By locator, String errorMessage, long timeOutInSeconds) {
    WebDriverWait wait = new WebDriverWait(driver, timeOutInSeconds);
    wait.withMessage(errorMessage + "\n");
    return wait.until(ExpectedConditions.invisibilityOfElementLocated(locator));
  }

  private WebElement waitForElementAndClear(
      By locator, String errorMessage, long timeOutInSeconds) {
    WebElement element = waitForElementPresent(locator, errorMessage);
    element.clear();
    return element;
  }

  protected void swipeUp(int timeOfSwipe) {
    TouchAction action = new TouchAction(driver);
    Dimension size = driver.manage().window().getSize();
    int x = size.getWidth() / 2;
    int y_start = (int) (size.getHeight() * 0.8);
    int y_end = (int) (size.getHeight() * 0.2);

    action
      .press(x, y_start)
      .waitAction(timeOfSwipe)
      .moveTo(x, y_end)
      .release()
      .perform();
  }

  protected void swipeUpQuick() {
    this.swipeUp(200);
  }

  protected void swipeUpToFindElement(By locator, String errorMessage, int maxSwipes) {
    int alreadySwiped = 0;

    while (driver.findElements(locator).size() == 0) {
      if (alreadySwiped > maxSwipes) {
        this.waitForElementsPresent(locator, String.format("Can't find element by swiping up.\n%s", errorMessage), 0);
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
    action
      .press(rightX, middleY)
      .waitAction(600)
      .moveTo(leftX, middleY)
      .release()
      .perform();
  }

  private int getAmountOfElements(By locator) {
    List<MobileElement> elements = driver.findElements(locator);
    return elements.size();
  }

  private void assertElementNotPresent(By locator, String errorMessage) {
    int amountOfElements = this.getAmountOfElements(locator);

    if (amountOfElements > 0) {
      throw new AssertionError(String.format("An element %s supposed to be present. %s",
                                             locator.toString(),
                                             errorMessage));
    }
  }

  private String waitForElementAndGetAttribure(By locator, String attribureName, String errorMessage, long timeOutInSecond) {
    WebElement element = this.waitForElementPresent(locator, errorMessage, timeOutInSecond);
    return element.getAttribute(attribureName);
  }

  private By byText(String elementText) {
    return By.xpath(String.format("//*[@text='%s']", elementText));
  }
}
