package de.oetting.bumpingbunnies.pc.game;

import de.oetting.bumpingbunnies.core.network.OpponentReceiverFactoryFactory;
import de.oetting.bumpingbunnies.core.networking.messaging.receiver.WlanOpponentTypeFactory;
import de.oetting.bumpingbunnies.core.networking.receive.AiOpponentTypeFactory;
import de.oetting.bumpingbunnies.core.networking.receive.MyPlayerOpponentTypeFactory;
import de.oetting.bumpingbunnies.core.networking.receive.OpponentTypeFactory;
import de.oetting.bumpingbunnies.model.game.objects.OpponentType;

public class PcOpponentReceiverFactoryFactory implements OpponentReceiverFactoryFactory {

	@Override
	public OpponentTypeFactory createReceiveFactory(OpponentType type) {
		switch (type) {
		case AI:
			return new AiOpponentTypeFactory();
		case BLUETOOTH:
			throw new IllegalArgumentException("Bluetooth for pc is not allowed.");
		case LOCAL_PLAYER:
			return new MyPlayerOpponentTypeFactory();
		case WLAN:
			return new WlanOpponentTypeFactory();
		}
		throw new IllegalArgumentException("Unknown type: " + type);
	}

}
