package de.jumpnbump.usecases.start.communication;

import android.bluetooth.BluetoothDevice;

public class DummyCommunication implements RemoteCommunication {

	@Override
	public void startServer() {
	}

	@Override
	public void closeOpenConnections() {
	}

	@Override
	public void connectToServer(BluetoothDevice device) {
	}

	@Override
	public boolean activate() {
		return false;
	}

}
