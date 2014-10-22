package de.oetting.bumpingbunnies.pc.music;

import de.oetting.bumpingbunnies.model.game.MusicPlayer;
import de.oetting.bumpingbunnies.model.game.objects.OnMusicCompletionListener;

public class PcMusicPlayer implements MusicPlayer {

	private final MusicPlayerThread playerThread;

	public PcMusicPlayer(MusicPlayerThread playerThread) {
		this.playerThread = playerThread;
	}

	@Override
	public void start() {
		playerThread.play();
	}

	@Override
	public void pauseBackground() {
		playerThread.pause();
	}

	@Override
	public void stopBackground() {
		playerThread.cancel();
	}

	@Override
	public void setOnCompletionListener(OnMusicCompletionListener listener) {
		PcOnCompletionListener completionListener = (PcOnCompletionListener) listener;
		playerThread.setCompletionListener(completionListener);
	}

}
