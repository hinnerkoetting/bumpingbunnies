package de.oetting.bumpingbunnies.pc.scoreMenu;

import javafx.beans.binding.IntegerBinding;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.util.Callback;

public class ScoreImageValueFactory implements Callback<TableColumn.CellDataFeatures<ScoreEntry, ScoreEntry>, ObservableValue<Number>> {

	@Override
	public ObservableValue<Number> call(final CellDataFeatures<ScoreEntry, ScoreEntry> param) {
		return new IntegerBinding() {

			@Override
			protected int computeValue() {
				return param.getValue().getColor();
			}
		};
	}
}
