package de.oetting.bumpingbunnies.usecases.game.android.sound;

import android.media.MediaPlayer;
import de.oetting.bumpingbunnies.usecases.game.music.MusicPlayer;
import de.oetting.bumpingbunnies.usecases.game.music.OnMusicCompletionListener;

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

	@Override
	public void setOnCompletionListener(OnMusicCompletionListener listener) {
		AndroidMusicOnCompletionListener androidListener = (AndroidMusicOnCompletionListener) listener;
		this.mediaPlayer.setOnCompletionListener(androidListener.getAndroidListener());
	}

}
