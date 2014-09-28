package de.oetting.bumpingbunnies.communication.bluetooth;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.widget.Toast;
import de.oetting.bumpingbunnies.core.networking.ServerDevice;
import de.oetting.bumpingbunnies.core.networking.SocketStorage;
import de.oetting.bumpingbunnies.core.networking.init.ConnectionEstablisher;
import de.oetting.bumpingbunnies.core.networking.init.DefaultConnectionEstablisher;
import de.oetting.bumpingbunnies.logger.Logger;
import de.oetting.bumpingbunnies.logger.LoggerFactory;
import de.oetting.bumpingbunnies.usecases.networkRoom.RoomActivity;

public class BluetoothCommunication implements ConnectionEstablisher {

	private static final Logger LOGGER = LoggerFactory.getLogger(BluetoothCommunication.class);
	private final BluetoothAdapter mBluetoothAdapter;
	private BroadcastReceiver mReceiver;
	private boolean discoveryRunning;
	private boolean receiversRegistered;
	private DefaultConnectionEstablisher commonBehaviour;
	private final RoomActivity origin;

	public BluetoothCommunication(RoomActivity origin, BluetoothAdapter mBluetoothAdapter, DefaultConnectionEstablisher commonBehaviour) {
		this.origin = origin;
		this.mBluetoothAdapter = mBluetoothAdapter;
		this.commonBehaviour = commonBehaviour;
	}

	@Override
	public void startThreadToAcceptClients() {
		LOGGER.info("Starting server");
		boolean bluetoothWorking = checkBluetoothSettings();
		if (bluetoothWorking) {
			Intent discoverableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
			discoverableIntent.putExtra(BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION, 300);
			this.origin.startActivity(discoverableIntent);
			closeOpenConnections();
			this.commonBehaviour.startThreadToAcceptClients();
		}
	}

	@Override
	public void closeOpenConnections() {
		LOGGER.info("Closing connections");
		if (this.discoveryRunning) {
			this.mBluetoothAdapter.cancelDiscovery();
		}
		SocketStorage.getSingleton().closeExistingSocket();
		if (this.mReceiver != null) {
			if (this.receiversRegistered) {
				this.origin.unregisterReceiver(this.mReceiver);
			}
			this.receiversRegistered = false;
		}
		this.commonBehaviour.closeOpenConnections();
	}

	@Override
	public void connectToServer(ServerDevice device) {
		try {
			// because of some bt discovery bug
			Thread.sleep(1500);
		} catch (InterruptedException e) {
			LOGGER.error("Fehler beim BT Connect", e);
		}
		this.commonBehaviour.connectToServer(device);
	}

	@Override
	public boolean activate() {
		LOGGER.info("Activating Bluetooth");
		if (this.mBluetoothAdapter == null) {
			Toast makeText = Toast.makeText(this.origin, "Bluetooth not supported", Toast.LENGTH_LONG);
			makeText.show();
			return false;
		} else {
			if (!this.mBluetoothAdapter.isEnabled()) {
				Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
				this.origin.startActivityForResult(enableBtIntent, RoomActivity.REQUEST_BT_ENABLE);
			}
			return true;
		}
	}

	@Override
	public void searchServer() {
		LOGGER.info("Finding Servers");
		boolean bluetoothWorking = checkBluetoothSettings();
		if (bluetoothWorking) {
			discoverDevices();
		}
	}

	private boolean checkBluetoothSettings() {
		return activate();
	}

	private void discoverDevices() {
		this.discoveryRunning = true;
		if (!this.receiversRegistered) {
			this.mReceiver = createBroadCastReceiver();
			registerReceiver();
		}
		BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
		boolean result = mBluetoothAdapter.startDiscovery();
		if (!result) {
			Toast t = Toast.makeText(this.origin, "Could not start discovery", Toast.LENGTH_LONG);
			t.show();
		}
	}

	private void registerReceiver() {
		LOGGER.info("Register Receivers");
		IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
		IntentFilter filterStop = new IntentFilter(BluetoothAdapter.ACTION_DISCOVERY_FINISHED);
		IntentFilter filterStart = new IntentFilter(BluetoothAdapter.ACTION_DISCOVERY_STARTED);

		this.origin.registerReceiver(this.mReceiver, filterStop);
		this.origin.registerReceiver(this.mReceiver, filter);
		this.origin.registerReceiver(this.mReceiver, filterStart);
	}

	private BroadcastReceiver createBroadCastReceiver() {
		return new BroadcastReceiver() {
			@Override
			public void onReceive(Context context, Intent intent) {
				LOGGER.info("Receive Bluettooth result");
				String action = intent.getAction();
				// When discovery finds a device
				if (BluetoothDevice.ACTION_FOUND.equals(action)) {
					handleNewDeviceFound(intent);
				}
				if (BluetoothAdapter.ACTION_DISCOVERY_FINISHED.equals(action)) {
					handleDiscoveryFinished();
				}
			}

			private void handleDiscoveryFinished() {
				BluetoothCommunication.this.discoveryRunning = false;
			}
		};
	}

	private void handleNewDeviceFound(Intent intent) {
		// Get the BluetoothDevice object from the Intent
		BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
		// Add the name and address to an array adapter to show in a
		// ListView
		BluetoothCommunication.this.origin.addServer(device);
	}
}
