package de.oetting.bumpingbunnies.pc.configMenu;

import java.net.URL;
import java.util.ResourceBundle;

import de.oetting.bumpingbunnies.logger.Logger;
import de.oetting.bumpingbunnies.logger.LoggerFactory;
import de.oetting.bumpingbunnies.model.configuration.SpeedMode;
import de.oetting.bumpingbunnies.pc.ApplicationStarter;
import de.oetting.bumpingbunnies.pc.configuration.ConfigAccess;
import de.oetting.bumpingbunnies.pc.error.ErrorHandler;
import de.oetting.bumpingbunnies.pc.mainMenu.MainMenuApplication;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

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
	CheckBox musicCheckbox;
	@FXML
	CheckBox soundCheckbox;
	@FXML
	RadioButton speedSlow;
	@FXML
	RadioButton speedNormal;
	@FXML
	RadioButton speedFast;
	@FXML
	TextField victoryCondition;

	private final Stage primaryStage;

	public ConfigController(Stage primaryStage) {
		this.primaryStage = primaryStage;
	}

	@FXML
	public void onButtonSave() {
		try {
			PcConfiguration configuration = createConfiguration();
			saveConfiguration(configuration);
			startMenuApplication();
		} catch (Exception e) {
			handleError(e);
		}
	}

	private void handleError(Exception e) {
		LOGGER.error("Error", e);
		new ErrorHandler().showError(primaryStage, "Technical error");
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
		configuration.setSpeed(getSpeedModel().getSpeed());
		configuration.setPlayMusic(musicCheckbox.isSelected());
		configuration.setPlaySound(soundCheckbox.isSelected());
		try {
			configuration.setVictoryLimit(Integer.parseInt(victoryCondition.getText()));
		} catch (Exception e) {
			LOGGER.warn("Could not read victory setting. Value was %s", victoryCondition.getText());
		}
	}

	private SpeedMode getSpeedModel() {
		if (speedSlow.isSelected())
			return SpeedMode.SLOW;
		else if (speedNormal.isSelected())
			return SpeedMode.MEDIUM;
		else if (speedFast.isSelected())
			return SpeedMode.FAST;
		throw new IllegalStateException("One speedmode has to be selected");
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		speedSlow.setSelected(true);// default value, if the current speed
									// setting does not match any buttons speed
									// value
		PcConfiguration loadedConfiguration = new ConfigAccess().load();
		fillFields(loadedConfiguration);
		selectWholeTextOnSelection();
		changeKeyOnSelection();
		addSpeedButtonsToToggleGroup();
	}

	private void addSpeedButtonsToToggleGroup() {
		ToggleGroup group = new ToggleGroup();
		speedSlow.setToggleGroup(group);
		speedNormal.setToggleGroup(group);
		speedFast.setToggleGroup(group);
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
		button.selectedProperty().addListener((event) -> changeKey(button));
	}

	private void changeKey(ToggleButton field) {
		if (field.isFocused() && field.isSelected())
			changeTextOnKeyPress(field);
		else
			removeFocusFromButton(field);
	}

	private void removeFocusFromButton(ToggleButton field) {
		field.selectedProperty().set(false);
		field.setOnKeyPressed(null);
	}

	private void changeTextOnKeyPress(ToggleButton field) {
		field.setOnKeyPressed((event) -> onKeyTyped(event, field));
	}

	private void onKeyTyped(KeyEvent event, ToggleButton field) {
		if (!event.getCode().equals(KeyCode.ESCAPE) && !event.getCode().equals(KeyCode.TAB)) {
			field.setText(event.getCode().getName());
		}
		field.selectedProperty().set(false);
		event.consume();
	}

	private void selectWholeTextOnSelection() {
		selectWholeTextOnSelection(player1Name);
		selectWholeTextOnSelection(player2Name);
		selectWholeTextOnSelection(player3Name);
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
		selectSpeedRadioButton(configuration);
		musicCheckbox.setSelected(configuration.isPlayMusic());
		soundCheckbox.setSelected(configuration.isPlaySound());
		victoryCondition.setText(Integer.toString(configuration.getVictoryLimit()));
	}

	private void selectSpeedRadioButton(PcConfiguration configuration) {
		if (configuration.getSpeed() == SpeedMode.SLOW.getSpeed())
			speedSlow.setSelected(true);
		else if (configuration.getSpeed() == SpeedMode.MEDIUM.getSpeed())
			speedNormal.setSelected(true);
		else if (configuration.getSpeed() == SpeedMode.FAST.getSpeed())
			speedFast.setSelected(true);
	}

	private void selectWholeText(TextField tf) {
		Platform.runLater(() -> {
			if (tf.isFocused() && !tf.getText().isEmpty()) {
				tf.selectAll();
			}
		});
	}

}
