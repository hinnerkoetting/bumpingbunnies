package de.oetting.bumpingbunnies.usecases.game.communication;

import java.io.BufferedReader;
import java.io.IOException;

import com.google.gson.Gson;

import de.oetting.bumpingbunnies.logger.LoggerFactory;
import de.oetting.bumpingbunnies.logger.Logger;
import de.oetting.bumpingbunnies.usecases.game.communication.objects.JsonWrapper;

public class NetworkReceiveThread extends Thread implements NetworkReceiver {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(NetworkReceiveThread.class);
	private final BufferedReader reader;
	private final Gson gson;
	private final IncomingNetworkDispatcher networkDispatcher;
	private boolean canceled;

	public NetworkReceiveThread(BufferedReader reader, Gson gson,
			IncomingNetworkDispatcher networkDispatcher) {
		super("Network receive thread");
		this.reader = reader;
		this.gson = gson;
		this.networkDispatcher = networkDispatcher;
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
		if (input == null) {
			LOGGER.warn("Input was null. Continuing...");
		} else {
			if (!this.canceled) {
				dispatchMessage(input);
			}
		}
	}

	private void dispatchMessage(String input) {
		JsonWrapper wrapper = convertToObject(input);
		if (wrapper == null) {
			LOGGER.error("Wrapper null. Input was %s", input);
		}
		dispatchMessage(wrapper);
	}

	private void dispatchMessage(JsonWrapper wrapper) {
		this.networkDispatcher.dispatchPlayerState(wrapper);
	}

	private JsonWrapper convertToObject(String input) {
		return this.gson.fromJson(input, JsonWrapper.class);
	}

	@Override
	public void cancel() {
		this.canceled = true;
	}

	@Override
	public NetworkToGameDispatcher getGameDispatcher() {
		return this.networkDispatcher.getNetworkToGameDispatcher();
	}

}
