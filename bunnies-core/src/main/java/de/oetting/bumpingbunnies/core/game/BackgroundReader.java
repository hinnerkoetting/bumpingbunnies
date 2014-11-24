package de.oetting.bumpingbunnies.core.game;

import java.io.InputStream;

public class BackgroundReader {

	public InputStream readBackground() {
		return getClass().getResourceAsStream("/hintergrund2.png");
	}
}
