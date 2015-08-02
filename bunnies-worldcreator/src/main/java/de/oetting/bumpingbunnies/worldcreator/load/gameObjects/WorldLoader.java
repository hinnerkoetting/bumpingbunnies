package de.oetting.bumpingbunnies.worldcreator.load.gameObjects;

import java.io.InputStream;

import de.oetting.bumpingbunnies.model.game.world.World;
import de.oetting.bumpingbunnies.util.Guard;
import de.oetting.bumpingbunnies.worldcreator.load.ClasspathZipreader;
import de.oetting.bumpingbunnies.worldcreator.load.DefaultResourceProvider;
import de.oetting.bumpingbunnies.worldcreator.load.ImageCache;
import de.oetting.bumpingbunnies.worldcreator.load.ImageCreator;
import de.oetting.bumpingbunnies.worldcreator.load.ImagesZipLoader;
import de.oetting.bumpingbunnies.worldcreator.load.XmlReader;

public class WorldLoader {

	public World load(WorldObjectsParser parser, ImageCreator imageCreator) {
		ImageCache images = loadAllImages(World.class.getResourceAsStream("/worlds/classic.zip"), imageCreator);
		XmlReader reader = new ClasspathZipreader(World.class.getResourceAsStream("/worlds/classic.zip"));
		return parser.build(new DefaultResourceProvider(images), reader);
	}
	
	private ImageCache loadAllImages(InputStream worldZip,  ImageCreator imageCreator) {
		Guard.againstNull("World.zip must exist but was not found in the classpath under /worlds/classic.zip", worldZip);
		return new ImagesZipLoader().loadAllImages(worldZip, imageCreator);
	}
}
