package de.oetting.bumpingbunnies.model.configuration;

public class SettingsEntity {

	private final InputConfiguration inputConfiguration;
	private final int zoom;
	private final int numberPlayer;
	private final int speed;
	private final String playerName;
	private final boolean background;
	private final boolean altPixelformat;

	public SettingsEntity(InputConfiguration inputConfiguration, int zoom, int numberPlayer, int speed, String playerName,
			boolean background, boolean altPixelFormat) {
		super();
		this.inputConfiguration = inputConfiguration;
		this.zoom = zoom;
		this.numberPlayer = numberPlayer;
		this.speed = speed;
		this.playerName = playerName;
		this.background = background;
		this.altPixelformat = altPixelFormat;
	}

	public InputConfiguration getInputConfiguration() {
		return this.inputConfiguration;
	}

	public int getZoom() {
		return this.zoom;
	}

	public int getNumberPlayer() {
		return this.numberPlayer;
	}

	public int getSpeed() {
		return this.speed;
	}

	public String getPlayerName() {
		return this.playerName;
	}

	public boolean isBackground() {
		return this.background;
	}

	public boolean isAltPixelformat() {
		return this.altPixelformat;
	}

}
