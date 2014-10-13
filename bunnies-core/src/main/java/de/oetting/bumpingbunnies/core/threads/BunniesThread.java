package de.oetting.bumpingbunnies.core.threads;

import de.oetting.bumpingbunnies.core.networking.messaging.stop.OnThreadErrorCallback;
import de.oetting.bumpingbunnies.logger.Logger;
import de.oetting.bumpingbunnies.logger.LoggerFactory;

/**
 * Every long running thread should extend this class. All threads must be
 * daemon threads so that the game can shut down when the main application
 * quits. Also they need a callback so that an error will end the game.<br>
 * A Name is required so that the thread can be identified while debugging.
 *
 */
public abstract class BunniesThread extends Thread {

	private static final Logger LOGGER = LoggerFactory.getLogger(BunniesThread.class);

	private final OnThreadErrorCallback stopper;

	protected BunniesThread(String name, OnThreadErrorCallback stopper) {
		super(name);
		this.stopper = stopper;
		setDaemon(true);
	}

	public final void run() {
		LOGGER.info("Starting threads %s", getName());
		try {
			doRun();
		} catch (Throwable t) {
			LOGGER.error("Exception on thread " + getName(), t);
			stopper.onThreadError();
		}

	}

	protected abstract void doRun() throws Exception;
}
