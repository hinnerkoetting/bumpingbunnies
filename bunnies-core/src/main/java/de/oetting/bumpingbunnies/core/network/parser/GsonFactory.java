package de.oetting.bumpingbunnies.core.network.parser;

import com.google.gson.Gson;

public class GsonFactory {

	public Gson create() {
		return new Gson();
	}
}
