package ru.appium.lesson;

import java.util.List;

import org.hamcrest.CoreMatchers;
import org.junit.Assert;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.ScreenOrientation;
import org.openqa.selenium.WebElement;

import ru.appium.lesson.lib.CoreTestCase;
import ru.appium.lesson.lib.ui.MainPageObject;

/** First test using appium. */
public class FirstTest extends CoreTestCase {
  private MainPageObject mainPage;

  @Override
  protected void setUp() throws Exception {
    super.setUp();
    mainPage = new MainPageObject(this.driver);
  }

  // Locators
  private static final By SKIP_BUTTON = By.id("org.wikipedia:id/fragment_onboarding_skip_button");
  private static final By SEARCH_INIT = By.xpath("//*[contains(@text, 'Search Wikipedia')]");
  private static final By SEARCH_INPUT_BY_ID = By.id("org.wikipedia:id/search_src_text");
  private static final By SEARCH_INPUT = By.xpath("//*[contains(@text, 'Search…')]");
  private static final By BACK_BUTTON =
      By.xpath("//*[@resource-id='org.wikipedia:id/search_toolbar']/android.widget.ImageButton");
  private static final By RESULT_JAVA =
      By.xpath(
          "//*[@resource-id='org.wikipedia:id/page_list_item_container']//*[@text='Object-oriented programming language']");
  private static final By RESULT_APPIUM =
      By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_title' and @text='Appium']");
  private static final By ARTICLE_TITLE = By.id("org.wikipedia:id/view_page_title_text");
  private static final By ARTICLE_APPIUM_FOOTER = By.xpath("//*[@text='View page in browser']");
  private static final By SEARCH_RESULTS =
      By.xpath(
          "//*[@resource-id='org.wikipedia:id/search_results_list']/*[@resource-id='org.wikipedia:id/page_list_item_container']");
  private static final By MORE_OPTIONS =
      By.xpath("//android.widget.ImageView[@content-desc='More options']");
  private static final By GOT_IT = By.id("org.wikipedia:id/onboarding_button");
  private static final By CREATE_LIST =
      By.xpath("//*[@resource-id='org.wikipedia:id/create_button']");
  private static final By LIST_NAME_INPUT = By.id("org.wikipedia:id/text_input");
  private static final By OK_BUTTON = By.id("android:id/button1");
  private static final By CLOSE_BUTTON =
      By.xpath("//android.widget.ImageButton[@content-desc='Navigate up']");
  private static final By SAVED_ITEMS =
      By.xpath(
          "//*[@resource-id='org.wikipedia:id/fragment_main_nav_tab_layout']//android.widget.FrameLayout[2]");

  @Test
  public void testSearch() {
    this.openAppAndSearch("Java");
    this.mainPage.waitForElementPresent(
        RESULT_JAVA, "Can't find 'Object-oriented programming language' topic by 'Java'", 15);
  }

  @Test
  public void testCancelSearch() {
    this.openAppAndSearch("Java");
    this.mainPage.waitForElementAndClear(SEARCH_INPUT_BY_ID, "Can't find 'Search' input");

    // В новой версии приложения нет крестика в поле ввода 'Search...' поэтому я нажимаю кнопку со
    // стрелочкой
    this.mainPage.waitForElementAndClick(BACK_BUTTON, "Can't find 'Back' button");

    // Кнопка со стрелочкой имеет тот же xpath, что и кнопка с лупой, поэтому ждем, когда пропадет
    // поле ввода по id
    this.mainPage.waitForElementNotPresent(
        SEARCH_INPUT_BY_ID, "Search src input still present on the page", 15);
  }

  @Test
  public void testCompareArticleTitle() {
    this.openAppAndSearch("Java");
    this.mainPage.waitForElementAndClick(
        RESULT_JAVA, "Can't find 'Object-oriented programming language' topic by 'Java'");

    WebElement titleElement =
        this.mainPage.waitForElementPresent(ARTICLE_TITLE, "Can't find article title", 15);

    String title = titleElement.getAttribute("text");

    Assert.assertEquals("We see unexpected title", "Java (programming language)", title);
  }

  @Test
  public void testSwipeArticle() {
    this.openAppAndSearch("Appium");
    this.mainPage.waitForElementAndClick(RESULT_APPIUM, "Can't find 'Appium' article");
    this.mainPage.waitForElementPresent(ARTICLE_TITLE, "Can't find article title", 15);
    this.mainPage.swipeUpToFindElement(
        ARTICLE_APPIUM_FOOTER, "Can't find the end of the article", 20);
  }

  @Test
  public void testSaveFirstArticleToMyList() {
    String listName = "Learning programming";
    String articleTitle = "Java (programming language)";

    this.openAppAndSearch("Java");
    this.mainPage.waitForElementAndClick(RESULT_JAVA, "Can't find 'Java' article");
    this.mainPage.waitForElementAndClick(MORE_OPTIONS, "Can't find 'More options' button");
    this.mainPage.waitForElementAndClick(
        this.mainPage.byText("Add to reading list"), "Can't find 'Add to reading list' item");
    this.mainPage.waitForElementAndClick(GOT_IT, "Can't find 'Got it!' button");
    this.mainPage.waitForElementAndClick(CREATE_LIST, "Can't find 'Create list' button");
    this.mainPage.waitForElementAndClear(LIST_NAME_INPUT, "Can't find 'List name' input field");
    this.mainPage.waitForElementAndSendKeys(
        LIST_NAME_INPUT,
        listName,
        String.format("Can't fill in 'List name' input with text '%s'", listName));
    this.mainPage.waitForElementAndClick(OK_BUTTON, "Can't find 'OK' button");
    this.mainPage.waitForElementAndClick(CLOSE_BUTTON, "Can't find 'Close' button");
    this.mainPage.waitForElementAndClick(SAVED_ITEMS, "Can't find 'Saved items' button");
    this.mainPage.waitForElementAndClick(
        this.mainPage.byText(listName),
        String.format("Can't find saved list with name '%s'", listName));
    this.mainPage.swipeElementToLeft(
        this.mainPage.byText(articleTitle), "Can't find saved article");
    this.mainPage.waitForElementNotPresent(
        By.xpath(
            String.format(
                "//*[@resource-id='org.wikipedia:id/page_list_item_container'][//*[@text='%s']]",
                articleTitle)),
        "Can't delete saved article");
  }

  @Test
  public void testSaveTwoArticles() {
    String listName = "Test articles";
    String firstArticleTitle = "Java (programming language)";
    String secondArticleTitle = "Appium";
    String firstsearchText = "Java";
    String secondSearchText = "Appium";

    this.openAppAndSearch(firstsearchText);
    this.mainPage.waitForElementAndClick(RESULT_JAVA, "Can't find 'Java' article");
    this.mainPage.waitForElementAndClick(MORE_OPTIONS, "Can't find 'More options' button");
    this.mainPage.waitForElementAndClick(
        this.mainPage.byText("Add to reading list"), "Can't find 'Add to reading list' item");
    this.mainPage.waitForElementAndClick(GOT_IT, "Can't find 'Got it!' button");
    this.mainPage.waitForElementAndClick(CREATE_LIST, "Can't find 'Create list' button");
    this.mainPage.waitForElementAndClear(LIST_NAME_INPUT, "Can't find 'List name' input field");
    this.mainPage.waitForElementAndSendKeys(
        LIST_NAME_INPUT,
        listName,
        String.format("Can't fill in 'List name' input with text '%s'", listName));
    this.mainPage.waitForElementAndClick(OK_BUTTON, "Can't find 'OK' button");
    this.mainPage.waitForElementAndClick(CLOSE_BUTTON, "Can't find 'Close' button");
    this.mainPage.waitForElementAndClick(SEARCH_INIT, "Can't find 'Search wikipedia' input");
    this.mainPage.waitForElementAndSendKeys(
        SEARCH_INPUT, secondSearchText, "Can't find 'Search' input");
    this.mainPage.waitForElementAndClick(RESULT_APPIUM, "Can't find 'Appium' article");
    this.mainPage.waitForElementAndClick(MORE_OPTIONS, "Can't find 'More options' button");
    this.mainPage.waitForElementAndClick(
        this.mainPage.byText("Add to reading list"), "Can't find 'Add to reading list' item");
    this.mainPage.waitForElementAndClick(
        this.mainPage.byText(listName),
        String.format("Can't find reading list with name '%s'", listName));
    this.mainPage.waitForElementAndClick(CLOSE_BUTTON, "Can't find 'Close' button");
    this.mainPage.waitForElementAndClick(SAVED_ITEMS, "Can't find 'Saved items' button");
    this.mainPage.waitForElementAndClick(
        this.mainPage.byText(listName),
        String.format("Can't find saved list with name '%s'", listName));
    this.mainPage.swipeElementToLeft(
        this.mainPage.byText(firstArticleTitle),
        String.format("Can't find saved article with name '%s'", firstArticleTitle));
    this.mainPage.waitForElementAndClick(
        this.mainPage.byText(secondArticleTitle),
        String.format("Can't find saved article with title '%s'", secondArticleTitle));
    String title =
        this.mainPage.waitForElementAndGetAttribure(
            ARTICLE_TITLE, "text", "Can't find article title", 15);

    Assert.assertEquals("Wrong article title", secondArticleTitle, title);
  }

  @Test
  public void testAmountOfNonEmptySearch() {
    String searchText = "Linkin park discograghy";
    this.openAppAndSearch(searchText);
    this.mainPage.waitForElementPresent(
        SEARCH_RESULTS, String.format("Can't find anything by request '%s'", searchText));
    int amountOfSearchResults = this.mainPage.getAmountOfElements(SEARCH_RESULTS);

    Assert.assertTrue("Too few search results", amountOfSearchResults > 0);
  }

  @Test
  public void testAmountOfEmptySearch() {
    String searchText = "lksjdfkajhsdlkfjhasd";
    this.openAppAndSearch(searchText);
    this.mainPage.waitForElementPresent(
        this.mainPage.byText("No results found"),
        String.format("Can't find empty results label by text '%s'", searchText));
    this.mainPage.assertElementNotPresent(
        SEARCH_RESULTS, String.format("We've found some results by request: %s", searchText));
  }

  @Test
  public void testChangeScreenOrientationOnSearchResult() {
    String searchText = "Java";
    this.openAppAndSearch(searchText);
    this.mainPage.waitForElementAndClick(RESULT_JAVA, "Can't find 'Java' article", 15);

    String titleBeforeRotation =
        this.mainPage.waitForElementAndGetAttribure(
            ARTICLE_TITLE, "text", "Can't find title of article");

    driver.rotate(ScreenOrientation.LANDSCAPE);

    String titleAfterRotation =
        this.mainPage.waitForElementAndGetAttribure(
            ARTICLE_TITLE, "text", "Can't find title of article");

    Assert.assertEquals(
        "Article title has been changed after rotation", titleBeforeRotation, titleAfterRotation);

    driver.rotate(ScreenOrientation.PORTRAIT);

    String titleAfterSecondRotation =
        this.mainPage.waitForElementAndGetAttribure(
            ARTICLE_TITLE, "text", "Can't find title of article");

    Assert.assertEquals(
        "Article title has been changed after second rotation",
        titleBeforeRotation,
        titleAfterSecondRotation);
  }

  @Test
  public void testCheckSearchArticleInBackground() {
    String searchText = "Java";
    this.openAppAndSearch(searchText);
    this.mainPage.waitForElementPresent(RESULT_JAVA, "Can't find 'Java' article", 15);

    driver.runAppInBackground(2);

    this.mainPage.waitForElementPresent(
        RESULT_JAVA, "Can't find 'Java' article after returning from background", 15);
  }

  @Test
  public void testCheckSearchPlaceholder() {
    this.mainPage.waitForElementAndClick(SKIP_BUTTON, "Can't find skip button");
    this.mainPage.waitForElementAndClick(SEARCH_INIT, "Can't find 'Search wikipedia' input");

    WebElement searchInputElement =
        this.mainPage.waitForElementPresent(SEARCH_INPUT_BY_ID, "Can't find 'Search' input");

    String placeholder = searchInputElement.getAttribute("text");

    Assert.assertEquals("Wrong placeholder in Search input", "Search…", placeholder);
  }

  @Test
  public void testSearchFewArticles() {
    this.openAppAndSearch("Java");
    this.mainPage.waitForElementsPresent(SEARCH_RESULTS, "No search results");
    int amountOfSearchResults = this.mainPage.getAmountOfElements(SEARCH_RESULTS);

    Assert.assertTrue("Too few search results", amountOfSearchResults > 1);

    // В новой версии приложения нет крестика в поле ввода 'Search...' поэтому я нажимаю кнопку со
    // стрелочкой
    this.mainPage.waitForElementAndClick(BACK_BUTTON, "Can't find 'Back' button");

    this.mainPage.waitForElementNotPresent(SEARCH_RESULTS, "Search results hasn't disappeared");
  }

  @Test
  public void testSearchFewArticlesAndCheckResults() {
    String searchString = "Test";

    this.openAppAndSearch(searchString);
    List<WebElement> resultElements =
        this.mainPage.waitForElementsPresent(SEARCH_RESULTS, "No search results");

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

  @Test
  public void testAssertElementPresent() {
    String searchText = "Java";
    this.openAppAndSearch(searchText);
    this.mainPage.waitForElementAndClick(RESULT_JAVA, "Can't find 'Java' article", 15);
    // Assert.assertTrue("Article title is not present", isElementCurrentlyVisible(ARTICLE_TITLE));
    this.mainPage.assertElementPresent(ARTICLE_TITLE, "Article title is not present");
  }

  private void openAppAndSearch(String searchText) {
    this.mainPage.waitForElementAndClick(SKIP_BUTTON, "Can't find skip button");
    this.mainPage.waitForElementAndClick(SEARCH_INIT, "Can't find 'Search wikipedia' input");
    this.mainPage.waitForElementAndSendKeys(SEARCH_INPUT, searchText, "Can't find 'Search' input");
  }
}
