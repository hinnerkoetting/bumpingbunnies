package de.jumpnbump.usecases.start.communication;

import android.bluetooth.BluetoothDevice;

public interface RemoteCommunication {

	void startServer();

	void closeOpenConnections();

	void connectToServer(BluetoothDevice device);

	boolean activate();

	void findServer();
}
