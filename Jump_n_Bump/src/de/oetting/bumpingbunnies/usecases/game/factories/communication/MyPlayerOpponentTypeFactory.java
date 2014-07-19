package de.oetting.bumpingbunnies.usecases.game.factories.communication;


public class MyPlayerOpponentTypeFactory implements OpponentTypeFactory {

	@Override
	public OpponentTypeReceiveFactory createReceiveFactory() {
		return new MyPlayerOpponentTypeReceiveFactory();
	}

	@Override
	public OpponentTypeSendFactory createSendFactory() {
		return new MyPlayerOpponentTypeSendFactory();
	}

}
