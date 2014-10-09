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
import de.oetting.bumpingbunnies.android.parcel.GeneralSettingsParcelableWrapper;
import de.oetting.bumpingbunnies.android.parcel.LocalPlayerSettingsParcellableWrapper;
import de.oetting.bumpingbunnies.android.parcel.LocalSettingsParcelableWrapper;
import de.oetting.bumpingbunnies.communication.bluetooth.BluetoothCommunication;
import de.oetting.bumpingbunnies.communication.bluetooth.BluetoothCommunicationFactory;
import de.oetting.bumpingbunnies.communication.wlan.WlanCommunicationFactory;
import de.oetting.bumpingbunnies.core.configuration.GameParameterFactory;
import de.oetting.bumpingbunnies.core.network.AcceptsClientConnections;
import de.oetting.bumpingbunnies.core.network.ConnectsToServer;
import de.oetting.bumpingbunnies.core.network.DummyCommunication;
import de.oetting.bumpingbunnies.core.network.MySocket;
import de.oetting.bumpingbunnies.core.network.RoomEntry;
import de.oetting.bumpingbunnies.core.network.ServerDevice;
import de.oetting.bumpingbunnies.core.network.SocketStorage;
import de.oetting.bumpingbunnies.core.network.StrictNetworkToGameDispatcher;
import de.oetting.bumpingbunnies.core.network.WlanDevice;
import de.oetting.bumpingbunnies.core.networking.SinglePlayerRoomEntry;
import de.oetting.bumpingbunnies.core.networking.client.ConnectionToServer;
import de.oetting.bumpingbunnies.core.networking.client.CouldNotOpenBroadcastSocketException;
import de.oetting.bumpingbunnies.core.networking.client.DisplaysConnectedServers;
import de.oetting.bumpingbunnies.core.networking.client.OnBroadcastReceived;
import de.oetting.bumpingbunnies.core.networking.client.SetupConnectionWithServer;
import de.oetting.bumpingbunnies.core.networking.init.ConnectionEstablisher;
import de.oetting.bumpingbunnies.core.networking.receive.PlayerDisconnectedCallback;
import de.oetting.bumpingbunnies.core.networking.sender.GameSettingSender;
import de.oetting.bumpingbunnies.core.networking.sender.SimpleNetworkSender;
import de.oetting.bumpingbunnies.core.networking.sender.SimpleNetworkSenderFactory;
import de.oetting.bumpingbunnies.core.networking.sender.StartGameSender;
import de.oetting.bumpingbunnies.core.networking.server.ConnectionToClientServiceFactory;
import de.oetting.bumpingbunnies.core.networking.server.NetworkBroadcaster;
import de.oetting.bumpingbunnies.core.networking.server.ToClientConnector;
import de.oetting.bumpingbunnies.logger.Logger;
import de.oetting.bumpingbunnies.logger.LoggerFactory;
import de.oetting.bumpingbunnies.model.configuration.AiModus;
import de.oetting.bumpingbunnies.model.configuration.Configuration;
import de.oetting.bumpingbunnies.model.configuration.GameStartParameter;
import de.oetting.bumpingbunnies.model.configuration.GeneralSettings;
import de.oetting.bumpingbunnies.model.configuration.LocalPlayerSettings;
import de.oetting.bumpingbunnies.model.configuration.LocalSettings;
import de.oetting.bumpingbunnies.model.configuration.OpponentConfiguration;
import de.oetting.bumpingbunnies.model.configuration.PlayerProperties;
import de.oetting.bumpingbunnies.model.game.objects.Opponent;
import de.oetting.bumpingbunnies.usecases.ActivityLauncher;
import de.oetting.bumpingbunnies.usecases.networkRoom.services.DummyConnectionToServer;
import de.oetting.bumpingbunnies.usecases.start.BluetoothArrayAdapter;

public class RoomActivity extends Activity implements ConnectToServerCallback, AcceptsClientConnections, ConnectionToServerSuccesfullCallback,
		OnBroadcastReceived, ConnectsToServer, DisplaysConnectedServers, PlayerDisconnectedCallback {

	private static final Logger LOGGER = LoggerFactory.getLogger(RoomActivity.class);
	public final static int REQUEST_BT_ENABLE = 1000;
	private BluetoothArrayAdapter listAdapter;

	private ConnectionEstablisher remoteCommunication;
	private RoomArrayAdapter playersAA;
	private NetworkBroadcaster broadcastService;

	private int playerCounter = 0;
	private ConnectionToServer connectedToServerService;
	private List<ToClientConnector> connectionToClientServices = new LinkedList<ToClientConnector>();

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
		this.broadcastService = new NetworkBroadcaster();
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
		this.remoteCommunication = BluetoothCommunicationFactory.create(BluetoothAdapter.getDefaultAdapter(), this);
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
		BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
		Set<BluetoothDevice> pairedDevices = mBluetoothAdapter.getBondedDevices();
		LOGGER.info("found %d devices", pairedDevices.size());
		this.listAdapter.clear();
		for (BluetoothDevice device : pairedDevices) {
			this.listAdapter.add(device);
		}
	}

	public void onClickDiscovery(View v) {
		new Thread(new Runnable() {

			@Override
			public void run() {
				try {
					listAdapter.clear();
					broadcastService.listenForBroadCasts(RoomActivity.this);
				} catch (CouldNotOpenBroadcastSocketException e) {
					displayErrorAddressInUse();
					LOGGER.warn("Error when trying to search for host", e);
				}
			}
		}).start();
	}

	public void displayErrorAddressInUse() {
		String addressInUse = getResources().getString(R.string.address_in_use);
		displayMessage(addressInUse);
	}

	public void displayListenError() {
		String unknownError = getResources().getString(R.string.unknown_error);
		displayMessage(unknownError);
	}

	private void displayMessage(final String message) {
		runOnUiThread(new Runnable() {

			@Override
			public void run() {
				Toast.makeText(RoomActivity.this, message, Toast.LENGTH_SHORT).show();
			}
		});
	}

	private String getInputIp() {
		EditText ipText = (EditText) findViewById(R.id.room_ip);
		return ipText.getText().toString();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		this.connectedToServerService.cancel();
		this.broadcastService.cancel();
		this.remoteCommunication.closeOpenConnections();
		for (ToClientConnector connectionToClient : this.connectionToClientServices) {
			connectionToClient.cancel();
		}
	}

	@Override
	public void startConnectToServer(final ServerDevice device) {
		new Thread(new Runnable() {

			@Override
			public void run() {
				try {
					remoteCommunication.closeOpenConnections();
					remoteCommunication.connectToServer(device);
				} catch (Exception e) {
					LOGGER.error("Error", e);
					displayCouldNotConnectException();
				}
			}
		}).start();
		enableButtons(false);
	}

	private void displayCouldNotConnectException() {
		runOnUiThread(new Runnable() {

			@Override
			public void run() {
				CharSequence text = getText(R.string.could_not_connect);
				Toast.makeText(RoomActivity.this, text, Toast.LENGTH_SHORT).show();
			}
		});

	}

	public void onClickMakeVisible(View v) {
		startHostThread();
	}

	private void startHostThread() {
		this.playerCounter = 0;
		int myPlayerId = getNextPlayerId();
		this.remoteCommunication.startThreadToAcceptClients();
		enableButtons(false);
		createNewRoom(myPlayerId);
		startBroadCast();
	}

	private void startBroadCast() {
		new Thread(new Runnable() {

			@Override
			public void run() {
				broadcastService.startRegularServerBroadcast();
			}
		}).start();
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
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				if (isChecked) {
					switchToBluetooth();
				}
			}
		});
		RadioButton wlanButton = (RadioButton) findViewById(R.id.start_remote_wlan);
		wlanButton.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				if (isChecked) {
					switchToWlan();
				}
			}
		});
		wlanButton.setChecked(true);
	}

	@Override
	public void clientConnectedSucessfull(final MySocket socket) {
		ToClientConnector connectionToClientService = ConnectionToClientServiceFactory.create(this, socket, new StrictNetworkToGameDispatcher(this));
		this.connectionToClientServices.add(connectionToClientService);
		connectionToClientService.onConnectToClient(socket);
		enableStartButton();
	}

	private List<OpponentConfiguration> createOtherPlayerconfigurations() {
		List<OpponentConfiguration> otherPlayers = new ArrayList<OpponentConfiguration>(this.playersAA.getCount() - 1);
		for (RoomEntry otherPlayer : this.playersAA.getAllOtherPlayers()) {
			AiModus aiMode = AiModus.NORMAL;

			OpponentConfiguration otherPlayerConfiguration = new OpponentConfiguration(aiMode, otherPlayer.getPlayerProperties(), otherPlayer.createOponent());
			otherPlayers.add(otherPlayerConfiguration);
		}
		return otherPlayers;
	}

	@Override
	public void addPlayerEntry(MySocket socket, PlayerProperties playerProperties, int socketIndex) {
		LOGGER.info("adding player info %d", playerProperties.getPlayerId());
		RoomEntry entry = new RoomEntry(playerProperties, socket, socketIndex);
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

	@Override
	public void connectionNotSuccesful(final String message) {
		runOnUiThread(new Runnable() {

			@Override
			public void run() {
				Toast toast = Toast.makeText(getBaseContext(), "Exception during connect. Game may still work. " + message, Toast.LENGTH_SHORT);
				toast.show();
			}
		});

	}

	public void createNewRoom(int myPlayerId) {
		LOGGER.info("Creating new room");
		addMyPlayerRoomEntry(myPlayerId);
	}

	@Override
	public void addMyPlayerRoomEntry(final int myPlayerId) {
		runOnUiThread(new Runnable() {

			@Override
			public void run() {
				LocalPlayerSettings settings = createLocalPlayerSettings();
				PlayerProperties singlePlayerProperties = new PlayerProperties(myPlayerId, settings.getPlayerName());
				RoomActivity.this.playersAA.addMe(new SinglePlayerRoomEntry(singlePlayerProperties));
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
		this.connectedToServerService = new SetupConnectionWithServer(socket, this, this);
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
		launchGame(generalSettings, true);
	}

	private void notifyClientsAboutlaunch() {
		SocketStorage singleton = SocketStorage.getSingleton();
		GeneralSettings settings = createGeneralSettingsFromIntent();
		for (MySocket socket : singleton.getAllSockets()) {
			SimpleNetworkSender networkSender = SimpleNetworkSenderFactory.createNetworkSender(socket);
			new GameSettingSender(networkSender).sendMessage(settings);
			new StartGameSender(networkSender).sendMessage("");
		}
	}

	@Override
	public void launchGame(GeneralSettings generalSettings, boolean asHost) {

		LocalSettings localSettings = createLocalSettingsFromIntent();
		LocalPlayerSettings localPlayerSettings = createLocalPlayerSettings();
		int myPlayerId = this.playersAA.getMyself().getPlayerProperties().getPlayerId();
		List<OpponentConfiguration> otherPlayers = createOtherPlayerconfigurations();
		Configuration config = new Configuration(localSettings, generalSettings, otherPlayers, localPlayerSettings, asHost);
		GameStartParameter parameter = GameParameterFactory.createParameter(myPlayerId, config);
		sleep();
		ActivityLauncher.launchGame(this, parameter);
		finish();
	}

	private void sleep() {
		try {
			Thread.sleep(2500);
		} catch (InterruptedException e) {
		}
	}

	private LocalSettings createLocalSettingsFromIntent() {
		return ((LocalSettingsParcelableWrapper) getIntent().getExtras().get(ActivityLauncher.LOCAL_SETTINGS)).getLocalSettings();
	}

	private GeneralSettings createGeneralSettingsFromIntent() {
		return ((GeneralSettingsParcelableWrapper) getIntent().getExtras().get(ActivityLauncher.GENERAL_SETTINGS)).getSettings();
	}

	@Override
	public LocalPlayerSettings createLocalPlayerSettings() {
		return ((LocalPlayerSettingsParcellableWrapper) getIntent().getExtras().get(ActivityLauncher.LOCAL_PLAYER_SETTINGS)).getSettings();
	}

	@Override
	public int getNextPlayerId() {
		return this.playerCounter++;
	}

	public List<RoomEntry> getAllOtherPlayers() {
		return this.playersAA.getAllOtherPlayers();
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
		// TODO make independent of wlan vs bluetooth
		if (!(remoteCommunication instanceof BluetoothCommunication)) {
			new Thread(new Runnable() {

				@Override
				public void run() {
					WlanDevice device = new WlanDevice(getInputIp());
					remoteCommunication.connectToServer(device);
				}
			}).start();
		} else {
			this.remoteCommunication.searchServer();
		}
	}

	@Override
	public void errorOnBroadcastListening() {
		runOnUiThread(new Runnable() {

			@Override
			public void run() {
				String message = getString(R.string.could_not_connect);
				Toast.makeText(RoomActivity.this, message, Toast.LENGTH_SHORT).show();
			}
		});
	}

	@Override
	public List<PlayerProperties> getAllPlayersProperties() {
		List<PlayerProperties> properties = new ArrayList<PlayerProperties>(this.playersAA.getCount());
		for (RoomEntry e : getAllOtherPlayers()) {
			properties.add(e.getPlayerProperties());
		}
		properties.add(this.playersAA.getMyself().getPlayerProperties());
		return properties;
	}

	@Override
	public List<MySocket> getAllOtherSockets() {
		List<MySocket> sockets = new ArrayList<MySocket>(this.playersAA.getCount());
		for (RoomEntry e : getAllOtherPlayers()) {
			sockets.add(e.getSocket());
		}
		return sockets;
	}

	@Override
	public void playerDisconnected(Opponent opponent) {
		RoomEntry entry = playersAA.findEntry(opponent);
		playersAA.remove(entry);
	}
}
