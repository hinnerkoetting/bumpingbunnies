package de.oetting.bumpingbunnies.usecases.networkRoom;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.Toast;
import de.oetting.bumpingbunnies.R;
import de.oetting.bumpingbunnies.logger.Logger;
import de.oetting.bumpingbunnies.logger.MyLog;
import de.oetting.bumpingbunnies.usecases.ActivityLauncher;
import de.oetting.bumpingbunnies.usecases.game.android.SocketStorage;
import de.oetting.bumpingbunnies.usecases.game.businesslogic.GameStartParameter;
import de.oetting.bumpingbunnies.usecases.game.configuration.Configuration;
import de.oetting.bumpingbunnies.usecases.game.configuration.GeneralSettings;
import de.oetting.bumpingbunnies.usecases.game.configuration.LocalSettings;
import de.oetting.bumpingbunnies.usecases.game.configuration.NetworkSettings;
import de.oetting.bumpingbunnies.usecases.game.configuration.OtherPlayerConfiguration;
import de.oetting.bumpingbunnies.usecases.game.factories.NetworkFactory;
import de.oetting.bumpingbunnies.usecases.start.BluetoothArrayAdapter;
import de.oetting.bumpingbunnies.usecases.start.GameParameterFactory;
import de.oetting.bumpingbunnies.usecases.start.communication.DummyCommunication;
import de.oetting.bumpingbunnies.usecases.start.communication.MySocket;
import de.oetting.bumpingbunnies.usecases.start.communication.RemoteCommunication;
import de.oetting.bumpingbunnies.usecases.start.communication.ServerDevice;
import de.oetting.bumpingbunnies.usecases.start.communication.bluetooth.BluetoothCommunicationFactory;
import de.oetting.bumpingbunnies.usecases.start.communication.wlan.WlanCommunicationFactory;

public class RoomActivity extends Activity implements ConnectToServerCallback,
		ClientConnectedSuccesfullCallback, ConnectionToServerSuccesfullCallback {

	private static final MyLog LOGGER = Logger.getLogger(RoomActivity.class);
	public final static int REQUEST_BT_ENABLE = 1000;
	private BluetoothArrayAdapter listAdapter;

	private RemoteCommunication remoteCommunication;
	private RoomArrayAdapter playersAA;

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
		displayDefaultIp();
	}

	private void displayDefaultIp() {
		EditText ipText = (EditText) findViewById(R.id.room_ip);
		String ipAddress = Utils.getIPAddress(true);
		int lastDot = ipAddress.lastIndexOf('.');
		ipText.setText(ipAddress.substring(0, lastDot + 1));
	}

	private void initRoom() {
		ListView players = (ListView) findViewById(R.id.room_players);
		this.playersAA = new RoomArrayAdapter(this, R.layout.room_player_entry);
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
		this.remoteCommunication.findServer(getInputIp());
	}

	private String getInputIp() {
		EditText ipText = (EditText) findViewById(R.id.room_ip);
		return ipText.getText().toString();
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
		wlanButton.setChecked(true);
	}

	@Override
	public void clientConnectedSucessfull(final MySocket socket) {
		runOnUiThread(new Runnable() {

			@Override
			public void run() {
				manadedConnectedClient(socket);
			}

		});
		LocalSettings localSettings = (LocalSettings) getIntent().getExtras()
				.get(ActivityLauncher.LOCAL_SETTINGS);
		GeneralSettings generalSettings = (GeneralSettings) getIntent()
				.getExtras().get(ActivityLauncher.GENERAL_SETTINGS);
		List<OtherPlayerConfiguration> otherPlayers = createOtherPlayerconfigurations(0);
		Configuration config = new Configuration(localSettings,
				generalSettings, otherPlayers);
		GameStartParameter parameter = GameParameterFactory.createParameter(0,
				config);
		ActivityLauncher.launchGame(this, parameter);
	}

	private List<OtherPlayerConfiguration> createOtherPlayerconfigurations(
			int myPlayerId) {
		// TODO
		LOGGER.warn("Fixed number player 2");
		int number = 2;
		List<OtherPlayerConfiguration> otherPlayers = new ArrayList<OtherPlayerConfiguration>(
				number);
		for (int i = 1; i < number; i++) {
			NetworkFactory networkFactory = new NetworkFactory(null, 0);
			// TODO
			OtherPlayerConfiguration otherPlayerConfiguration = new OtherPlayerConfiguration(
					networkFactory, myPlayerId == 0 ? 1 : 0);
			otherPlayers.add(otherPlayerConfiguration);
		}
		return otherPlayers;
	}

	private void manadedConnectedClient(MySocket socket) {
		int nextPlayerId = this.playersAA.getCount();
		addPlayerEntry(socket, nextPlayerId);
	}

	private void addPlayerEntry(MySocket socket, int nextPlayerId) {
		int socketIndex = SocketStorage.getSingleton().addSocket(socket);
		NetworkFactory factory = new NetworkFactory(socket, socketIndex);
		OtherPlayerConfiguration otherPlayerConfiguration = new OtherPlayerConfiguration(
				factory, nextPlayerId);
		RoomEntry entry = new RoomEntry(otherPlayerConfiguration);
		RoomActivity.this.playersAA.add(entry);
		RoomActivity.this.playersAA.notifyDataSetChanged();
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
		this.playersAA.add(new RoomEntry(null));
		this.playersAA.notifyDataSetChanged();
	}

	public void addServer(BluetoothDevice device) {
		LOGGER.info("Adding server");
		RoomActivity.this.listAdapter.add(device);
		this.listAdapter.notifyDataSetChanged();
	}

	@Override
	public void connectToServerSuccesfull(final MySocket socket) {
		LocalSettings localSettings = (LocalSettings) getIntent().getExtras()
				.get(ActivityLauncher.LOCAL_SETTINGS);
		GeneralSettings generalSettings = (GeneralSettings) getIntent()
				.getExtras().get(ActivityLauncher.GENERAL_SETTINGS);
		// TODO
		NetworkSettings networkSettings = new NetworkSettings(true);
		List<OtherPlayerConfiguration> otherPlayers = createOtherPlayerconfigurations(1);
		Configuration config = new Configuration(localSettings,
				generalSettings, otherPlayers);
		GameStartParameter parameter = GameParameterFactory.createParameter(1,
				config);
		ActivityLauncher.launchGame(this, parameter);
		runOnUiThread(new Runnable() {

			@Override
			public void run() {
				createNewRoom();
				// TODO
				manageConnectToServer(socket);

			}
		});

	}

	private void manageConnectToServer(MySocket socket) {
		addPlayerEntry(socket, 0);
	}
}
