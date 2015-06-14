package de.oetting.bumpingbunnies.usecases.networkRoom;

import java.util.Collection;

import android.view.Menu;
import de.oetting.bumpingbunnies.model.configuration.NetworkType;

public class RoomMenu {

	public static final int SETTINGS_ID = 1;

	public void createMenu(Menu menu, Collection<NetworkType> activeConnections) {
		menu.add(1, SETTINGS_ID, 1, de.oetting.bumpingbunnies.R.string.settings);
	}

}