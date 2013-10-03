package de.oetting.bumpingbunnies.usecases.game.configuration;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

import android.os.Parcel;

@RunWith(RobolectricTestRunner.class)
public class LocalSettingsTest {

	@Test
	public void testParcelling() {
		LocalSettings settings = new LocalSettings(InputConfiguration.ANALOG, 1, true, true);
		checkValues(settings);
		LocalSettings after = serializeAndDeserialize(settings);
		checkValues(after);
	}

	private LocalSettings serializeAndDeserialize(LocalSettings settings) {
		Parcel parcel = Parcel.obtain();
		settings.writeToParcel(parcel, 0);
		return new LocalSettings(parcel);
	}

	private void checkValues(LocalSettings settings) {
		assertThat(settings.getInputConfiguration(), is(equalTo(InputConfiguration.ANALOG)));
		assertThat(settings.getZoom(), is(equalTo(1)));
		assertThat(settings.isBackground(), is(true));
		assertThat(settings.isAltPixelMode(), is(true));
	}
}
