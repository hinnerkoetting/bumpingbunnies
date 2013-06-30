package de.oetting.bumpingbunnies.usecases.start.communication.bluetooth;

import java.io.IOException;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothServerSocket;
import de.oetting.bumpingbunnies.usecases.game.communication.NetworkConstants;
import de.oetting.bumpingbunnies.usecases.start.communication.MySocket;
import de.oetting.bumpingbunnies.usecases.start.communication.ServerDevice;
import de.oetting.bumpingbunnies.usecases.start.communication.ServerSocket;
import de.oetting.bumpingbunnies.usecases.start.communication.SocketFactory;

public class BluetoothServerSocketFactory implements SocketFactory {

	public BluetoothAdapter btAdapter;

	public BluetoothServerSocketFactory(BluetoothAdapter btAdapter) {
		super();
		this.btAdapter = btAdapter;
	}

	@Override
	public ServerSocket create() {
		BluetoothServerSocket tmp = null;
		try {
			// MY_UUID is the app's UUID string, also used by the client code
			tmp = this.btAdapter.listenUsingRfcommWithServiceRecord(
					NetworkConstants.NAME, NetworkConstants.MY_UUID);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		return new MyBluetoothServerSocket(tmp);
	}

	@Override
	public MySocket createClientSocket(ServerDevice serverDevice) {
		return serverDevice.createClientSocket();
	}

}
