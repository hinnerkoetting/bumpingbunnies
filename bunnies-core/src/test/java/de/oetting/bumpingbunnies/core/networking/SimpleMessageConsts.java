package de.oetting.bumpingbunnies.core.networking;

import de.oetting.bumpingbunnies.model.network.JsonWrapper;
import de.oetting.bumpingbunnies.model.network.MessageId;

public class SimpleMessageConsts {

	public static final String MSG = "1";
	public static final MessageId ID = MessageId.PLAYER_POS;
	public static final JsonWrapper WRAPPER = JsonWrapper.create(ID, MSG);
}
