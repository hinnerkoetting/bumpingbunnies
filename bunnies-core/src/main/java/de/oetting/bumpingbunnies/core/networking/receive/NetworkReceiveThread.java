package de.oetting.bumpingbunnies.core.networking.receive;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import de.oetting.bumpingbunnies.core.network.IncomingNetworkDispatcher;
import de.oetting.bumpingbunnies.core.network.MySocket;
import de.oetting.bumpingbunnies.core.network.NetworkToGameDispatcher;
import de.oetting.bumpingbunnies.core.networking.udp.UdpSocket.ReceiveFailure;
import de.oetting.bumpingbunnies.core.networking.wlan.socket.AbstractSocket.ReadFailed;
import de.oetting.bumpingbunnies.core.threads.BunniesThread;
import de.oetting.bumpingbunnies.core.threads.ThreadErrorCallback;
import de.oetting.bumpingbunnies.logger.Logger;
import de.oetting.bumpingbunnies.logger.LoggerFactory;
import de.oetting.bumpingbunnies.model.game.objects.Bunny;
import de.oetting.bumpingbunnies.model.network.JsonWrapper;

/**
 * Waits for incoming Messages. Each incoming message is forwarded to the
 * dispatcher where the appropriate handler should be called.
 * 
 */
public class NetworkReceiveThread extends BunniesThread implements NetworkReceiver {

	private static final Logger LOGGER = LoggerFactory.getLogger(NetworkReceiveThread.class);
	private final Gson gson;
	private final IncomingNetworkDispatcher networkDispatcher;
	private boolean canceled;
	private MySocket socket;

	public NetworkReceiveThread(Gson gson, IncomingNetworkDispatcher networkDispatcher, MySocket socket, ThreadErrorCallback errorCallback) {
		super("Network receive thread", errorCallback);
		this.gson = gson;
		this.networkDispatcher = networkDispatcher;
		this.socket = socket;
	}

	@Override
	protected void doRun() throws Exception {
		while (!this.canceled) {
			try {
				oneRun();
			} catch (ReadFailed e) {
				if (this.canceled) {
					return;
				} else {
					disconnectPlayer(e);
					canceled = true;
				}
			}
		}
	}

	private void disconnectPlayer(ReadFailed e) {
		LOGGER.warn("Read failed " + e.getMessage());
		networkDispatcher.playerWasDisconnected(socket.getConnectionIdentifier());
	}

	void oneRun() {
		try {
			String input = this.socket.blockingReceive();
			if (input != null) {
				if (!this.canceled) {
					dispatchMessage(input);
				}
			}
		} catch (ReceiveFailure e) {
			if (!canceled)
				throw e;
			else
				LOGGER.info("Error when reading on socket. But thread is already canceled...");
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
		} catch (JsonSyntaxException e) {
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

	@Override
	public boolean belongsToPlayer(Bunny player) {
		return player.getOpponent().equals(this.socket.getConnectionIdentifier());
	}

	@Override
	public boolean belongsToSocket(MySocket socket) {
		return this.socket.equals(socket);
	}

	@Override
	public void shutdown() {
		cancel();
		socket.close();
	}

	@Override
	public synchronized void start() {
		super.start();
	}
}
