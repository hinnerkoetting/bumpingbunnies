package de.oetting.bumpingbunnies.usecases.game.businesslogic;

import java.util.ArrayList;
import java.util.List;

import android.view.MotionEvent;
import de.oetting.bumpingbunnies.communication.RemoteConnection;
import de.oetting.bumpingbunnies.usecases.ActivityLauncher;
import de.oetting.bumpingbunnies.usecases.game.android.GameActivity;
import de.oetting.bumpingbunnies.usecases.game.android.SocketStorage;
import de.oetting.bumpingbunnies.usecases.game.android.input.InputDispatcher;
import de.oetting.bumpingbunnies.usecases.game.communication.NetworkReceiveThread;
import de.oetting.bumpingbunnies.usecases.game.communication.RemoteSender;
import de.oetting.bumpingbunnies.usecases.game.communication.messages.stop.StopGameSender;
import de.oetting.bumpingbunnies.usecases.game.model.Player;
import de.oetting.bumpingbunnies.usecases.game.model.World;
import de.oetting.bumpingbunnies.usecases.game.sound.MusicPlayer;
import de.oetting.bumpingbunnies.usecases.resultScreen.model.ResultPlayerEntry;
import de.oetting.bumpingbunnies.usecases.resultScreen.model.ResultWrapper;

public class GameMain {

	private GameThread gameThread;
	private InputDispatcher<?> inputDispatcher;
	private List<NetworkReceiveThread> networkReceiveThreads;
	private List<RemoteConnection> sendThreads = new ArrayList<RemoteConnection>();
	private MusicPlayer musicPlayer;
	private World world;

	public boolean ontouch(MotionEvent event) {
		return this.inputDispatcher.dispatchGameTouch(event);
	}

	public void setGameThread(GameThread gameThread) {
		this.gameThread = gameThread;
	}

	public void setInputDispatcher(InputDispatcher<?> inputDispatcher) {
		this.inputDispatcher = inputDispatcher;
	}

	public void setNetworkReceiveThreads(List<NetworkReceiveThread> networkReceiveThreads) {
		this.networkReceiveThreads = networkReceiveThreads;
	}

	public void setSendThreads(List<RemoteConnection> sendThreads) {
		this.sendThreads = sendThreads;
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

	public List<NetworkReceiveThread> getNetworkReceiveThreads() {
		return this.networkReceiveThreads;
	}

	public List<RemoteConnection> getSendThreads() {
		return this.sendThreads;
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
		SocketStorage.getSingleton().closeExistingSocket();
	}

	public void shutdownAllThreads() {
		this.gameThread.cancel();
		for (RemoteSender sender : this.sendThreads) {
			sender.cancel();
		}
		for (NetworkReceiveThread receiver : this.networkReceiveThreads) {
			receiver.cancel();
		}
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
		for (RemoteSender rs : this.sendThreads) {
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

}
