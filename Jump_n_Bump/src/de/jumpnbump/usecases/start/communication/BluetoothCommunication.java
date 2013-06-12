package de.jumpnbump.usecases.start.communication;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.widget.Toast;
import de.jumpnbump.usecases.MyApplication;
import de.jumpnbump.usecases.start.AcceptThread;
import de.jumpnbump.usecases.start.ConnectThread;
import de.jumpnbump.usecases.start.StartActivity;

public class BluetoothCommunication implements RemoteCommunication {

	private final StartActivity origin;
	private final AcceptThread acceptThread;
	private ConnectThread connectThread;
	private final BluetoothAdapter mBluetoothAdapter;

	public BluetoothCommunication(StartActivity origin,
			AcceptThread acceptThread, BluetoothAdapter mBluetoothAdapter) {
		this.origin = origin;
		this.acceptThread = acceptThread;
		this.mBluetoothAdapter = mBluetoothAdapter;
	}

	@Override
	public void startServer() {
		Intent discoverableIntent = new Intent(
				BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
		discoverableIntent.putExtra(
				BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION, 300);
		this.origin.startActivity(discoverableIntent);
		closeOpenConnections();

		this.acceptThread.start();

	}

	@Override
	public void closeOpenConnections() {
		this.mBluetoothAdapter.cancelDiscovery();
		MyApplication application = (MyApplication) this.origin
				.getApplication();
		application.closeExistingSocket();
	}

	@Override
	public void conntectToServer(BluetoothDevice device) {
		this.connectThread = new ConnectThread(device, this.mBluetoothAdapter,
				this.origin);
		this.connectThread.start();
	}

	@Override
	public boolean activate() {
		if (this.mBluetoothAdapter == null) {
			Toast makeText = Toast.makeText(this.origin,
					"Bluetooth not supported", Toast.LENGTH_LONG);
			makeText.show();
			return false;
		} else {
			if (!this.mBluetoothAdapter.isEnabled()) {
				Intent enableBtIntent = new Intent(
						BluetoothAdapter.ACTION_REQUEST_ENABLE);
				this.origin.startActivityForResult(enableBtIntent,
						StartActivity.REQUEST_BT_ENABLE);
			}
			return true;
		}
	}
}
