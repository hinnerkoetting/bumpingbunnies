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
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.Toast;
import de.oetting.bumpingbunnies.R;
import de.oetting.bumpingbunnies.logger.Logger;
import de.oetting.bumpingbunnies.logger.LoggerFactory;
import de.oetting.bumpingbunnies.usecases.ActivityLauncher;
import de.oetting.bumpingbunnies.usecases.game.android.SocketStorage;
import de.oetting.bumpingbunnies.usecases.game.businesslogic.GameStartParameter;
import de.oetting.bumpingbunnies.usecases.game.communication.DefaultNetworkListener;
import de.oetting.bumpingbunnies.usecases.game.communication.MessageIds;
import de.oetting.bumpingbunnies.usecases.game.communication.MessageParser;
import de.oetting.bumpingbunnies.usecases.game.communication.NetworkReceiver;
import de.oetting.bumpingbunnies.usecases.game.communication.NetworkToGameDispatcher;
import de.oetting.bumpingbunnies.usecases.game.communication.SimpleNetworkSender;
import de.oetting.bumpingbunnies.usecases.game.communication.factories.MessageParserFactory;
import de.oetting.bumpingbunnies.usecases.game.communication.factories.NetworkReceiverDispatcherThreadFactory;
import de.oetting.bumpingbunnies.usecases.game.communication.factories.SimpleNetworkSenderFactory;
import de.oetting.bumpingbunnies.usecases.game.communication.objects.JsonWrapper;
import de.oetting.bumpingbunnies.usecases.game.configuration.Configuration;
import de.oetting.bumpingbunnies.usecases.game.configuration.GeneralSettings;
import de.oetting.bumpingbunnies.usecases.game.configuration.LocalSettings;
import de.oetting.bumpingbunnies.usecases.game.configuration.OtherPlayerConfiguration;
import de.oetting.bumpingbunnies.usecases.game.configuration.PlayerProperties;
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

	private static final Logger LOGGER = LoggerFactory
			.getLogger(RoomActivity.class);
	public final static int REQUEST_BT_ENABLE = 1000;
	private BluetoothArrayAdapter listAdapter;

	private RemoteCommunication remoteCommunication;
	private RoomArrayAdapter playersAA;
	private int myPlayerId;
	private NetworkReceiver networkReceiver;
	private GeneralSettings generalSettingsFromNetwork;
	private int playerCounter = 0;

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
		this.playerCounter = 0;
		this.myPlayerId = this.playerCounter++;
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
		manageConnectedClient(socket);
		enableStartButton();
	}

	private List<OtherPlayerConfiguration> createOtherPlayerconfigurations(
			int myPlayerId) {
		List<OtherPlayerConfiguration> otherPlayers = new ArrayList<OtherPlayerConfiguration>(
				this.playersAA.getCount() - 1);
		for (RoomEntry otherPlayer : this.playersAA.getAllOtherPlayers()) {
			NetworkFactory factory = new NetworkFactory(
					otherPlayer.getSocket(), otherPlayer.getSocketIndex());

			OtherPlayerConfiguration otherPlayerConfiguration = new OtherPlayerConfiguration(
					factory, otherPlayer.getPlayerProperties());
			otherPlayers.add(otherPlayerConfiguration);
		}
		return otherPlayers;
	}

	private void manageConnectedClient(MySocket socket) {
		notifyAboutExistingPlayers(socket);
		int nextPlayerId = this.playersAA.getCount();
		int socketIndex = SocketStorage.getSingleton().addSocket(socket);
		addPlayerEntry(socket, nextPlayerId, socketIndex);

	}

	private void notifyAboutExistingPlayers(MySocket socket) {
		SimpleNetworkSender networkSender = SimpleNetworkSenderFactory
				.createNetworkSender(socket);
		MessageParser parser = MessageParserFactory.create();
		for (RoomEntry otherPlayer : this.playersAA.getAllOtherPlayers()) {
			informClientAboutPlayer(otherPlayer, networkSender, parser);
		}
		informClientAboutPlayer(this.playersAA.getMyself(), networkSender,
				parser);
		sendClientPlayer(networkSender, parser);
	}

	private void sendClientPlayer(SimpleNetworkSender networkSender,
			MessageParser parser) {
		JsonWrapper clientPlayer = createJsonPlayerId(parser);
		networkSender.sendMessage(clientPlayer);
	}

	private void informClientAboutPlayer(RoomEntry player,
			SimpleNetworkSender networkSender, MessageParser parser) {
		JsonWrapper message = createPlayerInfoMessage(player, parser);
		networkSender.sendMessage(message);
	}

	private JsonWrapper createPlayerInfoMessage(RoomEntry entry,
			MessageParser parser) {
		return new JsonWrapper(MessageIds.SEND_OTHER_PLAYER_ID,
				parser.encodeMessage(Integer.valueOf(entry
						.getPlayerConfiguration().getPlayerId())));
	}

	private void addPlayerEntry(MySocket socket, int nextPlayerId,
			int socketIndex) {

		LOGGER.info("adding player info %d", nextPlayerId);
		RoomEntry entry = new RoomEntry(new PlayerProperties(nextPlayerId,
				"Player " + nextPlayerId), socket, socketIndex);
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

	public void createNewRoom() {
		LOGGER.info("Creating new room");
		addMyPlayerRoomEntry();
	}

	private void addMyPlayerRoomEntry() {
		runOnUiThread(new Runnable() {

			@Override
			public void run() {
				PlayerProperties singlePlayerProperties = new PlayerProperties(
						RoomActivity.this.myPlayerId, "Me");
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
		manageConnectToServer(socket);
		this.networkReceiver = NetworkReceiverDispatcherThreadFactory
				.createRoomNetworkReceiver(socket);
		addObserver();
		this.networkReceiver.start();
	}

	private void addObserver() {
		LOGGER.info("registering observers to receive messages from server");
		NetworkToGameDispatcher gameDispatcher = this.networkReceiver
				.getGameDispatcher();
		gameDispatcher.addObserver(MessageIds.START_GAME_ID,
				new DefaultNetworkListener<Object>(Object.class) {

					@Override
					public void receiveMessage(Object message) {
						RoomActivity.this.networkReceiver.cancel();
						launchGame(RoomActivity.this.generalSettingsFromNetwork);
					}
				});
		gameDispatcher.addObserver(MessageIds.SEND_CONFIGURATION_ID,
				new DefaultNetworkListener<GeneralSettings>(
						GeneralSettings.class) {

					@Override
					public void receiveMessage(GeneralSettings message) {
						RoomActivity.this.generalSettingsFromNetwork = message;
					}
				});
		gameDispatcher.addObserver(MessageIds.SEND_CLIENT_PLAYER_ID,
				new DefaultNetworkListener<Integer>(Integer.class) {

					@Override
					public void receiveMessage(Integer object) {
						RoomActivity.this.myPlayerId = object;
						addMyPlayerRoomEntry();
					}
				});
		gameDispatcher.addObserver(MessageIds.SEND_OTHER_PLAYER_ID,
				new DefaultNetworkListener<Integer>(Integer.class) {

					@Override
					public void receiveMessage(Integer object) {
						MySocket serverSocket = SocketStorage.getSingleton()
								.getSocket();
						addPlayerEntry(serverSocket, object, 0);
					}
				});
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

	private void manageConnectToServer(MySocket socket) {
		int socketIndex = SocketStorage.getSingleton().addSocket(socket);
	}

	public void onClickStart(View v) {
		notifyClientsAboutlaunch();
		GeneralSettings generalSettings = createGeneralSettingsFromIntent();
		launchGame(generalSettings);
	}

	private void launchGame(GeneralSettings generalSettings) {

		LocalSettings localSettings = (LocalSettings) getIntent().getExtras()
				.get(ActivityLauncher.LOCAL_SETTINGS);

		List<OtherPlayerConfiguration> otherPlayers = createOtherPlayerconfigurations(this.myPlayerId);
		Configuration config = new Configuration(localSettings,
				generalSettings, otherPlayers);
		GameStartParameter parameter = GameParameterFactory.createParameter(
				this.myPlayerId, config);
		ActivityLauncher.launchGame(this, parameter);
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

	private JsonWrapper createJsonPlayerId(MessageParser parser) {
		Integer playerId = this.playerCounter++;
		return new JsonWrapper(MessageIds.SEND_CLIENT_PLAYER_ID,
				parser.encodeMessage(playerId));
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

}
