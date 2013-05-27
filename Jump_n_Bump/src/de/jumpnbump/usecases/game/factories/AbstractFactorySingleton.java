package de.jumpnbump.usecases.game.factories;

import android.bluetooth.BluetoothSocket;
import de.jumpnbump.usecases.game.communication.InformationSupplier;

public abstract class AbstractFactorySingleton {

	private static boolean withNetwork;
	private static AbstractFactorySingleton singleton;

	public static void initNetwork() {
		withNetwork = true;
	}

	public static void initSinglePlayer() {
		withNetwork = false;
	}

	public static AbstractFactorySingleton getSingleton() {
		if (withNetwork) {
			singleton = new NetworkFactorySingleton();
		} else {
			singleton = new SingleplayerFactorySingleton();
		}
		return singleton;
	}

	public abstract AbstractInputServiceFactory getInputServiceFactory();

	public abstract InformationSupplier createInformationSupplier(
			BluetoothSocket socket);

}
