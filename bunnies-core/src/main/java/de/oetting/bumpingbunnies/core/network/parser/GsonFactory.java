package de.oetting.bumpingbunnies.core.network.parser;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import de.oetting.bumpingbunnies.model.game.objects.HorizontalMovementState;
import de.oetting.bumpingbunnies.model.game.objects.PlayerState;

public class GsonFactory {

	public Gson create() {
		GsonBuilder builder = new GsonBuilder();
		builder.registerTypeAdapter(HorizontalMovementState.class, new HorizontalMovementStateTypeAdapter().nullSafe());
		builder.registerTypeAdapter(PlayerState.class, new PlayerStateAdapter());
		return builder.create();
	}
}
