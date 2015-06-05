package de.oetting.bumpingbunnies.leveleditor.viewer;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.GridLayout;
import java.text.NumberFormat;
import java.text.ParseException;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import de.oetting.bumpingbunnies.model.game.objects.GameObject;
import de.oetting.bumpingbunnies.model.game.objects.ModelConstants;

public class PropertiesPanel {

	private JTextField minXTextfield;
	private JTextField minYTextfield;
	private JTextField maxXTextfield;
	private JTextField maxYTextfield;

	private final GameObject editedObject;

	public PropertiesPanel(GameObject editedObject) {
		this.editedObject = editedObject;
	}

	public JPanel buildDialog() {
		JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout());
		panel.add(createTextFieldsPanel(), BorderLayout.PAGE_START);
		return panel;
	}

	public void updateMasks() {
		minXTextfield.setText(toDoubleText(editedObject.minX()));
		minYTextfield.setText(toDoubleText(editedObject.minY()));
		maxXTextfield.setText(toDoubleText(editedObject.maxX()));
		maxYTextfield.setText(toDoubleText(editedObject.maxY()));
	}

	public void updateObject() throws ParseException {
		editedObject.setMinX(toGame(minXTextfield.getText()));
		editedObject.setMinY(toGame(minYTextfield.getText()));
		editedObject.setMaxX(toGame(maxXTextfield.getText()));
		editedObject.setMaxY(toGame(maxYTextfield.getText()));
	}

	private long toGame(String text) throws ParseException {
		Number value = NumberFormat.getNumberInstance().parse(text);
		return (long) (value.doubleValue() * ModelConstants.STANDARD_WORLD_SIZE);
	}

	private String toDoubleText(long value) {
		return String.format("%.4f", (double) value / ModelConstants.STANDARD_WORLD_SIZE);
	}

	private JPanel createTextFieldsPanel() {
		JPanel panel = new JPanel(new GridLayout(2, 4));
		panel.add(new JLabel("Min. X"));
		panel.add(createMinXTextfield());
		panel.add(new JLabel("Max. X"));
		panel.add(createMaxXTextfield());
		panel.add(new JLabel("Min. Y"));
		panel.add(createMinYTextfield());
		panel.add(new JLabel("Max. Y"));
		panel.add(createMaxYTextfield());
		return panel;
	}

	private JTextField createMinXTextfield() {
		minXTextfield = new JTextField(10);
		return minXTextfield;
	}

	private JTextField createMinYTextfield() {
		minYTextfield = new JTextField(10);
		return minYTextfield;
	}

	private JTextField createMaxXTextfield() {
		maxXTextfield = new JTextField(10);
		return maxXTextfield;
	}

	private JTextField createMaxYTextfield() {
		maxYTextfield = new JTextField(10);
		return maxYTextfield;
	}
}
