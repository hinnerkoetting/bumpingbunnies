package de.jumpnbump.usecases.start.communication.bluetooth;

import java.io.IOException;

import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import de.jumpnbump.usecases.game.communication.NetworkConstants;
import de.jumpnbump.usecases.start.communication.MySocket;
import de.jumpnbump.usecases.start.communication.ServerDevice;

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
