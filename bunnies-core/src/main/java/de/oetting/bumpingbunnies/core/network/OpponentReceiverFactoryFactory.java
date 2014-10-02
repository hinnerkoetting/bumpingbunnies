package de.oetting.bumpingbunnies.core.network;

import de.oetting.bumpingbunnies.core.networking.receive.OpponentTypeFactory;
import de.oetting.bumpingbunnies.model.game.objects.OpponentType;

public interface OpponentReceiverFactoryFactory {

	public abstract OpponentTypeFactory createReceiveFactory(OpponentType type);

}
