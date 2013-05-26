package de.jumpnbump.usecases.game.network;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.Writer;

import android.bluetooth.BluetoothSocket;

import com.google.gson.Gson;

import de.jumpnbump.logger.Logger;
import de.jumpnbump.logger.MyLog;
import de.jumpnbump.usecases.game.model.Player;
import de.jumpnbump.usecases.game.model.PlayerState;

public class GameNetworkSendThread extends Thread {

	private static final MyLog LOGGER = Logger
			.getLogger(GameNetworkSendThread.class);
	private Gson gson;
	private Writer writer;
	private String nextMessage = null;
	private PlayerState latestPlayerCoordinates;
	private byte[] lengthBytes;
	private OutputStream os;

	public GameNetworkSendThread(BluetoothSocket socket) {
		this.gson = new Gson();
		this.lengthBytes = new byte[4];
		this.latestPlayerCoordinates = new PlayerState();
		try {
			this.os = socket.getOutputStream();
			this.writer = new PrintWriter(this.os);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public void run() {
		while (true) {
			try {
				handleMessageSending();
				sleep(1);
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
		intToByteArray(this.lengthBytes, messageToSend.length());
		LOGGER.info("sending number characters " + messageToSend.length());
		// this.os.write(this.lengthBytes);
		this.writer.write(messageToSend);
		this.writer.write('\n');
		this.writer.flush();
		this.nextMessage = null;
	}

	public static void intToByteArray(byte[] array, int value) {
		array[0] = (byte) (value >>> 24);
		array[1] = (byte) (value >>> 16);
		array[2] = (byte) (value >>> 8);
		array[3] = (byte) (value);
	}

	private boolean existsMessageToSend() {
		return this.nextMessage != null;
	}

	public synchronized void sendPlayerCoordinates(Player player) {
		this.nextMessage = this.gson.toJson(player.getState());
	}
}
