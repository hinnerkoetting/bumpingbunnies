package de.oetting.bumpingbunnies.pc.main;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import de.oetting.bumpingbunnies.core.game.graphics.calculation.CoordinatesCalculation;
import de.oetting.bumpingbunnies.core.world.World;
import de.oetting.bumpingbunnies.logger.Logger;
import de.oetting.bumpingbunnies.logger.LoggerFactory;
import de.oetting.bumpingbunnies.usecases.game.model.Player;
import de.oetting.bumpingbunnies.usecases.game.model.Wall;

public class BunniesApplication extends Application implements Runnable {

	private static Logger LOGGER = LoggerFactory.getLogger(BunniesApplication.class);
	private static World world;
	private static CoordinatesCalculation coordinatesCalculation;

	@Override
	public void start(Stage primaryStage) throws Exception {
		Group root = new Group();
		Scene scene = new Scene(root, 800, 600, Color.BLACK);
		primaryStage.setScene(scene);

		Group circles = new Group();
		for (Player player : world.getAllPlayer()) {
			Rectangle rectangle = new Rectangle((int) player.minX(), (int) player.minY(), (int) (player.maxX() - player.minX()),
					(int) (player.maxY() - player.minY()));
			circles.getChildren().add(rectangle);
		}
		for (Wall wall : world.getAllWalls()) {
			int screenCoordinateX = coordinatesCalculation.getScreenCoordinateX(wall.minX());
			int screenCoordinateY = coordinatesCalculation.getScreenCoordinateY(wall.minY());
			int width = coordinatesCalculation.getScreenCoordinateX(wall.maxX() - wall.minX());
			int height = coordinatesCalculation.getScreenCoordinateY(-wall.minY() + wall.maxY());
			LOGGER.info("%d, %d, %d, %d", screenCoordinateX, screenCoordinateY, width, height);
			Rectangle rectangle = new Rectangle(screenCoordinateX, screenCoordinateY, width, height);
			rectangle.setStrokeWidth(4);
			rectangle.setFill(Color.AQUA);
			circles.getChildren().add(rectangle);
		}

		root.getChildren().add(circles);
		primaryStage.show();
	}

	@Override
	public void run() {
		launch();
	}

	public static void setWorld(World newWorld) {
		world = newWorld;
	}

	public static void setCoordinatesCalculation(CoordinatesCalculation coordinatesCalculation) {
		BunniesApplication.coordinatesCalculation = coordinatesCalculation;
	}

}
