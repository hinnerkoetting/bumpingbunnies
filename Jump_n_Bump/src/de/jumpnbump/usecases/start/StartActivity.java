package de.jumpnbump.usecases.start;

import java.util.Set;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.Toast;
import de.jumpnbump.R;
import de.jumpnbump.logger.Logger;
import de.jumpnbump.logger.MyLog;
import de.jumpnbump.usecases.ActivityLauncher;
import de.jumpnbump.usecases.MyApplication;
import de.jumpnbump.usecases.game.configuration.Configuration;
import de.jumpnbump.usecases.game.configuration.InputConfiguration;

public class StartActivity extends Activity {

	private static final MyLog LOGGER = Logger.getLogger(StartActivity.class);
	private final int REQUEST_BT_ENABLE = 1000;
	private BluetoothArrayAdapter listAdapter;
	private BroadcastReceiver mReceiver;
	private AcceptThread acceptThread;
	private ConnectThread connectThread;
	private BluetoothAdapter mBluetoothAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_start);
		ListView list = (ListView) findViewById(R.id.start_bt_list);
		this.listAdapter = new BluetoothArrayAdapter(getBaseContext(), this);
		list.setAdapter(this.listAdapter);
		this.mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.game, menu);
		return true;
	}

	public void onClickConnect(View v) {
		if (this.mBluetoothAdapter == null) {
			Toast makeText = Toast.makeText(this, "Bluetooth not supported",
					Toast.LENGTH_LONG);
			makeText.show();
		} else {
			if (!this.mBluetoothAdapter.isEnabled()) {
				Intent enableBtIntent = new Intent(
						BluetoothAdapter.ACTION_REQUEST_ENABLE);
				startActivityForResult(enableBtIntent, this.REQUEST_BT_ENABLE);
			}
			connectToDevice();
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == this.REQUEST_BT_ENABLE) {
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

	public void onClickDiscovery(View v) {
		this.mReceiver = new BroadcastReceiver() {
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
					StartActivity.this.listAdapter.add(device);
				}
			}
		};
		this.listAdapter.clear();
		IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
		IntentFilter filterStop = new IntentFilter(
				BluetoothAdapter.ACTION_DISCOVERY_FINISHED);
		IntentFilter filterStart = new IntentFilter(
				BluetoothAdapter.ACTION_DISCOVERY_STARTED);
		registerReceiver(this.mReceiver, filterStop);
		registerReceiver(this.mReceiver, filter);
		registerReceiver(this.mReceiver, filterStart);
		BluetoothAdapter mBluetoothAdapter = BluetoothAdapter
				.getDefaultAdapter();
		boolean result = mBluetoothAdapter.startDiscovery();
		if (!result) {
			Toast t = Toast.makeText(this, "Could not start discovery",
					Toast.LENGTH_LONG);
			t.show();
		}
	}

	public void onClickMakeVisible(View v) {
		Intent discoverableIntent = new Intent(
				BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
		discoverableIntent.putExtra(
				BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION, 300);
		startActivity(discoverableIntent);
		closeOpenBtConnections();
		this.acceptThread = new AcceptThread(this.mBluetoothAdapter, this);
		this.acceptThread.start();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		if (this.mReceiver != null) {
			unregisterReceiver(this.mReceiver);
		}
		closeOpenBtConnections();
	}

	private void closeOpenBtConnections() {
		this.mBluetoothAdapter.cancelDiscovery();
		MyApplication application = (MyApplication) getApplication();
		application.closeExistingSocket();
	}

	public void startConnectToServer(BluetoothDevice device) {
		closeOpenBtConnections();
		this.connectThread = new ConnectThread(device, this.mBluetoothAdapter,
				this);
		this.connectThread.start();

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

	public void onClickSingleplayer(View v) {
		InputConfiguration selectedInput = findSelectedInputConfiguration();
		ActivityLauncher.launchGame(this, GameParameterFactory
				.createSingleplayerParameter(new Configuration(selectedInput)));
	}

	public void startGame(int playerId) {
		InputConfiguration selectedInput = findSelectedInputConfiguration();
		ActivityLauncher.launchGame(this, GameParameterFactory.createParameter(
				playerId, new Configuration(selectedInput)));
	}

	private InputConfiguration findSelectedInputConfiguration() {
		CompoundButton keyboard = (CompoundButton) findViewById(R.id.start_button_keyboard);
		CompoundButton touch = (CompoundButton) findViewById(R.id.start_button_touch);
		CompoundButton touchJump = (CompoundButton) findViewById(R.id.start_button_touch_jump);
		CompoundButton multiTouchJump = (CompoundButton) findViewById(R.id.start_button_multi_touch);
		if (keyboard.isChecked()) {
			return InputConfiguration.KEYBOARD;
		} else if (touch.isChecked()) {
			return InputConfiguration.TOUCH;
		} else if (touchJump.isChecked()) {
			return InputConfiguration.TOUCH_WITH_UP;
		} else if (multiTouchJump.isChecked()) {
			return InputConfiguration.MULTI_TOUCH;
		}
		throw new IllegalArgumentException("Unknown Input-Type");
	}

}
