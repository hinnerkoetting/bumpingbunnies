package de.oetting.bumpingbunnies.pc.main;

import java.util.ArrayList;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import de.oetting.bumpingbunnies.core.game.CameraPositionCalculation;
import de.oetting.bumpingbunnies.core.game.graphics.calculation.AbsoluteCoordinatesCalculation;
import de.oetting.bumpingbunnies.core.game.graphics.calculation.CoordinatesCalculation;
import de.oetting.bumpingbunnies.core.game.main.GameThread;
import de.oetting.bumpingbunnies.core.game.player.PlayerFactory;
import de.oetting.bumpingbunnies.core.graphics.Drawer;
import de.oetting.bumpingbunnies.core.networking.messaging.stop.NoopGameStopper;
import de.oetting.bumpingbunnies.core.world.World;
import de.oetting.bumpingbunnies.core.worldCreation.parser.ClasspathXmlreader;
import de.oetting.bumpingbunnies.core.worldCreation.parser.XmlReader;
import de.oetting.bumpingbunnies.logger.Logger;
import de.oetting.bumpingbunnies.logger.LoggerFactory;
import de.oetting.bumpingbunnies.pc.game.factory.GameThreadFactory;
import de.oetting.bumpingbunnies.pc.graphics.CanvasCoordinatesTranslation;
import de.oetting.bumpingbunnies.pc.graphics.PcDrawer;
import de.oetting.bumpingbunnies.pc.graphics.PcObjectsDrawer;
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

		initDrawer(canvas, world, coordinatesCalculation);

		GameThread gamethread = createGameThread(world, coordinatesCalculation);
		gamethread.start();

		Group root = new Group();
		Scene scene = new Scene(root, 800, 600, Color.BLACK);
		primaryStage.setScene(scene);

		Group circles = new Group();
		for (Player player : world.getAllPlayer()) {
			Rectangle rectangle = new Rectangle((int) player.minX(), (int) player.minY(), (int) (player.maxX() - player.minX()),
					(int) (player.maxY() - player.minY()));
			circles.getChildren().add(rectangle);
		}
		// for (Wall wall : world.getAllWalls()) {
		// int screenCoordinateX =
		// coordinatesCalculation.getScreenCoordinateX(wall.minX());
		// int screenCoordinateY =
		// coordinatesCalculation.getScreenCoordinateY(wall.minY());
		// int width = coordinatesCalculation.getScreenCoordinateX(wall.maxX() -
		// wall.minX());
		// int height = coordinatesCalculation.getScreenCoordinateY(-wall.minY()
		// + wall.maxY());
		// LOGGER.info("%d, %d, %d, %d", screenCoordinateX, screenCoordinateY,
		// width, height);
		// Rectangle rectangle = new Rectangle(screenCoordinateX,
		// screenCoordinateY, width, height);
		// rectangle.setStrokeWidth(4);
		// rectangle.setFill(Color.AQUA);
		// circles.getChildren().add(rectangle);
		// }

		// root.getChildren().add(circles);
		root.getChildren().add(canvas);
		primaryStage.show();
		new AnimationTimer() {

			@Override
			public void handle(long now) {
				drawer.draw();
			}
		}.start();
	}

	private void initDrawer(Canvas canvas, final World world, CoordinatesCalculation coordinatesCalculation) {
		drawer = new PcDrawer(
				new PcObjectsDrawer(world.getAllWalls(), new CanvasCoordinatesTranslation(coordinatesCalculation, canvas.getGraphicsContext2D())), canvas);
	}

	private static World createWorld() {
		XmlReader reader = new ClasspathXmlreader(World.class.getResourceAsStream("/worlds/classic.xml"));
		return new PcWorldObjectsParser().build(new NoopResourceProvider(), reader);
	}

	private static GameThread createGameThread(World world, CoordinatesCalculation coordinatesCalculation) {

		Configuration configuration = createConfiguration();
		Player myPlayer = new PlayerFactory(1).createPlayer(1, "local", new Opponent("", OpponentType.MY_PLAYER));
		CameraPositionCalculation cameraCalculation = new CameraPositionCalculation(myPlayer);
		return new GameThreadFactory().create(coordinatesCalculation, world, new NoopGameStopper(), configuration, myPlayer, cameraCalculation);

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
