package de.oetting.bumpingbunnies.tests;

import org.junit.experimental.categories.Categories;
import org.junit.experimental.categories.Categories.IncludeCategory;
import org.junit.runner.RunWith;
import org.junit.runners.Suite.SuiteClasses;

@IncludeCategory(UnitTest.class)
@RunWith(Categories.class)
@SuiteClasses({ AllTests.class })
public class UnitTestSuite {

}
