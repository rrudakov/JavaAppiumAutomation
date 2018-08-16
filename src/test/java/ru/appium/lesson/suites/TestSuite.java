package ru.appium.lesson.suites;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import ru.appium.lesson.tests.ArticleTests;
import ru.appium.lesson.tests.ChangeAppConditionTests;
import ru.appium.lesson.tests.MyListsTests;
import ru.appium.lesson.tests.SearchTests;
import ru.appium.lesson.tests.ios.GetStartedTest;

@RunWith(Suite.class)
@Suite.SuiteClasses({
  ArticleTests.class,
  ChangeAppConditionTests.class,
  GetStartedTest.class,
  MyListsTests.class,
  SearchTests.class
})
public class TestSuite {}
