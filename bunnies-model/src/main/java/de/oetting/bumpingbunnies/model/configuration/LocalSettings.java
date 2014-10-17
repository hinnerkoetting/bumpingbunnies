package de.oetting.bumpingbunnies.model.configuration;

import de.oetting.bumpingbunnies.model.configuration.input.InputConfiguration;


public class LocalSettings {

	private final InputConfiguration inputConfiguration;
	private final int zoom;
	private final boolean background;
	private final boolean altPixelMode;

	public LocalSettings(InputConfiguration inputConfiguration, int zoom, boolean background, boolean altPixelMode) {
		super();
		this.inputConfiguration = inputConfiguration;
		this.zoom = zoom;
		this.background = background;
		this.altPixelMode = altPixelMode;
	}

	public InputConfiguration getInputConfiguration() {
		return this.inputConfiguration;
	}

	public int getZoom() {
		return this.zoom;
	}

	public boolean isBackground() {
		return this.background;
	}

	public boolean isAltPixelMode() {
		return this.altPixelMode;
	}

}
