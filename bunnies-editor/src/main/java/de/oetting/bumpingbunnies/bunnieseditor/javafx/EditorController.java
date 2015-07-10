package de.oetting.bumpingbunnies.bunnieseditor.javafx;

import java.net.URL;
import java.util.ResourceBundle;

import de.oetting.bumpingbunnies.bunnieseditor.javafx.action.CanvasController;
import de.oetting.bumpingbunnies.bunnieseditor.javafx.action.CreateCircleAction;
import de.oetting.bumpingbunnies.bunnieseditor.javafx.action.EditorAction;
import de.oetting.bumpingbunnies.bunnieseditor.javafx.action.model.MementoEditorModel;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.MouseEvent;

public class EditorController implements Initializable {

	@FXML
	private Canvas canvas;
	@FXML
	private ToggleButton circleButton;
	@FXML
	private ToggleButton rectangleButton;

	private EditorAction currentAction;
	
	private CanvasDrawer drawer;
	private CanvasController controller;
	private final MementoEditorModel model;

	public EditorController() {
		this.model = new MementoEditorModel();
	}
	
	@FXML
	public void onActionCircle() {
		System.out.println("a");
	}

	@FXML
	public void onActionRectangle() {
		System.out.println("b");
//		GraphicsContext context2d = canvas.getGraphicsContext2D();
//		context2d.fillRect(0, 50, 150, 250);
	}
	
	private void repaintCanvas() {
		drawer.repaint();
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		circleButton.setSelected(true);
		createEditorButtonGroup();
		drawer = new CanvasDrawer(canvas, model);
		controller = new CanvasController(drawer, model);
		currentAction = new CreateCircleAction(controller);
		registerMouseListeners();
	}

	private void registerMouseListeners() {
		canvas.addEventHandler(MouseEvent.MOUSE_PRESSED, event -> onMousePressed(event));
		canvas.addEventHandler(MouseEvent.MOUSE_RELEASED, event -> onMouseReleased(event));
	}

	private void onMousePressed(MouseEvent event) {
		currentAction.mousePressed(event);
	}

	private void onMouseReleased(MouseEvent event) {
		currentAction.mouseReleased(event);
	}

	private void createEditorButtonGroup() {
		ToggleGroup group = new ToggleGroup();
		circleButton.setToggleGroup(group);
		rectangleButton.setToggleGroup(group);
	}
}
