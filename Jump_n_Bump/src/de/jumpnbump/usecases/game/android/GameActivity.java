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
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import de.jumpnbump.R;
import de.jumpnbump.logger.Logger;
import de.jumpnbump.logger.MyLog;
import de.jumpnbump.usecases.ActivityLauncher;
import de.jumpnbump.usecases.MyApplication;
import de.jumpnbump.usecases.game.android.input.GamepadInputService;
import de.jumpnbump.usecases.game.android.input.InputService;
import de.jumpnbump.usecases.game.android.input.TouchService;
import de.jumpnbump.usecases.game.businesslogic.CollisionDetection;
import de.jumpnbump.usecases.game.businesslogic.GamePlayerController;
import de.jumpnbump.usecases.game.businesslogic.GameThread;
import de.jumpnbump.usecases.game.factories.CollisionDetectionFactory;
import de.jumpnbump.usecases.game.factories.GameThreadFactory;
import de.jumpnbump.usecases.game.factories.InputServiceFactory;
import de.jumpnbump.usecases.game.factories.PlayerMovementFactory;
import de.jumpnbump.usecases.game.factories.TouchServiceFactory;
import de.jumpnbump.usecases.game.factories.UserInputFactory;
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
	private static final boolean GAMEPAD_IS_ACTIVE = true;
	private GameThread gameThread;
	private TouchService touchService;
	private GamepadInputService gamepadService;
	private InputService networkMovementService;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_game);

		final GameView contentView = (GameView) findViewById(R.id.fullscreen_content);

		registerScreenTouchListener(contentView);
		initGame();
		contentView.setGameThread(this.gameThread);
		registerGamepadTouchEvents();
	}

	private void registerScreenTouchListener(final GameView contentView) {
		contentView.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				GameActivity.this.touchService.onMotionEvent(event);
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
		findViewById(R.id.button_down).setOnTouchListener(touchListener);
		findViewById(R.id.button_up).setOnTouchListener(touchListener);
		findViewById(R.id.button_right).setOnTouchListener(touchListener);
		findViewById(R.id.button_left).setOnTouchListener(touchListener);

	}

	private void initGame() {
		final GameView contentView = (GameView) findViewById(R.id.fullscreen_content);
		World world = WorldFactory.create();
		GameThreadState threadState = new GameThreadState();

		CollisionDetection collisionDetection = CollisionDetectionFactory
				.create(world, contentView);

		GamePlayerController playerMovement = PlayerMovementFactory.create(
				world.getPlayer1(), collisionDetection);
		GamePlayerController player2Movement = PlayerMovementFactory.create(
				world.getPlayer2(), collisionDetection);
		int playerId;

		if (getIntent().getExtras() != null) {
			playerId = getIntent().getExtras().getInt(
					ActivityLauncher.PLAYER_ID_CONSTANT);
		} else {
			playerId = 0;
		}

		if (playerId == 0) {
			this.gamepadService = UserInputFactory.createGamepad(
					playerMovement, getSocket());
			initTouchService(world, playerMovement, contentView);
			this.networkMovementService = InputServiceFactory.create(
					getSocket(), world.getPlayer2());
		} else {
			this.gamepadService = UserInputFactory.createGamepad(
					player2Movement, getSocket());
			initTouchService(world, player2Movement, contentView);
			this.networkMovementService = InputServiceFactory.create(
					getSocket(), world.getPlayer1());
		}
		List<GamePlayerController> playerMovements = Arrays.asList(
				playerMovement, player2Movement);

		this.gameThread = GameThreadFactory.create(world, threadState,
				playerMovements, createInputServices());

		this.gameThread.start();
		initGamepadCbListener();

	}

	private void initGamepadCbListener() {
		CheckBox cb = (CheckBox) findViewById(R.id.gamepad_cb);
		cb.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				onClickGamepadCB();
			}
		});
	}

	private void initTouchService(World world,
			GamePlayerController playerMovement, GameView contentView) {
		this.touchService = TouchServiceFactory.create(playerMovement,
				contentView, getSocket());
	}

	private List<InputService> createInputServices() {
		CheckBox cb = (CheckBox) findViewById(R.id.gamepad_cb);
		if (cb.isChecked()) {
			return createInputServicesGamepad();
		} else {
			return createInputServicesTouch();
		}
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
	public boolean onTouchEvent(MotionEvent event) {
		LOGGER.debug("touch");

		return true;
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

	public void onClickGamepadCB() {
		this.gameThread.switchInputServices(createInputServices());
	}
}
