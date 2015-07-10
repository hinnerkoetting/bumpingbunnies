package de.oetting.bumpingbunnies.bunnieseditor.javafx.action;

import javafx.scene.input.MouseEvent;

public class CreateCircleAction implements EditorAction {

	private final CanvasController controller;

	public CreateCircleAction(CanvasController controller) {
		this.controller = controller;
	}

	@Override
	public void mousePressed(MouseEvent mouseEvent) {
		controller.createCircle();
	}

	@Override
	public void mouseReleased(MouseEvent event) {

	}
}
