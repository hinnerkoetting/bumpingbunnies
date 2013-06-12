package de.jumpnbump.usecases.start.communication;

import android.bluetooth.BluetoothDevice;

public interface RemoteCommunication {

	void startServer();

	void closeOpenConnections();

	void conntectToServer(BluetoothDevice device);

	boolean activate();
}
