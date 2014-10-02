package de.oetting.bumpingbunnies.core.networking.receive;

import com.google.gson.Gson;

import de.oetting.bumpingbunnies.core.network.IncomingNetworkDispatcher;
import de.oetting.bumpingbunnies.core.network.MySocket;
import de.oetting.bumpingbunnies.core.network.NetworkToGameDispatcher;
import de.oetting.bumpingbunnies.core.networking.wlan.socket.AbstractSocket.ReadFailed;
import de.oetting.bumpingbunnies.logger.Logger;
import de.oetting.bumpingbunnies.logger.LoggerFactory;
import de.oetting.bumpingbunnies.model.game.objects.Player;
import de.oetting.bumpingbunnies.model.network.JsonWrapper;

/**
 * Waits for incoming Messages. Each incoming message is forwarded to the dispatcher where the appropriate handler should be called.
 * 
 */
public class NetworkReceiveThread extends Thread implements NetworkReceiver {

	private static final Logger LOGGER = LoggerFactory.getLogger(NetworkReceiveThread.class);
	private final Gson gson;
	private final IncomingNetworkDispatcher networkDispatcher;
	private boolean canceled;
	private MySocket socket;

	public NetworkReceiveThread(Gson gson, IncomingNetworkDispatcher networkDispatcher, MySocket socket) {
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
			} catch (ReadFailed e) {
				if (this.canceled) {
					return;
				} else {
					throw e;
				}
			}
		}
	}

	void oneRun() {
		String input = this.socket.blockingReceive();
		if (input != null) {
			if (!this.canceled) {
				dispatchMessage(input);
			}
		}
	}

	private void dispatchMessage(String input) {
		JsonWrapper wrapper = convertToObject(input);
		if (wrapper == null) {
			throw new ContentNotConvertedToWrapper(input);
		}
		dispatchMessage(wrapper);
	}

	private void dispatchMessage(JsonWrapper wrapper) {
		this.networkDispatcher.dispatchMessage(wrapper);
	}

	private JsonWrapper convertToObject(String input) {
		try {
			return this.gson.fromJson(input, JsonWrapper.class);
		} catch (Exception e) {
			LOGGER.error("Message was %s", input);
			throw new JsonConvertionException(e);
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

	public static class InputIsNullException extends RuntimeException {
	}

	public static class ContentNotConvertedToWrapper extends RuntimeException {
		public ContentNotConvertedToWrapper(String content) {
			super(content);
		}
	}

	public static class JsonConvertionException extends RuntimeException {
		public JsonConvertionException(Exception e) {
			super(e);
		}
	}

	public boolean belongsToPlayer(Player player) {
		return player.getOpponent().equals(this.socket.getOwner());
	}
}
