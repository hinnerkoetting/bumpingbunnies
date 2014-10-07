package de.oetting.bumpingbunnies.tester;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class TesterApplication extends Application {

	@Override
	public void start(Stage primaryStage) throws Exception {

		Pane panel = loadFxml();
		Scene scene = new Scene(panel);
		primaryStage.setScene(scene);
		primaryStage.show();
	}

	private Pane loadFxml() throws IOException {
		return FXMLLoader.load(getClass().getResource("/fxml/test.fxml"));
	}

	public static void main(String args[]) {
		launch();
	}
}
