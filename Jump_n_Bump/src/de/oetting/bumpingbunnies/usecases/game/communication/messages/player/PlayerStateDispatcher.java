package de.oetting.bumpingbunnies.usecases.game.communication.messages.player;

import java.util.TreeMap;

import de.oetting.bumpingbunnies.communication.messageInterface.MessageReceiverTemplate;
import de.oetting.bumpingbunnies.logger.Logger;
import de.oetting.bumpingbunnies.logger.LoggerFactory;
import de.oetting.bumpingbunnies.usecases.game.android.input.network.PlayerFromNetworkInput;
import de.oetting.bumpingbunnies.usecases.game.communication.NetworkToGameDispatcher;
import de.oetting.bumpingbunnies.usecases.game.communication.objects.MessageId;
import de.oetting.bumpingbunnies.usecases.game.model.PlayerState;

/**
 * Receives player states messages and dispatches them to the correct player object. The message is only dispatched if there is no newer player state.
 * 
 */
public class PlayerStateDispatcher extends MessageReceiverTemplate<PlayerStateMessage> {
	private static final Logger LOGGER = LoggerFactory.getLogger(PlayerStateDispatcher.class);
	private final TreeMap<Integer, PlayerFromNetworkInput> inputServices;

	public PlayerStateDispatcher(NetworkToGameDispatcher dispatcher) {
		super(dispatcher, MessageId.SEND_PLAYER_STATE, PlayerStateMessage.class);
		this.inputServices = new TreeMap<Integer, PlayerFromNetworkInput>();
	}

	@Override
	public void onReceiveMessage(PlayerStateMessage message) {
		PlayerState state = message.getPlayerState();
		int playerId = state.getId();
		PlayerFromNetworkInput playerInputService = this.inputServices.get(playerId);
		if (playerInputService == null) {
			LOGGER.warn("Received message for unknown player Ignore this for the time being. Player-id is: %d", playerId);
		} else {
			playerInputService.sendNewMessage(message);
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
