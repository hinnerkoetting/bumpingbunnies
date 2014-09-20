package de.oetting.bumpingbunnies.pc.main;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.StrokeType;
import javafx.stage.Stage;
import javafx.util.Duration;

public class BunniesApplication extends Application implements Runnable {

	@Override
	public void start(Stage primaryStage) throws Exception {
		Group root = new Group();
		Scene scene = new Scene(root, 800, 600, Color.BLACK);
		primaryStage.setScene(scene);

		Timeline timeline = new Timeline();
		Group circles = new Group();
		for (int i = 0; i < 30; i++) {
			Circle circle = new Circle(150, Color.web("white", 0.05));
			circle.setStrokeType(StrokeType.OUTSIDE);
			circle.setStroke(Color.web("white", 0.16));
			circle.setStrokeWidth(4);
			circles.getChildren().add(circle);
		}
		root.getChildren().add(circles);
		for (Node circle : circles.getChildren()) {
			timeline.getKeyFrames().addAll(new KeyFrame(Duration.ZERO, // set
																		// start
																		// position
																		// at 0
					new KeyValue(circle.translateXProperty(), Math.random() * 800), new KeyValue(circle.translateYProperty(), Math.random() * 600)),
					new KeyFrame(new Duration(40000), // set end position at 40s
							new KeyValue(circle.translateXProperty(), Math.random() * 800), new KeyValue(circle.translateYProperty(), Math.random() * 600)));
		}
		// play 40s of animation
		timeline.play();

		primaryStage.show();
	}

	@Override
	public void run() {
		launch();
	}

}
