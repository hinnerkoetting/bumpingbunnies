package de.oetting.bumpingbunnies.core.networking;

import de.oetting.bumpingbunnies.model.networking.JsonWrapper;
import de.oetting.bumpingbunnies.model.networking.MessageId;

public class SimpleMessageConsts {

	public static final String MSG = "1";
	public static final MessageId ID = MessageId.SEND_PLAYER_STATE;
	public static final JsonWrapper WRAPPER = JsonWrapper.create(ID, MSG);
	public static final String CONVERTED_MESSAGE = "{\"id\":\"SEND_PLAYER_STATE\",\"message\":\"\\\"1\\\"\"}";
	public static final String CONVERTED_MESSAGE2 = "{\"id\":\"SEND_PLAYER_STATE\",\"message\":\"1\"}";
}