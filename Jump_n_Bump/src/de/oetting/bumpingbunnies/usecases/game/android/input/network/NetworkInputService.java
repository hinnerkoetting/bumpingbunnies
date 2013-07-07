package de.oetting.bumpingbunnies.usecases.game.android.input.network;

import de.oetting.bumpingbunnies.usecases.game.android.input.InputService;
import de.oetting.bumpingbunnies.usecases.game.businesslogic.PlayerMovementController;
import de.oetting.bumpingbunnies.usecases.game.communication.MessageParser;
import de.oetting.bumpingbunnies.usecases.game.communication.NetworkListener;
import de.oetting.bumpingbunnies.usecases.game.model.ModelConstants;
import de.oetting.bumpingbunnies.usecases.game.model.Player;
import de.oetting.bumpingbunnies.usecases.game.model.PlayerState;

public class NetworkInputService implements InputService, NetworkListener {

	private PlayerState playerStateFromNetwork;
	private final Player player;
	private final MessageParser parser;
	private PlayerMovementController movementController;

	public NetworkInputService(PlayerMovementController movementController,
			Player player, MessageParser parser) {
		this.movementController = movementController;
		this.player = player;
		this.parser = parser;
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
		this.playerStateFromNetwork.copyContentTo(this.player.getState());
		if (this.playerStateFromNetwork.getAccelerationY() == ModelConstants.PLAYER_GRAVITY_WHILE_JUMPING) {
			this.movementController.tryMoveUp();
		} else {
			this.movementController.tryMoveDown();
		}
	}

	private boolean existsNewMessage() {
		return this.playerStateFromNetwork != null;
	}

	@Override
	public void newMessage(String message) {
		PlayerState state = this.parser
				.parseMessage(message, PlayerState.class);
		this.playerStateFromNetwork = state;
	}

}
