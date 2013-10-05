package de.oetting.bumpingbunnies.usecases.game.businesslogic;

import java.util.ArrayList;
import java.util.List;

import android.view.MotionEvent;
import de.oetting.bumpingbunnies.communication.DividedNetworkSender;
import de.oetting.bumpingbunnies.communication.MySocket;
import de.oetting.bumpingbunnies.usecases.ActivityLauncher;
import de.oetting.bumpingbunnies.usecases.game.android.GameActivity;
import de.oetting.bumpingbunnies.usecases.game.android.SocketStorage;
import de.oetting.bumpingbunnies.usecases.game.android.input.InputDispatcher;
import de.oetting.bumpingbunnies.usecases.game.communication.NetworkSendQueueThread;
import de.oetting.bumpingbunnies.usecases.game.communication.ThreadedNetworkSender;
import de.oetting.bumpingbunnies.usecases.game.communication.factories.NetworkSendQueueThreadFactory;
import de.oetting.bumpingbunnies.usecases.game.communication.messages.stop.StopGameSender;
import de.oetting.bumpingbunnies.usecases.game.model.Opponent;
import de.oetting.bumpingbunnies.usecases.game.model.Player;
import de.oetting.bumpingbunnies.usecases.game.model.PlayerJoinObservable;
import de.oetting.bumpingbunnies.usecases.game.model.World;
import de.oetting.bumpingbunnies.usecases.game.sound.MusicPlayer;
import de.oetting.bumpingbunnies.usecases.resultScreen.model.ResultPlayerEntry;
import de.oetting.bumpingbunnies.usecases.resultScreen.model.ResultWrapper;

public class GameMain {

	private final SocketStorage sockets;
	private GameThread gameThread;
	private InputDispatcher<?> inputDispatcher;
	private NetworkReceiveControl receiveControl;
	private NetworkSendControl sendControl;
	private MusicPlayer musicPlayer;
	private World world;
	private PlayerJoinObservable playerObservable;
	private GameActivity activity;

	public GameMain(GameActivity activity, SocketStorage sockets, NetworkSendControl sendControl) {
		super();
		this.activity = activity;
		this.sockets = sockets;
		this.sendControl = sendControl;
		this.playerObservable = new PlayerJoinObservable();
	}

	public boolean ontouch(MotionEvent event) {
		return this.inputDispatcher.dispatchGameTouch(event);
	}

	public void setGameThread(GameThread gameThread) {
		this.gameThread = gameThread;
	}

	public void setInputDispatcher(InputDispatcher<?> inputDispatcher) {
		this.inputDispatcher = inputDispatcher;
	}

	public void setReceiveControl(NetworkReceiveControl receiveControl) {
		this.receiveControl = receiveControl;
	}

	public void setMusicPlayer(MusicPlayer musicPlayer) {
		this.musicPlayer = musicPlayer;
	}

	public GameThread getGameThread() {
		return this.gameThread;
	}

	public InputDispatcher<?> getInputDispatcher() {
		return this.inputDispatcher;
	}

	public NetworkSendControl getSendControl() {
		return this.sendControl;
	}

	public List<ThreadedNetworkSender> getSendThreads() {
		return this.sendControl.getSendThreads();
	}

	public MusicPlayer getMusicPlayer() {
		return this.musicPlayer;
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
	}

	public void shutdownAllThreads() {
		this.gameThread.cancel();
		for (ThreadedNetworkSender sender : this.sendControl.getSendThreads()) {
			sender.cancel();
		}
		this.receiveControl.shutDownThreads();
		this.musicPlayer.stopBackground();
	}

	public void stop(GameActivity activity) {
		shutdownAllThreads();
		startResultScreen(activity);
	}

	public void startResultScreen(GameActivity activity) {
		ActivityLauncher.startResult(activity, extractResult());
	}

	public void sendStopMessage() {
		for (ThreadedNetworkSender rs : this.sendControl.getSendThreads()) {
			new StopGameSender(rs).sendMessage("");
		}
	}

	private ResultWrapper extractResult() {
		return extractPlayerScores();
	}

	public ResultWrapper extractPlayerScores() {
		List<Player> players = this.world.getAllPlayer();
		List<ResultPlayerEntry> resultEntries = new ArrayList<ResultPlayerEntry>(
				players.size());
		for (Player p : players) {
			ResultPlayerEntry entry = new ResultPlayerEntry(p.getName(), p
					.getScore(), p.getColor());
			resultEntries.add(entry);
		}
		return new ResultWrapper(resultEntries);
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

	public void playerJoins(Player player) {
		this.world.getAllPlayer().add(player);
		if (this.sockets.existsSocket(player.getOpponent())) {
			// addSendThread(player);
		}
		this.playerObservable.playerJoined(player);
	}

	private void addSendThread(Player player) {
		DividedNetworkSender rc = createSendThread(player);
		this.sendControl.getSendThreads().add(rc);
	}

	private DividedNetworkSender createSendThread(Player player) {
		MySocket socket = this.sockets.findSocket(player.getOpponent());
		return createServerConnection(this.activity, socket, player.getOpponent());
	}

	public DividedNetworkSender createServerConnection(GameActivity activity, MySocket socket, Opponent opponent) {
		NetworkSendQueueThread tcpConnection = NetworkSendQueueThreadFactory.create(socket, activity);
		NetworkSendQueueThread udpConnection = createUdpConnection(activity, socket);

		DividedNetworkSender serverConnection = new DividedNetworkSender(tcpConnection, udpConnection, opponent);
		return serverConnection;
	}

	private NetworkSendQueueThread createUdpConnection(GameActivity activity, MySocket socket) {
		MySocket fastSocket = socket.createFastConnection();
		return NetworkSendQueueThreadFactory.create(fastSocket, activity);
	}

	public void addJoinListener(PlayerJoinListener listener) {
		this.playerObservable.addListener(listener);
	}

	public void playerLeaves(Player p) {
		this.playerObservable.playerLeft(p);
	}

	public void addAllJoinListeners() {
		addJoinListener(this.sendControl); // send control must be the first
		this.gameThread.addAllJoinListeners(this);
		addJoinListener(this.receiveControl);
	}

	public ThreadedNetworkSender findConnection(Opponent opponent) {
		ThreadedNetworkSender rc = findConnectionOrNull(opponent);
		if (rc == null) {
			throw new ConnectionDoesNotExist();
		} else {
			return rc;
		}
	}

	private ThreadedNetworkSender findConnectionOrNull(Opponent opponent) {
		for (ThreadedNetworkSender rc : this.sendControl.getSendThreads()) {
			if (rc.isConnectionToPlayer(opponent)) {
				return rc;
			}
		}
		return null;
	}

	public class ConnectionDoesNotExist extends RuntimeException {
	}

}
