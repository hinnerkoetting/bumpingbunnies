package de.oetting.bumpingbunnies.usecases.game.model;

import de.oetting.bumpingbunnies.usecases.game.factories.WlanOpponentTypeFactory;
import de.oetting.bumpingbunnies.usecases.game.factories.communication.AiOpponentTypeFactory;
import de.oetting.bumpingbunnies.usecases.game.factories.communication.BluetoothOpponentTypeFactory;
import de.oetting.bumpingbunnies.usecases.game.factories.communication.MyPlayerOpponentTypeFactory;
import de.oetting.bumpingbunnies.usecases.game.factories.communication.OpponentTypeFactory;

public enum OpponentType {
	MY_PLAYER(new MyPlayerOpponentTypeFactory()), AI(new AiOpponentTypeFactory()), WLAN(new WlanOpponentTypeFactory()), BLUETOOTH(
			new BluetoothOpponentTypeFactory());

	private OpponentTypeFactory factory;

	private OpponentType(OpponentTypeFactory factory) {
		this.factory = factory;
	}

	public OpponentTypeFactory getFactory() {
		return this.factory;
	}
}