package de.jumpnbump.usecases.viewer.Viewer;

import java.awt.BorderLayout;
import java.io.FileNotFoundException;

import javax.swing.JFrame;

public class Viewer {

	public JFrame createFrame(String file) {
		ViewerPanel panel = new ViewerPanel(file);
		return createBasicframe(panel);
	}

	public JFrame createFrame() {
		ViewerPanel panel = new ViewerPanel();
		return createBasicframe(panel);
	}

	private JFrame createBasicframe(ViewerPanel panel) {
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

	public void display() throws FileNotFoundException {
		final JFrame frame = createFrame();
		frame.setVisible(true);
	}

}
