package de.jumpnbump.usecases;

import android.app.Activity;
import android.content.Intent;
import de.jumpnbump.usecases.game.android.GameActivity;
import de.jumpnbump.usecases.game.businesslogic.GameStartParameter;
import de.jumpnbump.usecases.game.configuration.Configuration;
import de.jumpnbump.usecases.networkRoom.RoomActivity;

public class ActivityLauncher {

	public static final String GAMEPARAMETER = "PARAMETER";
	public static final String CONFIGURATION = "CONFIGURATION";
	public static final int SINGPLE_PLAYER_ID = 0;

	public static void launchGame(Activity origin, GameStartParameter parameter) {
		Intent intent = new Intent(origin, GameActivity.class);
		intent.putExtra(GAMEPARAMETER, parameter);
		origin.startActivity(intent);
	}

	public static void startRoom(Activity origin, Configuration configuration) {
		Intent intent = new Intent(origin, RoomActivity.class);
		intent.putExtra(CONFIGURATION, configuration);
		origin.startActivity(intent);
	}
}
