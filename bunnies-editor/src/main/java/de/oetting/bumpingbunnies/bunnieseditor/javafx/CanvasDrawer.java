package de.oetting.bumpingbunnies.bunnieseditor.javafx;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import de.oetting.bumpingbunnies.bunnieseditor.javafx.action.model.EditorModel;
import de.oetting.bumpingbunnies.bunnieseditor.javafx.action.model.shapes.Circle;

public class CanvasDrawer {

	private final Canvas canvas;
	private final EditorModel model;

	public CanvasDrawer(Canvas canvas, EditorModel model) {
		this.canvas = canvas;
		this.model = model;
	}

	public Canvas getCanvas() {
		return canvas;
	}

	public EditorModel getModel() {
		return model;
	}

	public void repaint() {
		GraphicsContext context2d = canvas.getGraphicsContext2D();
		context2d.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
		drawAllCircles();
	}

	private void drawAllCircles() {
		for (Circle circle: model.getAllCircles()) {
			drawCircle(circle);
		}
	}

	private void drawCircle(Circle circle) {
		GraphicsContext context2d = canvas.getGraphicsContext2D();
		context2d.fillOval(10, 20, 30, 40);
	}

}
