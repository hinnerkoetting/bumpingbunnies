package de.jumpnbump.usecases.networkRoom;

import android.bluetooth.BluetoothDevice;

public interface ManagesConnectionsToServer {

	void startConnectToServer(BluetoothDevice device);
}
