package de.oetting.bumpingbunnies.bunnieseditor.javafx;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class EditorApplication extends Application {

	public static void main(String[] args) {
		launch();
	}
		
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		primaryStage.setTitle("Bunny-Editor");

		FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/editor.fxml"));
		Parent pane = loader.load();
		Scene myScene = new Scene(pane);
		primaryStage.setScene(myScene);
		primaryStage.show();
		primaryStage.setOnCloseRequest((e) -> Platform.exit());
	}

}
