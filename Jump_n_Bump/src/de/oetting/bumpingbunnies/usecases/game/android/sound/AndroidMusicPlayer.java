package de.oetting.bumpingbunnies.usecases.game.android.sound;

import android.media.MediaPlayer;
import de.oetting.bumpingbunnies.usecases.game.sound.MusicPlayer;

public class AndroidMusicPlayer implements MusicPlayer {
	private final MediaPlayer mediaPlayer;

	public AndroidMusicPlayer(MediaPlayer mediaPlayer) {
		this.mediaPlayer = mediaPlayer;
	}

	@Override
	public void start() {
		this.mediaPlayer.start();
	}

	@Override
	public void stopBackground() {
		this.mediaPlayer.stop();
	}

	@Override
	public void pauseBackground() {
		this.mediaPlayer.pause();
	}

}