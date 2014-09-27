package de.oetting.bumpingbunnies.core.resources;

import de.oetting.bumpingbunnies.usecases.game.model.ImageWrapper;
import de.oetting.bumpingbunnies.usecases.game.music.MusicPlayer;

public interface ResourceProvider {

	ImageWrapper readBitmap(String fileName);

	MusicPlayer readerJumperMusic();

	MusicPlayer readWaterMusic();

}
