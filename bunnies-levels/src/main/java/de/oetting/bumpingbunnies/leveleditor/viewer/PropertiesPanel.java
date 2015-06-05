package de.oetting.bumpingbunnies.leveleditor.viewer;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.text.NumberFormat;
import java.text.ParseException;

import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import de.oetting.bumpingbunnies.model.game.objects.GameObjectWithImage;
import de.oetting.bumpingbunnies.model.game.objects.ModelConstants;
import de.oetting.bumpingbunnies.model.game.objects.NullObject;

public class PropertiesPanel {

	private JTextField minXTextfield;
	private JTextField minYTextfield;
	private JTextField maxXTextfield;
	private JTextField maxYTextfield;

	private GameObjectWithImage editedObject;
	private final ViewerPanel panel;

	public PropertiesPanel(GameObjectWithImage editedObject, ViewerPanel panel) {
		this.editedObject = editedObject;
		this.panel = panel;
	}

	public PropertiesPanel(ViewerPanel panel) {
		this.panel = panel;
		editedObject = new NullObject();
	}

	public JPanel buildDialog() {
		JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout());
		panel.add(createTextFieldsPanel(), BorderLayout.PAGE_START);
		panel.add(createMirroredCheckbox(), BorderLayout.AFTER_LAST_LINE);
		return panel;
	}

	private Component createMirroredCheckbox() {
		JCheckBox checkbox = new JCheckBox("Mirrored");
		checkbox.addActionListener(event -> onMirroredClicked());
		return checkbox;
	}

	private void onMirroredClicked() {
		editedObject.setMirroredHorizontally(!editedObject.isMirroredHorizontally());
		panel.refreshView();
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
		minXTextfield.addFocusListener(updatePanel());
		return minXTextfield;
	}

	private JTextField createMinYTextfield() {
		minYTextfield = new JTextField(10);
		minYTextfield.addFocusListener(updatePanel());
		return minYTextfield;
	}

	private JTextField createMaxXTextfield() {
		maxXTextfield = new JTextField(10);
		maxXTextfield.addFocusListener(updatePanel());
		return maxXTextfield;
	}

	private JTextField createMaxYTextfield() {
		maxYTextfield = new JTextField(10);
		maxYTextfield.addFocusListener(updatePanel());
		return maxYTextfield;
	}

	private FocusListener updatePanel() {
		return new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				try {
					updateObject();
					panel.refreshView();
				} catch (ParseException ex) {
					JOptionPane.showMessageDialog(panel, "Could not save information " + ex.getMessage());
				}
			}
		};
	}

	public void setSelectedObject(GameObjectWithImage object) {
		if (object == null)
			object = new NullObject();
		else
			editedObject = object;
	}
}
