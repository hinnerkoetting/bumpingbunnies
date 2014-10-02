package de.oetting.bumpingbunnies.model.game;

import de.oetting.bumpingbunnies.model.game.objects.OnMusicCompletionListener;

public interface MusicPlayer {

	void start();

	void pauseBackground();

	void stopBackground();

	void setOnCompletionListener(OnMusicCompletionListener listener);
}
