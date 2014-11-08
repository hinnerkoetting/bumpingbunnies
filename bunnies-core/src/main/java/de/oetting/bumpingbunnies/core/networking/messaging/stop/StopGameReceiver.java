package de.oetting.bumpingbunnies.core.networking.messaging.stop;

import de.oetting.bumpingbunnies.core.network.MessageReceiverTemplate;
import de.oetting.bumpingbunnies.core.network.NetworkToGameDispatcher;
import de.oetting.bumpingbunnies.model.network.MessageId;

public class StopGameReceiver extends MessageReceiverTemplate<String> {

	private final GameStopper gameStopper;

	public StopGameReceiver(NetworkToGameDispatcher dispatcher, GameStopper gameStopper) {
		super(dispatcher, MessageId.STOP_GAME, String.class);
		this.gameStopper = gameStopper;
	}

	@Override
	public void onReceiveMessage(String object) {
		gameStopper.gameStopped();
	}

}
