package de.oetting.bumpingbunnies.usecases.networkRoom;

import java.net.InetAddress;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.Toast;
import de.oetting.bumpingbunnies.R;
import de.oetting.bumpingbunnies.communication.DummyCommunication;
import de.oetting.bumpingbunnies.communication.MySocket;
import de.oetting.bumpingbunnies.communication.RemoteCommunication;
import de.oetting.bumpingbunnies.communication.ServerDevice;
import de.oetting.bumpingbunnies.communication.bluetooth.BluetoothCommunicationFactory;
import de.oetting.bumpingbunnies.communication.wlan.WlanCommunicationFactory;
import de.oetting.bumpingbunnies.configuration.gameStart.GameParameterFactory;
import de.oetting.bumpingbunnies.logger.Logger;
import de.oetting.bumpingbunnies.logger.LoggerFactory;
import de.oetting.bumpingbunnies.usecases.ActivityLauncher;
import de.oetting.bumpingbunnies.usecases.game.android.SocketStorage;
import de.oetting.bumpingbunnies.usecases.game.businesslogic.GameStartParameter;
import de.oetting.bumpingbunnies.usecases.game.communication.MessageIds;
import de.oetting.bumpingbunnies.usecases.game.communication.MessageParser;
import de.oetting.bumpingbunnies.usecases.game.communication.NetworkToGameDispatcher;
import de.oetting.bumpingbunnies.usecases.game.communication.SimpleNetworkSender;
import de.oetting.bumpingbunnies.usecases.game.communication.factories.MessageParserFactory;
import de.oetting.bumpingbunnies.usecases.game.communication.factories.SimpleNetworkSenderFactory;
import de.oetting.bumpingbunnies.usecases.game.communication.objects.JsonWrapper;
import de.oetting.bumpingbunnies.usecases.game.configuration.Configuration;
import de.oetting.bumpingbunnies.usecases.game.configuration.GeneralSettings;
import de.oetting.bumpingbunnies.usecases.game.configuration.LocalPlayersettings;
import de.oetting.bumpingbunnies.usecases.game.configuration.LocalSettings;
import de.oetting.bumpingbunnies.usecases.game.configuration.OpponentConfiguration;
import de.oetting.bumpingbunnies.usecases.game.configuration.PlayerProperties;
import de.oetting.bumpingbunnies.usecases.game.factories.NetworkFactory;
import de.oetting.bumpingbunnies.usecases.networkRoom.services.BroadcastService;
import de.oetting.bumpingbunnies.usecases.networkRoom.services.ConnectionToClientService;
import de.oetting.bumpingbunnies.usecases.networkRoom.services.ConnectionToServer;
import de.oetting.bumpingbunnies.usecases.networkRoom.services.ConnectionToServerService;
import de.oetting.bumpingbunnies.usecases.networkRoom.services.DummyConnectionToServer;
import de.oetting.bumpingbunnies.usecases.networkRoom.services.OnBroadcastReceived;
import de.oetting.bumpingbunnies.usecases.networkRoom.services.factory.ConnectionToClientServiceFactory;
import de.oetting.bumpingbunnies.usecases.start.BluetoothArrayAdapter;

public class RoomActivity extends Activity implements ConnectToServerCallback,
		ClientConnectedSuccesfullCallback,
		ConnectionToServerSuccesfullCallback, OnBroadcastReceived {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(RoomActivity.class);
	public final static int REQUEST_BT_ENABLE = 1000;
	private BluetoothArrayAdapter listAdapter;

	private RemoteCommunication remoteCommunication;
	private RoomArrayAdapter playersAA;
	private BroadcastService broadcastService;

	private int playerCounter = 0;
	private ConnectionToServer connectedToServerService;
	private List<ConnectionToClientService> connectionToClientServices = new LinkedList<ConnectionToClientService>();

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
		this.connectedToServerService = new DummyConnectionToServer();
		this.broadcastService = new BroadcastService();
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

	public void onClickKnownHosts(View v) {
		displayKnownHosts();
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == REQUEST_BT_ENABLE) {
			if (resultCode == RESULT_OK) {
				displayKnownHosts();
			}
		}
	}

	private void displayKnownHosts() {
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
		this.broadcastService.listenForBroadCasts(this);
	}

	private String getInputIp() {
		EditText ipText = (EditText) findViewById(R.id.room_ip);
		return ipText.getText().toString();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		this.remoteCommunication.closeOpenConnections();
		this.connectedToServerService.cancel();
		this.broadcastService.cancel();
		for (ConnectionToClientService connectionToClient : this.connectionToClientServices) {
			connectionToClient.cancel();
		}
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
		this.playerCounter = 0;
		int myPlayerId = getNextPlayerId();
		this.remoteCommunication.startServer();
		enableButtons(false);
		createNewRoom(myPlayerId);
		this.broadcastService.startRegularServerBroadcast(this);
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
		ConnectionToClientService connectionToClientService = ConnectionToClientServiceFactory
				.create(this, socket, new NetworkToGameDispatcher());
		this.connectionToClientServices.add(connectionToClientService);
		connectionToClientService.onConnectToClient(socket);
		enableStartButton();
	}

	private List<OpponentConfiguration> createOtherPlayerconfigurations(
			int myPlayerId) {
		List<OpponentConfiguration> otherPlayers = new ArrayList<OpponentConfiguration>(
				this.playersAA.getCount() - 1);
		for (RoomEntry otherPlayer : this.playersAA.getAllOtherPlayers()) {
			NetworkFactory factory = new NetworkFactory(
					otherPlayer.getSocket(), otherPlayer.getSocketIndex());

			OpponentConfiguration otherPlayerConfiguration = new OpponentConfiguration(
					factory, otherPlayer.getPlayerProperties());
			otherPlayers.add(otherPlayerConfiguration);
		}
		return otherPlayers;
	}

	public void addPlayerEntry(MySocket socket,
			PlayerProperties playerProperties, int socketIndex) {
		int playerId = playerProperties.getPlayerId();
		LOGGER.info("adding player info %d", playerId);
		RoomEntry entry = new RoomEntry(new PlayerProperties(playerId,
				playerProperties.getPlayerName()), socket, socketIndex);
		addPlayerEntry(entry);
	}

	private void addPlayerEntry(final RoomEntry entry) {
		runOnUiThread(new Runnable() {

			@Override
			public void run() {
				RoomActivity.this.playersAA.add(entry);
				RoomActivity.this.playersAA.notifyDataSetChanged();
			}
		});
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

	public void createNewRoom(int myPlayerId) {
		LOGGER.info("Creating new room");
		addMyPlayerRoomEntry(myPlayerId);
	}

	public void addMyPlayerRoomEntry(final int myPlayerId) {
		runOnUiThread(new Runnable() {

			@Override
			public void run() {
				LocalPlayersettings settings = createLocalPlayerSettingsFromIntent();
				PlayerProperties singlePlayerProperties = new PlayerProperties(
						myPlayerId, settings.getPlayerName());
				RoomActivity.this.playersAA.addMe(new SinglePlayerRoomEntry(
						singlePlayerProperties));
				RoomActivity.this.playersAA.notifyDataSetChanged();
			}
		});

	}

	public void addServer(BluetoothDevice device) {
		LOGGER.info("Adding server");
		RoomActivity.this.listAdapter.add(device);
		this.listAdapter.notifyDataSetChanged();
	}

	@Override
	public void connectToServerSuccesfull(final MySocket socket) {
		this.connectedToServerService = new ConnectionToServerService(socket,
				this);
		this.connectedToServerService.onConnectionToServer();
	}

	private void enableStartButton() {
		runOnUiThread(new Runnable() {

			@Override
			public void run() {
				findStartButton().setEnabled(true);
			}
		});
	}

	private Button findStartButton() {
		return (Button) findViewById(R.id.room_start);
	}

	public void onClickStart(View v) {
		notifyClientsAboutlaunch();
		GeneralSettings generalSettings = createGeneralSettingsFromIntent();
		launchGame(generalSettings);
	}

	private void notifyClientsAboutlaunch() {
		SocketStorage singleton = SocketStorage.getSingleton();
		MessageParser parser = MessageParserFactory.create();
		JsonWrapper generalSettings = createJsonGeneralSettings(parser);
		JsonWrapper startMessage = new JsonWrapper(MessageIds.START_GAME_ID, "");
		for (MySocket socket : singleton.getAllSockets()) {
			SimpleNetworkSender networkSender = SimpleNetworkSenderFactory
					.createNetworkSender(socket);
			networkSender.sendMessage(generalSettings);
			networkSender.sendMessage(startMessage);
		}
	}

	public void launchGame(GeneralSettings generalSettings) {

		LocalSettings localSettings = createLocalSettingsFromIntent();
		LocalPlayersettings localPlayerSettings = createLocalPlayerSettingsFromIntent();
		int myPlayerId = this.playersAA.getMyself().getPlayerProperties()
				.getPlayerId();
		List<OpponentConfiguration> otherPlayers = createOtherPlayerconfigurations(myPlayerId);
		Configuration config = new Configuration(localSettings,
				generalSettings, otherPlayers, localPlayerSettings);
		GameStartParameter parameter = GameParameterFactory.createParameter(
				myPlayerId, config);
		ActivityLauncher.launchGame(this, parameter);
	}

	private LocalSettings createLocalSettingsFromIntent() {
		return (LocalSettings) getIntent().getExtras().get(
				ActivityLauncher.LOCAL_SETTINGS);
	}

	private JsonWrapper createJsonGeneralSettings(MessageParser parser) {
		GeneralSettings settings = createGeneralSettingsFromIntent();
		JsonWrapper generalSettings = new JsonWrapper(
				MessageIds.SEND_CONFIGURATION_ID,
				parser.encodeMessage(settings));
		return generalSettings;
	}

	private GeneralSettings createGeneralSettingsFromIntent() {
		return (GeneralSettings) getIntent().getExtras().get(
				ActivityLauncher.GENERAL_SETTINGS);
	}

	public LocalPlayersettings createLocalPlayerSettingsFromIntent() {
		return (LocalPlayersettings) getIntent().getExtras().get(
				ActivityLauncher.LOCAL_PLAYER_SETTINGS);
	}

	public int getNextPlayerId() {
		return this.playerCounter++;
	}

	public List<RoomEntry> getAllOtherPlayers() {
		return this.playersAA.getAllOtherPlayers();
	}

	public RoomEntry getMyself() {
		return this.playersAA.getMyself();
	}

	@Override
	public void broadcastReceived(final InetAddress senderAddress) {
		runOnUiThread(new Runnable() {

			@Override
			public void run() {
				EditText ipText = (EditText) findViewById(R.id.room_ip);
				ipText.setText(senderAddress.getHostAddress());
			}
		});

	}

	public void onClickConnect(View v) {
		this.remoteCommunication.findServer(getInputIp());
	}

}
