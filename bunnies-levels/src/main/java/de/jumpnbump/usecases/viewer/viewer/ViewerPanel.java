package de.jumpnbump.usecases.viewer.viewer;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FileDialog;
import java.awt.GridLayout;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Enumeration;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.AbstractButton;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JToggleButton;

import de.jumpnbump.usecases.viewer.MyCanvas;
import de.jumpnbump.usecases.viewer.xml.XmlBuilder;
import de.jumpnbump.usecases.viewer.xml.XmlStorer;
import de.oetting.bumpingbunnies.core.world.World;
import de.oetting.bumpingbunnies.model.game.objects.Background;
import de.oetting.bumpingbunnies.model.game.objects.GameObject;
import de.oetting.bumpingbunnies.model.game.objects.IcyWall;
import de.oetting.bumpingbunnies.model.game.objects.Jumper;
import de.oetting.bumpingbunnies.model.game.objects.ModelConstants;
import de.oetting.bumpingbunnies.model.game.objects.SpawnPoint;
import de.oetting.bumpingbunnies.model.game.objects.Wall;
import de.oetting.bumpingbunnies.model.game.objects.Water;

public class ViewerPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	private MyCanvas myCanvas;
	private final XmlBuilder builder;
	private String lastFile;
	private World model;
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
		model = new World();
	}

	public void build() {
		setLayout(createLayout());
		parseXml();
		this.myCanvas = new MyCanvas(this.model);
		add(createModeButtons(), BorderLayout.LINE_START);
		add(new JScrollPane(this.myCanvas), BorderLayout.CENTER);
		add(createRightBox(), BorderLayout.LINE_END);
		add(createBottomImages(), BorderLayout.PAGE_END);
		addMouseListener();
	}

	private BorderLayout createLayout() {
		BorderLayout borderLayout = new BorderLayout();
		borderLayout.setHgap(10);
		return borderLayout;
	}

	private Component createModeButtons() {
		Box box = Box.createVerticalBox();
		ButtonGroup group = new ButtonGroup();
		group.add(createPointerButton());
		group.add(createTrashButton());
		group.add(createWallButton());
		group.add(createIceButton());
		group.add(createWaterButton());
		group.add(createJumperButton());
		group.add(createBackgroundButton());
		Enumeration<AbstractButton> enumeration = group.getElements();
		while (enumeration.hasMoreElements()) {
			box.add(enumeration.nextElement());
			box.add(Box.createVerticalStrut(10));
		}
		return box;
	}

	private AbstractButton createBackgroundButton() {
		return new JToggleButton(readIcon("/images/background.png"));
	}

	private AbstractButton createTrashButton() {
		return new JToggleButton(readIcon("/images/trash.png"));
	}

	private AbstractButton createWallButton() {
		return new JToggleButton(readIcon("/images/wall.png"));
	}

	private AbstractButton createIceButton() {
		return new JToggleButton(readIcon("/images/ice.png"));
	}

	private AbstractButton createWaterButton() {
		return new JToggleButton(readIcon("/images/water.png"));
	}

	private AbstractButton createJumperButton() {
		return new JToggleButton(readIcon("/images/jumper.png"));
	}

	private AbstractButton createPointerButton() {
		return new JToggleButton(readIcon("/images/pointer.png"));
	}

	private ImageIcon readIcon(String resource) {
		try {
			return new ImageIcon(ImageIO.read(getClass().getResourceAsStream(resource)));
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
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
		zoomField.addActionListener((event) -> applyZoom(zoomField));
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
		box.add(createRoundButton());
		return box;
	}

	private Component createRoundButton() {
		JButton button = new JButton("Round");
		button.addActionListener((event) -> round());
		return button;
	}

	private void round() {
		this.model.getAllObjects().stream().forEach((object) -> round(object));
		myCanvas.repaint();
	}

	private void round(GameObject go) {
		go.setMinX(findRoundedValueX(go.minX()));
		go.setMaxX(findRoundedValueX(go.maxX()));
		if (go.minX() == go.maxX()) {
			go.setMaxX(go.minX() + 1);
		}
		go.setMinY(findRoundedValueY(go.minY()));
		go.setMaxY(findRoundedValueY(go.maxY()));
		if (go.minY() == go.maxY()) {
			go.setMaxY(go.minY() + 1);
		}
	}

	private int findRoundedValueX(double inX) {
		double targetValue = inX / ModelConstants.MAX_VALUE;
		return (int) (Math.round(targetValue * 100) * ModelConstants.MAX_VALUE / 100.0);
	}

	private int findRoundedValueY(double inY) {
		double targetValue = inY / ModelConstants.MAX_VALUE;
		return (int) (Math.round(targetValue * 100) * ModelConstants.MAX_VALUE / 100.0);
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

	private JList<Wall> createWallList() {
		this.wall = new JList<>();
		setWallModel();
		return this.wall;
	}

	private void setWallModel() {
		setObjectsModel(model.getAllWalls(), wall);
	}

	public <S extends GameObject> void setObjectsModel(List<S> objects, JList<S> list) {
		final MyListModel<S> defaultListModel = new MyListModel<>();
		objects.stream().forEach((wall) -> defaultListModel.addElement(wall));
		list.setCellRenderer(new GameObjectRenderer());
		list.setModel(defaultListModel);
		list.addListSelectionListener(new SelectionToCanvasSynchronizer(this.myCanvas));
	}

	private JList<IcyWall> createIceWallList() {
		this.icyWallList = new JList<>();
		setIcyWallModel();
		return this.icyWallList;
	}

	private void setIcyWallModel() {
		setObjectsModel(model.getAllIcyWalls(), icyWallList);
	}

	private JList<Jumper> createJumperList() {
		this.jumpersList = new JList<>();
		setJumperModel();
		return this.jumpersList;
	}

	private void setJumperModel() {
		setObjectsModel(model.getAllJumper(), jumpersList);
	}

	private JList<Water> createWatersList() {
		this.watersList = new JList<>();
		setWaterModel();
		return this.watersList;
	}

	private void setWaterModel() {
		setObjectsModel(model.getAllWaters(), watersList);
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
		setObjectsModel(model.getBackgrounds(), backgrounds);
	}

	private JButton createRefreshButton() {
		JButton button = new JButton("Refresh");
		button.addActionListener((event) -> displayFile());
		return button;
	}

	private JButton createLoadButton() {
		JButton button = new JButton("Load");
		button.addActionListener((event) -> loadFilecontent());
		return button;
	}

	private void displayFile() {
		parseXml();
		this.myCanvas.setWorld(this.model);
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

	private void parseXml() {
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
		button.addActionListener((event) -> save());
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
