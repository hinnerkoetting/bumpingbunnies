package de.oetting.bumpingbunnies.usecases.game.factories.communication;

import android.bluetooth.BluetoothAdapter;
import de.oetting.bumpingbunnies.android.game.GameActivity;
import de.oetting.bumpingbunnies.communication.ConnectionEstablisher;
import de.oetting.bumpingbunnies.communication.DummyCommunication;
import de.oetting.bumpingbunnies.communication.RemoteCommunication;
import de.oetting.bumpingbunnies.communication.SocketFactory;
import de.oetting.bumpingbunnies.communication.bluetooth.BluetoothCommunication;
import de.oetting.bumpingbunnies.communication.bluetooth.BluetoothSocketFactory;
import de.oetting.bumpingbunnies.communication.bluetooth.DummySocketFactory;
import de.oetting.bumpingbunnies.communication.wlan.WlanCommunication;
import de.oetting.bumpingbunnies.communication.wlan.WlanSocketFactory;
import de.oetting.bumpingbunnies.core.networking.AcceptsClientConnections;
import de.oetting.bumpingbunnies.logger.Logger;
import de.oetting.bumpingbunnies.logger.LoggerFactory;
import de.oetting.bumpingbunnies.usecases.game.configuration.GeneralSettings;
import de.oetting.bumpingbunnies.usecases.game.configuration.NetworkType;

public class RemoteCommunicationFactory {

	private static final Logger LOGGER = LoggerFactory.getLogger(RemoteCommunicationFactory.class);

	public static RemoteCommunication create(GameActivity activity, AcceptsClientConnections newClientsAccepter, GeneralSettings settings) {
		SocketFactory factory = createSocketFactory(settings);
		ConnectionEstablisher rci = new ConnectionEstablisher(newClientsAccepter, null/**
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
			LOGGER.info("Creating dummy socket factory");
			return new DummySocketFactory();
		}
	}

	private static RemoteCommunication createRemotCommunication(ConnectionEstablisher rci, GeneralSettings settings) {
		if (settings.getNetworkType().equals(NetworkType.WLAN)) {
			LOGGER.info("Creating Wlan communication");
			return new WlanCommunication(null /** TODO */
			, rci);
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
