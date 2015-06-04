package de.oetting.bumpingbunnies.usecases;

import android.app.Activity;
import android.content.Intent;
import de.oetting.bumpingbunnies.android.game.GameActivity;
import de.oetting.bumpingbunnies.android.parcel.GamestartParameterParcellableWrapper;
import de.oetting.bumpingbunnies.model.configuration.GameStartParameter;
import de.oetting.bumpingbunnies.usecases.networkRoom.RoomActivity;
import de.oetting.bumpingbunnies.usecases.resultScreen.ResultActivity;
import de.oetting.bumpingbunnies.usecases.resultScreen.model.ResultWrapper;
import de.oetting.bumpingbunnies.usecases.settings.SettingsActivity;
import de.oetting.bumpingbunnies.usecases.start.StartActivity;

public class ActivityLauncher {

	public static final String GAMEPARAMETER = "PARAMETER";
	public static final String CONFIGURATION = "CONFIGURATION";
	public static final String RESULT = "RESULT";

	public static void launchGame(Activity origin, GameStartParameter parameter) {
		Intent intent = new Intent(origin, GameActivity.class);
		intent.putExtra(GAMEPARAMETER, new GamestartParameterParcellableWrapper(parameter));
		origin.startActivity(intent);
	}

	public static void startSettings(Activity origin) {
		Intent intent = new Intent(origin, SettingsActivity.class);
		origin.startActivity(intent);
	}

	public static void startRoom(Activity origin) {
		Intent intent = new Intent(origin, RoomActivity.class);
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
