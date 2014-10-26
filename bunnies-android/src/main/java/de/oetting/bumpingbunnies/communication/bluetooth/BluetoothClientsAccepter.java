package de.oetting.bumpingbunnies.communication.bluetooth;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import de.oetting.bumpingbunnies.core.network.sockets.SocketStorage;
import de.oetting.bumpingbunnies.core.networking.init.ClientAccepter;
import de.oetting.bumpingbunnies.core.networking.init.DefaultConnectionEstablisher;
import de.oetting.bumpingbunnies.logger.Logger;
import de.oetting.bumpingbunnies.logger.LoggerFactory;

public class BluetoothClientsAccepter implements ClientAccepter {

	private static final Logger LOGGER = LoggerFactory.getLogger(BluetoothClientsAccepter.class);

	private final BluetoothActivater activater;
	private final Activity origin;
	private final DefaultConnectionEstablisher connectionEstablisher;

	public BluetoothClientsAccepter(BluetoothActivater activater, Activity origin, DefaultConnectionEstablisher connectionEstablisher) {
		this.activater = activater;
		this.origin = origin;
		this.connectionEstablisher = connectionEstablisher;
	}

	@Override
	public void startThreadToAcceptClients() {
		LOGGER.info("Starting server");
		boolean bluetoothWorking = checkBluetoothSettings();
		if (bluetoothWorking) {
			Intent discoverableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
			discoverableIntent.putExtra(BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION, 300);
			this.origin.startActivity(discoverableIntent);
			closeOpenConnections();
			this.connectionEstablisher.startThreadToAcceptClients();
		}
	}

	@Override
	public void closeOpenConnections() {
		LOGGER.info("Closing connections");
		SocketStorage.getSingleton().closeExistingSocket();
		this.connectionEstablisher.closeOpenConnections();
	}

	private boolean checkBluetoothSettings() {
		return activater.activateBluetooth();
	}

}
