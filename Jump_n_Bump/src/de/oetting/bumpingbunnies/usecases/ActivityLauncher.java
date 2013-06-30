package de.oetting.bumpingbunnies.usecases;

import android.app.Activity;
import android.content.Intent;
import de.oetting.bumpingbunnies.usecases.game.android.GameActivity;
import de.oetting.bumpingbunnies.usecases.game.businesslogic.GameStartParameter;
import de.oetting.bumpingbunnies.usecases.game.configuration.GeneralSettings;
import de.oetting.bumpingbunnies.usecases.game.configuration.LocalSettings;
import de.oetting.bumpingbunnies.usecases.networkRoom.RoomActivity;

public class ActivityLauncher {

	public static final String GAMEPARAMETER = "PARAMETER";
	public static final String CONFIGURATION = "CONFIGURATION";
	public static final String LOCAL_SETTINGS = "LOCAL_SETTINGS";
	public static final String GENERAL_SETTINGS = "GENERAL_SETTINGS";
	public static final int SINGPLE_PLAYER_ID = 0;

	public static void launchGame(Activity origin, GameStartParameter parameter) {
		Intent intent = new Intent(origin, GameActivity.class);
		intent.putExtra(GAMEPARAMETER, parameter);
		origin.startActivity(intent);
	}

	public static void startRoom(Activity origin, LocalSettings localSettings,
			GeneralSettings generalSettings) {
		Intent intent = new Intent(origin, RoomActivity.class);
		intent.putExtra(LOCAL_SETTINGS, localSettings);
		intent.putExtra(GENERAL_SETTINGS, generalSettings);
		origin.startActivity(intent);
	}
}
