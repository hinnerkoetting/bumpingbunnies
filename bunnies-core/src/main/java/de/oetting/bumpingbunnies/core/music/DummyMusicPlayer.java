package de.oetting.bumpingbunnies.core.music;

import de.oetting.bumpingbunnies.usecases.game.music.MusicPlayer;
import de.oetting.bumpingbunnies.usecases.game.music.OnMusicCompletionListener;

public class DummyMusicPlayer implements MusicPlayer {

	@Override
	public void start() {
	}

	@Override
	public void pauseBackground() {
	}

	@Override
	public void stopBackground() {
	}

	@Override
	public void setOnCompletionListener(OnMusicCompletionListener fixture) {
	}

}
