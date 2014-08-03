package de.oetting.bumpingbunnies;

import com.googlecode.junittoolbox.SuiteClasses;
import com.googlecode.junittoolbox.WildcardPatternSuite;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(WildcardPatternSuite.class)
@SuiteClasses("**/*Test.class")
public class AllTests {

}
