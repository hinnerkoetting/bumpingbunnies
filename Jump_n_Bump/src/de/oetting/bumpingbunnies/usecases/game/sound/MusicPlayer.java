package de.oetting.bumpingbunnies.usecases.game.sound;

import android.media.MediaPlayer.OnCompletionListener;

public interface MusicPlayer {

	void start();

	void pauseBackground();

	void stopBackground();

	void setOnCompletionListener(OnCompletionListener fixture);
}
