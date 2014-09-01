package de.oetting.bumpingbunnies.usecases.game.configuration;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import android.os.Parcel;
import de.oetting.bumpingbunnies.android.parcel.LocalPlayerSettingsParceller;
import de.oetting.bumpingbunnies.tests.IntegrationTests;

@Category(IntegrationTests.class)
@RunWith(RobolectricTestRunner.class)
@Config(emulateSdk = 18)
public class LocalPlayersettingsTest {

	@Test
	public void testParcelling() {
		LocalPlayerSettings settings = new LocalPlayerSettings("name");
		checkValues(settings);
		LocalPlayerSettings after = serializeAndDeserialize(settings);
		checkValues(after);
	}

	private void checkValues(LocalPlayerSettings settings) {
		assertThat(settings.getPlayerName(), is(equalTo("name")));
	}

	private LocalPlayerSettings serializeAndDeserialize(LocalPlayerSettings settings) {
		Parcel parcel = Parcel.obtain();
		new LocalPlayerSettingsParceller().writeToParcel(settings, parcel);
		parcel.setDataPosition(0);
		return new LocalPlayerSettingsParceller().createFromParcel(parcel);
	}

}
