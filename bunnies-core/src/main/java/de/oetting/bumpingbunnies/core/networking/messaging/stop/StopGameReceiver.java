package de.oetting.bumpingbunnies.core.networking.messaging.stop;

import de.oetting.bumpingbunnies.core.networking.MessageReceiverTemplate;
import de.oetting.bumpingbunnies.core.networking.NetworkToGameDispatcher;
import de.oetting.bumpingbunnies.model.networking.MessageId;

public class StopGameReceiver extends MessageReceiverTemplate<String> {

	private final GameStopper stopper;

	public StopGameReceiver(NetworkToGameDispatcher dispatcher, GameStopper stopper) {
		super(dispatcher, MessageId.STOP_GAME, String.class);
		this.stopper = stopper;
	}

	@Override
	public void onReceiveMessage(String object) {
		this.stopper.stopGame();
	}

}
