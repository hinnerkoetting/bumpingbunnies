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

import de.oetting.bumpingbunnies.model.game.objects.GameObject;
import de.oetting.bumpingbunnies.model.game.objects.ModelConstants;
import de.oetting.bumpingbunnies.model.game.objects.Wall;


public class PropertyEditorDialog {
	
	private JDialog dialog;
	private JTextField minXTextfield;
	private JTextField minYTextfield;
	private JTextField maxXTextfield;
	private JTextField maxYTextfield;
	
	private final GameObject editedObject;
	
	public PropertyEditorDialog(JFrame owner, GameObject editedObject) {
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
		minXTextfield.setText(toDoubleText(editedObject.minX()));
		minYTextfield.setText(toDoubleText(editedObject.minY()));
		maxXTextfield.setText(toDoubleText(editedObject.maxX()));
		maxYTextfield.setText(toDoubleText(editedObject.maxY()));
	}
	
	private void fillObject() throws ParseException {
		editedObject.setMinX(toGame(minXTextfield.getText()));
		editedObject.setMinY(toGame(minYTextfield.getText()));
		editedObject.setMaxX(toGame(maxXTextfield.getText()));
		editedObject.setMaxY(toGame(maxYTextfield.getText()));
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