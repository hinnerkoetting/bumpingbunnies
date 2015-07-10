package de.oetting.bumpingbunnies.bunnieseditor.javafx.action;

import javafx.scene.input.MouseEvent;

public interface EditorAction {

	void mousePressed(MouseEvent mouseEvent);

	void mouseReleased(MouseEvent event);
}
