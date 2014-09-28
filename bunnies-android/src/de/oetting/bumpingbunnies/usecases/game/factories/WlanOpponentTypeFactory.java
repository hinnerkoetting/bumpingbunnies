package de.oetting.bumpingbunnies.usecases.game.factories;

import de.oetting.bumpingbunnies.core.networking.messaging.receiver.OpponentTypeReceiveFactory;
import de.oetting.bumpingbunnies.core.networking.messaging.receiver.WlanOpponentTypeReceiveFactory;
import de.oetting.bumpingbunnies.usecases.game.factories.communication.OpponentTypeFactory;

public class WlanOpponentTypeFactory implements OpponentTypeFactory {

	@Override
	public OpponentTypeReceiveFactory createReceiveFactory() {
		return new WlanOpponentTypeReceiveFactory();
	}

}
