package de.oetting.bumpingbunnies.usecases.game.factories.communication;

import de.oetting.bumpingbunnies.core.networking.messaging.receiver.OpponentTypeReceiveFactory;

public interface OpponentTypeFactory {

	OpponentTypeReceiveFactory createReceiveFactory();

}
