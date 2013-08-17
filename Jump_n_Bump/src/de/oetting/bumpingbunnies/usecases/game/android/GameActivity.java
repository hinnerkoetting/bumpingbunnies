package de.oetting.bumpingbunnies.usecases.game.android;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.Window;
import de.oetting.bumpingbunnies.R;
import de.oetting.bumpingbunnies.communication.MySocket;
import de.oetting.bumpingbunnies.communication.ServerConnection;
import de.oetting.bumpingbunnies.usecases.ActivityLauncher;
import de.oetting.bumpingbunnies.usecases.game.android.factories.PlayerConfigFactory;
import de.oetting.bumpingbunnies.usecases.game.android.input.InputDispatcher;
import de.oetting.bumpingbunnies.usecases.game.android.input.InputService;
import de.oetting.bumpingbunnies.usecases.game.android.input.factory.AbstractPlayerInputServicesFactory;
import de.oetting.bumpingbunnies.usecases.game.businesslogic.AllPlayerConfig;
import de.oetting.bumpingbunnies.usecases.game.businesslogic.GameStartParameter;
import de.oetting.bumpingbunnies.usecases.game.businesslogic.GameThread;
import de.oetting.bumpingbunnies.usecases.game.businesslogic.PlayerMovementController;
import de.oetting.bumpingbunnies.usecases.game.communication.GameNetworkSender;
import de.oetting.bumpingbunnies.usecases.game.communication.NetworkReceiveThread;
import de.oetting.bumpingbunnies.usecases.game.communication.NetworkSendQueueThread;
import de.oetting.bumpingbunnies.usecases.game.communication.NetworkToGameDispatcher;
import de.oetting.bumpingbunnies.usecases.game.communication.RemoteSender;
import de.oetting.bumpingbunnies.usecases.game.communication.StateSender;
import de.oetting.bumpingbunnies.usecases.game.communication.factories.NetworkReceiverDispatcherThreadFactory;
import de.oetting.bumpingbunnies.usecases.game.communication.factories.NetworkSendQueueThreadFactory;
import de.oetting.bumpingbunnies.usecases.game.communication.messages.playerIsDead.PlayerIsDeadReceiver;
import de.oetting.bumpingbunnies.usecases.game.communication.messages.playerIsRevived.PlayerIsRevivedReceiver;
import de.oetting.bumpingbunnies.usecases.game.communication.messages.playerScoreUpdated.PlayerScoreReceiver;
import de.oetting.bumpingbunnies.usecases.game.communication.messages.spawnPoint.SpawnPointReceiver;
import de.oetting.bumpingbunnies.usecases.game.communication.messages.stop.StopGameReceiver;
import de.oetting.bumpingbunnies.usecases.game.communication.messages.stop.StopGameSender;
import de.oetting.bumpingbunnies.usecases.game.factories.GameThreadFactory;
import de.oetting.bumpingbunnies.usecases.game.factories.WorldFactory;
import de.oetting.bumpingbunnies.usecases.game.model.Player;
import de.oetting.bumpingbunnies.usecases.game.model.World;
import de.oetting.bumpingbunnies.usecases.resultScreen.model.ResultPlayerEntry;
import de.oetting.bumpingbunnies.usecases.resultScreen.model.ResultWrapper;
import de.oetting.bumpingbunnies.util.SystemUiHider;

/**
 * An example full-screen activity that shows and hides the system UI (i.e. status bar and navigation/system bar) with user interaction.
 * 
 * @see SystemUiHider
 */
public class GameActivity extends Activity {
	private GameThread gameThread;

	private InputDispatcher<?> inputDispatcher;
	private List<NetworkReceiveThread> networkReceiveThreads;
	private List<RemoteSender> sendThreads = new ArrayList<RemoteSender>();
	private MediaPlayer backgroundMusic;
	private AllPlayerConfig allPlayerConfig;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_game);

		final GameView contentView = (GameView) findViewById(R.id.fullscreen_content);

		registerScreenTouchListener(contentView);
		initGame();
		contentView.setGameThread(this.gameThread);
		conditionalRestoreState();
		startNetworkThreads();
		this.gameThread.start();
		initGameSound();
	}

	private void initGameSound() {
		this.backgroundMusic = MediaPlayer.create(GameActivity.this,
				R.raw.bad_bunnies_2);
		this.backgroundMusic.setLooping(true);
	}

	private void startNetworkThreads() {
		for (NetworkReceiveThread receiver : this.networkReceiveThreads) {
			receiver.start();
		}
		for (RemoteSender sender : this.sendThreads) {
			sender.start();
		}
	}

	@SuppressWarnings({ "unchecked", "deprecation" })
	private void conditionalRestoreState() {
		Object data = getLastNonConfigurationInstance();
		if (data != null) {
			applyPlayers((List<Player>) data);
		}
	}

	private void registerScreenTouchListener(final GameView contentView) {
		contentView.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				return GameActivity.this.inputDispatcher
						.dispatchGameTouch(event);
			}
		});
	}

	private void initGame() {
		GameStartParameter parameter = (GameStartParameter) getIntent()
				.getExtras().get(ActivityLauncher.GAMEPARAMETER);
		final GameView contentView = (GameView) findViewById(R.id.fullscreen_content);
		World world = WorldFactory.create(parameter.getConfiguration(), this);

		this.allPlayerConfig = PlayerConfigFactory.create(parameter, world,
				contentView);
		Player myPlayer = this.allPlayerConfig.getTabletControlledPlayer();
		createRemoteSender();
		List<StateSender> allStateSender = createSender(myPlayer);
		List<InputService> inputServices = initInputServices(world,
				this.allPlayerConfig,
				this.sendThreads, parameter);

		this.gameThread = GameThreadFactory.create(this.sendThreads, world,
				inputServices,
				allStateSender, this, this.allPlayerConfig, parameter.getConfiguration());

		contentView.addOnSizeListener(this.gameThread);
	}

	private void createRemoteSender() {
		List<MySocket> allSockets = SocketStorage.getSingleton()
				.getAllSockets();
		List<RemoteSender> resultSender = new ArrayList<RemoteSender>(
				allSockets.size());
		for (MySocket socket : allSockets) {
			ServerConnection serverConnection = createServerConnection(socket);
			resultSender.add(serverConnection);
		}
		this.sendThreads = resultSender;
	}

	public ServerConnection createServerConnection(MySocket socket) {
		NetworkSendQueueThread tcpConnection = NetworkSendQueueThreadFactory.create(socket, this);
		ServerConnection serverConnection = new ServerConnection(tcpConnection, null); // TODO
		return serverConnection;
	}

	private List<StateSender> createSender(Player myPlayer) {
		List<StateSender> resultSender = new ArrayList<StateSender>(
				this.sendThreads.size());
		for (RemoteSender rs : this.sendThreads) {
			resultSender.add(new GameNetworkSender(myPlayer, rs));
		}
		return resultSender;
	}

	private List<InputService> initInputServices(
			World world, AllPlayerConfig config,
			List<RemoteSender> allSender, GameStartParameter parameter) {
		AbstractPlayerInputServicesFactory.init(parameter.getConfiguration()
				.getInputConfiguration());
		AbstractPlayerInputServicesFactory<InputService> myPlayerFactory = AbstractPlayerInputServicesFactory
				.getSingleton();

		InputService touchService = myPlayerFactory.createInputService(config, this);

		NetworkToGameDispatcher networkDispatcher = new NetworkToGameDispatcher();

		addAllNetworkListeners(networkDispatcher, world);
		this.inputDispatcher = myPlayerFactory.createInputDispatcher(touchService);
		createNetworkReceiveThreads(networkDispatcher, allSender);
		List<InputService> inputServices = config.createOtherInputService(networkDispatcher);
		inputServices.add(touchService);
		myPlayerFactory.insertGameControllerViews(
				(ViewGroup) findViewById(R.id.game_root), getLayoutInflater(),
				this.inputDispatcher);
		return inputServices;
	}

	public void stopGame() {
		shutdownAllThreads();
		startResultScreen();
	}

	private void addAllNetworkListeners(NetworkToGameDispatcher networkDispatcher, World world) {
		new StopGameReceiver(networkDispatcher, this);
		new PlayerIsDeadReceiver(networkDispatcher, world.getAllPlayer());
		new PlayerScoreReceiver(networkDispatcher, world.getAllPlayer());
		new PlayerIsRevivedReceiver(networkDispatcher, world.getAllPlayer());
		new SpawnPointReceiver(networkDispatcher, world.getAllPlayer());
	}

	private void createNetworkReceiveThreads(
			NetworkToGameDispatcher networkDispatcher,
			List<RemoteSender> allRemoteSender) {
		List<MySocket> allSockets = SocketStorage.getSingleton()
				.getAllSockets();
		this.networkReceiveThreads = new ArrayList<NetworkReceiveThread>();
		for (MySocket socket : allSockets) {
			NetworkReceiveThread receiveThread = NetworkReceiverDispatcherThreadFactory
					.createGameNetworkReceiver(socket, allRemoteSender,
							networkDispatcher);

			this.networkReceiveThreads.add(receiveThread);
		}
	}

	@Override
	protected void onResume() {
		super.onResume();

		this.gameThread.setRunning(true);
		this.backgroundMusic.start();
	}

	@Override
	protected void onPause() {
		super.onPause();
		this.gameThread.setRunning(false);
		this.backgroundMusic.pause();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
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
		this.backgroundMusic.stop();
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		boolean isKeyProcessed = GameActivity.this.inputDispatcher.dispatchOnKeyDown(keyCode, event);
		if (!isKeyProcessed) {
			return super.onKeyDown(keyCode, event);
		}
		return true;
	}

	@Override
	public boolean onKeyUp(int keyCode, KeyEvent event) {
		boolean isKeyProcessed = GameActivity.this.inputDispatcher.dispatchOnKeyUp(keyCode, event);
		if (!isKeyProcessed) {
			return super.onKeyUp(keyCode, event);
		}
		return true;
	}

	@Override
	public Object onRetainNonConfigurationInstance() {
		return getAllPlayers();
	}

	private List<Player> getAllPlayers() {
		List<PlayerMovementController> playermovements = this.allPlayerConfig.getAllPlayerMovementControllers();
		List<Player> players = new ArrayList<Player>(
				playermovements.size());
		for (PlayerMovementController movement : playermovements) {
			players.add(movement.getPlayer());
		}
		return players;
	}

	public void applyPlayers(List<Player> storedPlayers) {
		List<PlayerMovementController> playermovements = this.allPlayerConfig.getAllPlayerMovementControllers();
		for (PlayerMovementController movement : playermovements) {
			for (Player storedPlayer : storedPlayers) {
				if (movement.getPlayer().id() == storedPlayer.id()) {
					movement.getPlayer().applyState(storedPlayer);
				}
			}
		}
	}

	public ResultWrapper extractPlayerScores() {
		List<PlayerMovementController> playermovements = this.allPlayerConfig.getAllPlayerMovementControllers();
		List<ResultPlayerEntry> players = new ArrayList<ResultPlayerEntry>(
				playermovements.size());
		for (PlayerMovementController movement : playermovements) {
			Player player = movement.getPlayer();
			ResultPlayerEntry entry = new ResultPlayerEntry(player.getName(), player
					.getScore(), movement.getPlayer().getColor());
			players.add(entry);
		}
		return new ResultWrapper(players);
	}

	@Override
	public void onBackPressed() {
		sendStopMessage();
		startResultScreen();
	}

	private void startResultScreen() {
		ActivityLauncher.startResult(this, extractResult());
	}

	private void sendStopMessage() {
		for (RemoteSender rs : this.sendThreads) {
			new StopGameSender(rs).sendMessage("");
		}
	}

	private ResultWrapper extractResult() {
		return extractPlayerScores();
	}
}
