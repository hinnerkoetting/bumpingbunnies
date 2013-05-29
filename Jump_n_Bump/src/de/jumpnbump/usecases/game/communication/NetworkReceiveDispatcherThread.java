package de.jumpnbump.usecases.game.communication;

import java.io.BufferedReader;
import java.io.IOException;

import android.util.SparseArray;

import com.google.gson.Gson;

import de.jumpnbump.logger.Logger;
import de.jumpnbump.logger.MyLog;
import de.jumpnbump.usecases.game.communication.objects.JsonWrapper;
import de.jumpnbump.usecases.game.model.PlayerState;

public class NetworkReceiveDispatcherThread extends Thread implements
		InformationSupplier {

	private static final MyLog LOGGER = Logger
			.getLogger(NetworkReceiveDispatcherThread.class);
	private final BufferedReader reader;
	private final Gson gson;
	private SparseArray<NetworkListener> listeners;
	private NetworkListener singleListener;
	private boolean canceled;

	public NetworkReceiveDispatcherThread(BufferedReader reader, Gson gson) {
		this.reader = reader;
		this.gson = gson;
		this.listeners = new SparseArray<NetworkListener>();
	}

	@Override
	public void run() {
		while (!this.canceled) {
			try {
				oneRun();
			} catch (IOException e) {
				LOGGER.warn("IOException");
				break;
			}
		}
	}

	private void oneRun() throws IOException {
		String input = this.reader.readLine();
		JsonWrapper wrapper = convertToObject(input);
		dispatchMessage(wrapper);
	}

	private void dispatchMessage(JsonWrapper wrapper) {
		if (wrapper.getPlayerState() != null) {
			dispatchPlayerState(wrapper.getPlayerState());
		}
	}

	private synchronized void dispatchPlayerState(PlayerState playerState) {
		NetworkListener networkListener = this.listeners.get(playerState
				.getId());
		if (networkListener == null) {
			throw new IllegalStateException(
					"No Listener registered for player with id "
							+ playerState.getId());
		}
		networkListener.newMessage(playerState);
	}

	private JsonWrapper convertToObject(String input) {
		return this.gson.fromJson(input, JsonWrapper.class);
	}

	@Override
	public void cancel() {
		this.canceled = true;
	}

	@Override
	public void addObserver(int id, NetworkListener listener) {
		LOGGER.debug("Restering listener with id %d", id);
		this.listeners.put(id, listener);
	}

}