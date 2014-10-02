package de.oetting.bumpingbunnies.core.networking.factory;

import de.oetting.bumpingbunnies.core.networking.messaging.WlanOpponentTypeSendFactory;
import de.oetting.bumpingbunnies.model.game.objects.OpponentType;

public class OpponentTypeSendFactoryFactory {

	public OpponentTypeSendFactory createSendFactory(OpponentType type) {
		switch (type) {
		case AI:
			return new AiOpponentTypeSendFactory();
		case BLUETOOTH:
			return createBluetooth();
		case MY_PLAYER:
			return new MyPlayerOpponentTypeSendFactory();
		case WLAN:
			return new WlanOpponentTypeSendFactory();
		}
		throw new IllegalArgumentException("Unknown type: " + type);
	}

	private OpponentTypeSendFactory createBluetooth() {
		try {
			return (OpponentTypeSendFactory) Class.forName("de.oetting.bumpingbunnies.usecases.game.factories.communication.BluetoothOpponentTypeSendFactory")
					.newInstance();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}
