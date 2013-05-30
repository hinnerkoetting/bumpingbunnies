package de.jumpnbump;
import com.xtremelabs.robolectric.RobolectricTestRunner;

public class DefaultUnitTest extends RobolectricTestRunner {

	public DefaultUnitTest(Class<?> testClass)
			throws org.junit.runners.model.InitializationError {
		// defaults to "AndroidManifest.xml", "res" in the current directory
		super(testClass, "../Jump_n_Bump");
	}
}