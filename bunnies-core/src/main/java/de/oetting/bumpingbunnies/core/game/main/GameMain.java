package de.oetting.bumpingbunnies.core.game.main;

import java.util.List;

import de.oetting.bumpingbunnies.core.assertion.Guard;
import de.oetting.bumpingbunnies.core.game.player.PlayerJoinObservable;
import de.oetting.bumpingbunnies.core.game.steps.JoinObserver;
import de.oetting.bumpingbunnies.core.game.steps.PlayerJoinListener;
import de.oetting.bumpingbunnies.core.network.MySocket;
import de.oetting.bumpingbunnies.core.network.NetworkMessageDistributor;
import de.oetting.bumpingbunnies.core.network.NewClientsAccepter;
import de.oetting.bumpingbunnies.core.network.SocketStorage;
import de.oetting.bumpingbunnies.core.networking.communication.messageInterface.NetworkSender;
import de.oetting.bumpingbunnies.core.networking.messaging.stop.StopGameSender;
import de.oetting.bumpingbunnies.core.networking.receive.NetworkReceiveControl;
import de.oetting.bumpingbunnies.core.world.World;
import de.oetting.bumpingbunnies.model.game.MusicPlayer;
import de.oetting.bumpingbunnies.model.game.objects.Player;

public class GameMain implements JoinObserver, PlayerJoinListener {

	private final SocketStorage sockets;
	private final NetworkMessageDistributor sendControl;
	private final PlayerJoinObservable playerObservable;
	private final NewClientsAccepter newClientsAccepter;
	private final MusicPlayer musicPlayer;
	private GameThread gameThread;

	private NetworkReceiveControl receiveControl;
	private World world;

	public GameMain(SocketStorage sockets, NetworkMessageDistributor sendControl, NewClientsAccepter newClientsAccepter, MusicPlayer musicPlayer) {
		super();
		this.sockets = sockets;
		this.sendControl = sendControl;
		this.newClientsAccepter = newClientsAccepter;
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
		addJoinListener(this.sendControl); // send control must be the first
		this.gameThread.addAllJoinListeners(this);
		addJoinListener(this.receiveControl);
	}

	public void clientConnectedSuccessfull(MySocket socket) {
		this.newClientsAccepter.clientConnectedSucessfull(socket);
	}

	@Override
	public void newPlayerJoined(Player player) {
		this.world.getAllPlayer().add(player);
		this.playerObservable.playerJoined(player);
	}

	@Override
	public void playerLeftTheGame(Player p) {
		world.getAllPlayer().remove(p);
		this.playerObservable.playerLeft(p);
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
	}

	public void start() {
		gameThread.start();
	}

}
