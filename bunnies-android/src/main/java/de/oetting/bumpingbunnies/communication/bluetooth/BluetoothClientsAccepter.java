package de.oetting.bumpingbunnies.communication.bluetooth;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import de.oetting.bumpingbunnies.core.network.CouldNotStartServerException;
import de.oetting.bumpingbunnies.core.network.sockets.SocketStorage;
import de.oetting.bumpingbunnies.core.networking.init.ClientAccepter;
import de.oetting.bumpingbunnies.core.networking.init.DefaultClientAccepter;
import de.oetting.bumpingbunnies.logger.Logger;
import de.oetting.bumpingbunnies.logger.LoggerFactory;

public class BluetoothClientsAccepter implements ClientAccepter {

	private static final Logger LOGGER = LoggerFactory.getLogger(BluetoothClientsAccepter.class);

	private final BluetoothActivatation activater;
	private final Activity origin;
	private final DefaultClientAccepter connectionEstablisher;

	public BluetoothClientsAccepter(BluetoothActivatation activater, Activity origin,
			DefaultClientAccepter connectionEstablisher) {
		this.activater = activater;
		this.origin = origin;
		this.connectionEstablisher = connectionEstablisher;
	}

	@Override
	public void startThreadToAcceptClients() {
		LOGGER.info("Starting server");
		boolean bluetoothWorking = checkBluetoothSettings();
		if (bluetoothWorking
				&& BluetoothAdapter.getDefaultAdapter().getScanMode() != BluetoothAdapter.SCAN_MODE_CONNECTABLE_DISCOVERABLE) {
			try {
				makeDiscoverable();
			} catch (ActivityNotFoundException e) {
				throw new CouldNotStartServerException(e);
			}
		}
	}

	private void makeDiscoverable() {
		Intent discoverableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
		discoverableIntent.putExtra(BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION, 300);
		this.origin.startActivity(discoverableIntent);
		closeConnections();
		this.connectionEstablisher.startThreadToAcceptClients();
	}

	@Override
	public void closeConnections() {
		LOGGER.info("Closing connections");
		SocketStorage.getSingleton().closeExistingSockets();
		this.connectionEstablisher.closeConnections();
	}

	private boolean checkBluetoothSettings() {
		return activater.activateBluetooth();
	}

}
