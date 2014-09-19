package de.oetting.bumpingbunnies.usecases.game.factories.communication;


public interface OpponentTypeFactory {

	OpponentTypeReceiveFactory createReceiveFactory();

	OpponentTypeSendFactory createSendFactory();

}
