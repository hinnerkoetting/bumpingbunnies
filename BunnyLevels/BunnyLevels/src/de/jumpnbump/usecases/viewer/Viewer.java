package de.jumpnbump.usecases.viewer;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import javax.swing.JFrame;

import de.jumpnbump.usecases.viewer.xml.ObjectContainer;
import de.jumpnbump.usecases.viewer.xml.XmlBuilder;

public class Viewer {

	private MyCanvas myCanvas;

	public JFrame createFrame(ObjectContainer container) {
		this.myCanvas = new MyCanvas(container);
		JFrame frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(1000, 700);
		frame.add(this.myCanvas);
		return frame;
	}

	public void display(final String file) throws FileNotFoundException {

		final JFrame frame = createFrame(parse(file));

		frame.addKeyListener(new KeyListener() {

			@Override
			public void keyTyped(KeyEvent e) {
			}

			@Override
			public void keyReleased(KeyEvent e) {
			}

			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_F5) {
					try {
						ObjectContainer object = parse(file);
						Viewer.this.myCanvas.setObjectContainer(object);
						Viewer.this.myCanvas.repaint();
					} catch (FileNotFoundException e1) {
						throw new RuntimeException(e1);
					}
				}
			}
		});
		frame.setVisible(true);

	}

	private ObjectContainer parse(String file) throws FileNotFoundException {
		File f = new File(file);
		InputStream is = new FileInputStream(f);
		ObjectContainer parse = new XmlBuilder().parse(is);
		return parse;
	}
}
