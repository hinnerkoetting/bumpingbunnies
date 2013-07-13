package de.oetting.bumpingbunnies.usecases.game.communication.messages.player;

import android.util.SparseArray;
import de.oetting.bumpingbunnies.communication.messageInterface.MessageReceiverTemplate;
import de.oetting.bumpingbunnies.usecases.game.android.input.network.NetworkInputService;
import de.oetting.bumpingbunnies.usecases.game.communication.NetworkToGameDispatcher;
import de.oetting.bumpingbunnies.usecases.game.communication.objects.MessageId;
import de.oetting.bumpingbunnies.usecases.game.model.PlayerState;

/**
 * Receives player states messages and dispatches them to the correct player.
 * 
 */
public class PlayerStateDispatcher extends MessageReceiverTemplate<PlayerState> {
	private final SparseArray<NetworkInputService> inputServices;

	public PlayerStateDispatcher(NetworkToGameDispatcher dispatcher) {
		super(dispatcher, MessageId.SEND_PLAYER_STATE, PlayerState.class);
		this.inputServices = new SparseArray<NetworkInputService>();
	}

	@Override
	public void onReceiveMessage(PlayerState message) {
		int playerId = message.getId();
		this.inputServices.get(playerId).newMessage(message);
	}

	public void addInputService(int playerId, NetworkInputService inputService) {
		this.inputServices.put(playerId, inputService);
	}
}
