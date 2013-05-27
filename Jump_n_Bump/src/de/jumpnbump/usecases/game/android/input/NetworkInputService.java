package de.jumpnbump.usecases.game.android.input;

import de.jumpnbump.logger.Logger;
import de.jumpnbump.logger.MyLog;
import de.jumpnbump.usecases.game.communication.InformationSupplier;
import de.jumpnbump.usecases.game.communication.NetworkListener;
import de.jumpnbump.usecases.game.model.Player;
import de.jumpnbump.usecases.game.model.PlayerState;

public class NetworkInputService implements InputService, NetworkListener {

	private static final MyLog LOGGER = Logger
			.getLogger(NetworkInputService.class);
	private PlayerState playerStateFromNetwork;
	private final Player player;
	private InformationSupplier supplier;

	public NetworkInputService(Player player) {
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
		this.playerStateFromNetwork.copyContentTo(this.player.getState());
	}

	private boolean existsNewMessage() {
		return this.playerStateFromNetwork != null;
	}

	@Override
	public void destroy() {
	}

	@Override
	public void newMessage(Object message) {
		this.playerStateFromNetwork = (PlayerState) message;
	}

}
