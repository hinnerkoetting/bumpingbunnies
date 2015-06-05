package de.oetting.bumpingbunnies.leveleditor.viewer;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.GridLayout;
import java.text.NumberFormat;
import java.text.ParseException;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import de.oetting.bumpingbunnies.model.game.objects.ModelConstants;
import de.oetting.bumpingbunnies.model.game.objects.SpawnPoint;
import de.oetting.bumpingbunnies.model.game.objects.Wall;

public class SpawnPropertyEditorDialog {

	private JDialog dialog;
	private JTextField xTextfield;
	private JTextField yTextfield;

	private final SpawnPoint editedObject;

	public SpawnPropertyEditorDialog(JFrame owner, SpawnPoint editedObject) {
		this.editedObject = editedObject;
		dialog = new JDialog(owner, true);
		buildDialog();
		fillGui();
	}

	private void buildDialog() {
		dialog.setLayout(new BorderLayout());
		dialog.add(createTextFieldsPanel(), BorderLayout.PAGE_START);
		dialog.add(createControlButtonPanel());
	}

	private void fillGui() {
		xTextfield.setText(toDoubleText(editedObject.getX()));
		yTextfield.setText(toDoubleText(editedObject.getY()));
	}

	private void fillObject() throws ParseException {
		editedObject.setX(toGame(xTextfield.getText()));
		editedObject.setY(toGame(yTextfield.getText()));
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

	private long toGame(String text) throws ParseException {
		Number value = NumberFormat.getNumberInstance().parse(text);
		return (long) (value.doubleValue() * ModelConstants.STANDARD_WORLD_SIZE);
	}

	private String toDoubleText(long value) {
		return String.format("%.4f", (double) value / ModelConstants.STANDARD_WORLD_SIZE);
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

	private JPanel createTextFieldsPanel() {
		JPanel panel = new JPanel(new GridLayout(2, 4));
		panel.add(new JLabel("X"));
		panel.add(createXTextfield());
		panel.add(new JLabel("Y"));
		panel.add(createYTextfield());
		return panel;
	}

	private JTextField createXTextfield() {
		xTextfield = new JTextField(10);
		return xTextfield;
	}

	private JTextField createYTextfield() {
		yTextfield = new JTextField(10);
		return yTextfield;
	}

	public void show() {
		dialog.pack();
		dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		dialog.setVisible(true);
	}

}
