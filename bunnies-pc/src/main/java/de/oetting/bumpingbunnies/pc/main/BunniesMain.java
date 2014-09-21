package de.oetting.bumpingbunnies.pc.main;

import java.util.ArrayList;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import de.oetting.bumpingbunnies.core.game.CameraPositionCalculation;
import de.oetting.bumpingbunnies.core.game.graphics.ObjectsDrawer;
import de.oetting.bumpingbunnies.core.game.graphics.calculation.AbsoluteCoordinatesCalculation;
import de.oetting.bumpingbunnies.core.game.graphics.calculation.CoordinatesCalculation;
import de.oetting.bumpingbunnies.core.game.main.GameThread;
import de.oetting.bumpingbunnies.core.game.main.GameThreadState;
import de.oetting.bumpingbunnies.core.game.player.PlayerFactory;
import de.oetting.bumpingbunnies.core.graphics.Drawer;
import de.oetting.bumpingbunnies.core.networking.messaging.stop.NoopGameStopper;
import de.oetting.bumpingbunnies.core.world.World;
import de.oetting.bumpingbunnies.core.worldCreation.parser.ClasspathXmlreader;
import de.oetting.bumpingbunnies.core.worldCreation.parser.XmlReader;
import de.oetting.bumpingbunnies.logger.Logger;
import de.oetting.bumpingbunnies.logger.LoggerFactory;
import de.oetting.bumpingbunnies.pc.game.factory.GameThreadFactory;
import de.oetting.bumpingbunnies.pc.graphics.PcCanvasDelegate;
import de.oetting.bumpingbunnies.pc.graphics.PcDrawablesFactory;
import de.oetting.bumpingbunnies.pc.graphics.PcDrawer;
import de.oetting.bumpingbunnies.pc.graphics.YCoordinateInverterCalculation;
import de.oetting.bumpingbunnies.pc.worldcreation.parser.NoopResourceProvider;
import de.oetting.bumpingbunnies.pc.worldcreation.parser.PcWorldObjectsParser;
import de.oetting.bumpingbunnies.usecases.game.configuration.Configuration;
import de.oetting.bumpingbunnies.usecases.game.configuration.GeneralSettings;
import de.oetting.bumpingbunnies.usecases.game.configuration.InputConfiguration;
import de.oetting.bumpingbunnies.usecases.game.configuration.LocalPlayerSettings;
import de.oetting.bumpingbunnies.usecases.game.configuration.LocalSettings;
import de.oetting.bumpingbunnies.usecases.game.configuration.NetworkType;
import de.oetting.bumpingbunnies.usecases.game.configuration.WorldConfiguration;
import de.oetting.bumpingbunnies.usecases.game.model.ModelConstants;
import de.oetting.bumpingbunnies.usecases.game.model.Opponent;
import de.oetting.bumpingbunnies.usecases.game.model.OpponentType;
import de.oetting.bumpingbunnies.usecases.game.model.Player;
import de.oetting.bumpingbunnies.world.WorldProperties;

public class BunniesMain extends Application {

	private static Logger LOGGER = LoggerFactory.getLogger(BunniesMain.class);

	private Drawer drawer;

	public static void main(String[] args) {

		startApplication();
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		Canvas canvas = new Canvas(800, 600);
		final World world = createWorld();
		WorldProperties worldProperties = new WorldProperties(ModelConstants.STANDARD_WORLD_SIZE, ModelConstants.STANDARD_WORLD_SIZE);
		CoordinatesCalculation coordinatesCalculation = new YCoordinateInverterCalculation(new AbsoluteCoordinatesCalculation(800, 600, worldProperties));
		coordinatesCalculation.updateCanvas(800, 600);
		GameThreadState gameThreadState = new GameThreadState();
		initDrawer(canvas, world, coordinatesCalculation, gameThreadState);

		GameThread gamethread = createGameThread(world, coordinatesCalculation, gameThreadState);
		gamethread.start();

		Group root = new Group();
		Scene scene = new Scene(root, 800, 600, Color.BLACK);
		primaryStage.setScene(scene);

		root.getChildren().add(canvas);
		primaryStage.show();
		new AnimationTimer() {

			@Override
			public void handle(long now) {
				drawer.draw();
			}
		}.start();

		primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {

			@Override
			public void handle(WindowEvent event) {
				Platform.exit();
			}
		});
	}

	private void initDrawer(Canvas canvas, final World world, CoordinatesCalculation coordinatesCalculation, GameThreadState gameThreadState) {
		ObjectsDrawer objectsDrawer = new ObjectsDrawer(new PcDrawablesFactory(world, gameThreadState), new PcCanvasDelegate(coordinatesCalculation));
		drawer = new PcDrawer(objectsDrawer, canvas);
		objectsDrawer.buildAllDrawables();
	}

	private static World createWorld() {
		XmlReader reader = new ClasspathXmlreader(World.class.getResourceAsStream("/worlds/classic.xml"));
		return new PcWorldObjectsParser().build(new NoopResourceProvider(), reader);
	}

	private static GameThread createGameThread(World world, CoordinatesCalculation coordinatesCalculation, GameThreadState gameThreadState) {

		Configuration configuration = createConfiguration();
		Player myPlayer = new PlayerFactory(1).createPlayer(1, "local", new Opponent("", OpponentType.MY_PLAYER));
		CameraPositionCalculation cameraCalculation = new CameraPositionCalculation(myPlayer);
		return new GameThreadFactory()
				.create(coordinatesCalculation, world, new NoopGameStopper(), configuration, myPlayer, cameraCalculation, gameThreadState);

	}

	private static Configuration createConfiguration() {
		LocalSettings localSettings = new LocalSettings(InputConfiguration.DISTRIBUTED_KEYBOARD, 1, true, false);
		GeneralSettings generalSettings = new GeneralSettings(WorldConfiguration.CASTLE, 1, NetworkType.WLAN);
		LocalPlayerSettings localPlayerSettings = new LocalPlayerSettings("local");
		Configuration configuration = new Configuration(localSettings, generalSettings, new ArrayList<>(), localPlayerSettings, true);
		return configuration;
	}

	private static void startApplication() {
		launch();
	}

}
