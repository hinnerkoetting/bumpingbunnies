package de.oetting.bumpingbunnies.usecases.game.configuration;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.equalTo;
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
public class GeneralSettingsTest {

	@Test
	public void testParcelling() {
		GeneralSettings settings = new GeneralSettings(WorldConfiguration.CASTLE, 1, NetworkType.WLAN);
		checkValues(settings);
		GeneralSettings after = serializeAndDeserialize(settings);
		checkValues(after);
	}

	private GeneralSettings serializeAndDeserialize(GeneralSettings settings) {
		Parcel parcel = Parcel.obtain();
		settings.writeToParcel(parcel, 0);
        parcel.setDataPosition(0);
		return new GeneralSettings(parcel);
	}

	private void checkValues(GeneralSettings settings) {
		assertThat(settings.getWorldConfiguration(), is(equalTo(WorldConfiguration.CASTLE)));
		assertThat(settings.getSpeedSetting(), is(equalTo(1)));
		assertThat(settings.getNetworkType(), is(equalTo(NetworkType.WLAN)));
	}
}
