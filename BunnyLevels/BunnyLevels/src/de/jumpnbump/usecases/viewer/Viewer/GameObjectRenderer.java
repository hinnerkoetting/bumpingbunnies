package de.jumpnbump.usecases.viewer.Viewer;

import java.awt.Component;

import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;

import de.jumpnbump.usecases.viewer.model.GameObject;

public class GameObjectRenderer implements ListCellRenderer<GameObject> {

	private JLabel label;

	public GameObjectRenderer() {
		this.label = new JLabel();
	}

	@Override
	public Component getListCellRendererComponent(JList<? extends GameObject> list, GameObject value, int index, boolean isSelected,
			boolean cellHasFocus) {
		this.label.setText(value.id() + String.format(": [%d, %d] - [%d, %d]", value.minX(), value.minY(), value.maxX(), value.maxY()));
		return this.label;
	}
}
