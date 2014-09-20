package de.oetting.bumpingbunnies.usecases.game.communication;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import de.oetting.bumpingbunnies.communication.RemoteCommunication;
import de.oetting.bumpingbunnies.core.game.player.PlayerFactory;
import de.oetting.bumpingbunnies.core.networking.MySocket;
import de.oetting.bumpingbunnies.core.world.World;
import de.oetting.bumpingbunnies.logger.Logger;
import de.oetting.bumpingbunnies.logger.LoggerFactory;
import de.oetting.bumpingbunnies.usecases.game.android.SocketStorage;
import de.oetting.bumpingbunnies.usecases.game.businesslogic.GameMain;
import de.oetting.bumpingbunnies.usecases.game.communication.factories.MessageParserFactory;
import de.oetting.bumpingbunnies.usecases.game.configuration.GeneralSettings;
import de.oetting.bumpingbunnies.usecases.game.configuration.PlayerProperties;
import de.oetting.bumpingbunnies.usecases.game.model.Player;
import de.oetting.bumpingbunnies.usecases.networkRoom.communication.generalSettings.GameSettingSender;
import de.oetting.bumpingbunnies.usecases.networkRoom.communication.startGame.StartGameSender;
import de.oetting.bumpingbunnies.usecases.networkRoom.services.BroadcastService;
import de.oetting.bumpingbunnies.usecases.networkRoom.services.ConnectionToClientService;
import de.oetting.bumpingbunnies.usecases.networkRoom.services.factory.ConnectionToClientServiceFactory;

/**
 * Controls logic which a host must fulfill. Sends Broadcast messages to send
 * its IP for new clients, accepts connections from clients and adds them to the
 * game.
 * 
 */
public class HostNewClientsAccepter implements NewClientsAccepter {

	private static final Logger LOGGER = LoggerFactory.getLogger(HostNewClientsAccepter.class);
	private final BroadcastService broadcaster;
	private final RemoteCommunication remoteCommunication;
	private final World world;
	private final GeneralSettings generalSettings;
	private GameMain main;
	private List<ConnectionToClientService> connectionToClientServices;

	public HostNewClientsAccepter(BroadcastService broadcaster, RemoteCommunication remoteCommunication, World world,
			GeneralSettings generalSettings) {
		super();
		this.broadcaster = broadcaster;
		this.remoteCommunication = remoteCommunication;
		this.world = world;
		this.generalSettings = generalSettings;
		this.connectionToClientServices = new LinkedList<ConnectionToClientService>();
	}

	@Override
	public void start() {
		this.broadcaster.startRegularServerBroadcast();
		this.remoteCommunication.startServer();
	}

	@Override
	public void cancel() {
		this.broadcaster.cancel();
		this.remoteCommunication.closeOpenConnections();
		for (ConnectionToClientService cts : this.connectionToClientServices) {
			cts.cancel();
		}
	}

	@Override
	public void clientConnectedSucessfull(MySocket socket) {
		ConnectionToClientService connectionToClientService = ConnectionToClientServiceFactory.create(this, socket,
				new StrictNetworkToGameDispatcher());
		this.connectionToClientServices.add(connectionToClientService);
		connectionToClientService.onConnectToClient(socket);
	}

	@Override
	public void addPlayerEntry(MySocket socket, PlayerProperties playerProperties, int socketIndex) {
		Player player = new PlayerFactory(this.generalSettings.getSpeedSetting()).createPlayer(playerProperties.getPlayerId(),
				playerProperties.getPlayerName(), socket.getOwner());
		LOGGER.info("Player joins %s", player);
		this.main.playerJoins(player);
		signalPlayerToStartTheGame(socket);
	}

	private void signalPlayerToStartTheGame(MySocket socket) {
		SimpleNetworkSender sender = new SimpleNetworkSender(MessageParserFactory.create(), socket);
		new GameSettingSender(sender).sendMessage(this.generalSettings);
		new StartGameSender(new SimpleNetworkSender(MessageParserFactory.create(), socket)).sendMessage("");
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
	public List<MySocket> getAllOtherSockets() {
		return SocketStorage.getSingleton().getAllSockets();
	}

	@Override
	public void setMain(GameMain main) {
		this.main = main;
	}

}