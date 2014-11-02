package de.jumpnbump.usecases.viewer.viewer;

import java.awt.Component;

import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;

import de.oetting.bumpingbunnies.model.game.objects.SpawnPoint;

public class SpawnpointRender implements ListCellRenderer<SpawnPoint> {

	private JLabel label;

	public SpawnpointRender() {
		this.label = new JLabel();
		this.label.setOpaque(true);
	}

	@Override
	public Component getListCellRendererComponent(JList<? extends SpawnPoint> list, SpawnPoint value, int index, boolean isSelected, boolean cellHasFocus) {
		this.label.setText(String.format("[%d, %d]", value.getX(), value.getY()));
		if (isSelected) {
			this.label.setBackground(list.getSelectionBackground());
		} else {
			this.label.setBackground(list.getBackground());
		}
		return this.label;
	}
}
