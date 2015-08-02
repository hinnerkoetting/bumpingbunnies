package de.oetting.bumpingbunnies.usecases.networkRoom;

import android.view.Menu;

public class RoomMenu {

	public static final int SETTINGS_ID = 1;

	public void createMenu(Menu menu) {
		menu.add(1, SETTINGS_ID, 1, de.oetting.bumpingbunnies.R.string.settings);
	}

}