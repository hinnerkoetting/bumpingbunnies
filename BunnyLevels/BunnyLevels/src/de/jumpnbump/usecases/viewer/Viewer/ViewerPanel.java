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
	private JList<Wall> wall;
	private JList<IcyWall> icyWallList;
	private JList<Jumper> jumpersList;
	private JList<Water> watersList;
	private JList<SpawnPoint> spawns;

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
		this.wall = new JList<>();
		setWallModel();
		return this.wall;
	}

	private void setWallModel() {
		final DefaultListModel<Wall> defaultListModel = new DefaultListModel<>();
		for (Wall w : this.model.getWalls()) {
			defaultListModel.addElement(w);
		}
		this.wall.setCellRenderer(new GameObjectRenderer());
		this.wall.setModel(defaultListModel);
		this.wall.addListSelectionListener(new SelectionToCanvasSynchronizer(this.myCanvas));
	}

	private JList<IcyWall> createIceWallList() {
		this.icyWallList = new JList<>();
		setIcyWallModel();
		return this.icyWallList;
	}

	private void setIcyWallModel() {
		DefaultListModel<IcyWall> defaultListModel = new DefaultListModel<>();
		for (IcyWall w : this.model.getIceWalls()) {
			defaultListModel.addElement(w);
		}
		this.icyWallList.setCellRenderer(new GameObjectRenderer());
		this.icyWallList.setModel(defaultListModel);
		this.icyWallList.addListSelectionListener(new SelectionToCanvasSynchronizer(this.myCanvas));
	}

	private JList<Jumper> createJumperList() {
		this.jumpersList = new JList<>();
		setJumperModel();
		return this.jumpersList;
	}

	private void setJumperModel() {
		DefaultListModel<Jumper> defaultListModel = new DefaultListModel<>();
		for (Jumper w : this.model.getJumpers()) {
			defaultListModel.addElement(w);
		}
		this.jumpersList.setCellRenderer(new GameObjectRenderer());
		this.jumpersList.setModel(defaultListModel);
		this.jumpersList.addListSelectionListener(new SelectionToCanvasSynchronizer(this.myCanvas));
	}

	private JList<Water> createWatersList() {
		this.watersList = new JList<>();
		setWaterModel();
		return this.watersList;
	}

	private void setWaterModel() {
		DefaultListModel<Water> defaultListModel = new DefaultListModel<>();
		for (Water w : this.model.getWaters()) {
			defaultListModel.addElement(w);
		}
		this.watersList.setCellRenderer(new GameObjectRenderer());
		this.watersList.setModel(defaultListModel);
		this.watersList.addListSelectionListener(new SelectionToCanvasSynchronizer(this.myCanvas));
	}

	private JList<SpawnPoint> createSpawnList() {
		this.spawns = new JList<>();
		setSpawnModel();
		return this.spawns;
	}

	private void setSpawnModel() {
		DefaultListModel<SpawnPoint> defaultListModel = new DefaultListModel<>();
		for (SpawnPoint w : this.model.getSpawnPoints()) {
			defaultListModel.addElement(w);
		}
		this.spawns.setCellRenderer(new SpawnpointRender());
		this.spawns.setModel(defaultListModel);
		this.spawns.addListSelectionListener(new SelectionToCanvasSynchronizer(this.myCanvas));
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
		setWallModel();
		setIcyWallModel();
		setJumperModel();
		setWaterModel();
		setSpawnModel();
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
