package de.oetting.bumpingbunnies.core.game.main;

import java.util.ArrayList;
import java.util.List;

import de.oetting.bumpingbunnies.core.assertion.Guard;
import de.oetting.bumpingbunnies.core.game.logic.GameThread;
import de.oetting.bumpingbunnies.core.game.player.PlayerJoinObservable;
import de.oetting.bumpingbunnies.core.game.steps.GameStepController;
import de.oetting.bumpingbunnies.core.game.steps.JoinObserver;
import de.oetting.bumpingbunnies.core.game.steps.PlayerJoinListener;
import de.oetting.bumpingbunnies.core.network.MySocket;
import de.oetting.bumpingbunnies.core.network.NetworkMessageDistributor;
import de.oetting.bumpingbunnies.core.network.NetworkPlayerStateSenderThread;
import de.oetting.bumpingbunnies.core.network.NewClientsAccepter;
import de.oetting.bumpingbunnies.core.network.sockets.SocketStorage;
import de.oetting.bumpingbunnies.core.networking.communication.messageInterface.NetworkSender;
import de.oetting.bumpingbunnies.core.networking.messaging.playerDisconnected.PlayerDisconnectedMessage;
import de.oetting.bumpingbunnies.core.networking.messaging.stop.StopGameSender;
import de.oetting.bumpingbunnies.core.networking.receive.NetworkReceiveControl;
import de.oetting.bumpingbunnies.core.networking.receive.PlayerDisconnectedCallback;
import de.oetting.bumpingbunnies.core.networking.sender.SimpleNetworkSender;
import de.oetting.bumpingbunnies.core.networking.sender.SimpleNetworkSenderFactory;
import de.oetting.bumpingbunnies.logger.Logger;
import de.oetting.bumpingbunnies.logger.LoggerFactory;
import de.oetting.bumpingbunnies.model.configuration.Configuration;
import de.oetting.bumpingbunnies.model.game.MusicPlayer;
import de.oetting.bumpingbunnies.model.game.objects.ConnectionIdentifier;
import de.oetting.bumpingbunnies.model.game.objects.Bunny;
import de.oetting.bumpingbunnies.model.game.world.World;
import de.oetting.bumpingbunnies.model.network.MessageId;

public class GameMain implements JoinObserver, PlayerJoinListener, PlayerDisconnectedCallback {

	private static final Logger LOGGER = LoggerFactory.getLogger(GameMain.class);
	private final SocketStorage sockets;
	private final PlayerJoinObservable playerObservable;
	private final MusicPlayer musicPlayer;
	private final NetworkPlayerStateSenderThread networkSendThread;
	private final NetworkMessageDistributor sendControl;
	private final Configuration configuration;
	private NewClientsAccepter newClientsAccepter;
	private GameThread gameThread;

	private NetworkReceiveControl receiveControl;
	private World world;

	public GameMain(SocketStorage sockets, MusicPlayer musicPlayer, NetworkPlayerStateSenderThread networkSendThread,
			NetworkMessageDistributor sendControl, Configuration configuration) {
		this.sockets = sockets;
		this.musicPlayer = musicPlayer;
		this.networkSendThread = networkSendThread;
		this.sendControl = sendControl;
		this.configuration = configuration;
		this.playerObservable = new PlayerJoinObservable();
	}

	public void setGameThread(GameThread gameThread) {
		this.gameThread = gameThread;
	}

	public void setReceiveControl(NetworkReceiveControl receiveControl) {
		this.receiveControl = receiveControl;
	}

	public World getWorld() {
		return this.world;
	}

	public void setWorld(World world) {
		this.world = world;
	}

	public void onResume() {
		this.gameThread.setRunning(true);
		this.musicPlayer.start();
	}

	public void onPause() {
		this.gameThread.setRunning(false);
		this.musicPlayer.pauseBackground();
	}

	public void endGame() {
		notifyOthersAboutEnd();

		shutdownAllThreads();
		this.sockets.closeExistingSockets();
		this.newClientsAccepter.cancel();
		this.sockets.removeListeners();
		musicPlayer.stopBackground();
	}

	private void notifyOthersAboutEnd() {
		if (configuration.isHost()) {
			stopGame();
		} else {
			disconnectLocalPlayers();
		}
	}

	private void stopGame() {
		for (MySocket socket : SocketStorage.getSingleton().getAllSockets()) {
			try {
				new StopGameSender(SimpleNetworkSenderFactory.createNetworkSender(socket, this)).sendMessage("");
			} catch (Exception e) {
				LOGGER.warn("cannot send stop-game message to client. Ignoring this", e);
			}
		}
	}

	private List<Bunny> findLocalPlayers() {
		List<Bunny> localPlayers = new ArrayList<Bunny>();
		for (Bunny player : world.getAllConnectedBunnies()) {
			if (player.getOpponent().isLocalPlayer()) {
				localPlayers.add(player);
			}
		}
		return localPlayers;
	}

	private void disconnectLocalPlayers() {
		List<Bunny> localPlayers = findLocalPlayers();
		for (MySocket socket : SocketStorage.getSingleton().getAllSockets()) {
			SimpleNetworkSender networkSender = SimpleNetworkSenderFactory.createNetworkSender(socket, this);
			for (Bunny localPlayer : localPlayers)
				networkSender.sendMessage(MessageId.PLAYER_DISCONNECTED,
						new PlayerDisconnectedMessage(localPlayer.id()));
		}
	}

	public void shutdownAllThreads() {
		this.gameThread.cancel();
		for (NetworkSender sender : this.sendControl.getSendThreads()) {
			sender.cancel();
		}
		this.networkSendThread.cancel();
		this.receiveControl.shutDownThreads();
		this.musicPlayer.stopBackground();
	}

	public void restorePlayerStates(List<Bunny> players) {
		List<Bunny> existingPlayers = this.world.getAllConnectedBunnies();
		for (Bunny p : existingPlayers) {
			for (Bunny storedPlayer : players) {
				if (p.id() == storedPlayer.id()) {
					p.applyStateTo(storedPlayer);
				}
			}
		}
	}

	@Override
	public void addJoinListener(PlayerJoinListener listener) {
		this.playerObservable.addListener(listener);
	}

	public void addAllJoinListeners() {
		playerObservable.addListener(SocketStorage.getSingleton());
		this.gameThread.addAllJoinListeners(this);
	}

	@Override
	public void newEvent(Bunny player) {
		LOGGER.info("Player joined %d", player.id());
		world.addBunny(player);
		this.playerObservable.playerJoined(player);
	}

	@Override
	public void removeEvent(Bunny p) {
		world.disconnectBunny(p);
		this.playerObservable.playerLeft(p);
	}

	public void setNewClientsAccepter(NewClientsAccepter newClientsAccepter) {
		this.newClientsAccepter = newClientsAccepter;
	}

	public void validateInitialised() {
		Guard.againstNull(sockets);
		Guard.againstNull(sendControl);
		Guard.againstNull(playerObservable);
		Guard.againstNull(newClientsAccepter);
		Guard.againstNull(gameThread);
		Guard.againstNull(receiveControl);
		Guard.againstNull(musicPlayer);
		Guard.againstNull(world);
		Guard.againstNull(networkSendThread);
		Guard.againstNull(sendControl);
	}

	public void start() {
		gameThread.start();
		newClientsAccepter.start();
		networkSendThread.start();
		sendControl.start();
		receiveControl.start();
	}

	@Override
	public void playerDisconnected(ConnectionIdentifier opponent) {
		try {
			Bunny disconnectedPlayer = findPlayer(opponent);
			removeEvent(disconnectedPlayer);
		} catch (IllegalArgumentException e) {
			LOGGER.warn("Player disconnected but the player does not event exist anymore. Ignoring the event");
		}
	}

	@Override
	public void playerDisconnected(int playerId) {
		Bunny disconnectedPlayer = findPlayer(playerId);
		removeEvent(disconnectedPlayer);
	}

	private Bunny findPlayer(ConnectionIdentifier opponent) {
		for (Bunny p : world.getAllConnectedBunnies()) {
			if (p.getOpponent().getIdentifier().equals(opponent.getIdentifier()))
				return p;
		}
		throw new IllegalArgumentException("Could not find player " + opponent);
	}

	private Bunny findPlayer(int playerId) {
		for (Bunny p : world.getAllConnectedBunnies()) {
			if (p.id() == playerId)
				return p;
		}
		throw new IllegalArgumentException("Could not find player " + playerId);
	}

	public void addSocketListener() {
		sockets.addObserver(sendControl);
		sockets.addObserver(receiveControl);
		sockets.addObserver(this.networkSendThread);
		sockets.notifyListenersAboutExistingSockets();
	}

	public Configuration getConfiguration() {
		return configuration;
	}

	public void pause(boolean pause) {
		gameThread.pause(pause);
	}

	public boolean isPaused() {
		return gameThread.isPaused();
	}
}
