package de.jumpnbump.usecases.game;

import android.app.Activity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import de.jumpnbump.R;
import de.jumpnbump.logger.Logger;
import de.jumpnbump.logger.MyLog;
import de.jumpnbump.usecases.game.graphics.Drawer;
import de.jumpnbump.usecases.game.model.World;
import de.jumpnbump.usecases.game.services.MovementService;
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
	private MovementService movementService;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_game);

		final GameView contentView = (GameView) findViewById(R.id.fullscreen_content);

		World world = new World();
		Drawer drawer = new Drawer(world);
		this.movementService = new MovementService(world,
				contentView.getWidth(), contentView.getHeight());
		this.gameThread = new GameThread(drawer, new WorldController(world,
				this.movementService));
		contentView.setGameThread(this.gameThread);
		contentView.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				return GameActivity.this.onTouchEvent(event);
			}
		});
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		LOGGER.debug("touch");
		GameView contentView = (GameView) findViewById(R.id.fullscreen_content);
		this.movementService.setWindowHeight(contentView.getHeight());
		this.movementService.setWindowWidth(contentView.getWidth());
		this.movementService.onMotionEvent(event);
		return true;
	}

	@Override
	protected void onResume() {
		super.onResume();

		this.gameThread.setRunning(true);
		this.gameThread.start();
	}

	@Override
	protected void onPause() {
		super.onPause();
		this.gameThread.setRunning(false);
	}
}
