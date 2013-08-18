package de.oetting.bumpingbunnies.usecases.game.communication.messages.player;

import android.util.SparseArray;
import de.oetting.bumpingbunnies.communication.messageInterface.MessageReceiverTemplate;
import de.oetting.bumpingbunnies.logger.Logger;
import de.oetting.bumpingbunnies.logger.LoggerFactory;
import de.oetting.bumpingbunnies.usecases.game.android.input.network.NetworkInputService;
import de.oetting.bumpingbunnies.usecases.game.communication.NetworkToGameDispatcher;
import de.oetting.bumpingbunnies.usecases.game.communication.objects.MessageId;
import de.oetting.bumpingbunnies.usecases.game.model.PlayerState;

/**
 * Receives player states messages and dispatches them to the correct player.
 * 
 */
public class PlayerStateDispatcher extends MessageReceiverTemplate<PlayerState> {
	private static final Logger LOGGER = LoggerFactory.getLogger(PlayerStateDispatcher.class);
	private final SparseArray<NetworkInputService> inputServices;

	public PlayerStateDispatcher(NetworkToGameDispatcher dispatcher) {
		super(dispatcher, MessageId.SEND_PLAYER_STATE, PlayerState.class);
		this.inputServices = new SparseArray<NetworkInputService>();
	}

	@Override
	public void onReceiveMessage(PlayerState message) {
		int playerId = message.getId();
		NetworkInputService playerInputService = this.inputServices.get(playerId);
		if (playerInputService == null) {
			LOGGER.warn("Received message for unknown player Ignore this for the time being");
		} else {
			playerInputService.newMessage(message);
		}
	}

	public void addInputService(int playerId, NetworkInputService inputService) {
		this.inputServices.put(playerId, inputService);
	}

	public static class InputserviceDoesNotExist extends RuntimeException {
		public InputserviceDoesNotExist(int playerid) {
			super("Inputservice does not exist for playerid " + playerid);
		}
	}
}
