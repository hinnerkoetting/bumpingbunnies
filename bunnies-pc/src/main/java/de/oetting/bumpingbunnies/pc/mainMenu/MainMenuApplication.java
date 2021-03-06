package de.oetting.bumpingbunnies.pc.mainMenu;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import de.oetting.bumpingbunnies.pc.CssLoader;

public class MainMenuApplication extends Application {

	@Override
	public void start(Stage primaryStage) throws Exception {
		primaryStage.setTitle("Bumping bunnies");

		FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/mainMenu.fxml"));
		MainMenuController controller = new MainMenuController(primaryStage);
		loader.setController(controller);
		Pane myPane = (Pane) loader.load();
		Scene myScene = new Scene(myPane);
		myScene.getStylesheets().addAll(new CssLoader().loadCssForMainMenu());
		primaryStage.setScene(myScene);
		primaryStage.show();
		primaryStage.setOnCloseRequest((e) -> Platform.exit());
		primaryStage.setTitle("Bumping Bunnies");
		primaryStage.setResizable(false);
		myScene.addEventHandler(KeyEvent.KEY_PRESSED, (event) -> onKeyPressed(event));
	}

	private void onKeyPressed(KeyEvent event) {
		if (event.getCode().equals(KeyCode.ESCAPE)) {
			Platform.exit();
		}
	}

	public static void main(String[] args) {
		launch();
	}
}
