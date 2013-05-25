package de.jumpnbump.usecases.game.network;

import java.io.IOException;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import de.jumpnbump.logger.Logger;
import de.jumpnbump.logger.MyLog;
import de.jumpnbump.usecases.ActivityLauncher;
import de.jumpnbump.usecases.MyApplication;

public class ConnectThread extends Thread {

	private static final MyLog LOGGER = Logger.getLogger(ConnectThread.class);
	private final BluetoothSocket mmSocket;
	private final BluetoothDevice mmDevice;
	private final BluetoothAdapter mBluetoothAdapter;
	private final Activity activity;

	public ConnectThread(BluetoothDevice device,
			BluetoothAdapter mBluetoothAdapter, Activity activity) {
		this.mBluetoothAdapter = mBluetoothAdapter;
		this.activity = activity;
		// Use a temporary object that is later assigned to mmSocket,
		// because mmSocket is final
		BluetoothSocket tmp = null;
		this.mmDevice = device;

		// Get a BluetoothSocket to connect with the given BluetoothDevice
		try {
			// MY_UUID is the app's UUID string, also used by the server code
			tmp = device
					.createRfcommSocketToServiceRecord(NetworkConstants.MY_UUID);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		this.mmSocket = tmp;
	}

	@Override
	public void run() {
		LOGGER.info("Start Client Thread");
		// Cancel discovery because it will slow down the connection
		this.mBluetoothAdapter.cancelDiscovery();

		try {
			// Connect the device through the socket. This will block
			// until it succeeds or throws an exception
			this.mmSocket.connect();
		} catch (IOException connectException) {
			// Unable to connect; close the socket and get out
			try {
				this.mmSocket.close();
			} catch (IOException closeException) {
			}
			return;
		}

		// Do work to manage the connection (in a separate thread)
		manageConnectedSocket(this.mmSocket);
	}

	private void manageConnectedSocket(BluetoothSocket mmSocket2) {
		MyApplication application = (MyApplication) this.activity
				.getApplication();
		application.setSocket(this.mmSocket);
		ActivityLauncher.launchGame(this.activity, 1);
		LOGGER.info("Connection accepted");
	}

	/** Will cancel an in-progress connection, and close the socket */
	public void cancel() {
		try {
			this.mmSocket.close();
		} catch (IOException e) {
		}
	}
}