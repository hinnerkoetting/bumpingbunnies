package de.oetting.bumpingbunnies.usecases.game.configuration;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

import android.os.Parcel;

@RunWith(RobolectricTestRunner.class)
public class GeneralSettingsTest {

	@Test
	public void testParcelling() {
		GeneralSettings settings = new GeneralSettings(WorldConfiguration.CASTLE, 1);
		checkValues(settings);
		GeneralSettings after = serializeAndDeserialize(settings);
		checkValues(after);
	}

	private GeneralSettings serializeAndDeserialize(GeneralSettings settings) {
		Parcel parcel = Parcel.obtain();
		settings.writeToParcel(parcel, 0);
		return new GeneralSettings(parcel);
	}

	private void checkValues(GeneralSettings settings) {
		assertThat(settings.getWorldConfiguration(), is(equalTo(WorldConfiguration.CASTLE)));
		assertThat(settings.getSpeedSetting(), is(equalTo(1)));
	}
}
