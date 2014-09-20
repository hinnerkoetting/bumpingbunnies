package de.oetting.bumpingbunnies.usecases.game.factories.communication;

public class AiOpponentTypeFactory implements OpponentTypeFactory {

	@Override
	public OpponentTypeReceiveFactory createReceiveFactory() {
		return new AiOpponentTypeReceiveFactory();
	}

}
