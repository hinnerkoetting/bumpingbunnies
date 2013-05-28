package de.jumpnbump.usecases.game.factories;

import android.bluetooth.BluetoothSocket;
import de.jumpnbump.usecases.game.communication.InformationSupplier;
import de.jumpnbump.usecases.game.communication.NetworkSendQueueThread;
import de.jumpnbump.usecases.game.communication.factories.AbstractStateSenderFactory;

public abstract class AbstractInputServiceFactorySingleton {

	private static AbstractInputServiceFactorySingleton singleton;

	public static void initNetwork(BluetoothSocket socket,
			NetworkSendQueueThread networkSenderThread) {
		singleton = new NetworkFactorySingleton(socket, networkSenderThread);
	}

	public static void initSinglePlayer() {
		singleton = new SingleplayerFactorySingleton();
	}

	public static AbstractInputServiceFactorySingleton getSingleton() {
		if (singleton == null) {
			throw new IllegalArgumentException("Init first");
		}
		return singleton;
	}

	public abstract AbstractInputServiceFactory getInputServiceFactory();

	public abstract InformationSupplier createInformationSupplier();

	public abstract AbstractStateSenderFactory createStateSenderFactory();

}
