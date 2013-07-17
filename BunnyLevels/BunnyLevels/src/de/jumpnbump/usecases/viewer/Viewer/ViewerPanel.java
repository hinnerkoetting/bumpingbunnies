package de.jumpnbump.usecases.viewer.Viewer;

import java.awt.BorderLayout;
import java.awt.FileDialog;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import de.jumpnbump.usecases.viewer.MyCanvas;
import de.jumpnbump.usecases.viewer.xml.ObjectContainer;
import de.jumpnbump.usecases.viewer.xml.XmlBuilder;

public class ViewerPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	private MyCanvas myCanvas;
	private XmlBuilder builder;
	private String lastFile;

	public ViewerPanel(String file) {
		this.lastFile = file;

		this.builder = new XmlBuilder();
	}

	public void build() {
		setLayout(new BorderLayout());
		this.myCanvas = new MyCanvas(parsexml());
		add(this.myCanvas, BorderLayout.CENTER);
		addButtons();
	}

	private void addButtons() {
		Box box = new Box(BoxLayout.Y_AXIS);
		box.add(createLoadButton());
		box.add(createRefreshButton());

		box.add(new JButton("Save"));
		add(box, BorderLayout.LINE_END);
	}

	private JButton createRefreshButton() {
		JButton button = new JButton("Refresh");
		button.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				displayFile();
			}
		});
		return button;
	}

	private JButton createLoadButton() {
		JButton button = new JButton("Load");
		button.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				loadFilecontent();
			}

		});
		return button;
	}

	private void displayFile() {
		ObjectContainer parsedxml = parsexml();
		this.myCanvas.setObjectContainer(parsedxml);
		this.myCanvas.repaint();
	}

	private void loadFilecontent() {
		FileDialog dialog = new FileDialog((JFrame) ViewerPanel.this.getRootPane().getParent());
		dialog.setVisible(true);
		this.lastFile = dialog.getFile();
		displayFile();
	}

	private ObjectContainer parsexml() {
		try {
			return this.builder.parse(new FileInputStream(this.lastFile));
		} catch (FileNotFoundException e) {
			throw new RuntimeException(e);
		}
	}
}
