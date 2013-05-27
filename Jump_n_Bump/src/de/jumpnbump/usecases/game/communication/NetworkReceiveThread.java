package de.jumpnbump.usecases.game.communication;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import android.bluetooth.BluetoothSocket;
import android.content.Context;

import com.google.gson.Gson;

import de.jumpnbump.logger.Logger;
import de.jumpnbump.logger.MyLog;
import de.jumpnbump.usecases.game.model.PlayerState;

public class NetworkReceiveThread extends Thread {
	private static final MyLog LOGGER = Logger
			.getLogger(NetworkReceiveThread.class);
	private Gson gson;
	private BufferedReader reader;
	private PlayerState latestPlayerCoordinates;
	private boolean canceled;
	private final Context context;

	public NetworkReceiveThread(BluetoothSocket socket, Context context) {
		this.context = context;
		this.gson = new Gson();
		this.latestPlayerCoordinates = new PlayerState(-1);
		try {
			this.reader = new BufferedReader(new InputStreamReader(
					socket.getInputStream(), NetworkConstants.ENCODING));
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public void run() {
		while (!this.canceled) {
			try {
				handleMessageReading();
			} catch (Exception e) {
				synchronized (this) {
					if (this.canceled) {
						return;
					}
				}
				throw new RuntimeException(e);
			}
		}
	}

	private void handleMessageReading() throws IOException {
		String input = this.reader.readLine();
		readMessage(input);
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

	public synchronized void cancel() {
		this.canceled = true;
	}
}
