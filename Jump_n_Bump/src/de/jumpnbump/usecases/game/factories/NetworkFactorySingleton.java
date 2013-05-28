package de.jumpnbump.usecases.game.factories;

import android.bluetooth.BluetoothSocket;
import de.jumpnbump.usecases.game.communication.InformationSupplier;
import de.jumpnbump.usecases.game.communication.NetworkSendQueueThread;
import de.jumpnbump.usecases.game.communication.factories.AbstractStateSenderFactory;
import de.jumpnbump.usecases.game.communication.factories.NetworkReceiverDispatcherThreadFactory;
import de.jumpnbump.usecases.game.communication.factories.StateSenderFactory;

public class NetworkFactorySingleton extends
		AbstractInputServiceFactorySingleton {

	private final NetworkSendQueueThread networkThread;
	private final BluetoothSocket socket;

	public NetworkFactorySingleton(BluetoothSocket socket,
			NetworkSendQueueThread networkThread) {
		this.socket = socket;
		this.networkThread = networkThread;
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
	public AbstractStateSenderFactory createStateSenderFactory() {
		return new StateSenderFactory(this.networkThread);
	}
}
