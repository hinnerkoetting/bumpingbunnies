package de.jumpnbump.usecases.game.network;

import java.io.IOException;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothServerSocket;
import android.bluetooth.BluetoothSocket;
import de.jumpnbump.logger.Logger;
import de.jumpnbump.logger.MyLog;
import de.jumpnbump.usecases.ActivityLauncher;
import de.jumpnbump.usecases.MyApplication;

public class AcceptThread extends Thread {
	private final BluetoothServerSocket mmServerSocket;

	private static final MyLog LOGGER = Logger.getLogger(AcceptThread.class);

	private final Activity activity;

	public AcceptThread(BluetoothAdapter mBluetoothAdapter, Activity activity) {
		this.activity = activity;
		// Use a temporary object that is later assigned to mmServerSocket,
		// because mmServerSocket is final
		BluetoothServerSocket tmp = null;
		try {
			// MY_UUID is the app's UUID string, also used by the client code
			tmp = mBluetoothAdapter.listenUsingRfcommWithServiceRecord(
					NetworkConstants.NAME, NetworkConstants.MY_UUID);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		this.mmServerSocket = tmp;
	}

	@Override
	public void run() {
		LOGGER.info("Start Server Thread");
		BluetoothSocket socket = null;
		// Keep listening until exception occurs or a socket is returned
		while (true) {
			try {
				socket = this.mmServerSocket.accept();

				// If a connection was accepted
				if (socket != null) {
					// Do work to manage the connection (in a separate thread)
					manageConnectedSocket(socket);
					this.mmServerSocket.close();
					break;
				}
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}
	}

	private void manageConnectedSocket(BluetoothSocket socket) {
		MyApplication application = (MyApplication) this.activity
				.getApplication();
		application.setSocket(socket);
		ActivityLauncher.launchGame(this.activity, 0);
		LOGGER.info("Connection accepeted");
	}

	/** Will cancel the listening socket, and cause the thread to finish */
	public void cancel() {
		try {
			this.mmServerSocket.close();
		} catch (IOException e) {
		}
	}
}