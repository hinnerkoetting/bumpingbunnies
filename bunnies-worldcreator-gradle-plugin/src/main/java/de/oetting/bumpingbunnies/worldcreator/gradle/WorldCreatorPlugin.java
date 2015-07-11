package de.oetting.bumpingbunnies.worldcreator.gradle;
import org.gradle.api.Project;
import org.gradle.api.Plugin;

public class WorldCreatorPlugin implements Plugin<Project> {

	public void apply(Project target) {
		target.getTasks().create("createWorld", CreateWorldTask.class);
	}
}