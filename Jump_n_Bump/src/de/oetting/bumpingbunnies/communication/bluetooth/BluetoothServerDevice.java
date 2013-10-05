package de.oetting.bumpingbunnies.communication.bluetooth;

import java.io.IOException;

import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import de.oetting.bumpingbunnies.communication.MySocket;
import de.oetting.bumpingbunnies.communication.ServerDevice;
import de.oetting.bumpingbunnies.usecases.game.communication.NetworkConstants;
import de.oetting.bumpingbunnies.usecases.game.model.Opponent;
import de.oetting.bumpingbunnies.usecases.game.model.Opponent.OpponentType;

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
			return new MyBluetoothSocket(socket, Opponent.createOpponent("bluetoothclient", OpponentType.BLUETOOTH));
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

}
