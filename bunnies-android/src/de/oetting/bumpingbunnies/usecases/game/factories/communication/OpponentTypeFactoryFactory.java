package de.oetting.bumpingbunnies.usecases.game.factories.communication;

import de.oetting.bumpingbunnies.usecases.game.factories.WlanOpponentTypeFactory;
import de.oetting.bumpingbunnies.usecases.game.model.OpponentType;

public class OpponentTypeFactoryFactory {

	public OpponentTypeFactory createFactory(OpponentType type) {
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
