package de.jumpnbump.usecases.viewer.viewer.actions;

import java.awt.event.MouseEvent;

public interface MouseAction {

	void onMouseDragged(MouseEvent event);

	default void onMousePressedFirst(MouseEvent event) {
		onMouseDragged(event);
	}

	void rightMouseClick(MouseEvent event);

}
