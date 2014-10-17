package de.oetting.bumpingbunnies.pc.graphics;

import javafx.scene.image.Image;
import de.oetting.bumpingbunnies.core.music.DummyMusicPlayer;
import de.oetting.bumpingbunnies.core.resources.ResourceProvider;
import de.oetting.bumpingbunnies.core.worldCreation.ClasspathImageReader;
import de.oetting.bumpingbunnies.model.game.MusicPlayer;
import de.oetting.bumpingbunnies.model.game.objects.ImageWrapper;

public class PcResourceProvider implements ResourceProvider {

	private final ClasspathImageReader imageReader;

	public PcResourceProvider() {
		this.imageReader = new ClasspathImageReader();
	}

	@Override
	public ImageWrapper readBitmap(String fileName) {
		Image image = new Image(imageReader.readAsStream(fileName));
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
