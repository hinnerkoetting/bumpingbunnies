package de.oetting.bumpingbunnies.usecases.game.configuration;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

import android.os.Parcel;

@RunWith(RobolectricTestRunner.class)
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
