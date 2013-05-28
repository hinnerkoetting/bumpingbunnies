package de.jumpnbump.usecases.game.android;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import android.app.Activity;
import android.bluetooth.BluetoothSocket;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.Window;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.RadioButton;
import de.jumpnbump.R;
import de.jumpnbump.logger.Logger;
import de.jumpnbump.logger.MyLog;
import de.jumpnbump.usecases.MyApplication;
import de.jumpnbump.usecases.game.android.factories.PlayerConfigFactory;
import de.jumpnbump.usecases.game.android.input.GamepadInputService;
import de.jumpnbump.usecases.game.android.input.InputService;
import de.jumpnbump.usecases.game.android.input.TouchService;
import de.jumpnbump.usecases.game.android.input.TouchWithJumpService;
import de.jumpnbump.usecases.game.businesslogic.GameThread;
import de.jumpnbump.usecases.game.communication.NetworkSendQueueThread;
import de.jumpnbump.usecases.game.communication.factories.AbstractStateSenderFactory;
import de.jumpnbump.usecases.game.communication.factories.NetworkSendQueueThreadFactory;
import de.jumpnbump.usecases.game.factories.AbstractInputServiceFactorySingleton;
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
	private TouchWithJumpService touchWithJumpService;
	private TouchService touchService;
	private GamepadInputService gamepadService;
	private InputService networkMovementService;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_game);
		if (getSocket() != null) {
			NetworkSendQueueThread networkThread = NetworkSendQueueThreadFactory
					.create(getSocket());
			AbstractInputServiceFactorySingleton.initNetwork(getSocket(),
					networkThread);
		} else {
			AbstractInputServiceFactorySingleton.initSinglePlayer();
		}
		final GameView contentView = (GameView) findViewById(R.id.fullscreen_content);

		registerScreenTouchListener(contentView);
		initGame();
		contentView.setGameThread(this.gameThread);
		registerGamepadTouchEvents();
		registerAnalog();
	}

	private void registerAnalog() {
		// ImageView findViewById = findViewById(R.id.analog);

	}

	private void registerScreenTouchListener(final GameView contentView) {
		contentView.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				GameActivity.this.touchService.onMotionEvent(event);
				GameActivity.this.touchWithJumpService.onMotionEvent(event);
				return true;
			}
		});
	}

	private void registerGamepadTouchEvents() {
		OnTouchListener touchListener = new OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				GameActivity.this.gamepadService.onButtonTouch(v, event);
				return true;
			}
		};
		OnTouchListener upTouchListener = new OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				GameActivity.this.gamepadService.onButtonTouch(v, event);
				GameActivity.this.touchWithJumpService.onButtonTouchUp(event);
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

		AbstractInputServiceFactorySingleton singleton = AbstractInputServiceFactorySingleton
				.getSingleton();
		PlayerConfig config = PlayerConfigFactory.create(getIntent(), world,
				contentView);
		this.gamepadService = config.createGamepadService();
		this.touchWithJumpService = config.createTouchWithJumpService();
		this.touchService = config.createTouchService();
		this.networkMovementService = config
				.createNetworkInputService(singleton);

		AbstractStateSenderFactory stateSenderFactory = singleton
				.createStateSenderFactory();

		this.gameThread = GameThreadFactory.create(world, threadState,
				config.getAllPlayerMovementControllers(),
				createInputServices(),
				config.createStateSender(stateSenderFactory));

		this.gameThread.start();
		initInputTypeListener();
	}

	private void initInputTypeListener() {
		CompoundButton cb1 = (CompoundButton) findViewById(R.id.gamepad_cb);
		CompoundButton cb2 = (CompoundButton) findViewById(R.id.touch_cb);
		CompoundButton cb3 = (CompoundButton) findViewById(R.id.touch_with_jump_cb);
		OnCheckedChangeListener listener = new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				onClickInputTypeCb();
			}
		};
		cb1.setOnCheckedChangeListener(listener);
		cb2.setOnCheckedChangeListener(listener);
		cb3.setOnCheckedChangeListener(listener);
	}

	private List<InputService> createInputServices() {
		RadioButton cbGamepad = (RadioButton) findViewById(R.id.gamepad_cb);
		RadioButton cbTouch = (RadioButton) findViewById(R.id.touch_cb);
		RadioButton cbTouchJump = (RadioButton) findViewById(R.id.touch_with_jump_cb);
		if (cbGamepad.isChecked()) {
			return createInputServicesGamepad();
		} else if (cbTouch.isChecked()) {
			return createInputServicesTouch();
		} else if (cbTouchJump.isChecked()) {
			return createInputServicesTouchWithJump();
		}
		LOGGER.warn("Unknown user type");
		return new ArrayList<InputService>();
	}

	private List<InputService> createInputServicesTouchWithJump() {
		return Arrays.asList(this.networkMovementService,
				this.touchWithJumpService);
	}

	private List<InputService> createInputServicesTouch() {
		return Arrays.asList(this.networkMovementService, this.touchService);
	}

	private List<InputService> createInputServicesGamepad() {
		return Arrays.asList(this.networkMovementService, this.gamepadService);
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
		this.gameThread.onDestroy();
	}

	public void onClickInputTypeCb() {
		this.gameThread.switchInputServices(createInputServices());
	}
}
