package de.oetting.bumpingbunnies.pc.graphics.imageTest;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class FxGraphicsTester extends Application {

	private ImageView imageView;

	@Override
	public void start(Stage stage) throws Exception {
		Pane root = FXMLLoader.load(getClass().getResource("/fxml/ImageTester.fxml"));
		// FlowPane root = createPane();
		Scene scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
	}

	private FlowPane createPane() {
		FlowPane pane = new FlowPane();
		Image image = new Image(getClass().getResourceAsStream("/drawable/v1d_down_1.png"));
		imageView = new ImageView(image);
		pane.getChildren().add(imageView);
		return pane;
	}

	public static void main(String args[]) {
		launch();
	}

}
