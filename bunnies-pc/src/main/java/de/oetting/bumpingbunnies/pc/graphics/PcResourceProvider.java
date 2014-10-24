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
	private final ThreadErrorCallback errorCallback;

	public PcResourceProvider(ThreadErrorCallback errorCallback) {
		this.errorCallback = errorCallback;
		this.imageReader = new ClasspathImageReader();
	}

	@Override
	public ImageWrapper readBitmap(String fileName) {
		Image image = new Image(imageReader.readAsStream(fileName));
		return new ImageWrapper(image);
	}

	@Override
	public MusicPlayer readerJumperMusic() {
		return PcMusicPlayerFactory.createJumper(errorCallback);
	}

	@Override
	public MusicPlayer readWaterMusic() {
		return PcMusicPlayerFactory.createWater(errorCallback);
	}

}
