package de.oetting.bumpingbunnies.core.networking.messaging.player;

import com.google.gson.Gson;

import de.oetting.bumpingbunnies.core.network.MessageParser;
import de.oetting.bumpingbunnies.core.network.MessageReceiverTemplate;
import de.oetting.bumpingbunnies.core.network.NetworkListener;
import de.oetting.bumpingbunnies.model.network.JsonWrapper;

public class PlayerStateListener implements NetworkListener {

	private final MessageParser parser;
	private final MessageReceiverTemplate<PlayerStateMessage> receiver;

	public PlayerStateListener(MessageReceiverTemplate<PlayerStateMessage> receiver) {
		super();
		this.receiver = receiver;
		this.parser = new MessageParser(new Gson());
	}

	@Override
	public void newMessage(JsonWrapper wrapper) {
		try {
			String message = wrapper.getMessage();
			PlayerStateMessage playerState = this.parser.parseMessage(message, PlayerStateMessage.class);
			this.receiver.onReceiveMessage(playerState);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

}
