package de.oetting.bumpingbunnies.usecases.game.factories.communication;

import de.oetting.bumpingbunnies.core.networking.messaging.receiver.OpponentTypeReceiveFactory;
import de.oetting.bumpingbunnies.core.networking.receive.OpponentTypeFactory;

public class BluetoothOpponentTypeFactory implements OpponentTypeFactory {

	@Override
	public OpponentTypeReceiveFactory createReceiveFactory() {
		return new BluetoothOpponentTypeReceiveFactory();
	}

}
