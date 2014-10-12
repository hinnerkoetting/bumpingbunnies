package de.oetting.bumpingbunnies.communication;

import de.oetting.bumpingbunnies.core.network.OpponentReceiverFactoryFactory;
import de.oetting.bumpingbunnies.core.networking.messaging.receiver.WlanOpponentTypeFactory;
import de.oetting.bumpingbunnies.core.networking.receive.NullOpponentReceiverFactory;
import de.oetting.bumpingbunnies.core.networking.receive.OpponentTypeFactory;
import de.oetting.bumpingbunnies.model.game.objects.OpponentType;
import de.oetting.bumpingbunnies.usecases.game.factories.communication.BluetoothOpponentTypeFactory;

public class AndroidOpponentTypeReceiveFactoryFactory implements OpponentReceiverFactoryFactory {

	@Override
	public OpponentTypeFactory createReceiveFactory(OpponentType type) {
		switch (type) {
		case AI: // fallthrough
		case LOCAL_PLAYER:// fallthrouh
		case INDIRECT:
			return new NullOpponentReceiverFactory();
		case BLUETOOTH:
			return new BluetoothOpponentTypeFactory();
		case WLAN:
			return new WlanOpponentTypeFactory();
		}
		throw new IllegalArgumentException("Unknown type: " + type);
	}

}
