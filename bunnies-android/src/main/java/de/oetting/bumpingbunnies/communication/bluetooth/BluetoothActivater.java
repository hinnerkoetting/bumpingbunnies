package de.oetting.bumpingbunnies.communication.bluetooth;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.widget.Toast;
import de.oetting.bumpingbunnies.logger.Logger;
import de.oetting.bumpingbunnies.logger.LoggerFactory;
import de.oetting.bumpingbunnies.usecases.networkRoom.RoomActivity;

public class BluetoothActivater {

	private static final Logger LOGGER = LoggerFactory.getLogger(BluetoothActivater.class);

	private final Activity origin;
	private final BluetoothAdapter bluetoothAdapter;

	public BluetoothActivater(Activity orgin) {
		this.origin = orgin;
		this.bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
	}

	public boolean activateBluetooth() {
		LOGGER.info("Activating Bluetooth");
		if (this.bluetoothAdapter == null) {
			Toast makeText = Toast.makeText(this.origin, "Bluetooth not supported", Toast.LENGTH_LONG);
			makeText.show();
			return false;
		} else {
			if (!this.bluetoothAdapter.isEnabled()) {
				Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
				this.origin.startActivityForResult(enableBtIntent, RoomActivity.REQUEST_BT_ENABLE);
				return false;
			}
			return true;
		}
	}
}
