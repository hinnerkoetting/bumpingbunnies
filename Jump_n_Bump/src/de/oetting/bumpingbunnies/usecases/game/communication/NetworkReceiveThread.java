package de.oetting.bumpingbunnies.usecases.game.communication;

import java.io.IOException;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import de.oetting.bumpingbunnies.communication.MySocket;
import de.oetting.bumpingbunnies.logger.Logger;
import de.oetting.bumpingbunnies.logger.LoggerFactory;
import de.oetting.bumpingbunnies.usecases.game.communication.objects.JsonWrapper;

public class NetworkReceiveThread extends Thread implements NetworkReceiver {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(NetworkReceiveThread.class);
	private final Gson gson;
	private final IncomingNetworkDispatcher networkDispatcher;
	private boolean canceled;
	private MySocket socket;

	public NetworkReceiveThread(Gson gson,
			IncomingNetworkDispatcher networkDispatcher, MySocket socket) {
		super("Network receive thread");
		this.gson = gson;
		this.networkDispatcher = networkDispatcher;
		this.socket = socket;
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

	void oneRun() throws IOException {
		String input = this.socket.blockingReceive();
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
		try {
			return this.gson.fromJson(input, JsonWrapper.class);
		} catch (JsonSyntaxException e) {
			LOGGER.error(e.getMessage());
			throw e;
		}
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
