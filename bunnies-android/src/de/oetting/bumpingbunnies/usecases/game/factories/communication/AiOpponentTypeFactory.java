package de.oetting.bumpingbunnies.usecases.game.factories.communication;

import de.oetting.bumpingbunnies.core.networking.messaging.receiver.OpponentTypeReceiveFactory;

public class AiOpponentTypeFactory implements OpponentTypeFactory {

	@Override
	public OpponentTypeReceiveFactory createReceiveFactory() {
		return new AiOpponentTypeReceiveFactory();
	}

}
