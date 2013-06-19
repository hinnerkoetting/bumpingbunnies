package de.jumpnbump.usecases.networkRoom;

import java.util.Set;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.Toast;
import de.jumpnbump.R;
import de.jumpnbump.logger.Logger;
import de.jumpnbump.logger.MyLog;
import de.jumpnbump.usecases.ActivityLauncher;
import de.jumpnbump.usecases.game.businesslogic.GameStartParameter;
import de.jumpnbump.usecases.game.configuration.Configuration;
import de.jumpnbump.usecases.start.BluetoothArrayAdapter;
import de.jumpnbump.usecases.start.GameParameterFactory;
import de.jumpnbump.usecases.start.communication.BluetoothCommunicationFactory;
import de.jumpnbump.usecases.start.communication.DummyCommunication;
import de.jumpnbump.usecases.start.communication.RemoteCommunication;

public class RoomActivity extends Activity implements
		ManagesConnectionsToServer, GameStarter {

	private static final MyLog LOGGER = Logger.getLogger(RoomActivity.class);
	public final static int REQUEST_BT_ENABLE = 1000;
	private BluetoothArrayAdapter listAdapter;
	private BroadcastReceiver mReceiver;

	private RemoteCommunication remoteCommunication;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_room);
		ListView list = (ListView) findViewById(R.id.start_bt_list);
		this.listAdapter = new BluetoothArrayAdapter(getBaseContext(), this);
		this.remoteCommunication = new DummyCommunication();
		list.setAdapter(this.listAdapter);
		initRemoteCbListeners();
	}

	private void switchToBluetooth() {
		this.remoteCommunication = BluetoothCommunicationFactory.create(
				BluetoothAdapter.getDefaultAdapter(), this);
	}

	private void switchToWlan() {
		LOGGER.warn("TODO: implement");
	}

	public void onClickConnect(View v) {
		boolean bluetoothWorking = checkBluetoothSettings();
		if (bluetoothWorking) {
			connectToDevice();
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == REQUEST_BT_ENABLE) {
			if (resultCode == RESULT_OK) {
				connectToDevice();
			}
		}
	}

	private void connectToDevice() {
		BluetoothAdapter mBluetoothAdapter = BluetoothAdapter
				.getDefaultAdapter();
		Set<BluetoothDevice> pairedDevices = mBluetoothAdapter
				.getBondedDevices();
		LOGGER.info("found %d devices", pairedDevices.size());
		this.listAdapter.clear();
		for (BluetoothDevice device : pairedDevices) {
			this.listAdapter.add(device);
		}
	}

	private boolean checkBluetoothSettings() {
		return this.remoteCommunication.activate();
	}

	public void onClickDiscovery(View v) {
		boolean bluetoothWorking = checkBluetoothSettings();
		if (bluetoothWorking) {
			discoverDevices();
		}
	}

	private void discoverDevices() {
		this.mReceiver = createBroadCastReceiver();
		registerReceiver();
		BluetoothAdapter mBluetoothAdapter = BluetoothAdapter
				.getDefaultAdapter();
		boolean result = mBluetoothAdapter.startDiscovery();
		if (!result) {
			Toast t = Toast.makeText(this, "Could not start discovery",
					Toast.LENGTH_LONG);
			t.show();
		}
	}

	private void registerReceiver() {
		this.listAdapter.clear();
		IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
		IntentFilter filterStop = new IntentFilter(
				BluetoothAdapter.ACTION_DISCOVERY_FINISHED);
		IntentFilter filterStart = new IntentFilter(
				BluetoothAdapter.ACTION_DISCOVERY_STARTED);
		try {
			unregisterReceiver(this.mReceiver);
		} catch (IllegalArgumentException e) {
			LOGGER.info("Receiver not registered... continue");
		}
		registerReceiver(this.mReceiver, filterStop);
		registerReceiver(this.mReceiver, filter);
		registerReceiver(this.mReceiver, filterStart);
	}

	private BroadcastReceiver createBroadCastReceiver() {
		return new BroadcastReceiver() {
			@Override
			public void onReceive(Context context, Intent intent) {
				LOGGER.info("Receive Bluettooth result");
				String action = intent.getAction();
				// When discovery finds a device
				if (BluetoothDevice.ACTION_FOUND.equals(action)) {
					// Get the BluetoothDevice object from the Intent
					BluetoothDevice device = intent
							.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
					// Add the name and address to an array adapter to show in a
					// ListView
					RoomActivity.this.listAdapter.add(device);
				}
			}
		};
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		if (this.mReceiver != null) {
			unregisterReceiver(this.mReceiver);
		}
		this.remoteCommunication.closeOpenConnections();
	}

	@Override
	public void startConnectToServer(BluetoothDevice device) {
		this.remoteCommunication.closeOpenConnections();
		this.remoteCommunication.connectToServer(device);
	}

	public void onClickMakeVisible(View v) {
		boolean bluetoothWorking = checkBluetoothSettings();
		if (bluetoothWorking) {
			startHostThread();
		}
	}

	private void startHostThread() {
		this.remoteCommunication.startServer();
	}

	private void initRemoteCbListeners() {
		RadioButton btButton = (RadioButton) findViewById(R.id.start_remote_bt);
		btButton.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				switchToBluetooth();
			}
		});
		RadioButton wlanButton = (RadioButton) findViewById(R.id.start_remote_wlan);
		wlanButton.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				switchToWlan();
			}
		});
		btButton.setChecked(true);
	}

	@Override
	public void startGame(int playerId) {
		Configuration configuration = (Configuration) getIntent().getExtras()
				.get(ActivityLauncher.CONFIGURATION);
		GameStartParameter parameter = GameParameterFactory.createParameter(
				playerId, configuration);
		ActivityLauncher.launchGame(this, parameter);
	}

	public void connectionNotSuccesful(final String message) {
		runOnUiThread(new Runnable() {

			@Override
			public void run() {
				Toast toast = Toast.makeText(getBaseContext(),
						"Exception during connect. Game may still work. "
								+ message, Toast.LENGTH_SHORT);
				toast.show();
			}
		});

	}
}
