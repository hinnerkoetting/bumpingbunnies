package de.jumpnbump.usecases.game.factories;

import android.bluetooth.BluetoothSocket;
import de.jumpnbump.usecases.game.communication.InformationSupplier;
import de.jumpnbump.usecases.game.communication.factories.NetworkReceiverDispatcherThreadFactory;

public class NetworkFactorySingleton extends AbstractFactorySingleton {

	@Override
	public AbstractInputServiceFactory getInputServiceFactory() {
		return new NetworkInputServiceFactory();
	}

	@Override
	public InformationSupplier createInformationSupplier(BluetoothSocket socket) {
		return NetworkReceiverDispatcherThreadFactory.create(socket);
	}

}
