package de.oetting.bumpingbunnies.leveleditor.viewer;

import java.awt.Component;
import java.awt.event.ActionEvent;

import javax.swing.BoxLayout;
import javax.swing.JCheckBox;
import javax.swing.JPanel;

import de.oetting.bumpingbunnies.core.world.World;

public class ViewableItemsPanel {

	private final ViewerPanel viewPanel;
	private JPanel panel;

	public ViewableItemsPanel(ViewerPanel viewPanel) {
		this.viewPanel = viewPanel;
	}

	public JPanel build() {
		if (panel != null)
			throw new IllegalStateException("Panel is already created");
		return buildIntern();
	}

	private JPanel buildIntern() {
		panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		panel.add(createWallCheckbox());
		panel.add(createIceWallCheckbox());
		panel.add(createWaterCheckbox());
		panel.add(createJumperCheckbox());
		panel.add(createBackgroundCheckbox());
		return panel;
	}

	private Component createWallCheckbox() {
		JCheckBox wallsCheckbox = new JCheckBox("Walls");
		wallsCheckbox.addActionListener(event -> onWallCheckboxSelected(wallsCheckbox));
		return wallsCheckbox;
	}

	private Component createIceWallCheckbox() {
		JCheckBox wallsCheckbox = new JCheckBox("IceWalls");
		wallsCheckbox.addActionListener(event -> onIceWallCheckboxSelected(wallsCheckbox));
		return wallsCheckbox;
	}

	private Component createJumperCheckbox() {
		JCheckBox wallsCheckbox = new JCheckBox("Jumper");
		wallsCheckbox.addActionListener(event -> onJumperCheckboxSelected(wallsCheckbox));
		return wallsCheckbox;
	}

	private Component createWaterCheckbox() {
		JCheckBox wallsCheckbox = new JCheckBox("Water");
		wallsCheckbox.addActionListener(event -> onWaterCheckboxSelected(wallsCheckbox));
		return wallsCheckbox;
	}

	private Component createBackgroundCheckbox() {
		JCheckBox wallsCheckbox = new JCheckBox("Background");
		wallsCheckbox.addActionListener(event -> onBackgroundCheckboxSelected(wallsCheckbox));
		return wallsCheckbox;
	}

	private void onWallCheckboxSelected(JCheckBox cb) {
		onCheckboxSelected(cb, () -> getWorld().addAllWallstoDrawingObjects(), () -> getWorld()
				.removeAllWallsFromDrawingObjects());
	}

	private void onIceWallCheckboxSelected(JCheckBox cb) {
		onCheckboxSelected(cb, () -> getWorld().addAllIceWallstoDrawingObjects(), () -> getWorld()
				.removeAllIceWallsFromDrawingObjects());
	}

	private void onWaterCheckboxSelected(JCheckBox cb) {
		onCheckboxSelected(cb, () -> getWorld().addAllWaterToDrawingObjects(), () -> getWorld()
				.removeAllWaterFromDrawingObjects());
	}

	private void onJumperCheckboxSelected(JCheckBox cb) {
		onCheckboxSelected(cb, () -> getWorld().addAllJumperToDrawingObjects(), () -> getWorld()
				.removeAllJumperFromDrawingObjects());
	}

	private void onBackgroundCheckboxSelected(JCheckBox cb) {
		onCheckboxSelected(cb, () -> getWorld().addAllBackgroundToDrawingObjects(), () -> getWorld()
				.removeAllBackgroundFromDrawingObjects());
	}

	public void onCheckboxSelected(JCheckBox cb, ShowItems showAction, HideItems hideAction) {
		if (cb.isSelected())
			showAction.showItems();
		else
			hideAction.hideItems();
		viewPanel.repaintCanvas();
	}

	private World getWorld() {
		return viewPanel.getCurrentWorld();
	}

	public static interface ShowItems {
		void showItems();
	}

	public static interface HideItems {
		void hideItems();
	}

	public void selectAllCheckboxes() {
		for (int i = 0; i < panel.getComponentCount(); i++) {
			Component component = panel.getComponent(i);
			if (component instanceof JCheckBox) {
				((JCheckBox) component).setSelected(true);
			}
		}
	}

}
