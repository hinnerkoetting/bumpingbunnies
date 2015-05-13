package de.oetting.bumpingbunnies.pc.error;

import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class ErrorHandler {

	public void showError(Stage primaryStage, String text) {
		final Stage dialog = new Stage();
		dialog.initModality(Modality.APPLICATION_MODAL);
		dialog.initOwner(primaryStage);
		VBox dialogVbox = new VBox(20);
		javafx.scene.control.Button button = new javafx.scene.control.Button("Quit");
		button.setOnAction((event) -> Platform.exit());

		dialogVbox.getChildren().add(new Text(text));
		dialogVbox.getChildren().add(button);
		Scene dialogScene = new Scene(dialogVbox, primaryStage.getWidth(), primaryStage.getHeight());
		dialog.setResizable(false);
		dialog.setScene(dialogScene);
		dialog.show();
		dialog.setX(primaryStage.getX());
		dialog.setY(primaryStage.getY());
	}
}
