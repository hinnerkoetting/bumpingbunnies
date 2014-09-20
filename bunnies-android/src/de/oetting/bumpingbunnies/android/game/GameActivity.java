package de.oetting.bumpingbunnies.android.game;

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
import de.oetting.bumpingbunnies.android.input.InputDispatcher;
import de.oetting.bumpingbunnies.android.parcel.GamestartParameterParcellableWrapper;
import de.oetting.bumpingbunnies.core.configuration.PlayerConfigFactory;
import de.oetting.bumpingbunnies.core.networking.messaging.stop.GameStopper;
import de.oetting.bumpingbunnies.usecases.ActivityLauncher;
import de.oetting.bumpingbunnies.usecases.game.businesslogic.GameMain;
import de.oetting.bumpingbunnies.usecases.game.configuration.GameStartParameter;
import de.oetting.bumpingbunnies.usecases.game.factories.GameMainFactory;
import de.oetting.bumpingbunnies.usecases.game.model.Player;

/**
 * Controls the bumping-bunnies game.
 */
public class GameActivity extends Activity implements GameStopper {

	private GameMain main;
	private InputDispatcher<?> inputDispatcher;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_game);

		final GameView contentView = (GameView) findViewById(R.id.fullscreen_content);

		GameStartParameter parameter = ((GamestartParameterParcellableWrapper) getIntent().getExtras().get(ActivityLauncher.GAMEPARAMETER)).getParameter();
		Player myPlayer = PlayerConfigFactory.createMyPlayer(parameter);
		this.main = GameMainFactory.create(this, parameter, myPlayer);
		inputDispatcher = GameMainFactory.createInputDispatcher(this, parameter, myPlayer);
		registerScreenTouchListener(contentView);

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
		this.main.stop(this);
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
		this.main.startResultScreen(this);
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
