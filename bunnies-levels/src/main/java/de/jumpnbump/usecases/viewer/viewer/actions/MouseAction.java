package de.jumpnbump.usecases.viewer.viewer.actions;

import java.awt.event.MouseEvent;

public interface MouseAction {

	void newMousePosition(MouseEvent event);

	void rightMouseClick(MouseEvent event);
}
