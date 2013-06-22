package de.jumpnbump.usecases.game.android;

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
import de.jumpnbump.usecases.game.model.World;
import de.jumpnbump.usecases.start.communication.MySocket;
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
		conditionalRestoreState();
	}

	private void conditionalRestoreState() {
		Object data = getLastNonConfigurationInstance();
		if (data != null) {
			this.gameThread.applyState(data);
		}
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
				return GameActivity.this.inputDispatcher
						.dispatchGameTouch(event);
			}
		});
	}

	private void initGame() {
		final GameView contentView = (GameView) findViewById(R.id.fullscreen_content);
		World world = WorldFactory.create(this.parameter.getConfiguration(),
				this);

		initInputFactory();
		AbstractOtherPlayersFactorySingleton singleton = AbstractOtherPlayersFactorySingleton
				.getSingleton();

		PlayerConfig config = PlayerConfigFactory.create(getIntent(), world,
				contentView);
		initInputServices(singleton, config);

		this.networkThread = singleton.createSender();
		AbstractStateSenderFactory stateSenderFactory = singleton
				.createStateSenderFactory(this.networkThread);
		this.gameThread = GameThreadFactory.create(world,
				config.getAllPlayerMovementControllers(),
				createInputServices(),
				config.createStateSender(stateSenderFactory), this, config,
				this.parameter.getConfiguration(),
				config.getCoordinateCalculations());

		contentView.addOnSizeListener(this.gameThread);
		this.gameThread.start();
	}

	private void initInputServices(
			AbstractOtherPlayersFactorySingleton singleton, PlayerConfig config) {
		AbstractPlayerInputServicesFactory.init(this.parameter
				.getConfiguration().getInputConfiguration());
		AbstractPlayerInputServicesFactory<InputService> myPlayerFactory = AbstractPlayerInputServicesFactory
				.getSingleton();

		this.touchService = myPlayerFactory.createInputService(config, this);
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

	private MySocket getSocket() {
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
