package de.oetting.bumpingbunnies.pc.scoreMenu;

import java.util.Collections;
import java.util.List;

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

public class ScoreMenuApplication extends Application {

	private final List<ScoreEntry> entries;
	private Stage primaryStage;

	public ScoreMenuApplication(List<ScoreEntry> entries) {
		this.entries = entries;
		Collections.sort(entries, new ScoreEntryComparator());
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		this.primaryStage = primaryStage;
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/ScoreMenu.fxml"));
		Pane pane = loader.load();
		fillEntries(loader.getController());
		Scene scene = new Scene(pane);
		scene.getStylesheets().addAll(new CssLoader().loadScoreCss());
		primaryStage.setScene(scene);
		primaryStage.show();
		primaryStage.setOnCloseRequest((e) -> Platform.exit());
		primaryStage.setTitle("Bumping Bunnies: Score");
		primaryStage.setResizable(false);
		scene.addEventFilter(KeyEvent.KEY_PRESSED, (event) -> onKeyPressed(event));
	}

	private void onKeyPressed(KeyEvent event) {
		if (event.getCode().equals(KeyCode.ESCAPE))
			toToMainScreen();
	}

	private void toToMainScreen() {
		new ApplicationStarter().startApplication(new MainMenuApplication(), primaryStage);
	}

	private void fillEntries(ScoreMenuController controller) {
		for (ScoreEntry entry : entries)
			controller.addScore(entry);
	}

}
