package de.oetting.bumpingbunnies.usecases.game.android.input.network;

import de.oetting.bumpingbunnies.usecases.game.android.input.InputService;
import de.oetting.bumpingbunnies.usecases.game.model.Player;
import de.oetting.bumpingbunnies.usecases.game.model.PlayerState;

public class NetworkInputService implements InputService {

	private PlayerState playerStateFromNetwork;
	private final Player player;

	public NetworkInputService(
			Player player) {
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
		PlayerState playerFromNetwork = this.playerStateFromNetwork;
		this.player.applyState(playerFromNetwork);
	}

	private boolean existsNewMessage() {
		return this.playerStateFromNetwork != null;
	}

	public void newMessage(PlayerState message) {
		this.playerStateFromNetwork = message;
	}

}
