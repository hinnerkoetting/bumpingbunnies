package de.oetting.bumpingbunnies.usecases.networkRoom;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;
import de.oetting.bumpingbunnies.R;
import de.oetting.bumpingbunnies.android.sql.AsyncDatabaseCreation;
import de.oetting.bumpingbunnies.android.sql.OnDatabaseCreation;
import de.oetting.bumpingbunnies.communication.bluetooth.BluetoothCommunicationFactory;
import de.oetting.bumpingbunnies.communication.bluetooth.BluetoothServerDevice;
import de.oetting.bumpingbunnies.core.configuration.GameParameterFactory;
import de.oetting.bumpingbunnies.core.game.ConnectionIdentifierFactory;
import de.oetting.bumpingbunnies.core.game.player.BunnyNameFactory;
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
import de.oetting.bumpingbunnies.core.networking.client.ConnectionToServerEstablisher;
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
import de.oetting.bumpingbunnies.model.configuration.NetworkType;
import de.oetting.bumpingbunnies.model.configuration.OpponentConfiguration;
import de.oetting.bumpingbunnies.model.configuration.PlayerProperties;
import de.oetting.bumpingbunnies.model.configuration.ServerSettings;
import de.oetting.bumpingbunnies.model.configuration.SettingsEntity;
import de.oetting.bumpingbunnies.model.configuration.WorldConfiguration;
import de.oetting.bumpingbunnies.model.game.objects.ConnectionIdentifier;
import de.oetting.bumpingbunnies.usecases.ActivityLauncher;
import de.oetting.bumpingbunnies.usecases.networkRoom.services.DummyConnectionToServer;
import de.oetting.bumpingbunnies.usecases.start.sql.DummySettingsDao;
import de.oetting.bumpingbunnies.usecases.start.sql.SettingsDao;
import de.oetting.bumpingbunnies.usecases.start.sql.SettingsStorage;

public class RoomActivity extends Activity implements ConnectToServerCallback, AcceptsClientConnections,
		ConnectionToServerSuccesfullCallback, OnBroadcastReceived, ConnectsToServer, DisplaysConnectedServers,
		PlayerDisconnectedCallback, ThreadErrorCallback, OnDatabaseCreation {

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
	private SettingsStorage settingsDao;
	private ProgressDialog progressDialog;
	private NetworkType activeConnection = NetworkType.WLAN;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		SocketStorage.getSingleton().removeListeners();
		setContentView(R.layout.activity_room);
		ListView list = getHostsView();
		list.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				hostsAdapter.onItemClick(position);
			}
		});
		this.hostsAdapter = new HostsListViewAdapter(getBaseContext(), this);
		this.remoteCommunication = new DummyCommunication();
		list.setAdapter(this.hostsAdapter);
		initRoom();
		this.connectedToServerService = new DummyConnectionToServer();
		this.broadcastService = new NetworkBroadcaster(this);
		listenForBroadcasts();
		settingsDao = new DummySettingsDao();
		new AsyncDatabaseCreation().createReadonlyDatabase(this, this);
		switchToWlan();
	}

	private void initRoom() {
		ListView players = (ListView) findViewById(R.id.room_players);
		this.playersAA = new RoomArrayAdapter(this, R.layout.room_player_entry);
		players.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				playersAA.onItemClick(position);
			}
		});
		players.setAdapter(this.playersAA);
	}

	private void switchToBluetooth() {
		LOGGER.info("selected bluetooth");
		activeConnection = NetworkType.BLUETOOTH;
		if (clientAccepter != null)
			clientAccepter.closeConnections();
		hostsAdapter.clear();
		this.remoteCommunication = BluetoothCommunicationFactory.create(BluetoothAdapter.getDefaultAdapter(), this);
		clientAccepter = BluetoothCommunicationFactory.createClientAccepter(BluetoothAdapter.getDefaultAdapter(), this,
				this);

		openHostOrClientBluetoothDialog();
	}

	private void openHostOrClientBluetoothDialog() {
		DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				switch (which) {
				case DialogInterface.BUTTON_POSITIVE:
					startGame();
					break;
				case DialogInterface.BUTTON_NEGATIVE:
					remoteCommunication.searchServer();
					break;
				}
			}
		};
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setMessage(R.string.host_bluetooth_game_question)
				.setPositiveButton(R.string.host_bluetooth_game, dialogClickListener)
				.setNegativeButton(R.string.client_bluetooth_game, dialogClickListener).show();
	}

	private void switchToWlan() {
		LOGGER.info("selected wlan");
		activeConnection = NetworkType.WLAN;
		if (clientAccepter != null)
			clientAccepter.closeConnections();
		hostsAdapter.clear();
		this.remoteCommunication = new DummyCommunication();
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
		this.remoteCommunication.closeConnections();
		for (ToClientConnector connectionToClient : this.connectionToClientServices) {
			connectionToClient.cancel();
		}
		if (progressDialog != null)
			progressDialog.dismiss();
	}

	@Override
	public void startConnectToServer(final ServerDevice device) {
		showLoadingAnimation();
		playersAA.clear();
		new Thread(new Runnable() {

			@Override
			public void run() {
				try {
					remoteCommunication.closeConnections();
					connectToServer(device);
				} catch (Exception e) {
					LOGGER.error("Error", e);
					displayCouldNotConnectException();
				}
			}

			private void connectToServer(ServerDevice device) {
				if (device instanceof BluetoothServerDevice) {
					try {
						// because of some bt discovery bug
						Thread.sleep(1500);
					} catch (InterruptedException e) {
					}
				}
				ConnectionToServerEstablisher connectThread = new ConnectionToServerEstablisher(device.createClientSocket(), RoomActivity.this);
				connectThread.start();
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
				onConnectionError();
			}

		});

	}

	private void onConnectionError() {
		hostsAdapter.clear();
		enableButtons(true);
		progressDialog.dismiss();
		playersAA.clear();
		addMyPlayerRoomEntry(getNextPlayerId());
	}

	private void enableButtons(boolean enable) {
		findViewById(R.id.room_start).setEnabled(enable);
		findViewById(R.id.room_add_ai).setEnabled(enable);
	}

	@Override
	public void clientConnectedSucessfull(final MySocket socket) {
		ToClientConnector connectionToClientService = ConnectionToClientServiceFactory.create(this, socket,
				new StrictNetworkToGameDispatcher(this), this, this);
		this.connectionToClientServices.add(connectionToClientService);
		connectionToClientService.onConnectToClient(socket);
		enableStartButton();
	}

	private List<OpponentConfiguration> createOtherPlayerconfigurations() {
		List<OpponentConfiguration> otherPlayers = new ArrayList<OpponentConfiguration>(this.playersAA.getCount() - 1);
		for (RoomEntry otherPlayer : this.playersAA.getAllOtherPlayers()) {
			AiModus aiMode = AiModus.NORMAL;

			OpponentConfiguration otherPlayerConfiguration = new OpponentConfiguration(aiMode,
					otherPlayer.getPlayerProperties(), otherPlayer.getOponent(), new NoopInputConfiguration());
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
			return new RoomEntry(playerProperties, ConnectionIdentifierFactory.createJoinedPlayer(
					playerProperties.getPlayerName(), playerProperties.getPlayerId()));
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
				Toast toast = Toast.makeText(getBaseContext(), R.string.connection_unsuccesful + message,
						Toast.LENGTH_SHORT);
				toast.show();
				onConnectionError();
			}
		});

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
		enableButtons(false);
		startGame();
	}

	private void startGame() {
		LOGGER.info("Starting game");
		showLoadingAnimation();
		// so that the animation is show first
		startGameLater();
	}

	private void startGameLater() {
		new Thread(new Runnable() {

			@Override
			public void run() {
				notifyClientsAboutlaunch();
				ServerSettings generalSettings = createGeneralSettings();
				launchGame(generalSettings, true);

			}
		}).start();
	}

	private void showLoadingAnimation() {
		progressDialog = new ProgressDialog(this);
		progressDialog.setTitle("Loading");
		progressDialog.setMessage("Loading. Please wait...");
		progressDialog.show();
		progressDialog.setContentView(R.layout.progress_dialog);
	}

	private void notifyClientsAboutlaunch() {
		SocketStorage singleton = SocketStorage.getSingleton();
		ServerSettings settings = createGeneralSettings();
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
		if (playersAA.myPlayerExists()) {
			launchGameActiviti();
		}
	}

	private void launchGameActiviti() {
		SettingsEntity settings = readSettingsFromDb();
		LocalSettings localSettings = createLocalSettings(settings);
		LocalPlayerSettings localPlayerSettings = createLocalPlayerSettings();

		int myPlayerId = this.playersAA.getMyself().getPlayerProperties().getPlayerId();
		List<OpponentConfiguration> otherPlayers = createOtherPlayerconfigurations();
		Configuration config = new Configuration(localSettings, generalSettings, otherPlayers, localPlayerSettings,
				asHost);
		GameStartParameter parameter = GameParameterFactory.createParameter(myPlayerId, config);
		sleep();
		ActivityLauncher.launchGame(this, parameter);
		finish();
	}

	@Override
	public LocalPlayerSettings createLocalPlayerSettings() {
		SettingsEntity settings = readSettingsFromDb();
		return new LocalPlayerSettings(settings.getPlayerName());
	}

	private ServerSettings createGeneralSettings() {
		WorldConfiguration world = WorldConfiguration.CLASSIC;
		ServerSettings generalSettings = createServerSettings(world);
		return generalSettings;
	}

	private ServerSettings createServerSettings(WorldConfiguration world) {
		SettingsEntity settings = readSettingsFromDb();
		return new ServerSettings(world, settings.getSpeed(), activeConnection, settings.getVictoryLimit());
	}

	private LocalSettings createLocalSettings(SettingsEntity settings) {
		return new LocalSettings(settings.getInputConfiguration(), settings.getZoom(), settings.isPlayMusic(),
				settings.isPlaySound(), settings.isLefthanded());
	}

	private SettingsEntity readSettingsFromDb() {
		return settingsDao.readStoredSettings();
	}

	private void sleep() {
		try {
			Thread.sleep(2500);
		} catch (InterruptedException e) {
		}
	}

	@Override
	public int getNextPlayerId() {
		return this.playerCounter++;
	}

	private List<RoomEntry> getAllOtherPlayers() {
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
	public void playerDisconnected(final ConnectionIdentifier opponent) {
		runOnUiThread(new Runnable() {

			@Override
			public void run() {
				RoomEntry entry = playersAA.findEntry(opponent);

				playersAA.remove(entry);
			}
		});
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
				ActivityLauncher.startRoom(RoomActivity.this);
			}
		});
	}

	public void onClickAddAi(View view) {
		int nextPlayerId = getNextPlayerId();
		String playerName = BunnyNameFactory.createAiName(nextPlayerId);
		PlayerProperties properties = new PlayerProperties(nextPlayerId, playerName);
		addPlayerEntry(new NoopSocket(ConnectionIdentifierFactory.createAiPlayer(playerName)), properties, 0);
	}

	@Override
	public void databaseCreated(SQLiteDatabase database) {
		LOGGER.info("Db created");
		this.settingsDao = new SettingsDao(database, this);
		addMyPlayerRoomEntry(getNextPlayerId());
	}

	@Override
	protected void onResume() {
		super.onResume();
		setMyPlayerName();
	}

	private void setMyPlayerName() {
		playersAA.setMyPlayerName(readSettingsFromDb().getPlayerName());
	}

	@Override
	public void onInitializationError(final String message) {
		runOnUiThread(new Runnable() {

			@Override
			public void run() {
				Toast toast = Toast.makeText(RoomActivity.this, message, Toast.LENGTH_LONG);
				toast.show();
			}
		});
	}

	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {
		menu.clear();
		new RoomMenu().createMenu(menu, activeConnection);
		return super.onPrepareOptionsMenu(menu);
	}

	@Override
	public boolean onMenuItemSelected(int featureId, MenuItem item) {
		if (item.getItemId() == RoomMenu.SETTINGS_ID)
			ActivityLauncher.startSettings(this);
		else if (item.getItemId() == RoomMenu.BLUETOOTH_ID)
			switchToBluetooth();
		else {
			switchToWlan();
			Toast.makeText(this, R.string.wlan_enabled, Toast.LENGTH_LONG).show();
		}
		return super.onMenuItemSelected(featureId, item);
	}

}
