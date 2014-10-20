package de.oetting.bumpingbunnies.pc.configMenu;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class ConfigApplication extends Application {

	@Override
	public void start(Stage primaryStage) throws Exception {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/configMenu.fxml"));
		loader.setController(new ConfigController(primaryStage));
		Pane myPane = (Pane) loader.load();
		Scene myScene = new Scene(myPane);
		primaryStage.setScene(myScene);
		primaryStage.show();
		primaryStage.setOnCloseRequest((e) -> Platform.exit());
		primaryStage.setTitle("Bumping Bunnies: Configuration");
	}

	public static void main(String[] args) {
		launch();
	}
}
