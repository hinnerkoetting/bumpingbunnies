package de.oetting.bumpingbunnies.usecases.game.communication.messages;

import de.oetting.bumpingbunnies.communication.messageInterface.MessageReceiverTemplate;
import de.oetting.bumpingbunnies.logger.Logger;
import de.oetting.bumpingbunnies.logger.LoggerFactory;
import de.oetting.bumpingbunnies.usecases.game.communication.MessageIds;
import de.oetting.bumpingbunnies.usecases.game.communication.NetworkToGameDispatcher;
import de.oetting.bumpingbunnies.usecases.game.model.messages.PlayerIsDead;

public class PlayerIsDeadReceiver extends MessageReceiverTemplate<PlayerIsDead> {

	private static final Logger LOGGER = LoggerFactory.getLogger(PlayerIsDeadReceiver.class);

	public PlayerIsDeadReceiver(NetworkToGameDispatcher dispatcher) {
		super(dispatcher, MessageIds.PLAYER_IS_DEAD_MESSAGE, PlayerIsDead.class);
	}

	@Override
	public void message(PlayerIsDead message) {
		LOGGER.info("received message");
	}

}
