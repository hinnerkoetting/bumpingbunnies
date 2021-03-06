package de.oetting.bumpingbunnies.model.configuration;

import de.oetting.bumpingbunnies.model.configuration.input.InputConfiguration;

public class SettingsEntity {

	private final InputConfiguration inputConfiguration;
	private final int zoom;
	private final int speed;
	private final String playerName;
	private final boolean playMusic;
	private final boolean playSound;
	private final boolean lefthanded;
	private final int victoryLimit;

	public SettingsEntity(InputConfiguration inputConfiguration, int zoom,
			int speed, String playerName, boolean playMusic, boolean playSound,
			boolean leftHanded, int victoryLimit) {
		this.inputConfiguration = inputConfiguration;
		this.zoom = zoom;
		this.speed = speed;
		this.playerName = playerName;
		this.playMusic = playMusic;
		this.playSound = playSound;
		this.lefthanded = leftHanded;
		this.victoryLimit = victoryLimit;
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

	public boolean isPlayMusic() {
		return playMusic;
	}

	public boolean isPlaySound() {
		return playSound;
	}

	public boolean isLefthanded() {
		return lefthanded;
	}

	public int getVictoryLimit() {
		return victoryLimit;
	}
	
	

}
