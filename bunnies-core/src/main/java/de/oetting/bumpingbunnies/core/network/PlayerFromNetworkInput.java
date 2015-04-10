package de.oetting.bumpingbunnies.core.network;

import java.util.HashMap;
import java.util.Map;

import de.oetting.bumpingbunnies.core.input.OpponentInput;
import de.oetting.bumpingbunnies.core.networking.messaging.player.PlayerStateMessage;
import de.oetting.bumpingbunnies.logger.Logger;
import de.oetting.bumpingbunnies.logger.LoggerFactory;
import de.oetting.bumpingbunnies.model.game.objects.Bunny;
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
	private final Bunny player;
	private Map<Integer, Long> latestCounterForPlayer = new HashMap<Integer, Long>();

	public PlayerFromNetworkInput(Bunny player) {
		this.player = player;
	}

	@Override
	public synchronized void executeNextStep(long deltaSinceLastLast) {
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

	public synchronized void onReceiveNewMessage(PlayerStateMessage message) {
		if (messageIsNewerThenPrevious(message)) {
			this.playerStateFromNetwork = message.getPlayerState();
			latestCounterForPlayer.put(message.getPlayerState().getId(), message.getCounter());
		} else {
			LOGGER.info("throwing away message for player %d. Current counter is %d", message.getPlayerState().getId(),
					latestCounterForPlayer.get(message.getPlayerState().getId()));
		}
	}

	private boolean messageIsNewerThenPrevious(PlayerStateMessage message) {
		int playerId = message.getPlayerState().getId();
		assertEntryExists(playerId);
		return message.getCounter() > latestCounterForPlayer.get(playerId);
	}

	private void assertEntryExists(int playerId) {
		if (!latestCounterForPlayer.containsKey(playerId))
			latestCounterForPlayer.put(playerId, 0L);
	}
}
