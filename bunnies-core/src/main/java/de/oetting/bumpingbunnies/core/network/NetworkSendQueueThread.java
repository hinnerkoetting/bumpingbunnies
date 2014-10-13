package de.oetting.bumpingbunnies.core.network;

import java.io.IOException;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import de.oetting.bumpingbunnies.core.networking.communication.messageInterface.NetworkSender;
import de.oetting.bumpingbunnies.core.threads.ThreadErrorCallback;
import de.oetting.bumpingbunnies.logger.Logger;
import de.oetting.bumpingbunnies.logger.LoggerFactory;
import de.oetting.bumpingbunnies.model.game.objects.Opponent;
import de.oetting.bumpingbunnies.model.network.JsonWrapper;
import de.oetting.bumpingbunnies.model.network.MessageId;

/**
 * Messages are stored to a queue, The sender will send messages from this queue
 * ony by one.
 * 
 */
public class NetworkSendQueueThread extends Thread implements NetworkSender {

	private static final Logger LOGGER = LoggerFactory.getLogger(NetworkSendQueueThread.class);
	private final BlockingQueue<String> messageQueue;
	private final MySocket socket;
	private final MessageParser parser;
	private final ThreadErrorCallback threadErrorCallback;

	private boolean canceled;

	public NetworkSendQueueThread(MySocket socket, MessageParser parser, ThreadErrorCallback threadErrorCallback) {
		super("Network send thread");
		this.socket = socket;
		this.parser = parser;
		this.threadErrorCallback = threadErrorCallback;
		this.messageQueue = new LinkedBlockingQueue<String>();
		setDaemon(true);
	}

	@Override
	public void run() {
		while (!this.canceled) {
			runOnce();
		}
	}

	void runOnce() {
		try {
			sendNextMessage();
		} catch (Exception e) {
			LOGGER.error("Disconnected from server", e);
			endGameOnError();
		}
	}

	void sendNextMessage() throws IOException, InterruptedException {
		String poll = this.messageQueue.take();
		sendOneMessage(poll);
	}

	private void sendOneMessage(String message) throws IOException {
		this.socket.sendMessage(message);
	}

	private void endGameOnError() {
		threadErrorCallback.onThreadError();
	}

	@Override
	public void sendMessage(MessageId id, Object message) {
		String json = this.parser.encodeMessage(message);
		JsonWrapper wrapper = JsonWrapper.create(id, json);
		sendMessage(wrapper);
	}

	@Override
	public void sendMessage(JsonWrapper wrapper) {
		String data = this.parser.encodeMessage(wrapper);
		this.messageQueue.add(data);
	}

	@Override
	public void cancel() {
		this.canceled = true;
	}

	@Override
	public boolean usesThisSocket(MySocket socket) {
		return this.socket.equals(socket);
	}

	@Override
	public boolean isConnectionToPlayer(Opponent opponent) {
		return this.socket.getOwner().equals(opponent);
	}
}
