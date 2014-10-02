package de.oetting.bumpingbunnies.core.network;

import de.oetting.bumpingbunnies.core.networking.receive.OpponentTypeFactory;
import de.oetting.bumpingbunnies.usecases.game.model.OpponentType;

public interface OpponentReceiverFactoryFactory {

	public abstract OpponentTypeFactory createReceiveFactory(OpponentType type);

}
