package de.oetting.bumpingbunnies.communication.bluetooth;

import java.io.IOException;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothServerSocket;
import de.oetting.bumpingbunnies.core.network.MySocket;
import de.oetting.bumpingbunnies.core.network.NetworkConstants;
import de.oetting.bumpingbunnies.core.network.ServerDevice;
import de.oetting.bumpingbunnies.core.networking.sockets.ServerSocket;
import de.oetting.bumpingbunnies.core.networking.sockets.SocketFactory;

public class BluetoothSocketFactory implements SocketFactory {

	public BluetoothAdapter btAdapter;

	public BluetoothSocketFactory(BluetoothAdapter btAdapter) {
		super();
		this.btAdapter = btAdapter;
	}

	@Override
	public ServerSocket create() {
		BluetoothServerSocket tmp = null;
		try {
			// MY_UUID is the app's UUID string, also used by the client code
			tmp = this.btAdapter.listenUsingRfcommWithServiceRecord(
					NetworkConstants.NAME, NetworkConstants.MY_UUID_3);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		return new BunniesBluetoothServerSocket(tmp);
	}


}
