package de.oetting.bumpingbunnies.pc.mainMenu;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class MainMenuApplication extends Application {

	@Override
	public void start(Stage primaryStage) throws Exception {
		primaryStage.setTitle("Bumping bunnies");

		FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/mainMenu.fxml"));
		MainMenuController controller = new MainMenuController(primaryStage);
		loader.setController(controller);
		Pane myPane = (Pane) loader.load();
		Scene myScene = new Scene(myPane);
		primaryStage.setScene(myScene);
		primaryStage.show();
	}

	public static void main(String[] args) {
		launch();
	}
}
