package de.oetting.bumpingbunnies.core.game.main;

import java.util.List;

import de.oetting.bumpingbunnies.core.assertion.Guard;
import de.oetting.bumpingbunnies.core.game.logic.GameThread;
import de.oetting.bumpingbunnies.core.game.player.PlayerJoinObservable;
import de.oetting.bumpingbunnies.core.game.steps.JoinObserver;
import de.oetting.bumpingbunnies.core.game.steps.PlayerJoinListener;
import de.oetting.bumpingbunnies.core.network.NetworkMessageDistributor;
import de.oetting.bumpingbunnies.core.network.NetworkPlayerStateSenderThread;
import de.oetting.bumpingbunnies.core.network.NewClientsAccepter;
import de.oetting.bumpingbunnies.core.network.sockets.SocketStorage;
import de.oetting.bumpingbunnies.core.networking.communication.messageInterface.NetworkSender;
import de.oetting.bumpingbunnies.core.networking.messaging.stop.StopGameSender;
import de.oetting.bumpingbunnies.core.networking.receive.NetworkReceiveControl;
import de.oetting.bumpingbunnies.core.networking.receive.PlayerDisconnectedCallback;
import de.oetting.bumpingbunnies.core.world.World;
import de.oetting.bumpingbunnies.logger.Logger;
import de.oetting.bumpingbunnies.logger.LoggerFactory;
import de.oetting.bumpingbunnies.model.game.MusicPlayer;
import de.oetting.bumpingbunnies.model.game.objects.Opponent;
import de.oetting.bumpingbunnies.model.game.objects.Player;

public class GameMain implements JoinObserver, PlayerJoinListener, PlayerDisconnectedCallback {

	private static final Logger LOGGER = LoggerFactory.getLogger(GameMain.class);
	private final SocketStorage sockets;
	private final PlayerJoinObservable playerObservable;
	private final MusicPlayer musicPlayer;
	private NetworkPlayerStateSenderThread networkSenderThread;
	private NetworkMessageDistributor sendControl;
	private NewClientsAccepter newClientsAccepter;
	private GameThread gameThread;

	private NetworkReceiveControl receiveControl;
	private World world;

	public GameMain(SocketStorage sockets, MusicPlayer musicPlayer) {
		this.sockets = sockets;
		this.musicPlayer = musicPlayer;
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

	public void destroy() {
		shutdownAllThreads();
		this.sockets.closeExistingSocket();
		this.newClientsAccepter.cancel();
	}

	public void shutdownAllThreads() {
		this.gameThread.cancel();
		for (NetworkSender sender : this.sendControl.getSendThreads()) {
			sender.cancel();
		}
		networkSenderThread.cancel();
		this.receiveControl.shutDownThreads();
		this.musicPlayer.stopBackground();
	}

	public void stop() {
		shutdownAllThreads();
	}

	public void sendStopMessage() {
		for (NetworkSender rs : this.sendControl.getSendThreads()) {
			new StopGameSender(rs).sendMessage("");
		}
	}

	public void restorePlayerStates(List<Player> players) {
		List<Player> existingPlayers = this.world.getAllPlayer();
		for (Player p : existingPlayers) {
			for (Player storedPlayer : players) {
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
		this.gameThread.addAllJoinListeners(this);
	}

	@Override
	public void newEvent(Player player) {
		LOGGER.info("Player joined %d", player.id());
		this.world.getAllPlayer().add(player);
		this.playerObservable.playerJoined(player);
	}

	@Override
	public void removeEvent(Player p) {
		world.removePlayer(p);
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
		Guard.againstNull(networkSenderThread);
		Guard.againstNull(sendControl);
	}

	public void start() {
		gameThread.start();
		newClientsAccepter.start();
		networkSenderThread.start();
		sendControl.start();
	}

	@Override
	public void playerDisconnected(Opponent opponent) {
		Player disconnectedPlayer = findPlayer(opponent);
		removeEvent(disconnectedPlayer);
	}

	@Override
	public void playerDisconnected(int playerId) {
		Player disconnectedPlayer = findPlayer(playerId);
		removeEvent(disconnectedPlayer);
	}

	private Player findPlayer(Opponent opponent) {
		for (Player p : world.getAllPlayer()) {
			if (p.getOpponent().getIdentifier().equals(opponent.getIdentifier()))
				return p;
		}
		throw new IllegalArgumentException("Could not find player " + opponent);
	}

	private Player findPlayer(int playerId) {
		for (Player p : world.getAllPlayer()) {
			if (p.id() == playerId)
				return p;
		}
		throw new IllegalArgumentException("Could not find player " + playerId);
	}

	public void setSendControl(NetworkMessageDistributor sendControl) {
		this.sendControl = sendControl;
	}

	public void setNetworkSendThread(NetworkPlayerStateSenderThread networkSendThread) {
		this.networkSenderThread = networkSendThread;
	}

	public void addSocketListener() {
		sockets.addObserver(sendControl);
		sockets.addObserver(this.receiveControl);
		sockets.addObserver(networkSenderThread);
		SocketStorage.getSingleton().notifyListenersAboutAllSockets();
	}

}
