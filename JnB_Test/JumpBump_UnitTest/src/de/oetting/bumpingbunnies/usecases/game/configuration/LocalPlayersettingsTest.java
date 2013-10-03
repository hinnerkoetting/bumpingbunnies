package de.oetting.bumpingbunnies.usecases.game.configuration;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

import android.os.Parcel;

@RunWith(RobolectricTestRunner.class)
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
		return new LocalPlayersettings(parcel);
	}

}
