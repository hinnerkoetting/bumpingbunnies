package de.oetting.bumpingbunnies.core.networking.receive;

import de.oetting.bumpingbunnies.core.networking.messaging.receiver.OpponentTypeReceiveFactory;

public class NullOpponentReceiverFactory implements OpponentTypeFactory {

	@Override
	public OpponentTypeReceiveFactory createReceiveFactory() {
		return new AiOpponentTypeReceiveFactory();
	}

}
