package de.oetting.bumpingbunnies.worldcreator.WorldCreator;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import de.oetting.bumpingbunnies.core.level.LevelStorer;
import de.oetting.bumpingbunnies.core.level.XmlStorer;
import de.oetting.bumpingbunnies.core.world.World;
import de.oetting.bumpingbunnies.leveleditor.Main;
import de.oetting.bumpingbunnies.leveleditor.xml.XmlBuilder;

public class WorldCreator {

	public static void main(String[] args) throws Exception {
		WorldCreationSettings settings = createSettings(args);
		new WorldCreator().saveWorld(settings);
	}
	
	private static WorldCreationSettings createSettings(String[] args) {
		if (args.length != 3) {
			System.out.println("Illegal call. Has to be: worldcreator <source-worldxml> <source-image-directory> <targetfile>");
			throw new IllegalArgumentException();
		}
		return new WorldCreationSettings(args[0], args[1], args[2]);
	}

	public void saveWorld(WorldCreationSettings settings) throws Exception {
		Main.addToClasspathPath(settings.getSourceImages());
		World world = loadWorld(settings);
		saveWorld(settings, world);
		System.out.println("Created world at " + settings.getTargetFilename());
	}


	private World loadWorld(WorldCreationSettings settings) throws FileNotFoundException {
		return new XmlBuilder().parse(new FileInputStream(settings.getSourceWorld()));
	}

	private void saveWorld(WorldCreationSettings settings, World world) throws IOException {
		File storageFile = new File(settings.getTargetFilename());
		storageFile.getParentFile().mkdirs();
		new LevelStorer(new XmlStorer(world)).storeLevel(storageFile, world);
	}
}
