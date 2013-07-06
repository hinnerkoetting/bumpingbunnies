package de.oetting.bumpingbunnies.usecases.game.android;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.Window;
import de.oetting.bumpingbunnies.R;
import de.oetting.bumpingbunnies.logger.Logger;
import de.oetting.bumpingbunnies.logger.LoggerFactory;
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
import de.oetting.bumpingbunnies.usecases.game.configuration.InputConfiguration;
import de.oetting.bumpingbunnies.usecases.game.factories.AbstractOtherPlayersFactory;
import de.oetting.bumpingbunnies.usecases.game.factories.GameThreadFactory;
import de.oetting.bumpingbunnies.usecases.game.factories.WorldFactory;
import de.oetting.bumpingbunnies.usecases.game.model.Player;
import de.oetting.bumpingbunnies.usecases.game.model.World;
import de.oetting.bumpingbunnies.usecases.start.communication.MySocket;
import de.oetting.bumpingbunnies.util.SystemUiHider;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 * 
 * @see SystemUiHider
 */
public class GameActivity extends Activity {
	private static final Logger LOGGER = LoggerFactory
			.getLogger(GameActivity.class);
	private GameThread gameThread;
	private InputService touchService;

	private List<InputService> networkMovementService;
	// private RemoteSender networkThread;
	private GameStartParameter parameter;

	private InputDispatcher<?> inputDispatcher;
	private List<NetworkReceiveThread> networkReceiveThreads = new ArrayList<NetworkReceiveThread>();
	private List<RemoteSender> sendThreads = new ArrayList<RemoteSender>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_game);
		this.parameter = (GameStartParameter) getIntent().getExtras().get(
				ActivityLauncher.GAMEPARAMETER);

		final GameView contentView = (GameView) findViewById(R.id.fullscreen_content);

		registerScreenTouchListener(contentView);
		initGame();
		contentView.setGameThread(this.gameThread);
		conditionalRestoreState();
		startNetworkThreads();
		this.gameThread.start();
	}

	private void startNetworkThreads() {
		for (NetworkReceiveThread receiver : this.networkReceiveThreads) {
			receiver.start();
		}
	}

	private void conditionalRestoreState() {
		Object data = getLastNonConfigurationInstance();
		if (data != null) {
			this.gameThread.applyState(data);
		}
	}

	private AbstractOtherPlayersFactory initInputFactory() {
		return this.parameter.getConfiguration().getOtherPlayers().get(0)
				.getFactory();
		// if (getSocket() != null) {
		// return AbstractOtherPlayersFactory.initNetwork(getSocket(), 0);
		// } else {
		// return AbstractOtherPlayersFactory.initSinglePlayer(this.parameter
		// .getConfiguration().getAiModus());
		// }
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
		final GameView contentView = (GameView) findViewById(R.id.fullscreen_content);
		World world = WorldFactory.create(this.parameter.getConfiguration(),
				this);

		AbstractOtherPlayersFactory otherPlayerFactory = initInputFactory();
		GameStartParameter parameter = (GameStartParameter) getIntent()
				.getExtras().get(ActivityLauncher.GAMEPARAMETER);
		AllPlayerConfig config = PlayerConfigFactory.create(parameter, world,
				contentView, otherPlayerFactory,
				this.parameter.getConfiguration());
		// List<StateSender> allStateSender = config.createStateSender();
		Player myPlayer = config.getTabletControlledPlayer();
		List<StateSender> allStateSender = createSender(myPlayer);
		initInputServices(otherPlayerFactory, config,
				extractRemoteSenders(allStateSender));
		// this.networkThread = otherPlayerFactory.createSender();
		this.gameThread = GameThreadFactory.create(world,
				config.getAllPlayerMovementControllers(),
				createInputServices(), allStateSender, this, config,
				this.parameter.getConfiguration(),
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

	private void initInputServices(AbstractOtherPlayersFactory singleton,
			AllPlayerConfig config, List<RemoteSender> allSender) {
		AbstractPlayerInputServicesFactory.init(this.parameter
				.getConfiguration().getInputConfiguration());
		AbstractPlayerInputServicesFactory<InputService> myPlayerFactory = AbstractPlayerInputServicesFactory
				.getSingleton();

		this.touchService = myPlayerFactory.createInputService(config, this);

		NetworkToGameDispatcher networkDispatcher = new NetworkToGameDispatcher();
		this.inputDispatcher = myPlayerFactory
				.createInputDispatcher(this.touchService);
		this.networkMovementService = config.createOtherInputService(
				networkDispatcher,
				createNetworkReceiveThreads(networkDispatcher, allSender),
				singleton, allSender);
		myPlayerFactory.insertGameControllerViews(
				(ViewGroup) findViewById(R.id.game_root), getLayoutInflater(),
				this.inputDispatcher);
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
		InputConfiguration inputConfiguration = this.parameter
				.getConfiguration().getInputConfiguration();
		LOGGER.info("Selected Input is " + inputConfiguration.toString());
		return createInputServicesTouch();
	}

	private List<InputService> createInputServicesTouch() {
		List<InputService> inputServes = new ArrayList<InputService>();
		inputServes.addAll(this.networkMovementService);
		inputServes.add(this.touchService);
		return inputServes;
	}

	@Override
	protected void onResume() {
		super.onResume();

		this.gameThread.setRunning(true);

	}

	@Override
	protected void onPause() {
		super.onPause();
		this.gameThread.setRunning(false);
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
}
