package de.oetting.bumpingbunnies.usecases.game.factories.communication;

import android.bluetooth.BluetoothAdapter;
import de.oetting.bumpingbunnies.communication.DummyCommunication;
import de.oetting.bumpingbunnies.communication.bluetooth.BluetoothCommunication;
import de.oetting.bumpingbunnies.communication.bluetooth.BluetoothSocketFactory;
import de.oetting.bumpingbunnies.communication.wlan.WlanSocketFactory;
import de.oetting.bumpingbunnies.core.networking.AcceptsClientConnections;
import de.oetting.bumpingbunnies.core.networking.init.ConnectionEstablisher;
import de.oetting.bumpingbunnies.core.networking.init.DefaultConnectionEstablisher;
import de.oetting.bumpingbunnies.core.networking.sockets.SocketFactory;
import de.oetting.bumpingbunnies.logger.Logger;
import de.oetting.bumpingbunnies.logger.LoggerFactory;
import de.oetting.bumpingbunnies.usecases.game.configuration.GeneralSettings;
import de.oetting.bumpingbunnies.usecases.game.configuration.NetworkType;

public class ConnectionEstablisherFactory {

	private static final Logger LOGGER = LoggerFactory.getLogger(ConnectionEstablisherFactory.class);

	public static ConnectionEstablisher create(AcceptsClientConnections newClientsAccepter, GeneralSettings settings) {
		SocketFactory factory = createSocketFactory(settings);
		DefaultConnectionEstablisher rci = new DefaultConnectionEstablisher(newClientsAccepter, null/**
		 * 
		 * 
		 * TODO not needed
		 */
		, factory);
		return createRemotCommunication(rci, settings);
	}

	private static SocketFactory createSocketFactory(GeneralSettings settings) {
		if (settings.getNetworkType().equals(NetworkType.WLAN)) {
			LOGGER.info("Creating Wlan socket factory");
			return new WlanSocketFactory();
		} else if (settings.getNetworkType().equals(NetworkType.BLUETOOTH)) {
			LOGGER.info("Creating bluetooth socket factory");
			return new BluetoothSocketFactory(BluetoothAdapter.getDefaultAdapter());
		} else {
			throw new IllegalArgumentException("Unknown host type " + settings.getNetworkType());
		}
	}

	private static ConnectionEstablisher createRemotCommunication(DefaultConnectionEstablisher rci, GeneralSettings settings) {
		if (settings.getNetworkType().equals(NetworkType.WLAN)) {
			LOGGER.info("Creating Wlan communication");
			return rci;
		} else if (settings.getNetworkType().equals(NetworkType.BLUETOOTH)) {
			LOGGER.info("Creating bluetooth communication");
			return new BluetoothCommunication(null /** TODO */
			, BluetoothAdapter.getDefaultAdapter(), rci);
		} else {
			LOGGER.info("Creating dummy communication");
			return new DummyCommunication();
		}
	}
}
