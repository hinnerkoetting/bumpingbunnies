package de.jumpnbump.usecases.networkRoom;

import java.util.Set;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
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
import de.jumpnbump.usecases.start.communication.DummyCommunication;
import de.jumpnbump.usecases.start.communication.RemoteCommunication;
import de.jumpnbump.usecases.start.communication.ServerDevice;
import de.jumpnbump.usecases.start.communication.bluetooth.BluetoothCommunicationFactory;
import de.jumpnbump.usecases.start.communication.wlan.WlanCommunicationFactory;

public class RoomActivity extends Activity implements
		ManagesConnectionsToServer, GameStarter {

	private static final MyLog LOGGER = Logger.getLogger(RoomActivity.class);
	public final static int REQUEST_BT_ENABLE = 1000;
	private BluetoothArrayAdapter listAdapter;

	private RemoteCommunication remoteCommunication;
	private ArrayAdapter<String> playersAA;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_room);
		ListView list = (ListView) findViewById(R.id.start_bt_list);
		this.listAdapter = new BluetoothArrayAdapter(getBaseContext(), this);
		this.remoteCommunication = new DummyCommunication();
		list.setAdapter(this.listAdapter);
		initRemoteCbListeners();
		initRoom();
	}

	private void initRoom() {
		ListView players = (ListView) findViewById(R.id.room_players);
		this.playersAA = new ArrayAdapter<String>(this,
				R.layout.room_player_entry);
		players.setAdapter(this.playersAA);
	}

	private void switchToBluetooth() {
		LOGGER.info("selected bluetooth");
		this.remoteCommunication = BluetoothCommunicationFactory.create(
				BluetoothAdapter.getDefaultAdapter(), this);
	}

	private void switchToWlan() {
		LOGGER.info("selected wlan");
		this.remoteCommunication = WlanCommunicationFactory.create(this);
	}

	public void onClickConnect(View v) {
		connectToDevice();
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

	public void onClickDiscovery(View v) {
		this.listAdapter.clear();
		this.remoteCommunication.findServer();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		this.remoteCommunication.closeOpenConnections();
	}

	@Override
	public void startConnectToServer(ServerDevice device) {
		this.remoteCommunication.closeOpenConnections();
		this.remoteCommunication.connectToServer(device);
		enableButtons(false);
	}

	public void onClickMakeVisible(View v) {
		startHostThread();
	}

	private void startHostThread() {
		this.remoteCommunication.startServer();
		enableButtons(false);
		createNewRoom();
	}

	private void enableButtons(boolean enable) {
		findViewById(R.id.room_host).setEnabled(enable);
		findViewById(R.id.room_find).setEnabled(enable);
		findViewById(R.id.room_connect).setEnabled(enable);
	}

	private void initRemoteCbListeners() {
		RadioButton btButton = (RadioButton) findViewById(R.id.start_remote_bt);
		btButton.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				if (isChecked) {
					switchToBluetooth();
				}
			}
		});
		RadioButton wlanButton = (RadioButton) findViewById(R.id.start_remote_wlan);
		wlanButton.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				if (isChecked) {
					switchToWlan();
				}
			}
		});
		btButton.setChecked(true);
	}

	@Override
	public void startGame(int playerId) {
		// this.playersAA.add("Player " + playerId);
		// this.playersAA.notifyDataSetChanged();
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

	public void createNewRoom() {
		LOGGER.info("Creating new room");
		this.playersAA.add("You");
		this.playersAA.notifyDataSetChanged();
	}

	public void addServer(BluetoothDevice device) {
		LOGGER.info("Adding server");
		RoomActivity.this.listAdapter.add(device);
		this.listAdapter.notifyDataSetChanged();
	}
}
