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
import de.oetting.bumpingbunnies.usecases.ActivityLauncher;
import de.oetting.bumpingbunnies.usecases.game.android.factories.PlayerConfigFactory;
import de.oetting.bumpingbunnies.usecases.game.android.input.InputDispatcher;
import de.oetting.bumpingbunnies.usecases.game.android.input.InputService;
import de.oetting.bumpingbunnies.usecases.game.android.input.factory.AbstractPlayerInputServicesFactory;
import de.oetting.bumpingbunnies.usecases.game.businesslogic.AllPlayerConfig;
import de.oetting.bumpingbunnies.usecases.game.businesslogic.GameStartParameter;
import de.oetting.bumpingbunnies.usecases.game.businesslogic.GameThread;
import de.oetting.bumpingbunnies.usecases.game.communication.GameNetworkSender;
import de.oetting.bumpingbunnies.usecases.game.communication.NetworkReceiveThread;
import de.oetting.bumpingbunnies.usecases.game.communication.NetworkToGameDispatcher;
import de.oetting.bumpingbunnies.usecases.game.communication.RemoteSender;
import de.oetting.bumpingbunnies.usecases.game.communication.StateSender;
import de.oetting.bumpingbunnies.usecases.game.communication.factories.NetworkReceiverDispatcherThreadFactory;
import de.oetting.bumpingbunnies.usecases.game.communication.factories.NetworkSendQueueThreadFactory;
import de.oetting.bumpingbunnies.usecases.game.factories.AbstractOtherPlayersFactory;
import de.oetting.bumpingbunnies.usecases.game.factories.GameThreadFactory;
import de.oetting.bumpingbunnies.usecases.game.factories.WorldFactory;
import de.oetting.bumpingbunnies.usecases.game.model.Player;
import de.oetting.bumpingbunnies.usecases.game.model.World;
import de.oetting.bumpingbunnies.usecases.resultScreen.model.ResultWrapper;
import de.oetting.bumpingbunnies.util.SystemUiHider;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 * 
 * @see SystemUiHider
 */
public class GameActivity extends Activity {
	private GameThread gameThread;
	private InputService touchService;

	private InputDispatcher<?> inputDispatcher;
	private List<NetworkReceiveThread> networkReceiveThreads = new ArrayList<NetworkReceiveThread>();
	private List<RemoteSender> sendThreads = new ArrayList<RemoteSender>();
	private MediaPlayer backgroundMusic;

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
				R.raw.bad_bunnies160);
		this.backgroundMusic.setLooping(true);
	}

	private void startNetworkThreads() {
		for (NetworkReceiveThread receiver : this.networkReceiveThreads) {
			receiver.start();
		}
	}

	@SuppressWarnings({ "unchecked", "deprecation" })
	private void conditionalRestoreState() {
		Object data = getLastNonConfigurationInstance();
		if (data != null) {
			this.gameThread.applyState((List<Player>) data);
		}
	}

	private AbstractOtherPlayersFactory initInputFactory(
			GameStartParameter parameter) {
		return parameter.getConfiguration().getOtherPlayers().get(0)
				.getFactory();
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

		AbstractOtherPlayersFactory otherPlayerFactory = initInputFactory(parameter);
		AllPlayerConfig config = PlayerConfigFactory.create(parameter, world,
				contentView, otherPlayerFactory);
		// List<StateSender> allStateSender = config.createStateSender();
		Player myPlayer = config.getTabletControlledPlayer();
		List<StateSender> allStateSender = createSender(myPlayer);
		List<InputService> networkInputServices = initInputServices(
				otherPlayerFactory, config,
				extractRemoteSenders(allStateSender), parameter);
		List<InputService> inputServices = createInputServices();
		inputServices.addAll(networkInputServices);
		// this.networkThread = otherPlayerFactory.createSender();
		this.gameThread = GameThreadFactory.create(world,
				config.getAllPlayerMovementControllers(), inputServices,
				allStateSender, this, config, parameter.getConfiguration(),
				config.getCoordinateCalculations());

		contentView.addOnSizeListener(this.gameThread);
	}

	private List<StateSender> createSender(Player myPlayer) {
		List<MySocket> allSockets = SocketStorage.getSingleton()
				.getAllSockets();
		List<StateSender> resultSender = new ArrayList<StateSender>(
				allSockets.size());
		for (MySocket socket : allSockets) {
			RemoteSender sender = NetworkSendQueueThreadFactory.create(socket);
			this.sendThreads.add(sender);
			resultSender.add(new GameNetworkSender(myPlayer, sender));
		}
		return resultSender;
	}

	private List<RemoteSender> extractRemoteSenders(
			List<StateSender> stateSender) {
		List<RemoteSender> remoteSenders = new ArrayList<RemoteSender>(
				stateSender.size());
		for (StateSender ss : stateSender) {
			remoteSenders.add(ss.getRemoteSender());
		}
		return remoteSenders;
	}

	private List<InputService> initInputServices(
			AbstractOtherPlayersFactory singleton, AllPlayerConfig config,
			List<RemoteSender> allSender, GameStartParameter parameter) {
		AbstractPlayerInputServicesFactory.init(parameter.getConfiguration()
				.getInputConfiguration());
		AbstractPlayerInputServicesFactory<InputService> myPlayerFactory = AbstractPlayerInputServicesFactory
				.getSingleton();

		this.touchService = myPlayerFactory.createInputService(config, this);

		NetworkToGameDispatcher networkDispatcher = new NetworkToGameDispatcher();
		this.inputDispatcher = myPlayerFactory
				.createInputDispatcher(this.touchService);
		List<InputService> networkInputServices = config
				.createOtherInputService(
						networkDispatcher,
						createNetworkReceiveThreads(networkDispatcher,
								allSender), singleton, allSender);
		myPlayerFactory.insertGameControllerViews(
				(ViewGroup) findViewById(R.id.game_root), getLayoutInflater(),
				this.inputDispatcher);
		return networkInputServices;
	}

	private List<NetworkReceiveThread> createNetworkReceiveThreads(
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
		return this.networkReceiveThreads;
	}

	private List<InputService> createInputServices() {
		return createInputServicesTouch();
	}

	private List<InputService> createInputServicesTouch() {
		List<InputService> inputServes = new ArrayList<InputService>();
		inputServes.add(this.touchService);
		return inputServes;
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
		this.gameThread.cancel();
		for (RemoteSender sender : this.sendThreads) {
			sender.cancel();
		}
		for (NetworkReceiveThread receiver : this.networkReceiveThreads) {
			receiver.cancel();
		}
		this.backgroundMusic.stop();
	}

	public void onClickInputTypeCb() {
		this.gameThread.switchInputServices(createInputServices());
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			return super.onKeyDown(keyCode, event);
		} else {
			GameActivity.this.inputDispatcher.dispatchOnKeyDown(keyCode, event);
			return true;
		}
	}

	@Override
	public boolean onKeyUp(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			return super.onKeyUp(keyCode, event);
		} else {
			GameActivity.this.inputDispatcher.dispatchOnKeyUp(keyCode, event);
			return true;
		}
	}

	@Override
	public Object onRetainNonConfigurationInstance() {
		return this.gameThread.getCurrentState();
	}

	@Override
	public void onBackPressed() {
		ActivityLauncher.startResult(this, extractResult());
	}

	private ResultWrapper extractResult() {
		return this.gameThread.extractPlayerScores();
	}
}
