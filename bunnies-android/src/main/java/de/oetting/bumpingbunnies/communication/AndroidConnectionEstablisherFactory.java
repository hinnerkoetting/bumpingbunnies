package de.oetting.bumpingbunnies.communication;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import de.oetting.bumpingbunnies.communication.bluetooth.BluetoothActivatation;
import de.oetting.bumpingbunnies.communication.bluetooth.BluetoothClientsAccepter;
import de.oetting.bumpingbunnies.communication.bluetooth.BluetoothSocketFactory;
import de.oetting.bumpingbunnies.core.configuration.ConnectionEstablisherFactory;
import de.oetting.bumpingbunnies.core.network.AcceptsClientConnections;
import de.oetting.bumpingbunnies.core.network.DummyCommunication;
import de.oetting.bumpingbunnies.core.network.WlanSocketFactory;
import de.oetting.bumpingbunnies.core.networking.init.AcceptsClients;
import de.oetting.bumpingbunnies.core.networking.init.DefaultClientAccepter;
import de.oetting.bumpingbunnies.core.networking.sockets.SocketFactory;
import de.oetting.bumpingbunnies.core.threads.ThreadErrorCallback;
import de.oetting.bumpingbunnies.logger.Logger;
import de.oetting.bumpingbunnies.logger.LoggerFactory;
import de.oetting.bumpingbunnies.model.configuration.NetworkType;
import de.oetting.bumpingbunnies.model.configuration.ServerSettings;

public class AndroidConnectionEstablisherFactory implements ConnectionEstablisherFactory {

	private static final Logger LOGGER = LoggerFactory.getLogger(AndroidConnectionEstablisherFactory.class);
	private final Activity origin;

	public AndroidConnectionEstablisherFactory(Activity origin) {
		this.origin = origin;
	}

	@Override
	public AcceptsClients create(AcceptsClientConnections newClientsAccepter, ServerSettings settings, ThreadErrorCallback errorCallback) {
		SocketFactory factory = createSocketFactory(settings);
		DefaultClientAccepter rci = new DefaultClientAccepter(factory, newClientsAccepter, errorCallback);
		return createRemotCommunication(rci, settings, origin);
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

	private AcceptsClients createRemotCommunication(DefaultClientAccepter rci, ServerSettings settings, Activity origin) {
		if (settings.getNetworkType().equals(NetworkType.WLAN)) {
			LOGGER.info("Creating Wlan communication");
			return rci;
		} else if (settings.getNetworkType().equals(NetworkType.BLUETOOTH)) {
			LOGGER.info("Creating bluetooth communication");
			return new BluetoothClientsAccepter(new BluetoothActivatation(origin), origin, rci);
		} else {
			LOGGER.info("Creating dummy communication");
			return new DummyCommunication();
		}
	}
}
