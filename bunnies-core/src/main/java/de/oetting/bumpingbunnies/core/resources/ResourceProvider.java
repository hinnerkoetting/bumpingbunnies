package de.oetting.bumpingbunnies.core.resources;

import de.oetting.bumpingbunnies.usecases.game.model.Image;
import de.oetting.bumpingbunnies.usecases.game.music.MusicPlayer;

public interface ResourceProvider {

	Image readBitmap(String fileName);

	MusicPlayer readerJumperMusic();

	MusicPlayer readWaterMusic();

}
