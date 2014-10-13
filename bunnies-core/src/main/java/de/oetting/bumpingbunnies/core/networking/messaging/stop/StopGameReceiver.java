package de.oetting.bumpingbunnies.core.networking.messaging.stop;

import de.oetting.bumpingbunnies.core.network.MessageReceiverTemplate;
import de.oetting.bumpingbunnies.core.network.NetworkToGameDispatcher;
import de.oetting.bumpingbunnies.model.network.MessageId;

public class StopGameReceiver extends MessageReceiverTemplate<String> {

	private final OnThreadErrorCallback stopper;

	public StopGameReceiver(NetworkToGameDispatcher dispatcher, OnThreadErrorCallback stopper) {
		super(dispatcher, MessageId.STOP_GAME, String.class);
		this.stopper = stopper;
	}

	@Override
	public void onReceiveMessage(String object) {
		this.stopper.onThreadError();
	}

}
