package de.oetting.bumpingbunnies.usecases.game.communication.messages.playerIsDead;

import de.oetting.bumpingbunnies.communication.messageInterface.MessageReceiverTemplate;
import de.oetting.bumpingbunnies.logger.Logger;
import de.oetting.bumpingbunnies.logger.LoggerFactory;
import de.oetting.bumpingbunnies.usecases.game.communication.MessageIds;
import de.oetting.bumpingbunnies.usecases.game.communication.NetworkToGameDispatcher;

public class PlayerIsDeadReceiver extends MessageReceiverTemplate<PlayerIsDead> {

	private static final Logger LOGGER = LoggerFactory.getLogger(PlayerIsDeadReceiver.class);

	public PlayerIsDeadReceiver(NetworkToGameDispatcher dispatcher) {
		super(dispatcher, MessageIds.PLAYER_IS_DEAD_MESSAGE, PlayerIsDead.class);
	}

	@Override
	public void onReceiveMessage(PlayerIsDead object) {
		LOGGER.info("received message");
	}

}
