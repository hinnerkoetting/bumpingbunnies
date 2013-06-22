package de.jumpnbump.usecases.game.factories;

import de.jumpnbump.usecases.game.communication.InformationSupplier;
import de.jumpnbump.usecases.game.communication.RemoteSender;
import de.jumpnbump.usecases.game.communication.factories.AbstractStateSenderFactory;
import de.jumpnbump.usecases.game.communication.factories.NetworkReceiverDispatcherThreadFactory;
import de.jumpnbump.usecases.game.communication.factories.NetworkSendQueueThreadFactory;
import de.jumpnbump.usecases.game.communication.factories.StateSenderFactory;
import de.jumpnbump.usecases.start.communication.MySocket;

public class NetworkFactorySingleton extends
		AbstractOtherPlayersFactorySingleton {

	private final MySocket socket;

	public NetworkFactorySingleton(MySocket socket) {
		this.socket = socket;
	}

	@Override
	public AbstractInputServiceFactory getInputServiceFactory() {
		return new NetworkInputServiceFactory();
	}

	@Override
	public InformationSupplier createInformationSupplier() {
		return NetworkReceiverDispatcherThreadFactory.create(this.socket);
	}

	@Override
	public AbstractStateSenderFactory createStateSenderFactory(
			RemoteSender sender) {
		return new StateSenderFactory(sender);
	}

	@Override
	public RemoteSender createSender() {
		return NetworkSendQueueThreadFactory.create(this.socket);
	}
}
