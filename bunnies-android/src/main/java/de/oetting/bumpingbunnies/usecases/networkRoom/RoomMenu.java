package de.oetting.bumpingbunnies.usecases.networkRoom;

import java.util.Collection;
import java.util.List;

import de.oetting.bumpingbunnies.model.configuration.NetworkType;
import android.view.Menu;

public class RoomMenu {

	public static final int SETTINGS_ID = 1;
	public static final int WLAN_ID = 2;
	public static final int BLUETOOTH_ID = 3;

	public void createMenu(Menu menu, Collection<NetworkType> activeConnections) {
		menu.add(1, SETTINGS_ID, 1, de.oetting.bumpingbunnies.R.string.settings);
		if (activeConnections.contains(NetworkType.BLUETOOTH))
			menu.add(2, WLAN_ID, 2, de.oetting.bumpingbunnies.R.string.enable_wlan);
		else
			menu.add(2, BLUETOOTH_ID, 2, de.oetting.bumpingbunnies.R.string.enable_bluetooth);
	}

}