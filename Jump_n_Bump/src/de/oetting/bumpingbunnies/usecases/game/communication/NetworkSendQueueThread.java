package de.oetting.bumpingbunnies.usecases.game.communication;

import java.io.IOException;
import java.io.Writer;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import com.google.gson.Gson;

import de.oetting.bumpingbunnies.logger.Logger;
import de.oetting.bumpingbunnies.logger.MyLog;
import de.oetting.bumpingbunnies.usecases.game.communication.objects.JsonWrapper;
import de.oetting.bumpingbunnies.usecases.game.model.Player;
import de.oetting.bumpingbunnies.usecases.game.model.PlayerState;

public class NetworkSendQueueThread extends Thread implements RemoteSender {

	private static final MyLog LOGGER = Logger
			.getLogger(NetworkSendQueueThread.class);
	private final Gson gson;
	private final Writer writer;
	private boolean canceled;

	private BlockingQueue<String> messageQueue;

	public NetworkSendQueueThread(Writer writer, Gson gson) {
		super("Network send thread");
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
	public synchronized void sendPlayerCoordinates(Player player) {
		sendPlayerCoordinates(player.getState());
	}

	public synchronized void sendPlayerCoordinates(PlayerState state) {
		JsonWrapper wrapper = new JsonWrapper(state.getId(), state);
		String data = this.gson.toJson(wrapper);
		this.messageQueue.add(data);
	}

	@Override
	public void cancel() {
		this.canceled = true;
	}
}
