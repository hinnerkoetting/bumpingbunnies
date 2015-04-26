package de.oetting.bumpingbunnies.core.worldCreation.parser;

import java.io.InputStream;

import de.oetting.bumpingbunnies.core.game.ImageCache;
import de.oetting.bumpingbunnies.core.game.ImageCreator;
import de.oetting.bumpingbunnies.core.game.ImagesZipLoader;
import de.oetting.bumpingbunnies.core.world.World;
import de.oetting.bumpingbunnies.core.worldCreation.DefaultResourceProvider;

public class WorldLoader {

	public World load(WorldObjectsParser parser, ImageCreator imageCreator) {
		ImageCache images = loadAllImages(World.class.getResourceAsStream("/worlds/classic.zip"), imageCreator);
		XmlReader reader = new ClasspathZipreader(World.class.getResourceAsStream("/worlds/classic.zip"));
		return parser.build(new DefaultResourceProvider(images), reader);
	}
	
	private ImageCache loadAllImages(InputStream worldZip,  ImageCreator imageCreator) {
		return new ImagesZipLoader().loadAllImages(worldZip, imageCreator);
	}
}
