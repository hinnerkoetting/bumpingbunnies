package de.oetting.bumpingbunnies.usecases.game.music;


public interface MusicPlayer {

	void start();

	void pauseBackground();

	void stopBackground();

	void setOnCompletionListener(OnMusicCompletionListener listener);
}
