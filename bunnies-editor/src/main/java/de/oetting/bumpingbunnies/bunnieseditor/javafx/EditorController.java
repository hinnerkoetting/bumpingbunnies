package de.oetting.bumpingbunnies.bunnieseditor.javafx;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;

public class EditorController implements Initializable {

	@FXML
	private Canvas canvas;
	@FXML
	private ToggleButton circleButton;
	@FXML
	private ToggleButton rectangleButton;

	@FXML
	public void onActionCircle() {
		System.out.println("a");
	}

	@FXML
	public void onActionRectangle() {
		System.out.println("b");

	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		circleButton.setSelected(true);
		createEditorButtonGroup();
	}

	private void createEditorButtonGroup() {
		ToggleGroup group = new ToggleGroup();
		circleButton.setToggleGroup(group);
		rectangleButton.setToggleGroup(group);
	}
}
