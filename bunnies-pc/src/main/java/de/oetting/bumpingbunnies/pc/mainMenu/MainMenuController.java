package de.oetting.bumpingbunnies.pc.mainMenu;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.stage.Stage;
import de.oetting.bumpingbunnies.core.network.RoomEntry;
import de.oetting.bumpingbunnies.logger.Logger;
import de.oetting.bumpingbunnies.logger.LoggerFactory;
import de.oetting.bumpingbunnies.pc.main.BunniesMain;

public class MainMenuController implements Initializable {

	private static final Logger LOGGER = LoggerFactory.getLogger(MainMenuController.class);

	private final Stage primaryStage;
	@FXML
	private Button withTwoPlayersButton;
	@FXML
	private Button withAiButton;
	@FXML
	private Button connectButton;
	@FXML
	private TableView<RoomEntry> playerTable;

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

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		playerTable.getSelectionModel().selectedIndexProperty().addListener(event -> connectButton.setDisable(false));
	}

}