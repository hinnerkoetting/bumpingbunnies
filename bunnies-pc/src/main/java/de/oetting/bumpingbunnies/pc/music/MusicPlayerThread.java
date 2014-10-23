package de.oetting.bumpingbunnies.pc.music;

import javazoom.jl.player.Player;
import de.oetting.bumpingbunnies.core.threads.BunniesThread;
import de.oetting.bumpingbunnies.core.threads.ThreadErrorCallback;

public class MusicPlayerThread extends BunniesThread {

	private enum Status {
		PLAYING, PAUSED, STOPPED
	}

	private final Player player;
	private Status status = Status.PAUSED;
	private boolean canceled;
	private Object lock = new Object();
	private PcOnCompletionListener completionListener;

	public MusicPlayerThread(ThreadErrorCallback stopper, Player player) {
		super("Musicplayer", stopper);
		this.player = player;
	}

	@Override
	protected void doRun() throws Exception {
		while (!canceled) {
			if (status == Status.PLAYING) {
				boolean played = player.play(1);
				if (!played) {
					if (completionListener != null)
						completionListener.getRunnable().run();
				}
			} else
				waitForPlay();
		}
	}

	private void waitForPlay() {
		synchronized (lock) {
			if (status != Status.PLAYING) {
				try {
					lock.wait();
				} catch (InterruptedException e) {
				}
			}
		}
	}

	public void play() {
		synchronized (lock) {
			status = Status.PLAYING;
			if (isAlive())
				lock.notifyAll();
			else
				start();
		}
	}

	public void pause() {
		synchronized (lock) {
			status = Status.PAUSED;
		}
	}

	public void cancel() {
		synchronized (lock) {
			status = Status.STOPPED;
			player.close();
			canceled = true;
		}
	}

	public void setCompletionListener(PcOnCompletionListener completionListener) {
		this.completionListener = completionListener;
	}

}
