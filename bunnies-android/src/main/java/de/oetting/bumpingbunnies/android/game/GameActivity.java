package de.oetting.bumpingbunnies.android.game;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.graphics.BitmapFactory;
import android.graphics.PorterDuff.Mode;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.Window;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;
import de.oetting.bumpingbunnies.R;
import de.oetting.bumpingbunnies.android.graphics.AndroidDrawThread;
import de.oetting.bumpingbunnies.android.graphics.AndroidDrawer;
import de.oetting.bumpingbunnies.android.input.InputDispatcher;
import de.oetting.bumpingbunnies.android.input.hardwareKeyboard.HardwareKeyboardInputConfiguration;
import de.oetting.bumpingbunnies.android.parcel.GamestartParameterParcellableWrapper;
import de.oetting.bumpingbunnies.android.xml.parsing.AndroidXmlWorldParser;
import de.oetting.bumpingbunnies.communication.bluetooth.BluetoothDiscoverableFactory;
import de.oetting.bumpingbunnies.communication.bluetooth.BluetoothSocketFactory;
import de.oetting.bumpingbunnies.core.configuration.MakesGameVisibleFactory;
import de.oetting.bumpingbunnies.core.configuration.PlayerConfigFactory;
import de.oetting.bumpingbunnies.core.configuration.WlanNetworkBroadcasterfactory;
import de.oetting.bumpingbunnies.core.game.CameraPositionCalculation;
import de.oetting.bumpingbunnies.core.game.GameMainFactory;
import de.oetting.bumpingbunnies.core.game.IngameMenu;
import de.oetting.bumpingbunnies.core.game.graphics.ObjectsDrawer;
import de.oetting.bumpingbunnies.core.game.graphics.calculation.CoordinatesCalculationFactory;
import de.oetting.bumpingbunnies.core.game.graphics.calculation.RelativeCoordinatesCalculation;
import de.oetting.bumpingbunnies.core.game.main.GameMain;
import de.oetting.bumpingbunnies.core.game.main.GameThreadState;
import de.oetting.bumpingbunnies.core.game.player.BunnyFactory;
import de.oetting.bumpingbunnies.core.game.steps.ScoreboardSynchronisation;
import de.oetting.bumpingbunnies.core.graphics.DrawerFpsCounter;
import de.oetting.bumpingbunnies.core.network.WlanSocketFactory;
import de.oetting.bumpingbunnies.core.network.sockets.SocketStorage;
import de.oetting.bumpingbunnies.core.networking.messaging.stop.GameStopper;
import de.oetting.bumpingbunnies.core.networking.sockets.SocketFactory;
import de.oetting.bumpingbunnies.core.threads.ThreadErrorCallback;
import de.oetting.bumpingbunnies.model.configuration.GameStartParameter;
import de.oetting.bumpingbunnies.model.game.objects.Bunny;
import de.oetting.bumpingbunnies.model.game.objects.ImageWrapper;
import de.oetting.bumpingbunnies.model.game.world.World;
import de.oetting.bumpingbunnies.model.game.world.WorldProperties;
import de.oetting.bumpingbunnies.usecases.ActivityLauncher;
import de.oetting.bumpingbunnies.usecases.game.factories.DrawerFactory;
import de.oetting.bumpingbunnies.usecases.game.factories.InputDispatcherFactory;
import de.oetting.bumpingbunnies.usecases.game.sound.AndroidMusicPlayerFactory;
import de.oetting.bumpingbunnies.usecases.resultScreen.model.ResultPlayerEntry;
import de.oetting.bumpingbunnies.usecases.resultScreen.model.ResultWrapper;
import de.oetting.bumpingbunnies.worldcreator.load.ImageCreator;
import de.oetting.bumpingbunnies.worldcreator.load.gameObjects.WorldLoader;

/**
 * Controls the bumping-bunnies game.
 */
public class GameActivity extends Activity implements ThreadErrorCallback, GameStopper {

	private GameMain main;
	private InputDispatcher<?> inputDispatcher;
	private AndroidDrawThread drawThread;
	private AndroidIngameMenuAdapter menuAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_game);

		final GameView contentView = (GameView) findViewById(R.id.fullscreen_content);

		GameStartParameter parameter = ((GamestartParameterParcellableWrapper) getIntent().getExtras().get(
				ActivityLauncher.GAMEPARAMETER)).getParameter();
		Bunny myPlayer = PlayerConfigFactory.createMyPlayer(parameter);
		GameThreadState threadState = new GameThreadState();
		CameraPositionCalculation cameraCalculation = new CameraPositionCalculation(myPlayer, parameter
				.getConfiguration().getZoom());
		World world = createWorld(this, parameter);
		ScoreboardSynchronisation scoreboardSynchronisation = createScoreboardSynchronisation(world);
		this.main = new GameMainFactory().create(cameraCalculation, world, parameter, myPlayer, this,
				new AndroidMusicPlayerFactory(this), this, scoreboardSynchronisation,
				createPossibleBroadcasterFactories(), createPossibleSocketFactories());
		main.addJoinListener(scoreboardSynchronisation);
		scoreboardSynchronisation.scoreIsChanged();
		RelativeCoordinatesCalculation calculations = CoordinatesCalculationFactory.createCoordinatesCalculation(
				cameraCalculation, new WorldProperties());
		inputDispatcher = InputDispatcherFactory.createInputDispatcher(this, parameter, myPlayer, calculations);

		registerScreenTouchListener(contentView);

		ObjectsDrawer objectsDrawer = DrawerFactory.create(main.getWorld(), threadState, parameter.getConfiguration(),
				calculations, this);
		AndroidDrawer drawer = new AndroidDrawer(objectsDrawer, this);
		contentView.setCallback(drawer);
		drawThread = new AndroidDrawThread(new DrawerFpsCounter(drawer, threadState), this);
		drawThread.start();
		main.addJoinListener(drawer);
		contentView.addOnSizeListener(drawThread);
		menuAdapter = createMenu(parameter);
		checkForMultitouchAvailabilty(parameter);
		conditionalRestoreState();
		hideNonUsableObjects();
	}

	private List<SocketFactory> createPossibleSocketFactories() {
		List<SocketFactory> factories = new ArrayList<SocketFactory>();
		factories.add(new WlanSocketFactory());
		if (BluetoothAdapter.getDefaultAdapter() != null)
			factories.add(new BluetoothSocketFactory(BluetoothAdapter.getDefaultAdapter())); 
		return factories;
	}

	private List<MakesGameVisibleFactory> createPossibleBroadcasterFactories() {
		List<MakesGameVisibleFactory> factories = new ArrayList<MakesGameVisibleFactory>();
		factories.add(new WlanNetworkBroadcasterfactory());
		factories.add(new BluetoothDiscoverableFactory(this));
		return factories;
	}

	private void hideNonUsableObjects() {
		if (!getPackageManager().hasSystemFeature(PackageManager.FEATURE_TOUCHSCREEN)) {
			findViewById(R.id.menu_game).setVisibility(View.INVISIBLE);
		} else {
			ImageView icon = (ImageView) findViewById(R.id.menu_game);
			Drawable drawable = getResources().getDrawable(R.drawable.ic_settings_white_48dp);
			drawable.setColorFilter(de.oetting.bumpingbunnies.model.color.Color.DARK_BLUE, Mode.MULTIPLY);
			icon.setImageDrawable(drawable);
		}
	}

	private ScoreboardSynchronisation createScoreboardSynchronisation(World world) {
		ScoreboardArrayAdapter score = createScoreBoard(world);
		return new ScoreboardSynchronisation(new AndroidScoreboardAccess(score, this), world);
	}

	private ScoreboardArrayAdapter createScoreBoard(World world) {
		ScoreboardArrayAdapter scoreBoard = new ScoreboardArrayAdapter(this);
		ListView scoreboardList = (ListView) findViewById(R.id.game_scoreboard_list);
		scoreboardList.setAdapter(scoreBoard);
		for (Bunny bunny : world.getAllConnectedBunnies())
			scoreBoard.add(bunny);
		return scoreBoard;
	}

	private AndroidIngameMenuAdapter createMenu(GameStartParameter parameter) {
		IngameMenu ingameMenu = new IngameMenu(main, new BunnyFactory(parameter.getConfiguration().getGeneralSettings()
				.getSpeedSetting()), main.getWorld(), SocketStorage.getSingleton(), this);
		return new AndroidIngameMenuAdapter(ingameMenu, this, main.getWorld(), parameter, main);
	}

	private World createWorld(GameActivity activity, GameStartParameter parameter) {
		AndroidXmlWorldParser parser = new AndroidXmlWorldParser();
		return new WorldLoader().load(parser, new ImageCreator() {

			@Override
			public ImageWrapper createImage(InputStream inputStream, String imageKey) {
				return new ImageWrapper(BitmapFactory.decodeStream(inputStream), imageKey);
			}
		});
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
			this.main.restorePlayerStates((List<Bunny>) data);
		}
	}

	@Override
	public void onThreadError() {
		this.main.endGame();
		drawThread.cancel();
		ActivityLauncher.startResult(this, extractResult());
	}

	private ResultWrapper extractResult() {
		return extractPlayerScores();
	}

	public ResultWrapper extractPlayerScores() {
		List<Bunny> players = main.getWorld().getConnectedAndDisconnectedBunnies();
		List<ResultPlayerEntry> resultEntries = new ArrayList<ResultPlayerEntry>(players.size());
		for (Bunny p : players) {
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
		this.main.endGame();
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

	private List<Bunny> getAllPlayers() {
		return this.main.getWorld().getAllConnectedBunnies();
	}

	@Override
	public void onBackPressed() {
		gameStopped();
	}

	@Override
	public void gameStopped() {
		main.endGame();
		ActivityLauncher.startResult(this, extractResult());
	}

	@Override
	public void onInitializationError(final String message) {
		runOnUiThread(new Runnable() {

			@Override
			public void run() {
				Toast toast = Toast.makeText(GameActivity.this, message, Toast.LENGTH_LONG);
				toast.show();
			}
		});
	}

	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {
		menu.clear();
		this.menuAdapter.createMenu(menu);
		return super.onPrepareOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		return menuAdapter.menuItemSelected(item);
	}

	public void onMenuClick(View v) {
		openOptionsMenu();
	}

	// workaround for fix, that android usually does not allow menus on xlarge
	// devices
	// see also http://stackoverflow.com/a/17903128/2337393
	public void openOptionsMenu() {

		Configuration config = getResources().getConfiguration();

		if ((config.screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) > Configuration.SCREENLAYOUT_SIZE_LARGE) {

			int originalScreenLayout = config.screenLayout;
			config.screenLayout = Configuration.SCREENLAYOUT_SIZE_LARGE;
			super.openOptionsMenu();
			config.screenLayout = originalScreenLayout;

		} else {
			super.openOptionsMenu();
		}
	}

	private void checkForMultitouchAvailabilty(GameStartParameter parameter) {
		boolean supportsMultiTouch = getPackageManager()
				.hasSystemFeature(PackageManager.FEATURE_TOUCHSCREEN_MULTITOUCH);
		boolean isHardwareInput = parameter.getConfiguration().getInputConfiguration() instanceof HardwareKeyboardInputConfiguration;
		if (!supportsMultiTouch && !isHardwareInput) {
			Toast.makeText(this, R.string.no_multitouch, Toast.LENGTH_LONG).show();
		}
	}
}
