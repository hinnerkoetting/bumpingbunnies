package de.oetting.bumpingbunnies.pc.mainMenu;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.stage.Stage;
import de.oetting.bumpingbunnies.logger.Logger;
import de.oetting.bumpingbunnies.logger.LoggerFactory;
import de.oetting.bumpingbunnies.pc.main.BunniesMain;

public class MainMenuController {

	private static final Logger LOGGER = LoggerFactory.getLogger(MainMenuController.class);

	private final Stage primaryStage;
	@FXML
	private javafx.scene.control.Button withTwoPlayersButton;
	@FXML
	private javafx.scene.control.Button withAiButton;

	public MainMenuController(Stage primaryStage) {
		this.primaryStage = primaryStage;
	}

	@FXML
	public void onButtonWithTwoPlayers(ActionEvent event) {
		startGame(true);
	}

	@FXML
	public void onButtonWithAi(ActionEvent event) {
		startGame(false);
	}

	private void startGame(boolean withTwoHumanPlayers) {
		try {
			BunniesMain bunniesMain = new BunniesMain();
			bunniesMain.setWithTwoHumanPlayers(withTwoHumanPlayers);
			bunniesMain.start(primaryStage);
		} catch (Exception e) {
			LOGGER.error("", e);
			Platform.exit();
		}
	}

}