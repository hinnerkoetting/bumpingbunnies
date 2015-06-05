package de.oetting.bumpingbunnies.leveleditor.viewer;

import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.text.ParseException;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import de.oetting.bumpingbunnies.model.game.objects.GameObjectWithImage;
import de.oetting.bumpingbunnies.model.game.objects.Wall;

public class PropertyEditorDialog {

	private JDialog dialog;
	private PropertiesPanel panel;

	public PropertyEditorDialog(JFrame owner, GameObjectWithImage editedObject, ViewerPanel viewerPanel) {
		panel = new PropertiesPanel(editedObject, viewerPanel);
		dialog = new JDialog(owner, true);
		buildDialog();
		fillGui();
	}

	private void buildDialog() {
		dialog.setLayout(new GridBagLayout());
		dialog.add(panel.buildDialog());
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.gridy = 1;
		gbc.anchor = GridBagConstraints.EAST;
		dialog.add(createControlButtonPanel(), gbc);
	}

	private void fillGui() {
		panel.updateMasks();
	}

	private void fillObject() throws ParseException {
		panel.updateObject();
	}

	private Component createControlButtonPanel() {
		JPanel panel = new JPanel();
		panel.add(createCancelButton());
		panel.add(createOkButton());
		return panel;
	}

	private JButton createOkButton() {
		JButton button = new JButton("Ok");
		button.addActionListener((event) -> onOkButton());
		return button;
	}

	private JButton createCancelButton() {
		JButton button = new JButton("Cancel");
		button.addActionListener((event) -> onCancelButton());
		return button;
	}

	public void onOkButton() {
		try {
			fillObject();
			dialog.setVisible(false);
		} catch (Exception e) {
			JOptionPane.showMessageDialog(dialog, "Could not save information " + e.getMessage());
		}
	}

	public void onCancelButton() {
		dialog.dispose();
	}

	public void show() {
		dialog.pack();
		dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		dialog.setVisible(true);
	}

}