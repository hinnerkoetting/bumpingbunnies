package de.oetting.bumpingbunnies.pc.graphics;

import javafx.scene.image.Image;
import de.oetting.bumpingbunnies.core.resources.ResourceProvider;
import de.oetting.bumpingbunnies.core.threads.ThreadErrorCallback;
import de.oetting.bumpingbunnies.core.worldCreation.ClasspathImageReader;
import de.oetting.bumpingbunnies.model.game.MusicPlayer;
import de.oetting.bumpingbunnies.model.game.objects.ImageWrapper;
import de.oetting.bumpingbunnies.pc.music.PcMusicPlayerFactory;

public class PcResourceProvider implements ResourceProvider {

	private final ClasspathImageReader imageReader;
	private final PcMusicPlayerFactory musicPlayerFactory;

	public PcResourceProvider(ThreadErrorCallback errorCallback) {
		this.imageReader = new ClasspathImageReader();
		this.musicPlayerFactory = new PcMusicPlayerFactory(errorCallback);
	}

	@Override
	public ImageWrapper readBitmap(String fileName) {
		Image image = new Image(imageReader.readAsStream(fileName));
		return new ImageWrapper(image);
	}

	@Override
	public MusicPlayer readerJumperMusic() {
		return musicPlayerFactory.createJumper();
	}

	@Override
	public MusicPlayer readWaterMusic() {
		return musicPlayerFactory.createWater();
	}

}
