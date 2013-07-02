package de.oetting.bumpingbunnies.usecases.game.android.input.network;

import de.oetting.bumpingbunnies.logger.Logger;
import de.oetting.bumpingbunnies.logger.MyLog;
import de.oetting.bumpingbunnies.usecases.game.android.input.InputService;
import de.oetting.bumpingbunnies.usecases.game.communication.MessageParser;
import de.oetting.bumpingbunnies.usecases.game.communication.NetworkListener;
import de.oetting.bumpingbunnies.usecases.game.communication.NetworkReceiver;
import de.oetting.bumpingbunnies.usecases.game.model.Player;
import de.oetting.bumpingbunnies.usecases.game.model.PlayerState;

public class NetworkInputService implements InputService, NetworkListener {

	private static final MyLog LOGGER = Logger
			.getLogger(NetworkInputService.class);
	private PlayerState playerStateFromNetwork;
	private final Player player;
	private final NetworkReceiver receiverThread;
	private final MessageParser parser;

	public NetworkInputService(NetworkReceiver receiverThread, Player player,
			MessageParser parser) {
		this.receiverThread = receiverThread;
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
	public void destroy() {
		this.receiverThread.cancel();
	}

	@Override
	public void newMessage(String message) {
		PlayerState state = this.parser
				.parseMessage(message, PlayerState.class);
		this.playerStateFromNetwork = state;
	}

	@Override
	public void start() {
		this.receiverThread.start();
	}

}
