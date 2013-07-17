package de.jumpnbump.usecases.viewer.Viewer;

import java.awt.BorderLayout;
import java.io.FileNotFoundException;

import javax.swing.JFrame;

public class Viewer {

	// private MyCanvas myCanvas;

	public JFrame createFrame(String file) {
		// this.myCanvas = new MyCanvas(container);
		ViewerPanel panel = new ViewerPanel(file);
		panel.build();
		JFrame frame = new JFrame();
		frame.setLayout(new BorderLayout());
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(1200, 900);
		frame.add(panel, BorderLayout.CENTER);
		return frame;
	}

	public void display(final String file) throws FileNotFoundException {
		final JFrame frame = createFrame(file);
		frame.setVisible(true);
	}

}
