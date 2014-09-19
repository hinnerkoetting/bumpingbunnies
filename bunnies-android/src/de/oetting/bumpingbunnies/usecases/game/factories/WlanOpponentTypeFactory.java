package de.oetting.bumpingbunnies.usecases.game.factories;

import de.oetting.bumpingbunnies.usecases.game.communication.factories.WlanOpponentTypeReceiveFactory;
import de.oetting.bumpingbunnies.usecases.game.communication.factories.WlanOpponentTypeSendFactory;
import de.oetting.bumpingbunnies.usecases.game.factories.communication.OpponentTypeFactory;
import de.oetting.bumpingbunnies.usecases.game.factories.communication.OpponentTypeReceiveFactory;
import de.oetting.bumpingbunnies.usecases.game.factories.communication.OpponentTypeSendFactory;

public class WlanOpponentTypeFactory implements OpponentTypeFactory {

	@Override
	public OpponentTypeReceiveFactory createReceiveFactory() {
		return new WlanOpponentTypeReceiveFactory();
	}

	@Override
	public OpponentTypeSendFactory createSendFactory() {
		return new WlanOpponentTypeSendFactory();
	}

}
