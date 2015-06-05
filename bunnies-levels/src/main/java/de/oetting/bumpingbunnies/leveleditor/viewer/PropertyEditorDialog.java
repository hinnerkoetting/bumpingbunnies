package de.oetting.bumpingbunnies.leveleditor.viewer;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.text.ParseException;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import de.oetting.bumpingbunnies.model.game.objects.GameObject;
import de.oetting.bumpingbunnies.model.game.objects.Wall;

public class PropertyEditorDialog {

	private JDialog dialog;
	private PropertiesPanel panel;

	public PropertyEditorDialog(JFrame owner, GameObject editedObject) {
		panel = new PropertiesPanel(editedObject);
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

	public static void main(String[] args) {
		JFrame frame = new JFrame();
		frame.setVisible(true);
		Wall wall = new Wall(0, 1000, 10000, 10000, 50000);
		PropertyEditorDialog dialog = new PropertyEditorDialog(frame, wall);
		dialog.show();
		frame.setVisible(false);
		frame.dispose();
	}
}