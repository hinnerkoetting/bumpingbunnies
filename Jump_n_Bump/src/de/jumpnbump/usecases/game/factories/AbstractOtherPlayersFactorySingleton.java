package de.jumpnbump.usecases.game.factories;

import android.bluetooth.BluetoothSocket;
import de.jumpnbump.usecases.game.communication.InformationSupplier;
import de.jumpnbump.usecases.game.communication.RemoteSender;
import de.jumpnbump.usecases.game.communication.factories.AbstractStateSenderFactory;

public abstract class AbstractOtherPlayersFactorySingleton {

	private static AbstractOtherPlayersFactorySingleton singleton;

	public static void initNetwork(BluetoothSocket socket,
			RemoteSender networkSenderThread) {
		singleton = new NetworkFactorySingleton(socket, networkSenderThread);
	}

	public static void initSinglePlayer() {
		singleton = new SingleplayerFactorySingleton();
	}

	public static AbstractOtherPlayersFactorySingleton getSingleton() {
		if (singleton == null) {
			throw new IllegalArgumentException("Init first");
		}
		return singleton;
	}

	public abstract AbstractInputServiceFactory getInputServiceFactory();

	public abstract InformationSupplier createInformationSupplier();

	public abstract AbstractStateSenderFactory createStateSenderFactory();

	public abstract RemoteSender createSender();

}