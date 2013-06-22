package de.jumpnbump.usecases.start.communication.bluetooth;

import java.io.IOException;

import android.bluetooth.BluetoothServerSocket;
import android.bluetooth.BluetoothSocket;
import de.jumpnbump.usecases.start.communication.MySocket;
import de.jumpnbump.usecases.start.communication.ServerSocket;

public class MyBluetoothServerSocket implements ServerSocket {

	private BluetoothServerSocket socket;

	public MyBluetoothServerSocket(BluetoothServerSocket btSocket) {
		this.socket = btSocket;
	}

	@Override
	public MySocket accept() throws IOException {
		BluetoothSocket btSocket = this.socket.accept();
		return new MyBluetoothSocket(btSocket);
	}

	@Override
	public void close() throws IOException {
		this.socket.close();
	}
}
