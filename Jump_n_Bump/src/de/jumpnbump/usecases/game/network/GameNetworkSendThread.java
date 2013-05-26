package de.jumpnbump.usecases.game.network;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;

import android.bluetooth.BluetoothSocket;
import android.content.Context;

import com.google.gson.Gson;

import de.jumpnbump.logger.Logger;
import de.jumpnbump.logger.MyLog;
import de.jumpnbump.usecases.game.model.Player;

public class GameNetworkSendThread extends Thread implements StateSender {

	private static final MyLog LOGGER = Logger
			.getLogger(GameNetworkSendThread.class);
	private Gson gson;
	private Writer writer;
	private String nextMessage = null;
	private boolean canceled;
	private final Context context;

	public GameNetworkSendThread(BluetoothSocket socket, Context context) {
		this.context = context;
		this.gson = new Gson();
		try {
			this.writer = new OutputStreamWriter(socket.getOutputStream(),
					NetworkConstants.ENCODING);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public void run() {
		while (!this.canceled) {
			try {
				handleMessageSending();
				sleep(5);
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		}
	}

	private void handleMessageSending() throws IOException {
		if (existsMessageToSend()) {
			LOGGER.debug("Sending message");
			sendMessage();
		}
	}

	private void sendMessage() throws IOException {
		String messageToSend = this.nextMessage;
		this.writer.write(messageToSend);
		this.writer.write('\n');
		this.writer.flush();
		this.nextMessage = null;
	}

	private boolean existsMessageToSend() {
		return this.nextMessage != null;
	}

	@Override
	public synchronized void sendPlayerCoordinates(Player player) {
		this.nextMessage = this.gson.toJson(player.getState());
	}

	@Override
	public void cancel() {
		this.canceled = true;
	}
}
