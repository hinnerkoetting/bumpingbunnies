package de.oetting.bumpingbunnies.usecases.game.android;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.Window;
import de.oetting.bumpingbunnies.R;
import de.oetting.bumpingbunnies.usecases.game.businesslogic.GameMain;
import de.oetting.bumpingbunnies.usecases.game.businesslogic.PlayerMovementController;
import de.oetting.bumpingbunnies.usecases.game.factories.GameMainFactory;
import de.oetting.bumpingbunnies.usecases.game.model.Player;

/**
 * Controls the bumping-bunnies game.
 */
public class GameActivity extends Activity {
	private GameMain main;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_game);

		final GameView contentView = (GameView) findViewById(R.id.fullscreen_content);

		this.main = GameMainFactory.create(this);
		registerScreenTouchListener(contentView);

		conditionalRestoreState();
	}

	private void registerScreenTouchListener(final GameView contentView) {
		contentView.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				return GameActivity.this.main.ontouch(event);

			}
		});
	}

	@SuppressWarnings({ "unchecked", "deprecation" })
	private void conditionalRestoreState() {
		Object data = getLastNonConfigurationInstance();
		if (data != null) {
			applyPlayers((List<Player>) data);
		}
	}

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
		boolean isKeyProcessed = GameActivity.this.main.getInputDispatcher().dispatchOnKeyDown(keyCode, event);
		if (!isKeyProcessed) {
			return super.onKeyDown(keyCode, event);
		}
		return true;
	}

	@Override
	public boolean onKeyUp(int keyCode, KeyEvent event) {
		boolean isKeyProcessed = GameActivity.this.main.getInputDispatcher().dispatchOnKeyUp(keyCode, event);
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
		List<PlayerMovementController> playermovements = this.main.getAllPlayerConfig().getAllPlayerMovementControllers();
		List<Player> players = new ArrayList<Player>(playermovements.size());
		for (PlayerMovementController movement : playermovements) {
			players.add(movement.getPlayer());
		}
		return players;
	}

	public void applyPlayers(List<Player> storedPlayers) {
		List<PlayerMovementController> playermovements = this.main.getAllPlayerConfig().getAllPlayerMovementControllers();
		for (PlayerMovementController movement : playermovements) {
			for (Player storedPlayer : storedPlayers) {
				if (movement.getPlayer().id() == storedPlayer.id()) {
					movement.getPlayer().applyStateTo(storedPlayer);
				}
			}
		}
	}

	@Override
	public void onBackPressed() {
		sendStopMessage();
		this.main.startResultScreen(this);
	}

	private void sendStopMessage() {
		this.main.sendStopMessage();
	}

}
