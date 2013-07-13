package de.oetting.bumpingbunnies.usecases.game.communication;

import java.io.IOException;
import java.io.Writer;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import android.widget.Toast;
import de.oetting.bumpingbunnies.R;
import de.oetting.bumpingbunnies.communication.MySocket;
import de.oetting.bumpingbunnies.logger.Logger;
import de.oetting.bumpingbunnies.logger.LoggerFactory;
import de.oetting.bumpingbunnies.usecases.game.android.GameActivity;
import de.oetting.bumpingbunnies.usecases.game.communication.objects.JsonWrapper;
import de.oetting.bumpingbunnies.usecases.game.communication.objects.MessageId;
import de.oetting.bumpingbunnies.usecases.game.model.Player;
import de.oetting.bumpingbunnies.usecases.game.model.PlayerState;

/**
 * Messages are stored to a queue, The sender will send messages from this queue
 * ony by one.
 * 
 */
public class NetworkSendQueueThread extends Thread implements RemoteSender {

	private static final Logger LOGGER = LoggerFactory.getLogger(NetworkSendQueueThread.class);
	private final Writer writer;
	private final BlockingQueue<String> messageQueue;
	private final MySocket socket;
	private final MessageParser parser;
	private final GameActivity origin;

	private boolean canceled;

	public NetworkSendQueueThread(MySocket socket, Writer writer, MessageParser parser, GameActivity origin) {
		super("Network send thread");
		this.socket = socket;
		this.writer = writer;
		this.parser = parser;
		this.origin = origin;
		this.messageQueue = new LinkedBlockingQueue<String>();
	}

	@Override
	public void run() {
		while (!this.canceled) {
			try {
				oneRun();
			} catch (Exception e) {
				LOGGER.error("Disconnected from server", e);
				endGameOnError();
			}
		}
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

	private void oneRun() throws InterruptedException, IOException {
		sendNextMessage();
	}

	private void sendNextMessage() throws IOException, InterruptedException {
		String poll = this.messageQueue.take();
		sendOneMessage(poll);
	}

	private void sendOneMessage(String string) throws IOException {
		this.writer.write(string);
		this.writer.write('\n');
		this.writer.flush();
	}

	@Override
	public void sendPlayerCoordinates(Player player) {
		sendPlayerCoordinates(player.getState());
	}

	@Override
	public void sendPlayerCoordinates(PlayerState state) {
		String stateJson = this.parser.encodeMessage(state);
		JsonWrapper wrapper = new JsonWrapper(MessageId.SEND_PLAYER_STATE, stateJson);
		String data = this.parser.encodeMessage(wrapper);
		this.messageQueue.add(data);
	}

	@Override
	public void sendMessage(MessageId id, Object message) {
		String json = this.parser.encodeMessage(message);
		JsonWrapper wrapper = new JsonWrapper(id, json);
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
}
