package de.jumpnbump.usecases.viewer;

import javax.swing.JFrame;

import de.jumpnbump.usecases.viewer.xml.ObjectContainer;

public class Viewer {

	public void display(ObjectContainer container) {
		JFrame frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(1000, 700);
		frame.add(new MyCanvas(container));
		frame.setVisible(true);
	}
}
