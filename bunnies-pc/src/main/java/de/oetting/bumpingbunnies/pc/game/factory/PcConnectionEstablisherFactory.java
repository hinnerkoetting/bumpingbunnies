package de.oetting.bumpingbunnies.pc.game.factory;

import de.oetting.bumpingbunnies.core.configuration.ConnectionEstablisherFactory;
import de.oetting.bumpingbunnies.core.network.AcceptsClientConnections;
import de.oetting.bumpingbunnies.core.network.DummyCommunication;
import de.oetting.bumpingbunnies.core.network.WlanSocketFactory;
import de.oetting.bumpingbunnies.core.networking.init.ClientAccepter;
import de.oetting.bumpingbunnies.core.networking.init.DefaultClientAccepter;
import de.oetting.bumpingbunnies.core.networking.sockets.SocketFactory;
import de.oetting.bumpingbunnies.core.threads.ThreadErrorCallback;
import de.oetting.bumpingbunnies.logger.Logger;
import de.oetting.bumpingbunnies.logger.LoggerFactory;
import de.oetting.bumpingbunnies.model.configuration.NetworkType;
import de.oetting.bumpingbunnies.model.configuration.ServerSettings;

public class PcConnectionEstablisherFactory implements ConnectionEstablisherFactory {

	private static final Logger LOGGER = LoggerFactory.getLogger(PcConnectionEstablisherFactory.class);

	@Override
	public ClientAccepter create(AcceptsClientConnections newClientsAccepter, ServerSettings settings, ThreadErrorCallback errorCallback) {
		SocketFactory factory = createSocketFactory(settings);
		DefaultClientAccepter rci = new DefaultClientAccepter(factory, newClientsAccepter, errorCallback);
		return createRemotCommunication(rci, settings);
	}

	private SocketFactory createSocketFactory(ServerSettings settings) {
		if (settings.getNetworkType().equals(NetworkType.WLAN)) {
			LOGGER.info("Creating Wlan socket factory");
			return new WlanSocketFactory();
		} else if (settings.getNetworkType().equals(NetworkType.BLUETOOTH)) {
			throw new IllegalArgumentException("Bluetooth is not allowed for pc.");
		} else {
			throw new IllegalArgumentException("Unknown host type " + settings.getNetworkType());
		}
	}

	private ClientAccepter createRemotCommunication(DefaultClientAccepter rci, ServerSettings settings) {
		if (settings.getNetworkType().equals(NetworkType.WLAN)) {
			LOGGER.info("Creating Wlan communication");
			return rci;
		} else if (settings.getNetworkType().equals(NetworkType.BLUETOOTH)) {
			throw new IllegalArgumentException("Bluetooth is not allowed for pc.");
		} else {
			LOGGER.info("Creating dummy communication");
			return new DummyCommunication();
		}

	}

}
