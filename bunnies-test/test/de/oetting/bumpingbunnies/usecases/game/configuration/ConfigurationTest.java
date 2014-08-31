package de.oetting.bumpingbunnies.usecases.game.configuration;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import android.os.Parcel;
import de.oetting.bumpingbunnies.tests.IntegrationTests;

@Category(IntegrationTests.class)
@RunWith(RobolectricTestRunner.class)
@Config(emulateSdk = 18)
public class ConfigurationTest {

	@Test
	public void testParcelling() {
		Configuration configuration = createConfiguration();
		checkHostSetting(configuration);
		Configuration after = serializeAndDeserialize(configuration);
		checkHostSetting(after);
	}

	private Configuration serializeAndDeserialize(Configuration configuration) {
		Parcel parcel = Parcel.obtain();
		configuration.writeToParcel(parcel, 0);
        parcel.setDataPosition(0);
		return new Configuration(parcel);
	}

	private Configuration createConfiguration() {
		return TestConfigurationFactory.createDummyHost();
	}

	private void checkHostSetting(Configuration configuration) {
		// other values should be checked in their own test
		assertThat(configuration.isHost(), is(true));
	}
}