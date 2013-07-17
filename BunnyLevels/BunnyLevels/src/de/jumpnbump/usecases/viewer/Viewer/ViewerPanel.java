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
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import de.jumpnbump.usecases.viewer.MyCanvas;
import de.jumpnbump.usecases.viewer.model.IcyWall;
import de.jumpnbump.usecases.viewer.model.Jumper;
import de.jumpnbump.usecases.viewer.model.SpawnPoint;
import de.jumpnbump.usecases.viewer.model.Wall;
import de.jumpnbump.usecases.viewer.model.Water;
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
		add(new JScrollPane(this.myCanvas), BorderLayout.CENTER);
		add(createRightBox(), BorderLayout.LINE_END);
	}

	private Box createRightBox() {
		Box box = new Box(BoxLayout.Y_AXIS);
		addButtons(box);
		addLists(box);
		return box;
	}

	private void addButtons(Box box) {
		box.add(createLoadButton());
		box.add(createRefreshButton());

		box.add(createSaveButton());
	}

	private void addLists(Box box) {
		box.add(new JScrollPane(createWallList()));
		box.add(new JScrollPane(createIceWallList()));
		box.add(new JScrollPane(createJumperList()));
		box.add(new JScrollPane(createWatersList()));
		box.add(new JScrollPane(createSpawnList()));
	}

	private JList<Wall> createWallList() {
		final JList<Wall> wall = new JList<>();
		final DefaultListModel<Wall> defaultListModel = new DefaultListModel<>();
		for (Wall w : this.model.getWalls()) {
			defaultListModel.addElement(w);
		}
		wall.setCellRenderer(new GameObjectRenderer());
		wall.setModel(defaultListModel);
		wall.addListSelectionListener(new SelectionToCanvasSynchronizer(this.myCanvas));
		return wall;
	}

	private JList<IcyWall> createIceWallList() {
		JList<IcyWall> wall = new JList<>();
		DefaultListModel<IcyWall> defaultListModel = new DefaultListModel<>();
		for (IcyWall w : this.model.getIceWalls()) {
			defaultListModel.addElement(w);
		}
		wall.setCellRenderer(new GameObjectRenderer());
		wall.setModel(defaultListModel);
		wall.addListSelectionListener(new SelectionToCanvasSynchronizer(this.myCanvas));
		return wall;
	}

	private JList<Jumper> createJumperList() {
		JList<Jumper> jumpers = new JList<>();
		DefaultListModel<Jumper> defaultListModel = new DefaultListModel<>();
		for (Jumper w : this.model.getJumpers()) {
			defaultListModel.addElement(w);
		}
		jumpers.setCellRenderer(new GameObjectRenderer());
		jumpers.setModel(defaultListModel);
		jumpers.addListSelectionListener(new SelectionToCanvasSynchronizer(this.myCanvas));
		return jumpers;
	}

	private JList<Water> createWatersList() {
		JList<Water> waters = new JList<>();
		DefaultListModel<Water> defaultListModel = new DefaultListModel<>();
		for (Water w : this.model.getWaters()) {
			defaultListModel.addElement(w);
		}
		waters.setCellRenderer(new GameObjectRenderer());
		waters.setModel(defaultListModel);
		waters.addListSelectionListener(new SelectionToCanvasSynchronizer(this.myCanvas));
		return waters;
	}

	private JList<SpawnPoint> createSpawnList() {
		JList<SpawnPoint> spawns = new JList<>();
		DefaultListModel<SpawnPoint> defaultListModel = new DefaultListModel<>();
		for (SpawnPoint w : this.model.getSpawnPoints()) {
			defaultListModel.addElement(w);
		}
		spawns.setCellRenderer(new SpawnpointRender());
		spawns.setModel(defaultListModel);
		spawns.addListSelectionListener(new SelectionToCanvasSynchronizer(this.myCanvas));
		return spawns;
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
