package de.oetting.bumpingbunnies.usecases.game.communication;

import de.oetting.bumpingbunnies.usecases.game.communication.objects.JsonWrapper;
import de.oetting.bumpingbunnies.usecases.game.communication.objects.MessageId;

public class SimpleMessageConsts {

	public static final String MSG = "1";
	public static final MessageId ID = MessageId.SEND_PLAYER_STATE;
	public static final JsonWrapper WRAPPER = new JsonWrapper(ID, MSG);
	public static final String CONVERTED_MESSAGE = "{\"id\":\"SEND_PLAYER_STATE\",\"message\":\"\\\"1\\\"\"}";
	public static final String CONVERTED_MESSAGE2 = "{\"id\":\"SEND_PLAYER_STATE\",\"message\":\"1\"}";
}
