package de.oetting.bumpingbunnies.usecases.game.configuration;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;

import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import android.os.Parcel;
import de.oetting.bumpingbunnies.android.parcel.GeneralSettingsParceller;
import de.oetting.bumpingbunnies.model.configuration.NetworkType;
import de.oetting.bumpingbunnies.model.configuration.ServerSettings;
import de.oetting.bumpingbunnies.model.configuration.WorldConfiguration;
import de.oetting.bumpingbunnies.tests.IntegrationTests;

@Category(IntegrationTests.class)
@RunWith(RobolectricTestRunner.class)
@Config(sdk = 18)
public class GeneralSettingsTest {

	@Test
	public void testParcelling() {
		ServerSettings settings = new ServerSettings(WorldConfiguration.CLASSIC, 1, Collections.singleton(NetworkType.WLAN), 2, true);
		checkValues(settings);
		ServerSettings after = serializeAndDeserialize(settings);
		checkValues(after);
	}

	private ServerSettings serializeAndDeserialize(ServerSettings settings) {
		Parcel parcel = Parcel.obtain();
		new GeneralSettingsParceller().writeToParcel(settings, parcel);
		parcel.setDataPosition(0);
		return new GeneralSettingsParceller().createFromParcel(parcel);
	}

	private void checkValues(ServerSettings settings) {
		assertThat(settings.getWorldConfiguration(), is(equalTo(WorldConfiguration.CLASSIC)));
		assertThat(settings.getSpeedSetting(), is(equalTo(1)));
		assertThat(settings.getNetworkTypes(), contains(equalTo(NetworkType.WLAN)));
		assertThat(settings.getVictoryLimit(), is(equalTo(2)));
		assertTrue(settings.isGameIsCurrentlyPaused());
	}
}
