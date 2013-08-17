package de.oetting.bumpingbunnies.usecases.game.communication;

import com.google.gson.Gson;

import de.oetting.bumpingbunnies.communication.MySocket;
import de.oetting.bumpingbunnies.communication.messageInterface.NetworkSender;
import de.oetting.bumpingbunnies.logger.Logger;
import de.oetting.bumpingbunnies.logger.LoggerFactory;
import de.oetting.bumpingbunnies.usecases.game.communication.objects.JsonWrapper;

public class SimpleNetworkSender implements NetworkSender {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(SimpleNetworkSender.class);
	private final Gson gson;
	private final MySocket socket;

	public SimpleNetworkSender(Gson gson, MySocket socket) {
		super();
		this.gson = gson;
		this.socket = socket;
	}

	@Override
	public void sendMessage(JsonWrapper message) {
		try {
			LOGGER.debug("sending message %s", message.getMessage());
			String json = this.gson.toJson(message);
			this.socket.sendMessage(json);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

}
