package de.oetting.bumpingbunnies.usecases.game.communication;

import java.io.IOException;
import java.io.Writer;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import com.google.gson.Gson;

import de.oetting.bumpingbunnies.communication.MySocket;
import de.oetting.bumpingbunnies.usecases.game.communication.objects.JsonWrapper;
import de.oetting.bumpingbunnies.usecases.game.communication.objects.MessageId;
import de.oetting.bumpingbunnies.usecases.game.model.Player;
import de.oetting.bumpingbunnies.usecases.game.model.PlayerState;

public class NetworkSendQueueThread extends Thread implements RemoteSender {

	private final Gson gson;
	private final Writer writer;
	private boolean canceled;

	private BlockingQueue<String> messageQueue;
	private final MySocket socket;

	public NetworkSendQueueThread(MySocket socket, Writer writer, Gson gson) {
		super("Network send thread");
		this.socket = socket;
		this.writer = writer;
		this.gson = gson;
		this.messageQueue = new LinkedBlockingQueue<String>();
	}

	@Override
	public void run() {
		while (!this.canceled) {
			try {
				oneRun();
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		}
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
		String stateJson = this.gson.toJson(state);
		JsonWrapper wrapper = new JsonWrapper(MessageId.SEND_PLAYER_STATE, stateJson);
		String data = this.gson.toJson(wrapper);
		this.messageQueue.add(data);
	}

	@Override
	public void sendMessage(MessageId id, Object message) {
		String json = this.gson.toJson(message);
		JsonWrapper wrapper = new JsonWrapper(id, json);
		sendMessage(wrapper);
	}

	@Override
	public void sendMessage(JsonWrapper wrapper) {
		String data = this.gson.toJson(wrapper);
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
