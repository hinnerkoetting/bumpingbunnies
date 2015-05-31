package de.oetting.bumpingbunnies.usecases.game.configuration;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import android.os.Parcel;
import de.oetting.bumpingbunnies.android.input.hardwareKeyboard.HardwareKeyboardInputConfiguration;
import de.oetting.bumpingbunnies.android.parcel.LocalSettingsParceller;
import de.oetting.bumpingbunnies.model.configuration.LocalSettings;
import de.oetting.bumpingbunnies.tests.IntegrationTests;

@Category(IntegrationTests.class)
@RunWith(RobolectricTestRunner.class)
@Config(emulateSdk = 18)
public class LocalSettingsTest {

	@Test
	public void testParcelling() {
		LocalSettings settings = new LocalSettings(new HardwareKeyboardInputConfiguration(), 1, true, true, true);
		checkValues(settings);
		LocalSettings after = serializeAndDeserialize(settings);
		checkValues(after);
	}

	private LocalSettings serializeAndDeserialize(LocalSettings settings) {
		Parcel parcel = Parcel.obtain();
		new LocalSettingsParceller().writeToParcel(settings, parcel);
		parcel.setDataPosition(0);
		return new LocalSettingsParceller().createFromParcel(parcel);
	}

	private void checkValues(LocalSettings settings) {
		assertThat(settings.getInputConfiguration(), is(instanceOf(HardwareKeyboardInputConfiguration.class)));
		assertThat(settings.getZoom(), is(equalTo(1)));
		assertThat(settings.isPlayMusic(), is(true));
		assertThat(settings.isPlaySounds(), is(true));
		assertThat(settings.isLefthanded(), is(true));
	}
}
