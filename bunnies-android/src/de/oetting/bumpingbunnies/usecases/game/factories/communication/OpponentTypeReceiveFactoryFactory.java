package de.oetting.bumpingbunnies.usecases.game.factories.communication;

import de.oetting.bumpingbunnies.usecases.game.model.OpponentType;

public class OpponentTypeReceiveFactoryFactory {

	public OpponentTypeFactory createReceiveFactory(OpponentType type) {
		switch (type) {
		case AI:
			return new AiOpponentTypeFactory();
		case BLUETOOTH:
			return new BluetoothOpponentTypeFactory();
		case MY_PLAYER:
			return new MyPlayerOpponentTypeFactory();
		case WLAN:
			return createWlanFactory();
		}
		throw new IllegalArgumentException("Unknown type: " + type);
	}

	private OpponentTypeFactory createWlanFactory() {
		try {
			return (OpponentTypeFactory) Class.forName("de.oetting.bumpingbunnies.usecases.game.factories.WlanOpponentTypeFactory").newInstance();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

}
