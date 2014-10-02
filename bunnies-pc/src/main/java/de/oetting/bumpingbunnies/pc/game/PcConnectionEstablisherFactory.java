package de.oetting.bumpingbunnies.pc.game;

import de.oetting.bumpingbunnies.core.configuration.ConnectionEstablisherFactory;
import de.oetting.bumpingbunnies.core.network.AcceptsClientConnections;
import de.oetting.bumpingbunnies.core.network.DummyCommunication;
import de.oetting.bumpingbunnies.core.network.WlanSocketFactory;
import de.oetting.bumpingbunnies.core.networking.init.ConnectionEstablisher;
import de.oetting.bumpingbunnies.core.networking.init.DefaultConnectionEstablisher;
import de.oetting.bumpingbunnies.core.networking.sockets.SocketFactory;
import de.oetting.bumpingbunnies.logger.Logger;
import de.oetting.bumpingbunnies.logger.LoggerFactory;
import de.oetting.bumpingbunnies.model.configuration.GeneralSettings;
import de.oetting.bumpingbunnies.model.configuration.NetworkType;

public class PcConnectionEstablisherFactory implements ConnectionEstablisherFactory {

	private static final Logger LOGGER = LoggerFactory.getLogger(PcConnectionEstablisherFactory.class);

	@Override
	public ConnectionEstablisher create(AcceptsClientConnections newClientsAccepter, GeneralSettings settings) {
		SocketFactory factory = createSocketFactory(settings);
		DefaultConnectionEstablisher rci = new DefaultConnectionEstablisher(newClientsAccepter, null/**
		 * 
		 * 
		 * TODO not needed
		 */
		, factory);
		return createRemotCommunication(rci, settings);
	}

	private SocketFactory createSocketFactory(GeneralSettings settings) {
		if (settings.getNetworkType().equals(NetworkType.WLAN)) {
			LOGGER.info("Creating Wlan socket factory");
			return new WlanSocketFactory();
		} else if (settings.getNetworkType().equals(NetworkType.BLUETOOTH)) {
			throw new IllegalArgumentException("Bluetooth is not allowed for pc.");
		} else {
			throw new IllegalArgumentException("Unknown host type " + settings.getNetworkType());
		}
	}

	private ConnectionEstablisher createRemotCommunication(DefaultConnectionEstablisher rci, GeneralSettings settings) {
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
