package de.oetting.bumpingbunnies.usecases.game.communication;

import java.io.BufferedReader;
import java.io.IOException;

import com.google.gson.Gson;

import de.oetting.bumpingbunnies.logger.Logger;
import de.oetting.bumpingbunnies.logger.MyLog;
import de.oetting.bumpingbunnies.usecases.game.communication.objects.JsonWrapper;

public class NetworkReceiveThread extends Thread implements
		InformationSupplier {

	private static final MyLog LOGGER = Logger
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
		JsonWrapper wrapper = convertToObject(input);
		if (wrapper == null) {
			LOGGER.error("Wrapper null. Input was %s", input);
		}
		dispatchMessage(wrapper);
	}

	private void dispatchMessage(JsonWrapper wrapper) {
		if (wrapper.getPlayerState() != null) {
			this.networkDispatcher
					.dispatchPlayerState(wrapper.getPlayerState());
		}
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
