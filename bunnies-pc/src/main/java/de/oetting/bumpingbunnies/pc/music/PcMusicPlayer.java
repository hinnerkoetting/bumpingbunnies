package de.oetting.bumpingbunnies.pc.music;

import javafx.scene.media.MediaPlayer;
import de.oetting.bumpingbunnies.model.game.MusicPlayer;
import de.oetting.bumpingbunnies.model.game.objects.OnMusicCompletionListener;

public class PcMusicPlayer implements MusicPlayer {

	private final MediaPlayer mediaPlayer;

	public PcMusicPlayer(MediaPlayer mediaPlayer) {
		this.mediaPlayer = mediaPlayer;
	}

	@Override
	public void start() {
		mediaPlayer.play();
	}

	@Override
	public void pauseBackground() {
		mediaPlayer.pause();
	}

	@Override
	public void stopBackground() {
		mediaPlayer.stop();
	}

	@Override
	public void setOnCompletionListener(OnMusicCompletionListener listener) {
		PcOnCompletionListener completionListener = (PcOnCompletionListener) listener;
		mediaPlayer.setOnEndOfMedia(completionListener.getRunnable());
	}

}
