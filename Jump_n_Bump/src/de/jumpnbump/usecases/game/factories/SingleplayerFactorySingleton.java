package de.jumpnbump.usecases.game.factories;

import android.bluetooth.BluetoothSocket;
import de.jumpnbump.usecases.game.communication.DummyInformationSupplier;
import de.jumpnbump.usecases.game.communication.InformationSupplier;

public class SingleplayerFactorySingleton extends AbstractFactorySingleton {

	@Override
	public AbstractInputServiceFactory getInputServiceFactory() {
		return new DummyInputServiceFactory();
	}

	@Override
	public InformationSupplier createInformationSupplier(BluetoothSocket socket) {
		return new DummyInformationSupplier();
	}

}
