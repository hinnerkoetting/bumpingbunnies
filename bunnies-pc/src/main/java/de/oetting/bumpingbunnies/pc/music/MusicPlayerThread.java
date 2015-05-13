package de.oetting.bumpingbunnies.pc.music;

import de.oetting.bumpingbunnies.core.threads.BunniesThread;
import de.oetting.bumpingbunnies.core.threads.ThreadErrorCallback;
import de.oetting.bumpingbunnies.logger.Logger;
import de.oetting.bumpingbunnies.logger.LoggerFactory;

public class MusicPlayerThread extends BunniesThread {

	private static final Logger LOGGER = LoggerFactory.getLogger(MusicPlayerThread.class);

	private enum Status {
		PLAYING, PAUSED, STOPPED, FINISHED
	}

	private final MusicPlayerFactory playerFactory;
	// can be null.
	private Mp3Player player;
	private Status status = Status.PAUSED;
	private boolean canceled;
	private Object lock = new Object();
	private PcOnCompletionListener completionListener;
	private boolean reset;

	public MusicPlayerThread(ThreadErrorCallback stopper, MusicPlayerFactory playerFactory) {
		super("Musicplayer", stopper);
		this.playerFactory = playerFactory;
	}

	@Override
	protected void doRun() throws Exception {
		while (!canceled) {
			if (status == Status.PLAYING) {
				if (reset) {
					player = playerFactory.createPlayer();
					reset = false;
				}
				boolean played = player.play(1);
				if (!played) {
					status = Status.FINISHED;
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
		if (status != Status.PLAYING) {
			synchronized (lock) {
				// if (status == Status.PLAYING)
				reset = true;
				status = Status.PLAYING;
				if (isAlive())
					lock.notifyAll();
				else
					try {
						start();
					} catch (IllegalThreadStateException e) {
						LOGGER.error("Could not start thread. Previous error on thread?");
					}
			}
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
			if (player != null)
				player.close();
			canceled = true;
		}
	}

	public void setCompletionListener(PcOnCompletionListener completionListener) {
		this.completionListener = completionListener;
	}

}
