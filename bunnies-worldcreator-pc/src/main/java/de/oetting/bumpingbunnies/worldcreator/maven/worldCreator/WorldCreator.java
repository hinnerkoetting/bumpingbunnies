package de.oetting.bumpingbunnies.worldcreator.maven.worldCreator;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import de.oetting.bumpingbunnies.ClasspathFix;
import de.oetting.bumpingbunnies.logger.Logger;
import de.oetting.bumpingbunnies.logger.LoggerFactory;
import de.oetting.bumpingbunnies.model.game.world.World;
import de.oetting.bumpingbunnies.worldcreator.io.LevelStorer;
import de.oetting.bumpingbunnies.worldcreator.io.XmlStorer;
import de.oetting.bumpingbunnies.worldcreatorPc.load.XmlBuilder;

public class WorldCreator {

	private static final Logger LOGGER = LoggerFactory.getLogger(WorldCreator.class);
	
	public static void main(String[] args) throws Exception {
		WorldCreationSettings settings = createSettings(args);
		new WorldCreator().saveWorld(settings);
	}

	private static WorldCreationSettings createSettings(String[] args) {
		if (args.length != 3) {
			System.out
					.println("Illegal call. Has to be: worldcreator <source-worldxml> <source-image-directory> <targetfile>");
			throw new IllegalArgumentException();
		}
		return new WorldCreationSettings(args[0], args[1], args[2]);
	}

	public void saveWorld(WorldCreationSettings settings) throws Exception {
		LOGGER.info("Creating world with Settings " + settings);
		ClasspathFix.addToClasspathPath(settings.getSourceImages());
		World world = loadWorld(settings);
		saveWorld(settings, world);
		System.out.println("Created world at " + settings.getTargetFilename());
	}

	private World loadWorld(WorldCreationSettings settings) throws FileNotFoundException {
		return new XmlBuilder().parse(new FileInputStream(settings.getSourceWorld()), settings.getSourceImages());
	}

	private void saveWorld(WorldCreationSettings settings, World world) throws IOException {
		File storageFile = new File(settings.getTargetFilename());
		storageFile.getParentFile().mkdirs();
		new LevelStorer(new XmlStorer(world)).storeLevel(storageFile, world);
	}
}
