package ru.appium.lesson.tests.ios;

import org.junit.Test;

import ru.appium.lesson.lib.CoreTestCase;
import ru.appium.lesson.lib.Platform;
import ru.appium.lesson.lib.ui.WelcomePageObject;
import ru.appium.lesson.lib.ui.factories.WelcomePageObjectFactory;

public class GetStartedTest extends CoreTestCase {
  private WelcomePageObject welcome;

  @Override
  public void setUp() throws Exception {
    super.setUp();
    this.welcome = WelcomePageObjectFactory.get(this.driver);
  }

  @Test
  public void testPassThroughWelcome() {
    if (Platform.getInstance().isAndroid()) return;

    this.welcome.waitForLearnMoreLink();
    this.welcome.clickNextButton();

    this.welcome.waitForNewWayToExplore();
    this.welcome.clickNextButton();

    this.welcome.waitForAddOrEditPreferredLangText();
    this.welcome.clickNextButton();

    this.welcome.waitForLearnMoreAboutDataCollectedText();
    this.welcome.clickGetStartedButton();
  }
}
