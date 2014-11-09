package de.oetting.bumpingbunnies.pc.scoreMenu;

import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.util.Callback;

public class ScoreCellFactory implements Callback<TableColumn<ScoreEntry, Integer>, TableCell<ScoreEntry, Integer>> {

	@Override
	public TableCell<ScoreEntry, Integer> call(TableColumn<ScoreEntry, Integer> param) {
		return new ScoreImageCell();
	}

}
