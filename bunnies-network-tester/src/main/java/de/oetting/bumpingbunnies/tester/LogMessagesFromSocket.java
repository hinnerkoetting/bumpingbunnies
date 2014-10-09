package de.oetting.bumpingbunnies.tester;

import de.oetting.bumpingbunnies.core.network.MySocket;
import de.oetting.bumpingbunnies.logger.Logger;
import de.oetting.bumpingbunnies.logger.LoggerFactory;

public class LogMessagesFromSocket extends Thread {

	private static final Logger LOGGER = LoggerFactory.getLogger(LogMessagesFromSocket.class);

	private final MySocket socket;

	public LogMessagesFromSocket(MySocket socket) {
		this.socket = socket;
		setDaemon(true);
	}

	public void run() {
		while (true)
			readNextMessage();
	}

	private void readNextMessage() {
		String nextMessage = socket.blockingReceive();
		LOGGER.info(nextMessage);
	}
}
