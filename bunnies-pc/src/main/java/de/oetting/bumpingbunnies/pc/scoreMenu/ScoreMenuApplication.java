package de.oetting.bumpingbunnies.pc.scoreMenu;

import java.util.List;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class ScoreMenuApplication extends Application {

	private final List<ScoreEntry> entries;

	public ScoreMenuApplication(List<ScoreEntry> entries) {
		this.entries = entries;
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/ScoreMenu.fxml"));
		Pane pane = loader.load();
		fillEntries(loader.getController());
		Scene scene = new Scene(pane);
		primaryStage.setScene(scene);
		primaryStage.show();
		primaryStage.setOnCloseRequest((e) -> Platform.exit());
		primaryStage.setTitle("Bumping Bunnies: Score");
	}

	private void fillEntries(ScoreMenuController controller) {
		for (ScoreEntry entry : entries)
			controller.addScore(entry);
	}

}
