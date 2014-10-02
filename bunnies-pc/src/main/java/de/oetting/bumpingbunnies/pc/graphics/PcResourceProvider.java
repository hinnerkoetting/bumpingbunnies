package de.oetting.bumpingbunnies.pc.graphics;

import java.io.InputStream;

import javafx.scene.image.Image;
import de.oetting.bumpingbunnies.core.music.DummyMusicPlayer;
import de.oetting.bumpingbunnies.core.resources.ResourceProvider;
import de.oetting.bumpingbunnies.usecases.game.model.ImageWrapper;
import de.oetting.bumpingbunnies.usecases.game.music.MusicPlayer;

public class PcResourceProvider implements ResourceProvider {

	@Override
	public ImageWrapper readBitmap(String fileName) {
		String classpathName = "/drawable/" + fileName + ".png";
		InputStream resourceAsStream = getClass().getResourceAsStream(classpathName);
		if (resourceAsStream == null)
			throw new IllegalArgumentException("Coult not find file in classpath: " + classpathName);
		Image image = new Image(resourceAsStream);
		return new ImageWrapper(image);
	}

	@Override
	public MusicPlayer readerJumperMusic() {
		return new DummyMusicPlayer();
	}

	@Override
	public MusicPlayer readWaterMusic() {
		return new DummyMusicPlayer();
	}

}
