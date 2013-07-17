package de.jumpnbump.usecases.viewer.Viewer;

import java.awt.BorderLayout;
import java.awt.FileDialog;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
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
import de.jumpnbump.usecases.viewer.xml.XmlStorer;

public class ViewerPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	private MyCanvas myCanvas;
	private final XmlBuilder builder;
	private String lastFile;
	private ObjectContainer model;

	public ViewerPanel(String file) {
		this.lastFile = file;

		this.builder = new XmlBuilder();

	}

	public void build() {
		setLayout(new BorderLayout());
		parsexml();
		this.myCanvas = new MyCanvas(this.model);
		add(this.myCanvas, BorderLayout.CENTER);
		addButtons();
	}

	private void addButtons() {
		Box box = new Box(BoxLayout.Y_AXIS);
		box.add(createLoadButton());
		box.add(createRefreshButton());

		box.add(createSaveButton());
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
		parsexml();
		this.myCanvas.setObjectContainer(this.model);
		this.myCanvas.repaint();
	}

	private void loadFilecontent() {
		FileDialog dialog = new FileDialog((JFrame) ViewerPanel.this.getRootPane().getParent());
		dialog.setVisible(true);
		this.lastFile = dialog.getFile();
		displayFile();
	}

	private void parsexml() {
		try {
			this.model = this.builder.parse(new FileInputStream(this.lastFile));
		} catch (FileNotFoundException e) {
			throw new RuntimeException(e);
		}
	}

	private JButton createSaveButton() {
		JButton button = new JButton("save");
		button.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				save();
			}
		});
		return button;
	}

	private void save() {
		try {
			ObjectContainer container = this.builder.parse(new FileInputStream(this.lastFile));
			FileDialog dialog = new FileDialog((JFrame) ViewerPanel.this.getRootPane().getParent(), "save", FileDialog.SAVE);
			dialog.setVisible(true);
			this.lastFile = dialog.getDirectory() + File.separator + dialog.getFile();
			java.io.File newFile = new java.io.File(this.lastFile);
			newFile.delete();
			newFile.createNewFile();
			new XmlStorer(container).saveXml(newFile);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}
