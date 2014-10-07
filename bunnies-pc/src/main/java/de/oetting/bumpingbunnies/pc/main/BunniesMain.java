package de.oetting.bumpingbunnies.pc.main;

import java.util.Arrays;
import java.util.List;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import de.oetting.bumpingbunnies.core.configuration.GameParameterFactory;
import de.oetting.bumpingbunnies.core.configuration.PlayerConfigFactory;
import de.oetting.bumpingbunnies.core.game.CameraPositionCalculation;
import de.oetting.bumpingbunnies.core.game.graphics.CanvasCoordinateTranslator;
import de.oetting.bumpingbunnies.core.game.graphics.DefaultDrawablesFactory;
import de.oetting.bumpingbunnies.core.game.graphics.ObjectsDrawer;
import de.oetting.bumpingbunnies.core.game.graphics.calculation.AbsoluteCoordinatesCalculation;
import de.oetting.bumpingbunnies.core.game.graphics.calculation.CoordinatesCalculation;
import de.oetting.bumpingbunnies.core.game.main.GameMain;
import de.oetting.bumpingbunnies.core.game.main.GameThreadState;
import de.oetting.bumpingbunnies.core.game.movement.PlayerMovement;
import de.oetting.bumpingbunnies.core.graphics.Drawer;
import de.oetting.bumpingbunnies.core.graphics.DrawerFpsCounter;
import de.oetting.bumpingbunnies.core.graphics.NoopDrawer;
import de.oetting.bumpingbunnies.core.input.ConfigurableKeyboardInputService;
import de.oetting.bumpingbunnies.core.world.World;
import de.oetting.bumpingbunnies.core.worldCreation.parser.ClasspathXmlreader;
import de.oetting.bumpingbunnies.core.worldCreation.parser.XmlReader;
import de.oetting.bumpingbunnies.logger.Logger;
import de.oetting.bumpingbunnies.logger.LoggerFactory;
import de.oetting.bumpingbunnies.model.configuration.AiModus;
import de.oetting.bumpingbunnies.model.configuration.Configuration;
import de.oetting.bumpingbunnies.model.configuration.GameStartParameter;
import de.oetting.bumpingbunnies.model.configuration.GeneralSettings;
import de.oetting.bumpingbunnies.model.configuration.InputConfiguration;
import de.oetting.bumpingbunnies.model.configuration.LocalPlayerSettings;
import de.oetting.bumpingbunnies.model.configuration.LocalSettings;
import de.oetting.bumpingbunnies.model.configuration.NetworkType;
import de.oetting.bumpingbunnies.model.configuration.OpponentConfiguration;
import de.oetting.bumpingbunnies.model.configuration.PlayerConfig;
import de.oetting.bumpingbunnies.model.configuration.PlayerProperties;
import de.oetting.bumpingbunnies.model.configuration.WorldConfiguration;
import de.oetting.bumpingbunnies.model.game.objects.ModelConstants;
import de.oetting.bumpingbunnies.model.game.objects.Opponent;
import de.oetting.bumpingbunnies.model.game.objects.OpponentType;
import de.oetting.bumpingbunnies.model.game.objects.Player;
import de.oetting.bumpingbunnies.model.game.world.WorldProperties;
import de.oetting.bumpingbunnies.pc.game.GameMainFactory;
import de.oetting.bumpingbunnies.pc.game.input.PcInputDispatcher;
import de.oetting.bumpingbunnies.pc.graphics.PcCanvasDelegate;
import de.oetting.bumpingbunnies.pc.graphics.PcDrawer;
import de.oetting.bumpingbunnies.pc.graphics.YCoordinateInverterCalculation;
import de.oetting.bumpingbunnies.pc.graphics.drawables.factory.PcBackgroundDrawableFactory;
import de.oetting.bumpingbunnies.pc.graphics.drawables.factory.PcGameObjectDrawableFactory;
import de.oetting.bumpingbunnies.pc.graphics.drawables.factory.PcPlayerDrawableFactory;
import de.oetting.bumpingbunnies.pc.worldcreation.parser.NoopResourceProvider;
import de.oetting.bumpingbunnies.pc.worldcreation.parser.PcWorldObjectsParser;

public class BunniesMain extends Application {

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
		LocalSettings localSettings = new LocalSettings(InputConfiguration.KEYBOARD, 1, true, false);
		GeneralSettings generalSettings = new GeneralSettings(WorldConfiguration.CLASSIC, 25, NetworkType.WLAN);
		List<OpponentConfiguration> opponents = Arrays.asList(new OpponentConfiguration(AiModus.NORMAL, new PlayerProperties(1, "Player 2"), Opponent
				.createOpponent("Player2", OpponentType.AI)));
		LocalPlayerSettings localPlayerSettings = new LocalPlayerSettings("Player 1");
		Configuration configuration = new Configuration(localSettings, generalSettings, opponents, localPlayerSettings, true);
		parameter = GameParameterFactory.createSingleplayerParameter(configuration);
	}

	public BunniesMain(GameStartParameter parameter) {
		this.parameter = parameter;
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		this.primaryStage = primaryStage;
		Canvas canvas = new Canvas(1000, 600);
		createPanel(primaryStage, canvas);

		Player myPlayer = PlayerConfigFactory.createMyPlayer(parameter);

		buildGame(canvas, myPlayer);
		playerJoins(myPlayer);
		startRendering();
		inputDispatcher = new PcInputDispatcher();
		inputDispatcher.addInputService(new ConfigurableKeyboardInputService(KeyCode.A.getName(), KeyCode.D.getName(), KeyCode.W.getName(), new PlayerMovement(
				myPlayer)));
		List<PlayerConfig> players = PlayerConfigFactory.createOtherPlayers(parameter.getConfiguration());
		for (PlayerConfig config : players) {
			Player otherPlayer = config.getPlayer();
			if (config.getConfiguration().getOpponent().getType() == OpponentType.LOCAL_PLAYER) {
				// TODO works only for one additional player
				inputDispatcher.addInputService(new ConfigurableKeyboardInputService(KeyCode.LEFT.getName(), KeyCode.RIGHT.getName(), KeyCode.UP.getName(),
						new PlayerMovement(otherPlayer)));
			}
			playerJoins(otherPlayer);
		}
	}

	private void playerJoins(Player myPlayer) {
		gameMain.newPlayerJoined(myPlayer);
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
				inputDispatcher.dispatchOnKeyUp(event.getCode());
			}
		});
		scene.setOnKeyReleased(new EventHandler<KeyEvent>() {

			@Override
			public void handle(KeyEvent event) {
				inputDispatcher.dispatchOnKeyDown(event.getCode());
			}
		});
		primaryStage.setOnCloseRequest(event -> Platform.exit());
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

	private void buildGame(Canvas canvas, Player myPlayer) {
		World world = createWorld();
		WorldProperties worldProperties = new WorldProperties(ModelConstants.STANDARD_WORLD_SIZE, ModelConstants.STANDARD_WORLD_SIZE);
		CoordinatesCalculation coordinatesCalculation = new YCoordinateInverterCalculation(new AbsoluteCoordinatesCalculation((int) canvas.getWidth(),
				(int) canvas.getHeight(), worldProperties));
		changeSizeInCoordinatesCalculationWhenScreenChanges(canvas, coordinatesCalculation);
		GameThreadState gameThreadState = new GameThreadState();
		initDrawer(canvas, world, coordinatesCalculation, gameThreadState);

		CameraPositionCalculation cameraCalculation = new CameraPositionCalculation(myPlayer);
		gameMain = new GameMainFactory().create(cameraCalculation, world, parameter, myPlayer);
		gameMain.addAllJoinListeners();
		gameMain.addJoinListener(drawerThread);
	}

	private void changeSizeInCoordinatesCalculationWhenScreenChanges(Canvas canvas, CoordinatesCalculation coordinatesCalculation) {
		canvas.widthProperty().addListener(
				(observable, oldValue, newValue) -> coordinatesCalculation.updateCanvas(newValue.intValue(), (int) canvas.getHeight()));
		canvas.heightProperty().addListener(
				(observable, oldValue, newValue) -> coordinatesCalculation.updateCanvas((int) canvas.getWidth(), newValue.intValue()));
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

	private void initDrawer(Canvas canvas, final World world, CoordinatesCalculation coordinatesCalculation, GameThreadState gameThreadState) {
		DefaultDrawablesFactory factory = new DefaultDrawablesFactory(gameThreadState, world, new PcBackgroundDrawableFactory(),
				new PcGameObjectDrawableFactory(), new PcPlayerDrawableFactory());
		ObjectsDrawer objectsDrawer = new ObjectsDrawer(factory, new CanvasCoordinateTranslator(new PcCanvasDelegate(), coordinatesCalculation));
		Drawer drawer = new PcDrawer(objectsDrawer, canvas);
		drawerThread = new DrawerFpsCounter(drawer, gameThreadState);
	}

	private static World createWorld() {
		XmlReader reader = new ClasspathXmlreader(World.class.getResourceAsStream("/worlds/classic.xml"));
		return new PcWorldObjectsParser().build(new NoopResourceProvider(), reader);
	}

	private static void startApplication() {
		launch();
	}

	private void showTechnicalError() {
		final Stage dialog = new Stage();
		dialog.initModality(Modality.APPLICATION_MODAL);
		dialog.initOwner(primaryStage);
		VBox dialogVbox = new VBox(20);
		javafx.scene.control.Button button = new javafx.scene.control.Button("Ok");
		button.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				Platform.exit();
			}
		});
		dialogVbox.getChildren().add(new Text("Ein technischer Fehler ist aufgetreten."));
		dialogVbox.getChildren().add(button);
		Scene dialogScene = new Scene(dialogVbox, 300, 200);
		dialog.setScene(dialogScene);
		dialog.show();
	}

}
