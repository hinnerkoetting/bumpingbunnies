package de.oetting.bumpingbunnies.usecases.game.sound;

import java.util.List;

import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import de.oetting.bumpingbunnies.android.game.sound.AndroidMusicOnCompletionListener;
import de.oetting.bumpingbunnies.model.game.MusicPlayer;
import de.oetting.bumpingbunnies.model.game.objects.OnMusicCompletionListener;

public class MultiTrackMusicPlayer implements MusicPlayer, OnCompletionListener {

	private final List<MusicPlayer> musicPlayer;
	private int currentIndex = -1;
	private boolean playing;

	public MultiTrackMusicPlayer(List<MusicPlayer> tracks) {
		this.musicPlayer = tracks;
		for (MusicPlayer mp : tracks) {
			mp.setOnCompletionListener(new AndroidMusicOnCompletionListener(this));
		}
	}

	@Override
	public void start() {
		playing = true;
		if (this.musicPlayer.size() == 0) {
			throw new NoTrackExists();
		} else {
			this.musicPlayer.get(getNextIndex()).start();
		}
	}

	@Override
	public void pauseBackground() {
		this.musicPlayer.get(this.currentIndex).pauseBackground();
	}

	@Override
	public void stopBackground() {
		playing = false;
		this.musicPlayer.get(this.currentIndex).stopBackground();
	}


	@Override
	public void onCompletion(MediaPlayer mp) {
		if (playing)
			this.musicPlayer.get(getNextIndex()).start();
	}

	private int getNextIndex() {
		int nextIndex = this.currentIndex + 1;
		if (nextIndex >= this.musicPlayer.size()) {
			nextIndex = 0;
		}
		this.currentIndex = nextIndex;
		return nextIndex;
	}

	@Override
	public void setOnCompletionListener(OnMusicCompletionListener listener) {
	}

	public class NoTrackExists extends RuntimeException {
	}
}
