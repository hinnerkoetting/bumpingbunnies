package de.jumpnbump.usecases.game.network;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import android.bluetooth.BluetoothSocket;

import com.google.gson.Gson;

import de.jumpnbump.logger.Logger;
import de.jumpnbump.logger.MyLog;
import de.jumpnbump.usecases.game.model.PlayerState;

public class GameNetworkReceiveThread extends Thread {
	private static final MyLog LOGGER = Logger
			.getLogger(GameNetworkReceiveThread.class);
	private Gson gson;
	private InputStream is;
	private BufferedReader reader;
	private PlayerState latestPlayerCoordinates;
	private byte[] lengthBuffer;

	public GameNetworkReceiveThread(BluetoothSocket socket) {
		this.lengthBuffer = new byte[4];
		this.gson = new Gson();
		this.latestPlayerCoordinates = new PlayerState();
		try {
			this.is = socket.getInputStream();
			this.reader = new BufferedReader(new InputStreamReader(this.is));
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public void run() {
		char[] buffer = new char[2048];
		while (true) {
			try {
				handleMessageReading(buffer);
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		}
	}

	private void handleMessageReading(char[] buffer) throws IOException {
		// this.is.read(this.lengthBuffer);
		// int numbercharacters = byteArrayToInt(this.lengthBuffer);
		// LOGGER.info("Number of characters fetched %d", numbercharacters);
		String input = this.reader.readLine();
		// if (numbercharacters > buffer.length) {
		// throw new IllegalArgumentException(
		// "Message too big, need to implement this");
		// }
		// if (numbercharacters > 0) {
		readMessage(input);
		// }
	}

	public static int byteArrayToInt(byte[] array) {
		int b0 = array[0] << 24;
		int b1 = array[1] << 16;
		int b2 = array[2] << 8;
		int b3 = array[3];
		return b0 + b1 + b2 + b3;
	}

	private void readMessage(String input) {
		PlayerState fromJson = this.gson.fromJson(input, PlayerState.class);
		LOGGER.debug("read message");
		setPlayerCoordinatesFromNetwork(fromJson);
	}

	private void setPlayerCoordinatesFromNetwork(PlayerState fromJson) {
		this.latestPlayerCoordinates = fromJson;
		LOGGER.debug(this.latestPlayerCoordinates.toString());
	}

	public PlayerState getLatestCoordinates() {
		return this.latestPlayerCoordinates;
	}

}
