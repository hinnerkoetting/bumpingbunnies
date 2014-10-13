package de.oetting.bumpingbunnies.android.game;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.Window;
import android.widget.Toast;
import de.oetting.bumpingbunnies.R;
import de.oetting.bumpingbunnies.android.graphics.AndroidDrawThread;
import de.oetting.bumpingbunnies.android.graphics.AndroidDrawer;
import de.oetting.bumpingbunnies.android.input.InputDispatcher;
import de.oetting.bumpingbunnies.android.parcel.GamestartParameterParcellableWrapper;
import de.oetting.bumpingbunnies.core.configuration.PlayerConfigFactory;
import de.oetting.bumpingbunnies.core.game.CameraPositionCalculation;
import de.oetting.bumpingbunnies.core.game.graphics.ObjectsDrawer;
import de.oetting.bumpingbunnies.core.game.graphics.calculation.CoordinatesCalculationFactory;
import de.oetting.bumpingbunnies.core.game.graphics.calculation.RelativeCoordinatesCalculation;
import de.oetting.bumpingbunnies.core.game.main.GameMain;
import de.oetting.bumpingbunnies.core.game.main.GameThreadState;
import de.oetting.bumpingbunnies.core.graphics.DrawerFpsCounter;
import de.oetting.bumpingbunnies.core.networking.messaging.stop.OnThreadErrorCallback;
import de.oetting.bumpingbunnies.model.configuration.GameStartParameter;
import de.oetting.bumpingbunnies.model.game.objects.Player;
import de.oetting.bumpingbunnies.usecases.ActivityLauncher;
import de.oetting.bumpingbunnies.usecases.game.factories.DrawerFactory;
import de.oetting.bumpingbunnies.usecases.game.factories.GameMainFactory;
import de.oetting.bumpingbunnies.usecases.game.factories.InputDispatcherFactory;
import de.oetting.bumpingbunnies.usecases.resultScreen.model.ResultPlayerEntry;
import de.oetting.bumpingbunnies.usecases.resultScreen.model.ResultWrapper;

/**
 * Controls the bumping-bunnies game.
 */
public class GameActivity extends Activity implements OnThreadErrorCallback {

	private GameMain main;
	private InputDispatcher<?> inputDispatcher;
	private AndroidDrawThread drawThread;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_game);

		final GameView contentView = (GameView) findViewById(R.id.fullscreen_content);

		GameStartParameter parameter = ((GamestartParameterParcellableWrapper) getIntent().getExtras().get(ActivityLauncher.GAMEPARAMETER)).getParameter();
		Player myPlayer = PlayerConfigFactory.createMyPlayer(parameter);
		GameThreadState threadState = new GameThreadState();
		CameraPositionCalculation cameraCalculation = new CameraPositionCalculation(myPlayer);
		this.main = GameMainFactory.create(this, parameter, myPlayer, cameraCalculation);
		RelativeCoordinatesCalculation calculations = CoordinatesCalculationFactory.createCoordinatesCalculation(cameraCalculation);
		inputDispatcher = InputDispatcherFactory.createInputDispatcher(this, parameter, myPlayer, calculations);

		registerScreenTouchListener(contentView);

		ObjectsDrawer objectsDrawer = DrawerFactory.create(main.getWorld(), threadState, this, parameter.getConfiguration(), calculations);
		AndroidDrawer drawer = new AndroidDrawer(objectsDrawer, parameter.getConfiguration().getLocalSettings().isAltPixelMode());
		contentView.setCallback(drawer);
		drawThread = new AndroidDrawThread(new DrawerFpsCounter(drawer, threadState));
		drawThread.start();
		main.addJoinListener(drawer);
		contentView.addOnSizeListener(drawThread);
		conditionalRestoreState();
	}

	private void registerScreenTouchListener(final GameView contentView) {
		contentView.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				return inputDispatcher.dispatchGameTouch(event);
			}
		});
	}

	@SuppressWarnings({ "unchecked", "deprecation" })
	private void conditionalRestoreState() {
		Object data = getLastNonConfigurationInstance();
		if (data != null) {
			this.main.restorePlayerStates((List<Player>) data);
		}
	}

	@Override
	public void stopGame() {
		this.main.stop();
		drawThread.cancel();
		ActivityLauncher.startResult(this, extractResult());
	}

	private ResultWrapper extractResult() {
		return extractPlayerScores();
	}

	public ResultWrapper extractPlayerScores() {
		List<Player> players = main.getWorld().getAllPlayer();
		List<ResultPlayerEntry> resultEntries = new ArrayList<ResultPlayerEntry>(players.size());
		for (Player p : players) {
			ResultPlayerEntry entry = new ResultPlayerEntry(p.getName(), p.getScore(), p.getColor());
			resultEntries.add(entry);
		}
		return new ResultWrapper(resultEntries);
	}

	@Override
	protected void onResume() {
		super.onResume();
		this.main.onResume();
	}

	@Override
	protected void onPause() {
		super.onPause();
		this.main.onPause();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		this.main.destroy();
		drawThread.cancel();
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		boolean isKeyProcessed = inputDispatcher.dispatchOnKeyDown(keyCode, event);
		if (!isKeyProcessed) {
			return super.onKeyDown(keyCode, event);
		}
		return true;
	}

	@Override
	public boolean onKeyUp(int keyCode, KeyEvent event) {
		boolean isKeyProcessed = inputDispatcher.dispatchOnKeyUp(keyCode, event);
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
		return this.main.getWorld().getAllPlayer();
	}

	@Override
	public void onBackPressed() {
		sendStopMessage();
		ActivityLauncher.startResult(this, extractResult());
	}

	private void sendStopMessage() {
		this.main.sendStopMessage();
	}

	@Override
	public void onDisconnect() {
		runOnUiThread(new Runnable() {

			@Override
			public void run() {
				String message = getString(R.string.disconnected);
				Toast.makeText(GameActivity.this, message, Toast.LENGTH_LONG).show();
				stopGame();
			}
		});
	}
}
