package de.oetting.bumpingbunnies.core.networking.server;

import java.util.ArrayList;
import java.util.List;

import de.oetting.bumpingbunnies.core.game.player.BunnyFactory;
import de.oetting.bumpingbunnies.core.game.steps.PlayerJoinListener;
import de.oetting.bumpingbunnies.core.network.CouldNotStartServerException;
import de.oetting.bumpingbunnies.core.network.MySocket;
import de.oetting.bumpingbunnies.core.network.NewClientsAccepter;
import de.oetting.bumpingbunnies.core.network.StrictNetworkToGameDispatcher;
import de.oetting.bumpingbunnies.core.networking.init.ClientAccepter;
import de.oetting.bumpingbunnies.core.networking.messaging.MessageParserFactory;
import de.oetting.bumpingbunnies.core.networking.receive.PlayerDisconnectedCallback;
import de.oetting.bumpingbunnies.core.networking.sender.GameSettingSender;
import de.oetting.bumpingbunnies.core.networking.sender.SimpleNetworkSender;
import de.oetting.bumpingbunnies.core.networking.sender.StartGameSender;
import de.oetting.bumpingbunnies.core.threads.ThreadErrorCallback;
import de.oetting.bumpingbunnies.logger.Logger;
import de.oetting.bumpingbunnies.logger.LoggerFactory;
import de.oetting.bumpingbunnies.model.configuration.Configuration;
import de.oetting.bumpingbunnies.model.configuration.PlayerProperties;
import de.oetting.bumpingbunnies.model.game.objects.Bunny;
import de.oetting.bumpingbunnies.model.game.world.World;

/**
 * Controls logic which a host must fulfill. Sends Broadcast messages to send
 * its IP for new clients, accepts connections from clients and adds them to the
 * game.
 * 
 */
public class HostNewClientsAccepter implements NewClientsAccepter {

	private static final Logger LOGGER = LoggerFactory.getLogger(HostNewClientsAccepter.class);

	private final List<MakesGameVisible> broadcaster;
	private final ClientAccepter remoteCommunication;
	private final World world;
	private final PlayerDisconnectedCallback callback;
	private final ThreadErrorCallback errorCallback;
	private final Configuration configuration;

	private PlayerJoinListener mainJoinListener;

	public HostNewClientsAccepter(List<MakesGameVisible> broadcaster, ClientAccepter remoteCommunication, World world,
			Configuration configuration, PlayerDisconnectedCallback callback, ThreadErrorCallback errorCallback) {
		this.broadcaster = broadcaster;
		this.remoteCommunication = remoteCommunication;
		this.world = world;
		this.configuration = configuration;
		this.callback = callback;
		this.errorCallback = errorCallback;
	}

	@Override
	public void start() {
		new Thread(new Runnable() {

			@Override
			public void run() {
				try {
					LOGGER.info("Start to accept clients");
					makeVisible();
					remoteCommunication.startThreadToAcceptClients();
				} catch (CouldNotStartServerException e) {
					LOGGER.error("Error", e);
					errorCallback.onInitializationError("Error: Could not make device discoverable");
				}
			}
		}).start();
	}
	
	private void makeVisible() {
		for (MakesGameVisible broadcast: broadcaster)
			broadcast.makeVisible(configuration.getLocalPlayerSettings().getPlayerName());
		
	}

	@Override
	public void cancel() {
		for (MakesGameVisible broadcast: broadcaster)
			broadcast.cancel();
		this.remoteCommunication.closeConnections();
	}

	@Override
	public void clientConnectedSucessfull(MySocket socket) {
		ToClientConnector connectionToClientService = ConnectionToClientServiceFactory.create(this, socket,
				new StrictNetworkToGameDispatcher(callback), callback, errorCallback);
		connectionToClientService.onConnectToClient(socket);
	}

	@Override
	public void addPlayerEntry(MySocket socket, PlayerProperties playerProperties, int socketIndex) {
		Bunny player = new BunnyFactory(configuration.getGeneralSettings().getSpeedSetting()).createPlayer(
				playerProperties.getPlayerId(), playerProperties.getPlayerName(), socket.getConnectionIdentifier());
		LOGGER.info("Player joins %s", player);
		signalPlayerToStartTheGame(socket);
		this.mainJoinListener.newEvent(player);
	}

	private void signalPlayerToStartTheGame(MySocket socket) {
		SimpleNetworkSender sender = new SimpleNetworkSender(MessageParserFactory.create(), socket, callback);
		new GameSettingSender(sender).sendMessage(configuration.getGeneralSettings());
		new StartGameSender(sender).sendMessage("");
	}

	@Override
	public int getNextPlayerId() {
		return this.world.getNextBunnyId();
	}

	@Override
	public List<PlayerProperties> getAllPlayersProperties() {
		List<PlayerProperties> properties = new ArrayList<PlayerProperties>(this.world.getAllConnectedBunnies().size());
		for (Bunny p : world.getAllConnectedBunnies()) {
			properties.add(new PlayerProperties(p.id(), p.getName()));
		}
		return properties;
	}

	@Override
	public void setMain(PlayerJoinListener main) {
		this.mainJoinListener = main;
	}

}
