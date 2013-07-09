package de.oetting.bumpingbunnies.usecases.game.configuration;

public class SettingsEntity {

	private final InputConfiguration inputConfiguration;
	private final int zoom;
	private final int numberPlayer;
	private final int speed;

	public SettingsEntity(InputConfiguration inputConfiguration, int zoom,
			int numberPlayer, int speed) {
		super();
		this.inputConfiguration = inputConfiguration;
		this.zoom = zoom;
		this.numberPlayer = numberPlayer;
		this.speed = speed;
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

}
