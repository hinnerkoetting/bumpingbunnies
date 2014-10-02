package de.oetting.bumpingbunnies.communication;

import de.oetting.bumpingbunnies.core.networking.OpponentReceiverFactoryFactory;
import de.oetting.bumpingbunnies.core.networking.messaging.receiver.WlanOpponentTypeFactory;
import de.oetting.bumpingbunnies.core.networking.receive.AiOpponentTypeFactory;
import de.oetting.bumpingbunnies.core.networking.receive.MyPlayerOpponentTypeFactory;
import de.oetting.bumpingbunnies.core.networking.receive.OpponentTypeFactory;
import de.oetting.bumpingbunnies.usecases.game.factories.communication.BluetoothOpponentTypeFactory;
import de.oetting.bumpingbunnies.usecases.game.model.OpponentType;

public class AndroidOpponentTypeReceiveFactoryFactory implements OpponentReceiverFactoryFactory {

	@Override
	public OpponentTypeFactory createReceiveFactory(OpponentType type) {
		switch (type) {
		case AI:
			return new AiOpponentTypeFactory();
		case BLUETOOTH:
			return new BluetoothOpponentTypeFactory();
		case MY_PLAYER:
			return new MyPlayerOpponentTypeFactory();
		case WLAN:
			return new WlanOpponentTypeFactory();
		}
		throw new IllegalArgumentException("Unknown type: " + type);
	}

}
