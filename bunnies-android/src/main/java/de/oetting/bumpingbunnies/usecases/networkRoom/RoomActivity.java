package de.oetting.bumpingbunnies.usecases.networkRoom;

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
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.Toast;
import de.oetting.bumpingbunnies.R;
import de.oetting.bumpingbunnies.android.parcel.GeneralSettingsParcelableWrapper;
import de.oetting.bumpingbunnies.android.parcel.LocalPlayerSettingsParcellableWrapper;
import de.oetting.bumpingbunnies.android.parcel.LocalSettingsParcelableWrapper;
import de.oetting.bumpingbunnies.communication.bluetooth.BluetoothCommunicationFactory;
import de.oetting.bumpingbunnies.communication.bluetooth.BluetoothServerDevice;
import de.oetting.bumpingbunnies.communication.wlan.WlanCommunicationFactory;
import de.oetting.bumpingbunnies.core.configuration.GameParameterFactory;
import de.oetting.bumpingbunnies.core.game.ConnectionIdentifierFactory;
import de.oetting.bumpingbunnies.core.input.NoopInputConfiguration;
import de.oetting.bumpingbunnies.core.network.AcceptsClientConnections;
import de.oetting.bumpingbunnies.core.network.ConnectsToServer;
import de.oetting.bumpingbunnies.core.network.DummyCommunication;
import de.oetting.bumpingbunnies.core.network.MySocket;
import de.oetting.bumpingbunnies.core.network.NoopSocket;
import de.oetting.bumpingbunnies.core.network.ServerDevice;
import de.oetting.bumpingbunnies.core.network.StrictNetworkToGameDispatcher;
import de.oetting.bumpingbunnies.core.network.room.Host;
import de.oetting.bumpingbunnies.core.network.room.RoomEntry;
import de.oetting.bumpingbunnies.core.network.sockets.SocketStorage;
import de.oetting.bumpingbunnies.core.networking.LocalPlayerEntry;
import de.oetting.bumpingbunnies.core.networking.client.ConnectionToServer;
import de.oetting.bumpingbunnies.core.networking.client.CouldNotOpenBroadcastSocketException;
import de.oetting.bumpingbunnies.core.networking.client.DisplaysConnectedServers;
import de.oetting.bumpingbunnies.core.networking.client.OnBroadcastReceived;
import de.oetting.bumpingbunnies.core.networking.client.SetupConnectionWithServer;
import de.oetting.bumpingbunnies.core.networking.init.ClientAccepter;
import de.oetting.bumpingbunnies.core.networking.init.ConnectionEstablisher;
import de.oetting.bumpingbunnies.core.networking.receive.PlayerDisconnectedCallback;
import de.oetting.bumpingbunnies.core.networking.sender.GameSettingSender;
import de.oetting.bumpingbunnies.core.networking.sender.SimpleNetworkSender;
import de.oetting.bumpingbunnies.core.networking.sender.SimpleNetworkSenderFactory;
import de.oetting.bumpingbunnies.core.networking.sender.StartGameSender;
import de.oetting.bumpingbunnies.core.networking.server.ConnectionToClientServiceFactory;
import de.oetting.bumpingbunnies.core.networking.server.NetworkBroadcaster;
import de.oetting.bumpingbunnies.core.networking.server.ToClientConnector;
import de.oetting.bumpingbunnies.core.threads.ThreadErrorCallback;
import de.oetting.bumpingbunnies.logger.Logger;
import de.oetting.bumpingbunnies.logger.LoggerFactory;
import de.oetting.bumpingbunnies.model.configuration.AiModus;
import de.oetting.bumpingbunnies.model.configuration.Configuration;
import de.oetting.bumpingbunnies.model.configuration.GameStartParameter;
import de.oetting.bumpingbunnies.model.configuration.LocalPlayerSettings;
import de.oetting.bumpingbunnies.model.configuration.LocalSettings;
import de.oetting.bumpingbunnies.model.configuration.OpponentConfiguration;
import de.oetting.bumpingbunnies.model.configuration.PlayerProperties;
import de.oetting.bumpingbunnies.model.configuration.ServerSettings;
import de.oetting.bumpingbunnies.model.game.objects.ConnectionIdentifier;
import de.oetting.bumpingbunnies.usecases.ActivityLauncher;
import de.oetting.bumpingbunnies.usecases.networkRoom.services.DummyConnectionToServer;

public class RoomActivity extends Activity implements ConnectToServerCallback, AcceptsClientConnections, ConnectionToServerSuccesfullCallback,
		OnBroadcastReceived, ConnectsToServer, DisplaysConnectedServers, PlayerDisconnectedCallback, ThreadErrorCallback {

	private static final Logger LOGGER = LoggerFactory.getLogger(RoomActivity.class);
	public final static int REQUEST_BT_ENABLE = 1000;
	private HostsListViewAdapter hostsAdapter;

	private ConnectionEstablisher remoteCommunication;
	private ClientAccepter clientAccepter;
	private RoomArrayAdapter playersAA;
	private NetworkBroadcaster broadcastService;

	private int playerCounter = 0;
	private ConnectionToServer connectedToServerService;
	private List<ToClientConnector> connectionToClientServices = new LinkedList<ToClientConnector>();
	private boolean canLaunchGame = false;
	private ServerSettings generalSettings;
	private boolean asHost;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		SocketStorage.getSingleton().removeListeners();
		setContentView(R.layout.activity_room);
		ListView list = getHostsView();
		this.hostsAdapter = new HostsListViewAdapter(getBaseContext(), this);
		this.remoteCommunication = new DummyCommunication();
		list.setAdapter(this.hostsAdapter);
		initRemoteCbListeners();
		initRoom();
		this.connectedToServerService = new DummyConnectionToServer();
		this.broadcastService = new NetworkBroadcaster(this);
		listenForBroadcasts();
		addMyPlayerRoomEntry(getNextPlayerId());
	}

	private void initRoom() {
		ListView players = (ListView) findViewById(R.id.room_players);
		this.playersAA = new RoomArrayAdapter(this, R.layout.room_player_entry);
		players.setAdapter(this.playersAA);
	}

	private void switchToBluetooth() {
		LOGGER.info("selected bluetooth");
		this.remoteCommunication = BluetoothCommunicationFactory.create(BluetoothAdapter.getDefaultAdapter(), this);
		clientAccepter = BluetoothCommunicationFactory.createClientAccepter(BluetoothAdapter.getDefaultAdapter(), this, this);
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
		this.hostsAdapter.clear();
		for (BluetoothDevice device : pairedDevices) {
			hostsAdapter.add(createBluetoothHostsEntry(device));
		}
	}

	private void listenForBroadcasts() {
		try {
			broadcastService.listenForBroadCasts(this);
		} catch (CouldNotOpenBroadcastSocketException e) {
			displayErrorAddressInUse();
			LOGGER.warn("Error when trying to search for host", e);
		}
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
		this.clientAccepter.startThreadToAcceptClients();
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
		findViewById(R.id.room_start).setEnabled(enable);
		findViewById(R.id.room_add_ai).setEnabled(enable);
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
		ToClientConnector connectionToClientService = ConnectionToClientServiceFactory
				.create(this, socket, new StrictNetworkToGameDispatcher(this), this, this);
		this.connectionToClientServices.add(connectionToClientService);
		connectionToClientService.onConnectToClient(socket);
		enableStartButton();
	}

	private List<OpponentConfiguration> createOtherPlayerconfigurations() {
		List<OpponentConfiguration> otherPlayers = new ArrayList<OpponentConfiguration>(this.playersAA.getCount() - 1);
		for (RoomEntry otherPlayer : this.playersAA.getAllOtherPlayers()) {
			AiModus aiMode = AiModus.NORMAL;

			OpponentConfiguration otherPlayerConfiguration = new OpponentConfiguration(aiMode, otherPlayer.getPlayerProperties(), otherPlayer.getOponent(),
					new NoopInputConfiguration());
			otherPlayers.add(otherPlayerConfiguration);
		}
		return otherPlayers;
	}

	@Override
	public void addPlayerEntry(MySocket socket, PlayerProperties playerProperties, int socketIndex) {
		LOGGER.info("adding player info %d", playerProperties.getPlayerId());
		RoomEntry entry = createRoomEntry(socket, playerProperties);
		addPlayerEntry(entry);
	}

	private RoomEntry createRoomEntry(MySocket socket, PlayerProperties playerProperties) {
		if (socket.getConnectionIdentifier().isDirectlyConnected() || socket.getConnectionIdentifier().isLocalPlayer())
			return new RoomEntry(playerProperties, socket.getConnectionIdentifier());
		else
			return new RoomEntry(playerProperties, ConnectionIdentifierFactory.createJoinedPlayer(playerProperties.getPlayerName(),
					playerProperties.getPlayerId()));
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
			public synchronized void run() {
				LocalPlayerSettings settings = createLocalPlayerSettings();
				PlayerProperties singlePlayerProperties = new PlayerProperties(myPlayerId, settings.getPlayerName());
				RoomActivity.this.playersAA.addMe(new LocalPlayerEntry(singlePlayerProperties));
				RoomActivity.this.playersAA.notifyDataSetChanged();
				if (canLaunchGame) {
					launchGameActiviti();
				}
			}
		});

	}

	public void addServer(BluetoothDevice device) {
		LOGGER.info("Adding server");
		hostsAdapter.add(createBluetoothHostsEntry(device));
		hostsAdapter.notifyDataSetChanged();
	}

	private Host createBluetoothHostsEntry(BluetoothDevice device) {
		return new Host(new BluetoothServerDevice(device));
	}

	@Override
	public void connectToServerSuccesfull(final MySocket socket) {
		this.connectedToServerService = new SetupConnectionWithServer(socket, this, this, this, this);
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
		ServerSettings generalSettings = createGeneralSettingsFromIntent();
		launchGame(generalSettings, true);
	}

	private void notifyClientsAboutlaunch() {
		SocketStorage singleton = SocketStorage.getSingleton();
		ServerSettings settings = createGeneralSettingsFromIntent();
		synchronized (singleton) {
			for (MySocket socket : singleton.getAllSockets()) {
				SimpleNetworkSender networkSender = SimpleNetworkSenderFactory.createNetworkSender(socket, this);
				new GameSettingSender(networkSender).sendMessage(settings);
				new StartGameSender(networkSender).sendMessage("");
			}
		}
	}

	@Override
	public synchronized void launchGame(ServerSettings generalSettings, boolean asHost) {
		this.generalSettings = generalSettings;
		this.asHost = asHost;
		canLaunchGame = true;
		launchGameActiviti();
	}

	private void launchGameActiviti() {
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

	private ServerSettings createGeneralSettingsFromIntent() {
		return ((GeneralSettingsParcelableWrapper) getIntent().getExtras().get(ActivityLauncher.GENERAL_SETTINGS)).getSettings();
	}

	@Override
	public LocalPlayerSettings createLocalPlayerSettings() {
		Object object = getIntent().getExtras().get(ActivityLauncher.LOCAL_PLAYER_SETTINGS);
		return ((LocalPlayerSettingsParcellableWrapper) object).getSettings();
	}

	@Override
	public int getNextPlayerId() {
		return this.playerCounter++;
	}

	public List<RoomEntry> getAllOtherPlayers() {
		return this.playersAA.getAllOtherPlayers();
	}

	@Override
	public void broadcastReceived(final ServerDevice device) {
		runOnUiThread(new Runnable() {

			@Override
			public void run() {
				Host object = new Host(device);
				if (!hostsAdapter.contains(object)) {
					hostsAdapter.add(object);
				}
			}
		});
	}

	private ListView getHostsView() {
		return (ListView) findViewById(R.id.hosts_list);
	}

	public void onClickConnect(View v) {
		playersAA.clear();
		new Thread(new Runnable() {

			@Override
			public void run() {
				Host selectedItem = (Host) getHostsView().getSelectedItem();
				if (selectedItem != null) {
					ServerDevice device = selectedItem.getDevice();
					remoteCommunication.connectToServer(device);
				}
			}
		}).start();
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
	public void playerDisconnected(ConnectionIdentifier opponent) {
		RoomEntry entry = playersAA.findEntry(opponent);
		playersAA.remove(entry);
	}

	@Override
	public void playerDisconnected(int playerId) {
		RoomEntry entry = playersAA.findEntry(playerId);
		playersAA.remove(entry);
	}

	@Override
	public void onThreadError() {
		runOnUiThread(new Runnable() {

			@Override
			public void run() {
				String message = getString(R.string.unknown_error);
				Toast.makeText(RoomActivity.this, message, Toast.LENGTH_SHORT).show();
				ActivityLauncher.toStart(RoomActivity.this);
			}
		});
	}

	public void onClickAddAi(View view) {
		int nextPlayerId = getNextPlayerId();
		String playerName = "AI" + nextPlayerId;
		PlayerProperties properties = new PlayerProperties(nextPlayerId, playerName);
		addPlayerEntry(new NoopSocket(ConnectionIdentifierFactory.createAiPlayer(playerName)), properties, 0);
	}
	
	public void onClickSettings(View view) {
		ActivityLauncher.startSettings(this);
	}
	
}
