package de.oetting.bumpingbunnies.model.configuration;

import de.oetting.bumpingbunnies.model.configuration.input.InputConfiguration;

public class LocalSettings {

	private final InputConfiguration inputConfiguration;
	private final int zoom;
	private final boolean playMusic;
	private final boolean playSounds;
	private final boolean lefthanded;

	public LocalSettings(InputConfiguration inputConfiguration, int zoom, boolean playMusic, boolean playSounds,
			boolean leftHanded) {
		this.inputConfiguration = inputConfiguration;
		this.zoom = zoom;
		this.playMusic = playMusic;
		this.playSounds = playSounds;
		lefthanded = leftHanded;
	}

	public InputConfiguration getInputConfiguration() {
		return this.inputConfiguration;
	}

	public int getZoom() {
		return this.zoom;
	}

	public boolean isPlayMusic() {
		return playMusic;
	}

	public boolean isPlaySounds() {
		return playSounds;
	}

	public boolean isLefthanded() {
		return lefthanded;
	}

}
