package de.oetting.bumpingbunnies.usecases.game.communication.messages.player;

import com.google.gson.Gson;

import de.oetting.bumpingbunnies.communication.messageInterface.MessageReceiverTemplate;
import de.oetting.bumpingbunnies.usecases.game.communication.MessageParser;
import de.oetting.bumpingbunnies.usecases.game.communication.NetworkListener;
import de.oetting.bumpingbunnies.usecases.game.communication.objects.JsonWrapper;

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
