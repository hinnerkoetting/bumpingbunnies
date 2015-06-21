package de.oetting.bumpingbunnies.core.game.graphics;

import java.io.InputStream;

import de.oetting.bumpingbunnies.model.game.objects.BunnyImageModel;

public class InputStreamAndModel {

	private final InputStream inputStream;
	private final BunnyImageModel model;

	public InputStreamAndModel(InputStream inputStream, BunnyImageModel model) {
		this.inputStream = inputStream;
		this.model = model;
	}

	public InputStream getInputStream() {
		return inputStream;
	}

	public BunnyImageModel getModel() {
		return model;
	}

}
