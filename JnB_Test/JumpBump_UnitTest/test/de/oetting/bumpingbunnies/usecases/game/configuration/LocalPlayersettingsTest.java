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
import de.oetting.bumpingbunnies.tests.IntegrationTests;

@Category(IntegrationTests.class)
@RunWith(RobolectricTestRunner.class)
@Config(emulateSdk = 18)
public class LocalPlayersettingsTest {

	@Test
	public void testParcelling() {
		LocalPlayersettings settings = new LocalPlayersettings("name");
		checkValues(settings);
		LocalPlayersettings after = serializeAndDeserialize(settings);
		checkValues(after);
	}

	private void checkValues(LocalPlayersettings settings) {
		assertThat(settings.getPlayerName(), is(equalTo("name")));
	}

	private LocalPlayersettings serializeAndDeserialize(LocalPlayersettings settings) {
		Parcel parcel = Parcel.obtain();
		settings.writeToParcel(parcel, 0);
        parcel.setDataPosition(0);
		return new LocalPlayersettings(parcel);
	}

}
