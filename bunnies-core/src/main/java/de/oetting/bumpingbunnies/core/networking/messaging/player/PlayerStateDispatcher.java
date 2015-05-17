package de.oetting.bumpingbunnies.core.networking.messaging.player;

import java.util.TreeMap;

import de.oetting.bumpingbunnies.core.network.MessageReceiverTemplate;
import de.oetting.bumpingbunnies.core.network.NetworkToGameDispatcher;
import de.oetting.bumpingbunnies.core.network.PlayerFromNetworkInput;
import de.oetting.bumpingbunnies.logger.Logger;
import de.oetting.bumpingbunnies.logger.LoggerFactory;
import de.oetting.bumpingbunnies.model.game.objects.PlayerState;
import de.oetting.bumpingbunnies.model.network.MessageId;

/**
 * Receives player states messages and dispatches them to the correct player
 * object. The message is only dispatched if there is no newer player state.
 * 
 */
public class PlayerStateDispatcher extends MessageReceiverTemplate<PlayerStateMessage> {
	private static final Logger LOGGER = LoggerFactory.getLogger(PlayerStateDispatcher.class);
	private final TreeMap<Integer, PlayerFromNetworkInput> inputServices;

	public PlayerStateDispatcher(NetworkToGameDispatcher dispatcher) {
		super(dispatcher, MessageId.PLAYER_POS, PlayerStateMessage.class);
		this.inputServices = new TreeMap<Integer, PlayerFromNetworkInput>();
	}

	@Override
	public void onReceiveMessage(PlayerStateMessage message) {
		PlayerState state = message.getPlayerState();
		int playerId = state.getId();
		PlayerFromNetworkInput playerInputService = this.inputServices.get(playerId);
		if (playerInputService == null) {
			// this is probably caused by a bug in android because for some
			// reason we also do receive messages we send from this device
			// if the socket is bound to the remote device this should not
			// happen
			LOGGER.debug("Received message for unknown player Ignore this for the time being. Player-id is: %d", playerId);
		} else {
			playerInputService.onReceiveNewMessage(message);
		}
	}

	public void addInputService(int playerId, PlayerFromNetworkInput inputService) {
		LOGGER.debug("Registering Player with id %d", playerId);
		this.inputServices.put(playerId, inputService);
	}

	public static class InputserviceDoesNotExist extends RuntimeException {
		public InputserviceDoesNotExist(int playerid) {
			super("Inputservice does not exist for playerid " + playerid);
		}
	}
}
