package de.oetting.bumpingbunnies.usecases;

import android.app.Activity;
import android.content.Intent;
import de.oetting.bumpingbunnies.usecases.game.android.GameActivity;
import de.oetting.bumpingbunnies.usecases.game.businesslogic.GameStartParameter;
import de.oetting.bumpingbunnies.usecases.game.configuration.GeneralSettings;
import de.oetting.bumpingbunnies.usecases.game.configuration.LocalPlayersettings;
import de.oetting.bumpingbunnies.usecases.game.configuration.LocalSettings;
import de.oetting.bumpingbunnies.usecases.networkRoom.RoomActivity;
import de.oetting.bumpingbunnies.usecases.resultScreen.ResultActivity;
import de.oetting.bumpingbunnies.usecases.resultScreen.model.ResultWrapper;
import de.oetting.bumpingbunnies.usecases.settings.SettingsActivity;
import de.oetting.bumpingbunnies.usecases.start.StartActivity;

public class ActivityLauncher {

	public static final String GAMEPARAMETER = "PARAMETER";
	public static final String CONFIGURATION = "CONFIGURATION";
	public static final String LOCAL_SETTINGS = "LOCAL_SETTINGS";
	public static final String GENERAL_SETTINGS = "GENERAL_SETTINGS";
	public static final String LOCAL_PLAYER_SETTINGS = "LOCAL_PLAYER_SETTINGS";
	public static final String RESULT = "RESULT";
	public static final int SINGPLE_PLAYER_ID = 0;

	public static void launchGame(Activity origin, GameStartParameter parameter) {
		Intent intent = new Intent(origin, GameActivity.class);
		intent.putExtra(GAMEPARAMETER, parameter);
		origin.startActivity(intent);
	}

	public static void startSettings(Activity origin) {
		Intent intent = new Intent(origin, SettingsActivity.class);
		origin.startActivity(intent);
	}

	public static void startRoom(Activity origin, LocalSettings localSettings,
			GeneralSettings generalSettings,
			LocalPlayersettings localPlayerSettings) {
		Intent intent = new Intent(origin, RoomActivity.class);
		intent.putExtra(LOCAL_SETTINGS, localSettings);
		intent.putExtra(GENERAL_SETTINGS, generalSettings);
		intent.putExtra(LOCAL_PLAYER_SETTINGS, localPlayerSettings);
		origin.startActivity(intent);
	}

	public static void startResult(Activity origin, ResultWrapper gameResult) {
		Intent intent = new Intent(origin, ResultActivity.class);
		intent.putExtra(RESULT, gameResult);
		origin.startActivity(intent);
		origin.finish();
	}

	public static void toStart(Activity origin) {
		Intent intent = new Intent(origin, StartActivity.class);
		origin.startActivity(intent);
		origin.finish();
	}
}
