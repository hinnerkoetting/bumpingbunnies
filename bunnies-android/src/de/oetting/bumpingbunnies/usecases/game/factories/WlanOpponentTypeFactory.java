package de.oetting.bumpingbunnies.usecases.game.factories;

import de.oetting.bumpingbunnies.usecases.game.communication.factories.WlanOpponentTypeReceiveFactory;
import de.oetting.bumpingbunnies.usecases.game.factories.communication.OpponentTypeFactory;
import de.oetting.bumpingbunnies.usecases.game.factories.communication.OpponentTypeReceiveFactory;

public class WlanOpponentTypeFactory implements OpponentTypeFactory {

	@Override
	public OpponentTypeReceiveFactory createReceiveFactory() {
		return new WlanOpponentTypeReceiveFactory();
	}

}
