package de.jumpnbump.usecases.game.android;

import java.util.Arrays;
import java.util.List;

import android.app.Activity;
import android.bluetooth.BluetoothSocket;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.Window;
import de.jumpnbump.R;
import de.jumpnbump.logger.Logger;
import de.jumpnbump.logger.MyLog;
import de.jumpnbump.usecases.ActivityLauncher;
import de.jumpnbump.usecases.MyApplication;
import de.jumpnbump.usecases.game.android.factories.PlayerConfigFactoryFactory;
import de.jumpnbump.usecases.game.android.input.InputService;
import de.jumpnbump.usecases.game.android.input.dispatcher.InputDispatcher;
import de.jumpnbump.usecases.game.android.input.factory.AbstractInputServicesFactory;
import de.jumpnbump.usecases.game.businesslogic.GameStartParameter;
import de.jumpnbump.usecases.game.businesslogic.GameThread;
import de.jumpnbump.usecases.game.businesslogic.PlayerConfigFactory;
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

	private InputService networkMovementService;
	private RemoteSender networkThread;
	private GameStartParameter parameter;

	private InputDispatcher<?> inputTouchDispatcher;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_game);
		this.parameter = (GameStartParameter) getIntent().getExtras().get(
				ActivityLauncher.GAMEPARAMETER);
		initInputFactory();

		final GameView contentView = (GameView) findViewById(R.id.fullscreen_content);

		registerScreenTouchListener(contentView);
		initGame();
		contentView.setGameThread(this.gameThread);
		registerGamepadTouchEvents();
	}

	private void initInputFactory() {
		if (getSocket() != null) {
			AbstractOtherPlayersFactorySingleton.initNetwork(getSocket(),
					this.networkThread);
		} else {
			AbstractOtherPlayersFactorySingleton.initSinglePlayer();
		}
	}

	private void registerScreenTouchListener(final GameView contentView) {
		contentView.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				GameActivity.this.inputTouchDispatcher.dispatchGameTouch(event);
				return true;
			}
		});
	}

	private void registerGamepadTouchEvents() {
		OnTouchListener touchListener = new OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				GameActivity.this.inputTouchDispatcher.dispatchViewTouch(v,
						event);
				return true;
			}
		};
		OnTouchListener upTouchListener = new OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				GameActivity.this.inputTouchDispatcher.dispatchViewTouch(v,
						event);
				return true;
			}
		};
		findViewById(R.id.button_down).setOnTouchListener(touchListener);
		findViewById(R.id.button_up).setOnTouchListener(upTouchListener);
		findViewById(R.id.button_right).setOnTouchListener(touchListener);
		findViewById(R.id.button_left).setOnTouchListener(touchListener);

	}

	private void initGame() {
		final GameView contentView = (GameView) findViewById(R.id.fullscreen_content);
		World world = WorldFactory.create();
		GameThreadState threadState = new GameThreadState();

		AbstractOtherPlayersFactorySingleton singleton = AbstractOtherPlayersFactorySingleton
				.getSingleton();
		PlayerConfigFactory config = PlayerConfigFactoryFactory.create(
				getIntent(), world, contentView);
		initInputServices(singleton, config);

		AbstractStateSenderFactory stateSenderFactory = singleton
				.createStateSenderFactory();
		this.networkThread = singleton.createSender();
		this.gameThread = GameThreadFactory.create(world, threadState,
				config.getAllPlayerMovementControllers(),
				createInputServices(),
				config.createStateSender(stateSenderFactory));

		this.gameThread.start();
	}

	private void initInputServices(
			AbstractOtherPlayersFactorySingleton singleton,
			PlayerConfigFactory config) {
		AbstractInputServicesFactory.init(this.parameter.getConfiguration()
				.getInputConfiguration());
		AbstractInputServicesFactory singleton2 = AbstractInputServicesFactory
				.getSingleton();

		this.touchService = singleton2.createInputService(config);
		this.networkMovementService = config
				.createNetworkInputService(singleton);
		this.inputTouchDispatcher = singleton2
				.createInputDispatcher(this.touchService);
	}

	private List<InputService> createInputServices() {
		InputConfiguration inputConfiguration = this.parameter
				.getConfiguration().getInputConfiguration();
		LOGGER.info("Selected Input is " + inputConfiguration.toString());
		return createInputServicesTouch();

	}

	private List<InputService> createInputServicesTouch() {
		return Arrays.asList(this.networkMovementService, this.touchService);
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
