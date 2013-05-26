package de.jumpnbump.usecases.game;

import android.app.Activity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.Window;
import de.jumpnbump.R;
import de.jumpnbump.logger.Level;
import de.jumpnbump.logger.Logger;
import de.jumpnbump.logger.MyLog;
import de.jumpnbump.usecases.ActivityLauncher;
import de.jumpnbump.usecases.MyApplication;
import de.jumpnbump.usecases.game.businesslogic.CollisionDetection;
import de.jumpnbump.usecases.game.businesslogic.MovementService;
import de.jumpnbump.usecases.game.businesslogic.NetworkMovementService;
import de.jumpnbump.usecases.game.businesslogic.PlayerMovement;
import de.jumpnbump.usecases.game.businesslogic.TouchService;
import de.jumpnbump.usecases.game.factories.CollisionDetectionFactory;
import de.jumpnbump.usecases.game.factories.GameThreadFactory;
import de.jumpnbump.usecases.game.factories.PlayerMovementFactory;
import de.jumpnbump.usecases.game.factories.TouchServiceFactory;
import de.jumpnbump.usecases.game.graphics.Drawer;
import de.jumpnbump.usecases.game.model.World;
import de.jumpnbump.usecases.game.network.GameNetworkReceiveThread;
import de.jumpnbump.usecases.game.network.GameNetworkSendThread;
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
		Logger.setGlobalLogLevel(Level.DEBUG);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_game);

		final GameView contentView = (GameView) findViewById(R.id.fullscreen_content);

		World world = new World();
		GameThreadState threadState = new GameThreadState();
		Drawer drawer = new Drawer(world, threadState);

		MyApplication application = (MyApplication) getApplication();
		CollisionDetection collisionDetection = CollisionDetectionFactory
				.create(world, contentView);
		PlayerMovement playerMovent = PlayerMovementFactory.create(
				world.getPlayer1(), collisionDetection);
		PlayerMovement player2Movement = PlayerMovementFactory.create(
				world.getPlayer2(), collisionDetection);
		int playerId;

		if (getIntent().getExtras() != null) {
			playerId = getIntent().getExtras().getInt(
					ActivityLauncher.PLAYER_ID_CONSTANT);
		} else {
			playerId = 0;
		}
		GameNetworkReceiveThread networkThread;
		GameNetworkSendThread sendthread = new GameNetworkSendThread(
				application.getSocket());
		MovementService networkMovementService;

		if (playerId == 0) {
			networkThread = new GameNetworkReceiveThread(
					application.getSocket());
			sendthread = new GameNetworkSendThread(application.getSocket());
			this.touchService = TouchServiceFactory.create(world, playerMovent,
					contentView, sendthread);
			// networkMovementService = new DummyMovementService();
			networkMovementService = new NetworkMovementService(networkThread,
					world.getPlayer2());
		} else {
			networkThread = new GameNetworkReceiveThread(
					application.getSocket());
			sendthread = new GameNetworkSendThread(application.getSocket());
			this.touchService = TouchServiceFactory.create(world,
					player2Movement, contentView, sendthread);
			networkMovementService = new NetworkMovementService(networkThread,
					world.getPlayer1());
			// networkMovementService = new DummyMovementService();
		}

		WorldController worldController = new WorldController(playerMovent,
				player2Movement);
		worldController.addMovementService(networkMovementService);
		worldController.addMovementService(this.touchService);
		this.gameThread = GameThreadFactory.create(drawer, worldController,
				threadState);

		contentView.setGameThread(this.gameThread);
		contentView.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				return GameActivity.this.onTouchEvent(event);
			}
		});
		this.gameThread.start();
		networkThread.start();
		sendthread.start();
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
}
