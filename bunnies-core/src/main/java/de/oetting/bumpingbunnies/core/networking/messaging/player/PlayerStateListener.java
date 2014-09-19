package de.oetting.bumpingbunnies.core.networking.messaging.player;

import com.google.gson.Gson;

import de.oetting.bumpingbunnies.core.networking.MessageParser;
import de.oetting.bumpingbunnies.core.networking.MessageReceiverTemplate;
import de.oetting.bumpingbunnies.core.networking.NetworkListener;
import de.oetting.bumpingbunnies.model.networking.JsonWrapper;

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
