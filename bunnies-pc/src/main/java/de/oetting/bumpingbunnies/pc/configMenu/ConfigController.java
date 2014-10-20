package de.oetting.bumpingbunnies.pc.configMenu;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import de.oetting.bumpingbunnies.logger.Logger;
import de.oetting.bumpingbunnies.logger.LoggerFactory;
import de.oetting.bumpingbunnies.pc.configuration.ConfigAccess;
import de.oetting.bumpingbunnies.pc.mainMenu.MainMenuApplication;

public class ConfigController implements Initializable {

	protected static final Logger LOGGER = LoggerFactory.getLogger(ConfigController.class);

	@FXML
	TextField player1Name;
	@FXML
	TextField player1Left;
	@FXML
	TextField player1Up;
	@FXML
	TextField player1Right;

	@FXML
	TextField player2Name;
	@FXML
	TextField player2Left;
	@FXML
	TextField player2Up;
	@FXML
	TextField player2Right;

	@FXML
	TextField player3Name;
	@FXML
	TextField player3Left;
	@FXML
	TextField player3Up;
	@FXML
	TextField player3Right;
	@FXML
	TextField speed;

	private final Stage primaryStage;

	public ConfigController(Stage primaryStage) {
		this.primaryStage = primaryStage;
	}

	@FXML
	public void onButtonSave() {
		PcConfiguration configuration = createConfiguration();
		saveConfiguration(configuration);
		startMenuApplication();
	}

	@FXML
	public void onButtonCancel() {
		startMenuApplication();
	}

	private void startMenuApplication() {
		Platform.runLater(new Runnable() {

			@Override
			public void run() {
				try {
					MainMenuApplication menuApplication = new MainMenuApplication();
					menuApplication.start(primaryStage);
				} catch (Exception e) {
					LOGGER.error("", e);
					Platform.exit();
				}
			}
		});
	}

	private void saveConfiguration(PcConfiguration configuration) {
		new ConfigAccess().saveConfiguration(configuration);
	}

	private PcConfiguration createConfiguration() {
		PcConfiguration configuration = new PcConfiguration();
		readPlayer1(configuration);
		readPlayer2(configuration);
		readPlayer3(configuration);
		readSpeed(configuration);
		return configuration;
	}

	private void readPlayer1(PcConfiguration configuration) {
		configuration.getPlayer1Configuration().setPlayerName(player1Name.getText());
		configuration.getPlayer1Configuration().setPlayerUp(player1Up.getText());
		configuration.getPlayer1Configuration().setPlayerLeft(player1Left.getText());
		configuration.getPlayer1Configuration().setPlayerRight(player1Right.getText());
	}

	private void readPlayer2(PcConfiguration configuration) {
		configuration.getPlayer2Configuration().setPlayerName(player2Name.getText());
		configuration.getPlayer2Configuration().setPlayerUp(player2Up.getText());
		configuration.getPlayer2Configuration().setPlayerLeft(player2Left.getText());
		configuration.getPlayer2Configuration().setPlayerRight(player2Right.getText());
	}

	private void readPlayer3(PcConfiguration configuration) {
		configuration.getPlayer3Configuration().setPlayerName(player3Name.getText());
		configuration.getPlayer3Configuration().setPlayerUp(player3Up.getText());
		configuration.getPlayer3Configuration().setPlayerLeft(player3Left.getText());
		configuration.getPlayer3Configuration().setPlayerRight(player3Right.getText());
	}

	private void readSpeed(PcConfiguration configuration) {
		configuration.setSpeed(Integer.parseInt(speed.getText()));
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		PcConfiguration loadedConfiguration = new ConfigAccess().load();
		fillFields(loadedConfiguration);
	}

	private void fillFields(PcConfiguration configuration) {
		fillPlayer1(configuration);
		fillPlayer2(configuration);
		fillPlayer3(configuration);
		fillSpeed(configuration);
	}

	private void fillPlayer1(PcConfiguration configuration) {
		player1Name.setText(configuration.getPlayer1Configuration().getPlayerName());
		player1Up.setText(configuration.getPlayer1Configuration().getPlayerUp());
		player1Left.setText(configuration.getPlayer1Configuration().getPlayerLeft());
		player1Right.setText(configuration.getPlayer1Configuration().getPlayerRight());
	}

	private void fillPlayer2(PcConfiguration configuration) {
		player2Name.setText(configuration.getPlayer2Configuration().getPlayerName());
		player2Up.setText(configuration.getPlayer2Configuration().getPlayerUp());
		player2Left.setText(configuration.getPlayer2Configuration().getPlayerLeft());
		player2Right.setText(configuration.getPlayer2Configuration().getPlayerRight());
	}

	private void fillPlayer3(PcConfiguration configuration) {
		player3Name.setText(configuration.getPlayer3Configuration().getPlayerName());
		player3Up.setText(configuration.getPlayer3Configuration().getPlayerUp());
		player3Left.setText(configuration.getPlayer3Configuration().getPlayerLeft());
		player3Right.setText(configuration.getPlayer3Configuration().getPlayerRight());
	}

	private void fillSpeed(PcConfiguration configuration) {
		speed.setText(Integer.toString(configuration.getSpeed()));
	}

}
