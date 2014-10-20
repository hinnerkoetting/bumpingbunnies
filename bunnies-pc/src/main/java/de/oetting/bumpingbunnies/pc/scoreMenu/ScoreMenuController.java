package de.oetting.bumpingbunnies.pc.scoreMenu;

import javafx.fxml.FXML;
import javafx.scene.control.TableView;

public class ScoreMenuController {

	@FXML
	TableView<ScoreEntry> scoreTable;

	public void addScore(ScoreEntry score) {
		scoreTable.getItems().add(score);
	}
}
