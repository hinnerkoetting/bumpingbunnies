package de.jumpnbump.usecases.viewer.Viewer;

import java.awt.event.MouseEvent;

public class ScrollTopMouseAction implements MouseAction {

	private int startX;
	private int startY;

	public ScrollTopMouseAction(int startX, int startY) {
		super();
		this.startX = startX;
		this.startY = startY;
	}

	@Override
	public void newMousePosition(MouseEvent event) {

	}

}
