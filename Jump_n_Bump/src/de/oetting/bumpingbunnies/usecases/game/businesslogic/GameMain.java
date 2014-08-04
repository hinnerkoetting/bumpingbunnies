package de.oetting.bumpingbunnies.usecases.game.businesslogic;

import java.util.ArrayList;
import java.util.List;

import android.view.MotionEvent;
import de.oetting.bumpingbunnies.communication.MySocket;
import de.oetting.bumpingbunnies.communication.messageInterface.NetworkSender;
import de.oetting.bumpingbunnies.usecases.ActivityLauncher;
import de.oetting.bumpingbunnies.usecases.game.android.GameActivity;
import de.oetting.bumpingbunnies.usecases.game.android.SocketStorage;
import de.oetting.bumpingbunnies.usecases.game.android.input.InputDispatcher;
import de.oetting.bumpingbunnies.usecases.game.communication.NewClientsAccepter;
import de.oetting.bumpingbunnies.usecases.game.communication.messages.stop.StopGameSender;
import de.oetting.bumpingbunnies.usecases.game.model.Player;
import de.oetting.bumpingbunnies.usecases.game.model.PlayerJoinObservable;
import de.oetting.bumpingbunnies.usecases.game.model.World;
import de.oetting.bumpingbunnies.usecases.game.music.MusicPlayer;
import de.oetting.bumpingbunnies.usecases.resultScreen.model.ResultPlayerEntry;
import de.oetting.bumpingbunnies.usecases.resultScreen.model.ResultWrapper;

public class GameMain {

	private final SocketStorage sockets;
	private final NetworkSendControl sendControl;
	private final PlayerJoinObservable playerObservable;
	private final NewClientsAccepter newClientsAccepter;
	private GameThread gameThread;
	private InputDispatcher<?> inputDispatcher;
	private NetworkReceiveControl receiveControl;
	private MusicPlayer musicPlayer;
	private World world;

	public GameMain(SocketStorage sockets, NetworkSendControl sendControl, NewClientsAccepter newClientsAccepter) {
		super();
		this.sockets = sockets;
		this.sendControl = sendControl;
		this.newClientsAccepter = newClientsAccepter;
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

	public InputDispatcher<?> getInputDispatcher() {
		return this.inputDispatcher;
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

	public void stop(GameActivity activity) {
		shutdownAllThreads();
		startResultScreen(activity);
	}

	public void startResultScreen(GameActivity activity) {
		ActivityLauncher.startResult(activity, extractResult());
	}

	public void sendStopMessage() {
		for (NetworkSender rs : this.sendControl.getSendThreads()) {
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
		this.playerObservable.playerJoined(player);
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

	public void clientConnectedSuccessfull(MySocket socket) {
		this.newClientsAccepter.clientConnectedSucessfull(socket);
	}

}
