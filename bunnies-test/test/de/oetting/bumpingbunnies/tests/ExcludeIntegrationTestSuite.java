package de.oetting.bumpingbunnies.tests;

import org.junit.experimental.categories.Categories;
import org.junit.experimental.categories.Categories.ExcludeCategory;
import org.junit.runner.RunWith;
import org.junit.runners.Suite.SuiteClasses;

import de.oetting.bumpingbunnies.AllTests;

@ExcludeCategory(IntegrationTests.class)
@RunWith(Categories.class)
@SuiteClasses({ AllTests.class })
public class ExcludeIntegrationTestSuite {

}
