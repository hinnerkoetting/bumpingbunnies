package de.oetting.bumpingbunnies.pc.worldcreation.parser;

import de.oetting.bumpingbunnies.core.music.DummyMusicPlayer;
import de.oetting.bumpingbunnies.core.resources.ResourceProvider;
import de.oetting.bumpingbunnies.model.game.MusicPlayer;
import de.oetting.bumpingbunnies.model.game.objects.ImageWrapper;

public class NoopResourceProvider implements ResourceProvider {

	@Override
	public ImageWrapper readBitmap(String fileName) {
		throw new IllegalArgumentException("not yet implemented");
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
