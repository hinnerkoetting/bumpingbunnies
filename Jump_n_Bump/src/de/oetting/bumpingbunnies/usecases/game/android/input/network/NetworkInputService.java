package de.oetting.bumpingbunnies.usecases.game.android.input.network;

import de.oetting.bumpingbunnies.usecases.game.android.input.InputService;
import de.oetting.bumpingbunnies.usecases.game.communication.MessageParser;
import de.oetting.bumpingbunnies.usecases.game.communication.NetworkListener;
import de.oetting.bumpingbunnies.usecases.game.model.Player;
import de.oetting.bumpingbunnies.usecases.game.model.PlayerState;

public class NetworkInputService implements InputService, NetworkListener {

	private PlayerState playerStateFromNetwork;
	private final Player player;
	private final MessageParser parser;

	public NetworkInputService(Player player, MessageParser parser) {
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
