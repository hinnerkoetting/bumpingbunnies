package de.oetting.bumpingbunnies.pc.graphics.imageTest;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class FxGraphicsTester extends Application {

	@Override
	public void start(Stage stage) throws Exception {
		Pane root = FXMLLoader.load(getClass().getResource("/fxml/ImageTester.fxml"));
		// FlowPane root = createPane();
		Scene scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
	}

	public static void main(String args[]) {
		launch();
	}

}
