package de.oetting.bumpingbunnies.worldcreator.gradle;

import org.gradle.api.DefaultTask;
import org.gradle.api.tasks.TaskAction;

import de.oetting.bumpingbunnies.worldcreator.maven.worldCreator.WorldCreationSettings;
import de.oetting.bumpingbunnies.worldcreator.maven.worldCreator.WorldCreator;


public class CreateWorldTask extends DefaultTask {

	public String sourceWorld;
	public String sourceImageDirectory;
	public String targetFilename;
    
    @TaskAction
	public void createWorld() throws Exception {
		WorldCreationSettings settings = new WorldCreationSettings(sourceWorld, sourceImageDirectory, targetFilename);
		new WorldCreator().saveWorld(settings);
    }

}