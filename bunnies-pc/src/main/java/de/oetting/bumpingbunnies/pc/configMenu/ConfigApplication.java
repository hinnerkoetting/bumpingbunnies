package de.oetting.bumpingbunnies.pc.configMenu;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import de.oetting.bumpingbunnies.pc.ApplicationStarter;
import de.oetting.bumpingbunnies.pc.CssLoader;
import de.oetting.bumpingbunnies.pc.mainMenu.MainMenuApplication;

public class ConfigApplication extends Application {

	private Stage primaryStage;

	@Override
	public void start(Stage primaryStage) throws Exception {
		this.primaryStage = primaryStage;
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/configMenu.fxml"));
		loader.setController(new ConfigController(primaryStage));
		Pane myPane = (Pane) loader.load();
		Scene myScene = new Scene(myPane);
		myScene.getStylesheets().addAll(new CssLoader().loadCssForConfiguration());
		primaryStage.setScene(myScene);
		primaryStage.show();
		primaryStage.setOnCloseRequest((e) -> Platform.exit());
		primaryStage.setTitle("Configuration");
		primaryStage.setResizable(false);
		myScene.addEventHandler(KeyEvent.KEY_PRESSED, (event) -> onKeyPressed(event));
	}

	private void onKeyPressed(KeyEvent event) {
		if (event.getCode().equals(KeyCode.ESCAPE)) {
			new ApplicationStarter().startApplication(new MainMenuApplication(), primaryStage);
		}
	}

	public static void main(String[] args) {
		launch();
	}
}
