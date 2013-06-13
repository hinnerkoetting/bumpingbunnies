package de.jumpnbump.usecases.start.communication;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.widget.Toast;
import de.jumpnbump.usecases.MyApplication;
import de.jumpnbump.usecases.networkRoom.RoomActivity;
import de.jumpnbump.usecases.start.AcceptThread;
import de.jumpnbump.usecases.start.AcceptThreadImpl;
import de.jumpnbump.usecases.start.ConnectThread;
import de.jumpnbump.usecases.start.ConnectThreadImpl;
import de.jumpnbump.usecases.start.DummyAcceptThread;
import de.jumpnbump.usecases.start.DummyConnectThread;

public class BluetoothCommunication implements RemoteCommunication {

	private final RoomActivity origin;
	private AcceptThread acceptThread;
	private ConnectThread connectThread;
	private final BluetoothAdapter mBluetoothAdapter;

	public BluetoothCommunication(RoomActivity origin,
			BluetoothAdapter mBluetoothAdapter) {
		this.origin = origin;
		this.mBluetoothAdapter = mBluetoothAdapter;
		this.acceptThread = new DummyAcceptThread();
		this.connectThread = new DummyConnectThread();
	}

	@Override
	public void startServer() {
		Intent discoverableIntent = new Intent(
				BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
		discoverableIntent.putExtra(
				BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION, 300);
		this.origin.startActivity(discoverableIntent);
		closeOpenConnections();
		this.acceptThread = new AcceptThreadImpl(this.mBluetoothAdapter,
				this.origin, this.origin);
		this.acceptThread.start();

	}

	@Override
	public void closeOpenConnections() {
		this.acceptThread.close();
		this.connectThread.close();
		this.mBluetoothAdapter.cancelDiscovery();
		MyApplication application = (MyApplication) this.origin
				.getApplication();
		application.closeExistingSocket();
	}

	@Override
	public void conntectToServer(BluetoothDevice device) {
		this.connectThread = new ConnectThreadImpl(device,
				this.mBluetoothAdapter, this.origin);
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
						RoomActivity.REQUEST_BT_ENABLE);
			}
			return true;
		}
	}
}
