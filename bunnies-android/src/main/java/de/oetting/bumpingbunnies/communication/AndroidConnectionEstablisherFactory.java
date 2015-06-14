package de.oetting.bumpingbunnies.communication;

import android.bluetooth.BluetoothAdapter;
import de.oetting.bumpingbunnies.communication.bluetooth.BluetoothSocketFactory;
import de.oetting.bumpingbunnies.core.configuration.ConnectionEstablisherFactory;
import de.oetting.bumpingbunnies.core.network.AcceptsClientConnections;
import de.oetting.bumpingbunnies.core.network.WlanSocketFactory;
import de.oetting.bumpingbunnies.core.networking.init.ClientAccepter;
import de.oetting.bumpingbunnies.core.networking.init.DefaultClientAccepter;
import de.oetting.bumpingbunnies.core.networking.sockets.SocketFactory;
import de.oetting.bumpingbunnies.core.threads.ThreadErrorCallback;
import de.oetting.bumpingbunnies.logger.Logger;
import de.oetting.bumpingbunnies.logger.LoggerFactory;
import de.oetting.bumpingbunnies.model.configuration.NetworkType;
import de.oetting.bumpingbunnies.model.configuration.ServerSettings;

public class AndroidConnectionEstablisherFactory implements ConnectionEstablisherFactory {

	private static final Logger LOGGER = LoggerFactory.getLogger(AndroidConnectionEstablisherFactory.class);

	@Override
	public ClientAccepter create(AcceptsClientConnections newClientsAccepter, ServerSettings settings, ThreadErrorCallback errorCallback) {
		SocketFactory factory = createSocketFactory(settings);
		return new DefaultClientAccepter(factory, newClientsAccepter, errorCallback);
	}

	private SocketFactory createSocketFactory(ServerSettings settings) {
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
}
