package de.jumpnbump.usecases.game.communication;

import java.io.IOException;
import java.io.Writer;
import java.util.LinkedList;
import java.util.Queue;

import com.google.gson.Gson;

import de.jumpnbump.logger.Logger;
import de.jumpnbump.logger.MyLog;
import de.jumpnbump.usecases.game.communication.objects.JsonWrapper;
import de.jumpnbump.usecases.game.model.Player;

public class NetworkSendQueueThread extends Thread implements RemoteSender {

	private static final MyLog LOGGER = Logger
			.getLogger(NetworkSendQueueThread.class);
	private final Gson gson;
	private final Writer writer;
	private boolean canceled;

	private Queue<String> messageQueue;

	public NetworkSendQueueThread(Writer writer, Gson gson) {
		this.writer = writer;
		this.gson = gson;
		this.messageQueue = new LinkedList<String>();
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
		if (this.messageQueue.isEmpty()) {
			sleep(1);
		} else {
			sendNextMessage();
		}
	}

	private void sendNextMessage() throws IOException {
		String poll = this.messageQueue.poll();
		sendOneMessage(poll);
	}

	private void sendOneMessage(String string) throws IOException {
		this.writer.write(string);
		this.writer.write('\n');
		this.writer.flush();
	}

	public synchronized void sendPlayerCoordinates(Player player) {
		JsonWrapper wrapper = new JsonWrapper(player.id(), player.getState());
		String data = this.gson.toJson(wrapper);
		this.messageQueue.add(data);
	}

	public void cancel() {
		this.canceled = true;
	}
}
