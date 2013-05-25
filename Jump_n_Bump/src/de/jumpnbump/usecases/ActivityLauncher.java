package de.jumpnbump.usecases;

import android.app.Activity;
import android.content.Intent;
import de.jumpnbump.usecases.game.GameActivity;

public class ActivityLauncher {

	public static void launchGame(Activity origin) {
		Intent intent = new Intent(origin, GameActivity.class);
		origin.startActivity(intent);
	}
}
