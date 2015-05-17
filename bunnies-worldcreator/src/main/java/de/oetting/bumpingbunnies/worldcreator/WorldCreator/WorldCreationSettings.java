package de.oetting.bumpingbunnies.worldcreator.WorldCreator;

public class WorldCreationSettings {

	private final String sourceWorld;
	private final String sourceImages;
	private final String targetFilename;

	
	public WorldCreationSettings(String sourceWorld, String sourceImages, String targetFilename) {
		this.sourceWorld = sourceWorld;
		this.sourceImages = sourceImages;
		this.targetFilename = targetFilename;
	}

	public String getSourceWorld() {
		return sourceWorld;
	}

	public String getSourceImages() {
		return sourceImages;
	}

	public String getTargetFilename() {
		return targetFilename;
	}

}
