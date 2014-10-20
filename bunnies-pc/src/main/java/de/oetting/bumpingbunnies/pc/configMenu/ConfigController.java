package de.oetting.bumpingbunnies.pc.configMenu;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import de.oetting.bumpingbunnies.pc.configuration.ConfigAccess;

public class ConfigController implements Initializable {

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

	@FXML
	public void onButtonSave() {
		PcConfiguration configuration = createConfiguration();
		saveConfiguration(configuration);
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
		configuration.setPlayer1Name(player1Name.getText());
		configuration.setPlayer1Up(player1Up.getText());
		configuration.setPlayer1Left(player1Left.getText());
		configuration.setPlayer1Right(player1Right.getText());
	}

	private void readPlayer2(PcConfiguration configuration) {
		configuration.setPlayer2Name(player2Name.getText());
		configuration.setPlayer2Up(player2Up.getText());
		configuration.setPlayer2Left(player2Left.getText());
		configuration.setPlayer2Right(player2Right.getText());
	}

	private void readPlayer3(PcConfiguration configuration) {
		configuration.setPlayer3Name(player3Name.getText());
		configuration.setPlayer3Up(player3Up.getText());
		configuration.setPlayer3Left(player3Left.getText());
		configuration.setPlayer3Right(player3Right.getText());
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
		player1Name.setText(configuration.getPlayer1Name());
		player1Up.setText(configuration.getPlayer1Up());
		player1Left.setText(configuration.getPlayer1Left());
		player1Right.setText(configuration.getPlayer1Right());
	}

	private void fillPlayer2(PcConfiguration configuration) {
		player2Name.setText(configuration.getPlayer2Name());
		player2Up.setText(configuration.getPlayer2Up());
		player2Left.setText(configuration.getPlayer2Left());
		player2Right.setText(configuration.getPlayer2Right());
	}

	private void fillPlayer3(PcConfiguration configuration) {
		player3Name.setText(configuration.getPlayer3Name());
		player3Up.setText(configuration.getPlayer3Up());
		player3Left.setText(configuration.getPlayer3Left());
		player3Right.setText(configuration.getPlayer3Right());
	}

	private void fillSpeed(PcConfiguration configuration) {
		speed.setText(Integer.toString(configuration.getSpeed()));
	}

}
