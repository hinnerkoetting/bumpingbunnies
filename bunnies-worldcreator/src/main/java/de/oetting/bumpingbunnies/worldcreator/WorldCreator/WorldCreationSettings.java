package de.oetting.bumpingbunnies.worldcreator.WorldCreator;

public class WorldCreationSettings {

	private final String sourceDirectory;
	private final String targetFilename;

	public WorldCreationSettings(String sourceDirectory, String targetFilename) {
		this.sourceDirectory = sourceDirectory;
		this.targetFilename = targetFilename;
	}

	public String getSourceFilename() {
		return sourceDirectory + "/" + "world.xml";
	}

	public String getSourceDirectory() {
		return sourceDirectory;
	}

	public String getTargetFilename() {
		return targetFilename;
	}

}
