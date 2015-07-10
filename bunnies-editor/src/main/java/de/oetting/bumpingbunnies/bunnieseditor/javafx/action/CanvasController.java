package de.oetting.bumpingbunnies.bunnieseditor.javafx.action;

import de.oetting.bumpingbunnies.bunnieseditor.javafx.CanvasDrawer;
import de.oetting.bumpingbunnies.bunnieseditor.javafx.action.model.MementoEditorModel;
import de.oetting.bumpingbunnies.bunnieseditor.javafx.action.model.shapes.Circle;

public class CanvasController {

	private final CanvasDrawer drawer;
	private final MementoEditorModel model;

	public CanvasController(CanvasDrawer drawer, MementoEditorModel model) {
		this.drawer = drawer;
		this.model = model;
	}

	public void createCircle() {
		drawer.repaint();
		model.addCircle(new Circle());
	}

}
