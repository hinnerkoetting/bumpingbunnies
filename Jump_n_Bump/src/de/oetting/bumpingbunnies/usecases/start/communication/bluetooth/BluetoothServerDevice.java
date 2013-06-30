package de.oetting.bumpingbunnies.usecases.start.communication.bluetooth;

import java.io.IOException;

import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import de.oetting.bumpingbunnies.usecases.game.communication.NetworkConstants;
import de.oetting.bumpingbunnies.usecases.start.communication.MySocket;
import de.oetting.bumpingbunnies.usecases.start.communication.ServerDevice;

public class BluetoothServerDevice implements ServerDevice {

	public BluetoothDevice device;

	public BluetoothServerDevice(BluetoothDevice device) {
		super();
		this.device = device;
	}

	@Override
	public MySocket createClientSocket() {
		try {
			BluetoothSocket socket = this.device
					.createRfcommSocketToServiceRecord(NetworkConstants.MY_UUID);
			return new MyBluetoothSocket(socket);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

}
