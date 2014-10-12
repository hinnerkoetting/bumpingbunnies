package de.oetting.bumpingbunnies.core.networking.server;

import java.util.ArrayList;
import java.util.List;

import de.oetting.bumpingbunnies.core.game.player.PlayerFactory;
import de.oetting.bumpingbunnies.core.game.steps.PlayerJoinListener;
import de.oetting.bumpingbunnies.core.network.MySocket;
import de.oetting.bumpingbunnies.core.network.NewClientsAccepter;
import de.oetting.bumpingbunnies.core.network.StrictNetworkToGameDispatcher;
import de.oetting.bumpingbunnies.core.networking.init.ConnectionEstablisher;
import de.oetting.bumpingbunnies.core.networking.messaging.MessageParserFactory;
import de.oetting.bumpingbunnies.core.networking.receive.PlayerDisconnectedCallback;
import de.oetting.bumpingbunnies.core.networking.sender.GameSettingSender;
import de.oetting.bumpingbunnies.core.networking.sender.SimpleNetworkSender;
import de.oetting.bumpingbunnies.core.networking.sender.StartGameSender;
import de.oetting.bumpingbunnies.core.world.World;
import de.oetting.bumpingbunnies.logger.Logger;
import de.oetting.bumpingbunnies.logger.LoggerFactory;
import de.oetting.bumpingbunnies.model.configuration.PlayerProperties;
import de.oetting.bumpingbunnies.model.configuration.ServerSettings;
import de.oetting.bumpingbunnies.model.game.objects.Player;

/**
 * Controls logic which a host must fulfill. Sends Broadcast messages to send
 * its IP for new clients, accepts connections from clients and adds them to the
 * game.
 * 
 */
public class HostNewClientsAccepter implements NewClientsAccepter {

	private static final Logger LOGGER = LoggerFactory.getLogger(HostNewClientsAccepter.class);
	private final NetworkBroadcaster broadcaster;
	private final ConnectionEstablisher remoteCommunication;
	private final World world;
	private final ServerSettings generalSettings;
	private final PlayerDisconnectedCallback callback;
	private PlayerJoinListener mainJoinListener;

	public HostNewClientsAccepter(NetworkBroadcaster broadcaster, ConnectionEstablisher remoteCommunication, World world, ServerSettings generalSettings,
			PlayerDisconnectedCallback callback) {
		this.broadcaster = broadcaster;
		this.remoteCommunication = remoteCommunication;
		this.world = world;
		this.generalSettings = generalSettings;
		this.callback = callback;
	}

	@Override
	public void start() {
		LOGGER.info("Start to accept clients");
		this.broadcaster.startRegularServerBroadcast();
		this.remoteCommunication.startThreadToAcceptClients();
	}

	@Override
	public void cancel() {
		this.broadcaster.cancel();
		this.remoteCommunication.closeOpenConnections();
	}

	@Override
	public void clientConnectedSucessfull(MySocket socket) {
		ToClientConnector connectionToClientService = ConnectionToClientServiceFactory.create(this, socket, new StrictNetworkToGameDispatcher(callback),
				callback);
		connectionToClientService.onConnectToClient(socket);
	}

	@Override
	public void addPlayerEntry(MySocket socket, PlayerProperties playerProperties, int socketIndex) {
		Player player = new PlayerFactory(this.generalSettings.getSpeedSetting()).createPlayer(playerProperties.getPlayerId(),
				playerProperties.getPlayerName(), socket.getOwner());
		LOGGER.info("Player joins %s", player);
		signalPlayerToStartTheGame(socket);
		this.mainJoinListener.newEvent(player);
	}

	private void signalPlayerToStartTheGame(MySocket socket) {
		SimpleNetworkSender sender = new SimpleNetworkSender(MessageParserFactory.create(), socket, callback);
		new GameSettingSender(sender).sendMessage(this.generalSettings);
		new StartGameSender(new SimpleNetworkSender(MessageParserFactory.create(), socket, callback)).sendMessage("");
	}

	@Override
	public int getNextPlayerId() {
		return this.world.getNextPlayerId();
	}

	@Override
	public List<PlayerProperties> getAllPlayersProperties() {
		List<PlayerProperties> properties = new ArrayList<PlayerProperties>(this.world.getAllPlayer().size());
		for (Player p : world.getAllPlayer()) {
			properties.add(new PlayerProperties(p.id(), p.getName()));
		}
		return properties;
	}

	@Override
	public void setMain(PlayerJoinListener main) {
		this.mainJoinListener = main;
	}

}
