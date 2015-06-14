package de.oetting.bumpingbunnies.communication.bluetooth;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import de.oetting.bumpingbunnies.core.network.CouldNotStartServerException;
import de.oetting.bumpingbunnies.core.networking.server.MakesGameVisible;
import de.oetting.bumpingbunnies.logger.Logger;
import de.oetting.bumpingbunnies.logger.LoggerFactory;

public class MakesBluetoothDiscoverable implements MakesGameVisible {

	private static final Logger LOGGER = LoggerFactory.getLogger(MakesBluetoothDiscoverable.class);

	private final BluetoothActivatation activater;
	private final Activity origin;

	public MakesBluetoothDiscoverable(BluetoothActivatation activater, Activity origin) {
		this.activater = activater;
		this.origin = origin;
	}

	
	@Override
	public void makeVisible(String name) {
		LOGGER.info("Making game visible for bluetooth clients");
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
		cancel();
	}

	@Override
	public void cancel() {
		LOGGER.info("Closing connections");
	}

	private boolean checkBluetoothSettings() {
		return activater.activateBluetooth();
	}

}
