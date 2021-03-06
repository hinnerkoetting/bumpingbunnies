package de.oetting.bumpingbunnies.pc.main;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.FlowPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import de.oetting.bumpingbunnies.core.BunniesException;
import de.oetting.bumpingbunnies.core.configuration.GameParameterFactory;
import de.oetting.bumpingbunnies.core.configuration.MakesGameVisibleFactory;
import de.oetting.bumpingbunnies.core.configuration.PlayerConfigFactory;
import de.oetting.bumpingbunnies.core.configuration.WlanNetworkBroadcasterfactory;
import de.oetting.bumpingbunnies.core.game.CameraPositionCalculation;
import de.oetting.bumpingbunnies.core.game.GameMainFactory;
import de.oetting.bumpingbunnies.core.game.IngameMenu;
import de.oetting.bumpingbunnies.core.game.graphics.BunnyDrawerFactory;
import de.oetting.bumpingbunnies.core.game.graphics.BunnyImagesReader;
import de.oetting.bumpingbunnies.core.game.graphics.CanvasCoordinateTranslator;
import de.oetting.bumpingbunnies.core.game.graphics.DrawablesFactory;
import de.oetting.bumpingbunnies.core.game.graphics.ObjectsDrawer;
import de.oetting.bumpingbunnies.core.game.graphics.DefaultPlayerImagesProvider;
import de.oetting.bumpingbunnies.core.game.graphics.calculation.AbsoluteCoordinatesCalculation;
import de.oetting.bumpingbunnies.core.game.graphics.calculation.CoordinatesCalculation;
import de.oetting.bumpingbunnies.core.game.main.GameMain;
import de.oetting.bumpingbunnies.core.game.main.GameThreadState;
import de.oetting.bumpingbunnies.core.game.player.BunnyFactory;
import de.oetting.bumpingbunnies.core.game.steps.ScoreboardSynchronisation;
import de.oetting.bumpingbunnies.core.graphics.Drawer;
import de.oetting.bumpingbunnies.core.graphics.DrawerFpsCounter;
import de.oetting.bumpingbunnies.core.graphics.NoopDrawer;
import de.oetting.bumpingbunnies.core.input.ConfigurableKeyboardInputFactory;
import de.oetting.bumpingbunnies.core.network.WlanSocketFactory;
import de.oetting.bumpingbunnies.core.network.sockets.SocketStorage;
import de.oetting.bumpingbunnies.core.networking.messaging.stop.GameStopper;
import de.oetting.bumpingbunnies.core.networking.sockets.SocketFactory;
import de.oetting.bumpingbunnies.core.threads.ThreadErrorCallback;
import de.oetting.bumpingbunnies.logger.Logger;
import de.oetting.bumpingbunnies.logger.LoggerFactory;
import de.oetting.bumpingbunnies.model.configuration.Configuration;
import de.oetting.bumpingbunnies.model.configuration.GameStartParameter;
import de.oetting.bumpingbunnies.model.configuration.LocalPlayerSettings;
import de.oetting.bumpingbunnies.model.configuration.LocalSettings;
import de.oetting.bumpingbunnies.model.configuration.OpponentConfiguration;
import de.oetting.bumpingbunnies.model.configuration.ServerSettings;
import de.oetting.bumpingbunnies.model.configuration.input.KeyboardInputConfiguration;
import de.oetting.bumpingbunnies.model.game.objects.Bunny;
import de.oetting.bumpingbunnies.model.game.objects.ImageWrapper;
import de.oetting.bumpingbunnies.model.game.objects.ModelConstants;
import de.oetting.bumpingbunnies.model.game.world.World;
import de.oetting.bumpingbunnies.model.game.world.WorldProperties;
import de.oetting.bumpingbunnies.pc.ApplicationStarter;
import de.oetting.bumpingbunnies.pc.configMenu.PcConfiguration;
import de.oetting.bumpingbunnies.pc.configuration.ConfigAccess;
import de.oetting.bumpingbunnies.pc.configuration.PcConfigurationConverter;
import de.oetting.bumpingbunnies.pc.error.ErrorHandler;
import de.oetting.bumpingbunnies.pc.game.input.PcInputDispatcher;
import de.oetting.bumpingbunnies.pc.graphics.PcCanvasAdapter;
import de.oetting.bumpingbunnies.pc.graphics.PcDrawer;
import de.oetting.bumpingbunnies.pc.graphics.YCoordinateInverterCalculation;
import de.oetting.bumpingbunnies.pc.graphics.drawables.factory.PcBackgroundDrawableFactory;
import de.oetting.bumpingbunnies.pc.graphics.drawables.factory.PcGameObjectDrawableFactory;
import de.oetting.bumpingbunnies.pc.graphics.drawables.factory.PcHeadImageDrawer;
import de.oetting.bumpingbunnies.pc.graphics.drawables.factory.PcImageLoader;
import de.oetting.bumpingbunnies.pc.graphics.drawables.factory.PcImageMirroror;
import de.oetting.bumpingbunnies.pc.graphics.drawables.factory.PcImagesColoror;
import de.oetting.bumpingbunnies.pc.music.PcMusicPlayerFactory;
import de.oetting.bumpingbunnies.pc.scoreMenu.ScoreEntry;
import de.oetting.bumpingbunnies.pc.scoreMenu.ScoreEntryComparator;
import de.oetting.bumpingbunnies.pc.scoreMenu.ScoreMenuApplication;
import de.oetting.bumpingbunnies.worldcreator.load.gameObjects.WorldLoader;
import de.oetting.bumpingbunnies.worldcreatorPc.load.PcWorldObjectsParser;

public class BunniesMain extends Application implements ThreadErrorCallback, GameStopper {

	private static Logger LOGGER = LoggerFactory.getLogger(BunniesMain.class);

	private Stage primaryStage;

	private boolean errorHappened;

	private GameMain gameMain;
	private Drawer drawerThread = new NoopDrawer();
	private PcInputDispatcher inputDispatcher;
	private GameStartParameter parameter;

	public static void main(String[] args) {
		startApplication();
	}

	public BunniesMain() {
		PcConfiguration pcConfiguration = new ConfigAccess().load();
		LocalSettings localSettings = new PcConfigurationConverter().convert2LocalSettings(pcConfiguration);
		ServerSettings generalSettings = new PcConfigurationConverter().convert2ServerSettings(pcConfiguration);
		List<OpponentConfiguration> opponents = new ArrayList<>();
		LocalPlayerSettings localPlayerSettings = new PcConfigurationConverter()
				.convert2LocalPlayerSettings(pcConfiguration);
		Configuration configuration = new Configuration(localSettings, generalSettings, opponents, localPlayerSettings,
				true);
		parameter = GameParameterFactory.createSingleplayerParameter(configuration);
	}

	public BunniesMain(GameStartParameter parameter) {
		this.parameter = parameter;
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		try {
			this.primaryStage = primaryStage;
			Canvas canvas = new Canvas(1000, 600);
			createPanel(primaryStage, canvas);

			Bunny myPlayer = PlayerConfigFactory.createMyPlayer(parameter);

			buildGame(canvas, myPlayer);
			inputDispatcher = new PcInputDispatcher(canvas, createIngameMenu(), gameMain.getWorld(), parameter, gameMain);

			startRendering();
			ConfigurableKeyboardInputFactory inputFactory = new ConfigurableKeyboardInputFactory();
			inputDispatcher.addInputService(inputFactory.create((KeyboardInputConfiguration) parameter
					.getConfiguration().getInputConfiguration(), myPlayer));

			addInputForOtherPlayers(inputFactory);
			primaryStage.setResizable(true);
		} catch (BunniesException e) {
			LOGGER.error("", e);
			showError(e.getUserMessage());
		} catch (Exception e) {
			LOGGER.error("", e);
			showTechnicalError();
		}
	}

	private IngameMenu createIngameMenu() {
		int speedSetting = parameter.getConfiguration().getGeneralSettings().getSpeedSetting();
		return new IngameMenu(gameMain, new BunnyFactory(speedSetting), gameMain.getWorld(),
				SocketStorage.getSingleton(), this);
	}

	private void addInputForOtherPlayers(ConfigurableKeyboardInputFactory inputFactory) {
		List<OpponentConfiguration> players = parameter.getConfiguration().getOtherPlayers();
		for (OpponentConfiguration config : players) {
			if (config.getOpponent().isLocalHumanPlayer()) {
				Bunny otherPlayer = gameMain.getWorld().findBunny(config.getPlayerId());
				inputDispatcher.addInputService(inputFactory.create((KeyboardInputConfiguration) config.getInput(),
						otherPlayer));
			}
		}
	}

	private void createPanel(Stage primaryStage, Canvas canvas) {
		FlowPane root = new FlowPane();
		Scene scene = new Scene(root, 1000, 600, Color.WHITESMOKE);
		primaryStage.setScene(scene);
		root.getChildren().add(canvas);
		resizeCanvasWhenPanelGetsResised(canvas, root);
		primaryStage.show();
		scene.setOnKeyPressed(new EventHandler<KeyEvent>() {

			@Override
			public void handle(KeyEvent event) {
				inputDispatcher.dispatchOnKeyDown(event);
			}

		});
		scene.setOnKeyReleased(new EventHandler<KeyEvent>() {

			@Override
			public void handle(KeyEvent event) {
				inputDispatcher.dispatchOnKeyUp(event.getCode());
			}
		});
		primaryStage.setOnCloseRequest((e) -> onCloseRequest(e));
		primaryStage.setTitle("Bumping Bunnies");
	}

	private void resizeCanvasWhenPanelGetsResised(Canvas canvas, FlowPane root) {
		root.widthProperty().addListener((event) -> updateWidth(canvas, root));
		root.heightProperty().addListener((event) -> updateHeight(canvas, root));
	}

	private void updateWidth(Canvas canvas, FlowPane root) {
		canvas.setWidth(root.getWidth());
		drawerThread.setNeedsUpdate(true);
	}

	private void updateHeight(Canvas canvas, FlowPane root) {
		canvas.setHeight(root.getHeight());
		drawerThread.setNeedsUpdate(true);

	}

	private void buildGame(Canvas canvas, Bunny myPlayer) {
		World world = createWorld();
		WorldProperties worldProperties = new WorldProperties(ModelConstants.STANDARD_WORLD_SIZE,
				ModelConstants.STANDARD_WORLD_SIZE);
		CoordinatesCalculation coordinatesCalculation = new YCoordinateInverterCalculation(
				new AbsoluteCoordinatesCalculation((int) canvas.getWidth(), (int) canvas.getHeight(), worldProperties));
		changeSizeInCoordinatesCalculationWhenScreenChanges(canvas, coordinatesCalculation);
		GameThreadState gameThreadState = new GameThreadState();
		initDrawer(canvas, world, coordinatesCalculation, gameThreadState);

		CameraPositionCalculation cameraCalculation = new CameraPositionCalculation(myPlayer, parameter
				.getConfiguration().getZoom());
		gameMain = new GameMainFactory().create(cameraCalculation, world, parameter, myPlayer, this,
				new PcMusicPlayerFactory(this), this, new ScoreboardSynchronisation(new NullScoreAccess(), world),
				createPossibleBroadcasterFactories(), createPossibleSocketFactories());
		gameMain.addJoinListener(drawerThread);
		gameMain.onResume();
	}

	private List<SocketFactory> createPossibleSocketFactories() {
		return Arrays.asList(new WlanSocketFactory());
	}

	private List<MakesGameVisibleFactory> createPossibleBroadcasterFactories() {
		return Arrays.asList(new WlanNetworkBroadcasterfactory());
	}

	private void changeSizeInCoordinatesCalculationWhenScreenChanges(Canvas canvas,
			CoordinatesCalculation coordinatesCalculation) {
		canvas.widthProperty().addListener(
				(observable, oldValue, newValue) -> coordinatesCalculation.updateCanvas(newValue.intValue(),
						(int) canvas.getHeight()));
		canvas.heightProperty().addListener(
				(observable, oldValue, newValue) -> coordinatesCalculation.updateCanvas((int) canvas.getWidth(),
						newValue.intValue()));
	}

	private void startRendering() {
		new AnimationTimer() {

			@Override
			public void handle(long now) {
				try {
					if (!errorHappened) {
						drawerThread.draw();
					}
				} catch (Exception e) {
					errorHappened = true;
					LOGGER.error("", e);
					showTechnicalError();
				}
			}

		}.start();
	}

	private void initDrawer(Canvas canvas, final World world, CoordinatesCalculation coordinatesCalculation,
			GameThreadState gameThreadState) {
		PcCanvasAdapter canvasDelegate = new PcCanvasAdapter();
		CanvasCoordinateTranslator coordinateTranslator = new CanvasCoordinateTranslator(canvasDelegate,
				coordinatesCalculation);
		DrawablesFactory factory = new DrawablesFactory(world, new PcBackgroundDrawableFactory(),
				new PcGameObjectDrawableFactory(new PcImageMirroror()), new BunnyDrawerFactory(
						new DefaultPlayerImagesProvider(new BunnyImagesReader(), new PcImageLoader()), new PcImagesColoror(),
						new PcImageMirroror(), new PcHeadImageDrawer()), true, coordinatesCalculation);
		ObjectsDrawer objectsDrawer = new ObjectsDrawer(factory, coordinateTranslator);
		Drawer drawer = new PcDrawer(objectsDrawer, canvas);
		drawerThread = new DrawerFpsCounter(drawer, gameThreadState);
	}

	private World createWorld() {
		return new WorldLoader().load(new PcWorldObjectsParser(), (is, key) -> new ImageWrapper(new Image(is), key));
	}

	private static void startApplication() {
		launch();
	}

	private void showTechnicalError() {
		showError("Technical error.");
	}

	private void showError(String text) {
		new ErrorHandler().showError(primaryStage, text);
	}

	private List<ScoreEntry> extractScores() {
		List<Bunny> allPlayer = gameMain.getWorld().getConnectedAndDisconnectedBunnies();
		List<ScoreEntry> entries = new ArrayList<>(allPlayer.size());
		synchronized (allPlayer) {
			for (Bunny player : allPlayer) {
				entries.add(new ScoreEntry(player.getName(), player.getScore(), player.getColor()));
			}
		}
		Collections.sort(entries, new ScoreEntryComparator());
		return entries;
	}

	private void onCloseRequest(WindowEvent e) {
		e.consume();
		gameStopped();
	}

	private void goToScoreScreen() {
		List<ScoreEntry> scores = extractScores();
		new ApplicationStarter().startApplication(new ScoreMenuApplication(scores), primaryStage);
	}

	@Override
	public void onThreadError() {
		Platform.runLater(() -> showTechnicalError());
	}

	@Override
	public void gameStopped() {
		gameMain.endGame();
		goToScoreScreen();
	}

	@Override
	public void onInitializationError(String message) {
		showError(message);
	}
}
