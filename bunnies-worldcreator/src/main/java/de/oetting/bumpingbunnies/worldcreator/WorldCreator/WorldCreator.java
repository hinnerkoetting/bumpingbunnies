package de.oetting.bumpingbunnies.worldcreator.WorldCreator;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import de.oetting.bumpingbunnies.core.level.LevelStorer;
import de.oetting.bumpingbunnies.core.level.XmlStorer;
import de.oetting.bumpingbunnies.core.world.World;
import de.oetting.bumpingbunnies.leveleditor.Main;
import de.oetting.bumpingbunnies.leveleditor.xml.XmlBuilder;

public class WorldCreator {

	public static void main(String[] args) throws Exception {
		WorldCreationSettings settings = createSettings(args);
		Main.addToClasspathPath(settings.getSourceDirectory());
		World world = loadWorld(settings);
		saveWorld(settings, world);
		System.out.println("Created world at " + settings.getTargetFilename());
	}

	private static WorldCreationSettings createSettings(String[] args) {
		if (args.length != 2) {
			System.out.println("Illegal call. Has to be: worldcreator <source-directory> <targetfile>");
			throw new IllegalArgumentException();
		}
		return new WorldCreationSettings(args[0], args[1]);
	}

	private static World loadWorld(WorldCreationSettings settings) throws FileNotFoundException {
		return new XmlBuilder().parse(new FileInputStream(settings.getSourceFilename()));
	}

	private static void saveWorld(WorldCreationSettings settings, World world) {
		File storageFile = new File(settings.getTargetFilename());
		new LevelStorer(new XmlStorer(world)).storeLevel(storageFile, world);
	}
}
