package de.oetting.bumpingbunnies.pc.configMenu;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import de.oetting.bumpingbunnies.logger.Logger;
import de.oetting.bumpingbunnies.logger.LoggerFactory;
import de.oetting.bumpingbunnies.pc.ApplicationStarter;
import de.oetting.bumpingbunnies.pc.configuration.ConfigAccess;
import de.oetting.bumpingbunnies.pc.mainMenu.MainMenuApplication;

public class ConfigController implements Initializable {

	protected static final Logger LOGGER = LoggerFactory.getLogger(ConfigController.class);

	@FXML
	TextField player1Name;
	@FXML
	ToggleButton player1Left;
	@FXML
	ToggleButton player1Up;
	@FXML
	ToggleButton player1Right;

	@FXML
	TextField player2Name;
	@FXML
	ToggleButton player2Left;
	@FXML
	ToggleButton player2Up;
	@FXML
	ToggleButton player2Right;

	@FXML
	TextField player3Name;
	@FXML
	ToggleButton player3Left;
	@FXML
	ToggleButton player3Up;
	@FXML
	ToggleButton player3Right;

	@FXML
	TextField speed;
	@FXML
	CheckBox musicCheckbox;
	@FXML
	CheckBox soundCheckbox;

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
		new ApplicationStarter().startApplication(new MainMenuApplication(), primaryStage);
	}

	private void saveConfiguration(PcConfiguration configuration) {
		new ConfigAccess().saveConfiguration(configuration);
	}

	private PcConfiguration createConfiguration() {
		PcConfiguration configuration = new PcConfiguration();
		readPlayer1(configuration);
		readPlayer2(configuration);
		readPlayer3(configuration);
		readSettings(configuration);
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

	private void readSettings(PcConfiguration configuration) {
		configuration.setSpeed(Integer.parseInt(speed.getText()));
		configuration.setPlayMusic(musicCheckbox.isSelected());
		configuration.setPlaySound(soundCheckbox.isSelected());
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		PcConfiguration loadedConfiguration = new ConfigAccess().load();
		fillFields(loadedConfiguration);
		selectWholeTextOnSelection();
		changeKeyOnSelection();
	}

	private void changeKeyOnSelection() {
		registerSetOnKeyPress(player1Left);
		registerSetOnKeyPress(player1Right);
		registerSetOnKeyPress(player1Up);
		registerSetOnKeyPress(player2Left);
		registerSetOnKeyPress(player2Right);
		registerSetOnKeyPress(player2Up);
		registerSetOnKeyPress(player3Left);
		registerSetOnKeyPress(player3Right);
		registerSetOnKeyPress(player3Up);
	}

	private void registerSetOnKeyPress(ToggleButton button) {
		button.focusedProperty().addListener((event) -> changeKey(button));
	}

	private void changeKey(ToggleButton field) {
		if (field.isFocused())
			changeTextOnKeyPress(field);
		else
			removeFocusFromButton(field);
	}

	private void removeFocusFromButton(ToggleButton field) {
		field.selectedProperty().set(false);
		field.setOnKeyPressed(null);
	}

	private void changeTextOnKeyPress(ToggleButton field) {
		field.setOnKeyPressed((event) -> (onKeyTyped(event, field)));
	}

	private void onKeyTyped(KeyEvent event, ToggleButton field) {
		if (!event.getCode().equals(KeyCode.ESCAPE)) {
			field.setText(event.getCode().getName());
		}
		field.selectedProperty().set(false);
	}

	private void selectWholeTextOnSelection() {
		selectWholeTextOnSelection(player1Name);
		selectWholeTextOnSelection(player2Name);
		selectWholeTextOnSelection(player3Name);
		selectWholeTextOnSelection(speed);
	}

	private void selectWholeTextOnSelection(TextField textfield) {
		textfield.focusedProperty().addListener((event) -> selectWholeText(textfield));
	}

	private void fillFields(PcConfiguration configuration) {
		fillPlayer1(configuration);
		fillPlayer2(configuration);
		fillPlayer3(configuration);
		fillSettings(configuration);
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

	private void fillSettings(PcConfiguration configuration) {
		speed.setText(Integer.toString(configuration.getSpeed()));
		musicCheckbox.setSelected(configuration.isPlayMusic());
		soundCheckbox.setSelected(configuration.isPlaySound());
	}

	private void selectWholeText(TextField tf) {
		Platform.runLater(() -> {
			if (tf.isFocused() && !tf.getText().isEmpty()) {
				tf.selectAll();
			}
		});
	}

}
