package de.oetting.bumpingbunnies.pc.music;

import de.oetting.bumpingbunnies.model.game.objects.OnMusicCompletionListener;

public class PcOnCompletionListener implements OnMusicCompletionListener {

	private final Runnable runnable;

	public PcOnCompletionListener(Runnable runnable) {
		this.runnable = runnable;
	}

	public Runnable getRunnable() {
		return runnable;
	}

}
