package de.oetting.bumpingbunnies.pc.graphics;

import java.io.InputStream;

public class BackgroundReader {

	public InputStream readBackground() {
		return getClass().getResourceAsStream("/drawable/hintergrund2.png");
	}
}
