package de.oetting.bumpingbunnies.usecases.game.android.input.network;

import de.oetting.bumpingbunnies.usecases.game.android.input.InputService;
import de.oetting.bumpingbunnies.usecases.game.businesslogic.PlayerMovementController;
import de.oetting.bumpingbunnies.usecases.game.model.Player;
import de.oetting.bumpingbunnies.usecases.game.model.PlayerState;

public class NetworkInputService implements InputService {

	private PlayerState playerStateFromNetwork;
	private final Player player;
	private PlayerMovementController movementController;

	public NetworkInputService(PlayerMovementController movementController,
			Player player) {
		this.movementController = movementController;
		this.player = player;
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
		// get local copy to avoid synch problems if this method is executed
		// while new message is sent
		PlayerState playerFromNetwork = this.playerStateFromNetwork;
		playerFromNetwork.copyContentTo(this.player.getState());
	}

	private boolean existsNewMessage() {
		return this.playerStateFromNetwork != null;
	}

	public void newMessage(PlayerState message) {
		this.playerStateFromNetwork = message;
	}

}
