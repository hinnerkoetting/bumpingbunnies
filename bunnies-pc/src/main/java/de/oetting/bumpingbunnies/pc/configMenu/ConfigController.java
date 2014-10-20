package de.oetting.bumpingbunnies.pc.configMenu;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import de.oetting.bumpingbunnies.pc.configuration.ConfigAccess;

public class ConfigController {

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
		configuration.setPlayer1Name(player1Name.getText());
		configuration.setPlayer1Up(player1Up.getText());
		configuration.setPlayer1Left(player1Left.getText());
		configuration.setPlayer1Right(player1Right.getText());

		configuration.setPlayer2Name(player2Name.getText());
		configuration.setPlayer2Up(player2Up.getText());
		configuration.setPlayer2Left(player2Left.getText());
		configuration.setPlayer2Right(player2Right.getText());

		configuration.setPlayer3Name(player3Name.getText());
		configuration.setPlayer3Up(player3Up.getText());
		configuration.setPlayer3Left(player3Left.getText());
		configuration.setPlayer3Right(player3Right.getText());

		configuration.setSpeed(Integer.parseInt(speed.getText()));
		return configuration;
	}

}
