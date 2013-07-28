package de.jumpnbump.usecases.viewer.Viewer;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FileDialog;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import de.jumpnbump.usecases.viewer.MyCanvas;
import de.jumpnbump.usecases.viewer.model.Background;
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
	private JList<Background> backgrounds;

	public ViewerPanel(String file) {
		this.lastFile = file;
		this.builder = new XmlBuilder();
	}

	public ViewerPanel() {
		this.builder = new XmlBuilder();
		this.model = new ObjectContainer();
	}

	public void build() {
		setLayout(new BorderLayout());
		parsexml();
		this.myCanvas = new MyCanvas(this.model);
		add(new JScrollPane(this.myCanvas), BorderLayout.CENTER);
		add(createRightBox(), BorderLayout.LINE_END);
		add(createBottomImages(), BorderLayout.PAGE_END);
		addMouseListener();
	}

	private JPanel createBottomImages() {
		ImagesPanel panel = new ImagesPanel(this.myCanvas);
		panel.build();
		panel.setPreferredSize(new Dimension(100, 200));
		return panel;
	}

	private Box createRightBox() {
		Box box = new Box(BoxLayout.Y_AXIS);
		box.add(createTopPanel());
		addLists(box);
		return box;
	}

	private JComponent createTopPanel() {
		JComponent panel = new JPanel(new GridLayout(0, 2));
		panel.add(createButtons());
		panel.add(createSettings());
		return panel;
	}

	private JComponent createSettings() {
		JPanel panel = new JPanel();
		panel.add(new JLabel("Zoom"));
		final JTextField zoomField = new JTextField(5);
		zoomField.setText("1");
		zoomField.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				applyZoom(zoomField);
			}
		});
		zoomField.addFocusListener(new FocusListener() {

			@Override
			public void focusLost(FocusEvent arg0) {
				applyZoom(zoomField);
			}

			@Override
			public void focusGained(FocusEvent arg0) {
			}
		});
		panel.add(zoomField);
		return panel;
	}

	private void applyZoom(JTextField zoomField) {
		String text = zoomField.getText();
		try {
			double newZoom = Double.parseDouble(text);
			ViewerPanel.this.myCanvas.setZoom(newZoom);
			ViewerPanel.this.myCanvas.repaint();
		} catch (NumberFormatException e) {
			double oldZoom = ViewerPanel.this.myCanvas.getZoom();
			zoomField.setText(Double.toString(oldZoom));
		}
	}

	private JComponent createButtons() {
		Box box = Box.createVerticalBox();
		box.add(createLoadButton());
		box.add(createRefreshButton());
		box.add(createSaveButton());
		return box;
	}

	private void addLists(Box box) {
		JPanel p = new JPanel();
		p.setLayout(new GridLayout(0, 2));
		p.add(createWallsPanel());

		p.add(createIceWallsPanel());
		p.add(createJumpersPanel());
		p.add(createWatersPanel());
		p.add(createSpawnPanel());
		p.add(createBackgroundsPanel());
		box.add(p);
	}

	private JComponent createWallsPanel() {
		Box p = Box.createVerticalBox();
		p.add(new JLabel("Walls"));
		p.add(new JScrollPane(createWallList()));
		return p;
	}

	private JComponent createIceWallsPanel() {
		Box p = Box.createVerticalBox();
		p.add(new JLabel("IceWalls"));
		p.add(new JScrollPane(createIceWallList()));
		return p;
	}

	private JComponent createJumpersPanel() {
		Box p = Box.createVerticalBox();
		p.add(new JLabel("Jumpers"));
		p.add(new JScrollPane(createJumperList()));
		return p;
	}

	private JComponent createWatersPanel() {
		Box p = Box.createVerticalBox();
		p.add(new JLabel("Waters"));
		p.add(new JScrollPane(createWatersList()));
		return p;
	}

	private JComponent createSpawnPanel() {
		Box p = Box.createVerticalBox();
		p.add(new JLabel("Spawns"));
		p.add(new JScrollPane(createSpawnList()));
		return p;
	}

	private JComponent createBackgroundsPanel() {
		Box p = Box.createVerticalBox();
		p.add(new JLabel("Background"));
		p.add(new JScrollPane(createBackgroundsList()));
		return p;
	}

	private LayoutManager createLayout() {
		GridBagLayout layout = new GridBagLayout();
		return layout;
	}

	private JList<Wall> createWallList() {
		this.wall = new JList<>();
		setWallModel();
		return this.wall;
	}

	private void setWallModel() {
		final MyListModel<Wall> defaultListModel = new MyListModel<>();
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
		MyListModel<IcyWall> defaultListModel = new MyListModel<>();
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
		MyListModel<Jumper> defaultListModel = new MyListModel<>();
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
		MyListModel<Water> defaultListModel = new MyListModel<>();
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

	private JList<Background> createBackgroundsList() {
		this.backgrounds = new JList<>();
		setBackgroundsModel();
		return this.backgrounds;
	}

	private void setSpawnModel() {
		MyListModel<SpawnPoint> defaultListModel = new MyListModel<>();
		for (SpawnPoint w : this.model.getSpawnPoints()) {
			defaultListModel.addElement(w);
		}
		this.spawns.setCellRenderer(new SpawnpointRender());
		this.spawns.setModel(defaultListModel);
		this.spawns.addListSelectionListener(new SelectionToCanvasSynchronizer(this.myCanvas));
	}

	private void setBackgroundsModel() {
		MyListModel<Background> defaultListModel = new MyListModel<>();
		for (Background w : this.model.getBackgrounds()) {
			defaultListModel.addElement(w);
		}
		this.backgrounds.setCellRenderer(new GameObjectRenderer());
		this.backgrounds.setModel(defaultListModel);
		this.backgrounds.addListSelectionListener(new SelectionToCanvasSynchronizer(this.myCanvas));
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
		setBackgroundsModel();
		addMouseListener();
		this.myCanvas.repaint();

	}

	private void loadFilecontent() {
		FileDialog dialog = new FileDialog((JFrame) ViewerPanel.this.getRootPane().getParent());
		dialog.setVisible(true);
		this.lastFile = dialog.getDirectory() + File.separator + dialog.getFile();
		displayFile();
	}

	private void parsexml() {
		try {
			if (this.lastFile != null) {
				this.model = this.builder.parse(new FileInputStream(this.lastFile));
			}
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

	private void addMouseListener() {
		for (MouseListener ml : this.myCanvas.getMouseListeners()) {
			this.myCanvas.removeMouseListener(ml);
		}
		for (MouseMotionListener ml : this.myCanvas.getMouseMotionListeners()) {
			this.myCanvas.removeMouseMotionListener(ml);
		}
		CanvasMouseListener ml = new CanvasMouseListener(this.model, this.myCanvas, this);
		this.myCanvas.addMouseListener(ml);
		this.myCanvas.addMouseMotionListener(ml);
	}

	private void save() {
		try {
			FileDialog dialog = new FileDialog((JFrame) ViewerPanel.this.getRootPane().getParent(), "save", FileDialog.SAVE);
			dialog.setVisible(true);
			this.lastFile = dialog.getDirectory() + File.separator + dialog.getFile();
			java.io.File newFile = new java.io.File(this.lastFile);
			newFile.delete();
			newFile.createNewFile();
			new XmlStorer(this.model).saveXml(newFile);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public void refreshTables() {
		refreshList(this.spawns);
		refreshList(this.wall);
		refreshList(this.icyWallList);
		refreshList(this.watersList);
		refreshList(this.jumpersList);
		refreshList(this.backgrounds);
	}

	private void refreshList(JList<?> list) {
		MyListModel<?> lModel = (MyListModel<?>) list.getModel();
		lModel.fireContentsChanged(this, 0, lModel.size() - 1);
	}

}
