package de.oetting.bumpingbunnies.worldcreator.maven.worldCreator;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;

@Mojo(name = "CreateWorld", defaultPhase=LifecyclePhase.GENERATE_RESOURCES)
public class WorldCreationMojo extends AbstractMojo {

	@Parameter(property = "sourceWorld", required = true)
	private String sourceWorld;
	@Parameter(property = "sourceImages", required = true)
	private String sourceImageDirectory;
	@Parameter(property = "targetFilename", required = true)
	private String targetFilename;

	@Override
	public void execute() throws MojoExecutionException, MojoFailureException {
		try {
			WorldCreationSettings settings = new WorldCreationSettings(sourceWorld, sourceImageDirectory, targetFilename);
			new WorldCreator().saveWorld(settings);
		} catch (Exception e) {
			throw new MojoExecutionException("Error", e);
		}
	}
}
