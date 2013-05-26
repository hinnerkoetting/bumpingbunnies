package de.jumpnbump.usecases.game;

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
import de.jumpnbump.usecases.game.businesslogic.CollisionDetection;
import de.jumpnbump.usecases.game.businesslogic.GamePlayerController;
import de.jumpnbump.usecases.game.businesslogic.InputService;
import de.jumpnbump.usecases.game.businesslogic.TouchService;
import de.jumpnbump.usecases.game.factories.CollisionDetectionFactory;
import de.jumpnbump.usecases.game.factories.GameThreadFactory;
import de.jumpnbump.usecases.game.factories.InputServiceFactory;
import de.jumpnbump.usecases.game.factories.PlayerMovementFactory;
import de.jumpnbump.usecases.game.factories.TouchServiceFactory;
import de.jumpnbump.usecases.game.factories.WorldFactory;
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
	private TouchService touchService;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_game);

		final GameView contentView = (GameView) findViewById(R.id.fullscreen_content);

		contentView.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				return GameActivity.this.onTouchEvent(event);
			}
		});
		initGame();
		contentView.setGameThread(this.gameThread);
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

		InputService networkMovementService;

		if (playerId == 0) {

			initTouchService(world, playerMovement, contentView);
			networkMovementService = InputServiceFactory.create(getSocket(),
					world.getPlayer2());
		} else {
			initTouchService(world, player2Movement, contentView);
			networkMovementService = InputServiceFactory.create(getSocket(),
					world.getPlayer1());
		}
		List<GamePlayerController> playerMovements = Arrays.asList(
				playerMovement, player2Movement);
		List<InputService> inputServices = Arrays.asList(
				networkMovementService, this.touchService);
		this.gameThread = GameThreadFactory.create(world, threadState,
				playerMovements, inputServices);

		this.gameThread.start();

	}

	private void initTouchService(World world,
			GamePlayerController playerMovement, GameView contentView) {
		this.touchService = TouchServiceFactory.create(playerMovement,
				contentView, getSocket());
	}

	private BluetoothSocket getSocket() {
		MyApplication application = (MyApplication) getApplication();
		return application.getSocket();
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		LOGGER.debug("touch");
		this.touchService.onMotionEvent(event);
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
}
