package de.jumpnbump.usecases.game.android;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.bluetooth.BluetoothSocket;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.Window;
import de.jumpnbump.R;
import de.jumpnbump.logger.Logger;
import de.jumpnbump.logger.MyLog;
import de.jumpnbump.usecases.ActivityLauncher;
import de.jumpnbump.usecases.MyApplication;
import de.jumpnbump.usecases.game.android.factories.PlayerConfigFactory;
import de.jumpnbump.usecases.game.android.input.InputDispatcher;
import de.jumpnbump.usecases.game.android.input.InputService;
import de.jumpnbump.usecases.game.android.input.factory.AbstractPlayerInputServicesFactory;
import de.jumpnbump.usecases.game.businesslogic.GameStartParameter;
import de.jumpnbump.usecases.game.businesslogic.GameThread;
import de.jumpnbump.usecases.game.businesslogic.PlayerConfig;
import de.jumpnbump.usecases.game.communication.RemoteSender;
import de.jumpnbump.usecases.game.communication.factories.AbstractStateSenderFactory;
import de.jumpnbump.usecases.game.configuration.InputConfiguration;
import de.jumpnbump.usecases.game.factories.AbstractOtherPlayersFactorySingleton;
import de.jumpnbump.usecases.game.factories.GameThreadFactory;
import de.jumpnbump.usecases.game.factories.WorldFactory;
import de.jumpnbump.usecases.game.model.GameThreadState;
import de.jumpnbump.usecases.game.model.World;
import de.jumpnbump.util.SystemUiHider;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 * 
 * @see SystemUiHider
 */
public class GameActivity extends Activity {
	private static final MyLog LOGGER = Logger.getLogger(GameActivity.class);
	private GameThread gameThread;
	private InputService touchService;

	private List<InputService> networkMovementService;
	private RemoteSender networkThread;
	private GameStartParameter parameter;

	private InputDispatcher<?> inputDispatcher;

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
	}

	private void initInputFactory() {
		if (getSocket() != null) {
			AbstractOtherPlayersFactorySingleton.initNetwork(getSocket());
		} else {
			AbstractOtherPlayersFactorySingleton
					.initSinglePlayer(this.parameter.getConfiguration()
							.getAiModus());
		}
	}

	private void registerScreenTouchListener(final GameView contentView) {
		contentView.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				GameActivity.this.inputDispatcher.dispatchGameTouch(event);
				return true;
			}
		});
	}

	private void initGame() {
		final GameView contentView = (GameView) findViewById(R.id.fullscreen_content);
		World world = WorldFactory.create(this.parameter.getConfiguration(),
				this);
		GameThreadState threadState = new GameThreadState();

		initInputFactory();
		AbstractOtherPlayersFactorySingleton singleton = AbstractOtherPlayersFactorySingleton
				.getSingleton();
		PlayerConfig config = PlayerConfigFactory.create(
				getIntent(), world, contentView);
		initInputServices(singleton, config);

		this.networkThread = singleton.createSender();
		AbstractStateSenderFactory stateSenderFactory = singleton
				.createStateSenderFactory(this.networkThread);
		this.gameThread = GameThreadFactory.create(world, threadState,
				config.getAllPlayerMovementControllers(),
				createInputServices(),
				config.createStateSender(stateSenderFactory), getResources(),
				config);

		this.gameThread.start();
	}

	private void initInputServices(
			AbstractOtherPlayersFactorySingleton singleton,
			PlayerConfig config) {
		AbstractPlayerInputServicesFactory.init(this.parameter
				.getConfiguration().getInputConfiguration());
		AbstractPlayerInputServicesFactory<InputService> myPlayerFactory = AbstractPlayerInputServicesFactory
				.getSingleton();

		this.touchService = myPlayerFactory.createInputService(config);
		this.inputDispatcher = myPlayerFactory
				.createInputDispatcher(this.touchService);
		this.networkMovementService = config.createOtherInputService(singleton);
		myPlayerFactory.insertGameControllerViews(
				(ViewGroup) findViewById(R.id.game_root), getLayoutInflater(),
				this.inputDispatcher);
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

	private BluetoothSocket getSocket() {
		MyApplication application = (MyApplication) getApplication();
		return application.getSocket();
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
		this.networkThread.cancel();
	}

	public void onClickInputTypeCb() {
		this.gameThread.switchInputServices(createInputServices());
	}

}
