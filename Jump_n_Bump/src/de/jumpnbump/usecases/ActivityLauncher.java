package de.jumpnbump.usecases;

import android.app.Activity;
import android.content.Intent;
import de.jumpnbump.usecases.game.GameActivity;

public class ActivityLauncher {

	public static final String PLAYER_ID_CONSTANT = "PLAYER_ID";

	public static void launchGame(Activity origin, int playerId) {
		Intent intent = new Intent(origin, GameActivity.class);
		intent.putExtra(PLAYER_ID_CONSTANT, playerId);
		origin.startActivity(intent);
	}
}
