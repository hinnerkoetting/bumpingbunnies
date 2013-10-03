package de.oetting.bumpingbunnies.communication.bluetooth;

import java.io.IOException;

import android.bluetooth.BluetoothServerSocket;
import android.bluetooth.BluetoothSocket;
import de.oetting.bumpingbunnies.communication.MySocket;
import de.oetting.bumpingbunnies.communication.ServerSocket;
import de.oetting.bumpingbunnies.usecases.game.model.Opponent;

public class MyBluetoothServerSocket implements ServerSocket {

	private BluetoothServerSocket socket;

	public MyBluetoothServerSocket(BluetoothServerSocket btSocket) {
		this.socket = btSocket;
	}

	@Override
	public MySocket accept() throws IOException {
		BluetoothSocket btSocket = this.socket.accept();
		return new MyBluetoothSocket(btSocket, new Opponent("bluetoothserver"));
	}

	@Override
	public void close() throws IOException {
		this.socket.close();
	}
}
