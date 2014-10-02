package de.oetting.bumpingbunnies.core.network;

import de.oetting.bumpingbunnies.core.input.OpponentInput;
import de.oetting.bumpingbunnies.core.networking.messaging.player.PlayerStateMessage;
import de.oetting.bumpingbunnies.logger.Logger;
import de.oetting.bumpingbunnies.logger.LoggerFactory;
import de.oetting.bumpingbunnies.model.game.objects.Player;
import de.oetting.bumpingbunnies.model.game.objects.PlayerState;

/**
 * This class receives new player states from a remote device. This inputservice
 * directly manipulates the playerstate. This seems a more stable approach than
 * sending key- or touch-events over the network.
 * 
 * 
 */
public class PlayerFromNetworkInput implements OpponentInput {

	private static final Logger LOGGER = LoggerFactory.getLogger(PlayerFromNetworkInput.class);
	private PlayerState playerStateFromNetwork;
	private final Player player;
	private long latestCounter;

	public PlayerFromNetworkInput(Player player) {
		this.player = player;
		this.latestCounter = -1;
	}

	@Override
	public synchronized void executeNextStep() {
		if (existsNewMessage()) {
			copyStateFromNetwork();
			deleteLatestMessage();
		}
	}

	private void deleteLatestMessage() {
		this.playerStateFromNetwork = null;
	}

	private void copyStateFromNetwork() {
		PlayerState playerFromNetwork = this.playerStateFromNetwork;
		this.player.applyState(playerFromNetwork);
	}

	boolean existsNewMessage() {
		return this.playerStateFromNetwork != null;
	}

	public synchronized void sendNewMessage(PlayerStateMessage message) {
		if (message.getCounter() > this.latestCounter) {
			this.playerStateFromNetwork = message.getPlayerState();
			this.latestCounter = message.getCounter();
		} else {
			LOGGER.info("throwing away message");
		}
	}

}
