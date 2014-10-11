package de.oetting.bumpingbunnies.core.network.parser;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import de.oetting.bumpingbunnies.model.game.objects.HorizontalMovementState;

public class GsonFactory {

	public Gson create() {
		GsonBuilder builder = new GsonBuilder();
		builder.registerTypeAdapter(HorizontalMovementState.class, new HorizontalMovementStateTypeAdapter().nullSafe());
		return builder.create();
	}
}
