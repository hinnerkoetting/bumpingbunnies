package de.oetting.bumpingbunnies.model.configuration;

import de.oetting.bumpingbunnies.model.configuration.input.InputConfiguration;

public class SettingsEntity {

	private final InputConfiguration inputConfiguration;
	private final int zoom;
	private final int speed;
	private final String playerName;
	private final boolean background;
	private final boolean altPixelformat;
	private final boolean playMusic;
	private final boolean playSound;
	private final boolean lefthanded;

	public SettingsEntity(InputConfiguration inputConfiguration, int zoom,
			int speed, String playerName, boolean background,
			boolean altPixelFormat, boolean playMusic, boolean playSound,
			boolean leftHanded) {
		super();
		this.inputConfiguration = inputConfiguration;
		this.zoom = zoom;
		this.speed = speed;
		this.playerName = playerName;
		this.background = background;
		this.altPixelformat = altPixelFormat;
		this.playMusic = playMusic;
		this.playSound = playSound;
		lefthanded = leftHanded;
	}

	public InputConfiguration getInputConfiguration() {
		return this.inputConfiguration;
	}

	public int getZoom() {
		return this.zoom;
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

	public boolean isPlayMusic() {
		return playMusic;
	}

	public boolean isPlaySound() {
		return playSound;
	}

	public boolean isLefthanded() {
		return lefthanded;
	}

}
