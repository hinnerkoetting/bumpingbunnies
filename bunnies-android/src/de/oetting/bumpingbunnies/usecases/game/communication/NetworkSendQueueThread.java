package de.oetting.bumpingbunnies.usecases.game.communication;

import java.io.IOException;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import android.widget.Toast;
import de.oetting.bumpingbunnies.R;
import de.oetting.bumpingbunnies.communication.messageInterface.NetworkSender;
import de.oetting.bumpingbunnies.core.networking.MessageParser;
import de.oetting.bumpingbunnies.core.networking.MySocket;
import de.oetting.bumpingbunnies.logger.Logger;
import de.oetting.bumpingbunnies.logger.LoggerFactory;
import de.oetting.bumpingbunnies.model.networking.JsonWrapper;
import de.oetting.bumpingbunnies.model.networking.MessageId;
import de.oetting.bumpingbunnies.usecases.game.android.GameActivity;
import de.oetting.bumpingbunnies.usecases.game.model.Opponent;

/**
 * Messages are stored to a queue, The sender will send messages from this queue ony by one.
 * 
 */
public class NetworkSendQueueThread extends Thread implements NetworkSender {

	private static final Logger LOGGER = LoggerFactory.getLogger(NetworkSendQueueThread.class);
	private final BlockingQueue<String> messageQueue;
	private final MySocket socket;
	private final MessageParser parser;
	private final GameActivity origin;

	private boolean canceled;

	public NetworkSendQueueThread(MySocket socket, MessageParser parser, GameActivity origin) {
		super("Network send thread");
		this.socket = socket;
		this.parser = parser;
		this.origin = origin;
		this.messageQueue = new LinkedBlockingQueue<String>();
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
		this.origin.runOnUiThread(new Runnable() {

			@Override
			public void run() {
				String message = NetworkSendQueueThread.this.origin.getString(R.string.disconnected);
				Toast.makeText(NetworkSendQueueThread.this.origin, message, Toast.LENGTH_LONG).show();
				NetworkSendQueueThread.this.origin.stopGame();
			}
		});
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
	public void sendMessageFast(MessageId id, Object message) {
		sendMessage(id, message);
	}

	@Override
	public boolean isConnectionToPlayer(Opponent opponent) {
		return this.socket.getOwner().equals(opponent);
	}
}
