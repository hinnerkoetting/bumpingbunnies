package de.oetting.bumpingbunnies.usecases.game.android.input.network;

import de.oetting.bumpingbunnies.usecases.game.android.input.InputService;
import de.oetting.bumpingbunnies.usecases.game.communication.messages.player.PlayerStateMessage;
import de.oetting.bumpingbunnies.usecases.game.model.Player;
import de.oetting.bumpingbunnies.usecases.game.model.PlayerState;

/**
 * This class receives new player states from a remote device. This inputservice directly manipulates the playerstate. This seems a more stable approach than
 * sending key- or touch-events over the network.
 * 
 * 
 */
public class PlayerFromNetworkInput implements InputService {

	private PlayerState playerStateFromNetwork;
	private final Player player;
	private long latestCounter;

	public PlayerFromNetworkInput(Player player) {
		this.player = player;
		this.latestCounter = -1;
	}

	@Override
	public void executeUserInput() {
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

	public void sendNewMessage(PlayerStateMessage message) {
		if (message.getCounter() > this.latestCounter) {
			this.playerStateFromNetwork = message.getPlayerState();
			this.latestCounter = message.getCounter();
		}
	}

}
