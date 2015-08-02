package de.oetting.bumpingbunnies.leveleditor.viewer;

import java.awt.Component;

import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;

import de.oetting.bumpingbunnies.model.game.objects.GameObjectWithImage;
import de.oetting.bumpingbunnies.model.game.objects.ModelConstants;

public class GameObjectRenderer implements ListCellRenderer<GameObjectWithImage> {

	private JLabel label;

	public GameObjectRenderer() {
		this.label = new JLabel();
		this.label.setOpaque(true);
	}

	@Override
	public Component getListCellRendererComponent(JList<? extends GameObjectWithImage> list, GameObjectWithImage value, int index, boolean isSelected, boolean cellHasFocus) {
		this.label
				.setText(value.id()
						+ String.format(": [%.2f, %.2f] - [%.2f, %.2f], (%d)", toFloat(value.minX()), toFloat(value.minY()), toFloat(value.maxX()),
								toFloat(value.maxY()), value.getZIndex()));
		if (isSelected) {
			this.label.setBackground(list.getSelectionBackground());
		} else {
			this.label.setBackground(list.getBackground());
		}
		return this.label;
	}

	private float toFloat(long value) {
		return (float) ((double) value / ModelConstants.STANDARD_WORLD_SIZE);
	}
}
