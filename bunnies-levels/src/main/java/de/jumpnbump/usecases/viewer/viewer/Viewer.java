package de.jumpnbump.usecases.viewer.viewer;

import java.awt.BorderLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

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
		panel.setFrame(frame);
		frame.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				storeLastFile(panel);
			}

			private void storeLastFile(ViewerPanel panel) {
				try {
					String lastFile = panel.getLastFileName();
					Properties props = new Properties();
					props.put("file", lastFile);
					props.store(new FileOutputStream("config.properties"), "");
				} catch (IOException e) {
					throw new RuntimeException(e);
				}
			}
		});
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
