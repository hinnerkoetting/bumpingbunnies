package de.oetting.bumpingbunnies.core.networking.receive;

import de.oetting.bumpingbunnies.core.networking.messaging.receiver.OpponentTypeReceiveFactory;

public interface OpponentTypeFactory {

	OpponentTypeReceiveFactory createReceiveFactory();

}
