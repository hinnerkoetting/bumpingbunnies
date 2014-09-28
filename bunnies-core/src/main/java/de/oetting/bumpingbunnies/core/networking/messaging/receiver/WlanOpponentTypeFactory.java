package de.oetting.bumpingbunnies.core.networking.messaging.receiver;

import de.oetting.bumpingbunnies.core.networking.receive.OpponentTypeFactory;

public class WlanOpponentTypeFactory implements OpponentTypeFactory {

	@Override
	public OpponentTypeReceiveFactory createReceiveFactory() {
		return new WlanOpponentTypeReceiveFactory();
	}

}
