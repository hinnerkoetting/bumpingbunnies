package de.jumpnbump;

import org.robolectric.RobolectricTestRunner;

public class DefaultUnitTest extends RobolectricTestRunner {

	public DefaultUnitTest(Class<?> testClass)
			throws org.junit.runners.model.InitializationError {
		// defaults to "AndroidManifest.xml", "res" in the current directory
		super(testClass);
	}
}