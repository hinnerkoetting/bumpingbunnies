package de.oetting.bumpingbunnies.leveleditor.viewer;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.GridLayout;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import javax.imageio.ImageIO;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.TitledBorder;
import javax.swing.filechooser.FileNameExtensionFilter;

import de.oetting.bumpingbunnies.core.worldCreation.NonClosingInputstream;
import de.oetting.bumpingbunnies.leveleditor.MyCanvas;
import de.oetting.bumpingbunnies.leveleditor.viewer.actions.CanvasObjectsFinder;
import de.oetting.bumpingbunnies.leveleditor.viewer.editingMode.CreateBackgroundEditingMode;
import de.oetting.bumpingbunnies.leveleditor.viewer.editingMode.CreateIceWallEditingMode;
import de.oetting.bumpingbunnies.leveleditor.viewer.editingMode.CreateJumperEditingMode;
import de.oetting.bumpingbunnies.leveleditor.viewer.editingMode.CreateWallEditingMode;
import de.oetting.bumpingbunnies.leveleditor.viewer.editingMode.CreateWaterEditingMode;
import de.oetting.bumpingbunnies.leveleditor.viewer.editingMode.DefaultSelectionModeProvider;
import de.oetting.bumpingbunnies.leveleditor.viewer.editingMode.DeleteModeMouseListener;
import de.oetting.bumpingbunnies.leveleditor.viewer.editingMode.ModeMouseListener;
import de.oetting.bumpingbunnies.leveleditor.viewer.editingMode.SelectModeMouseListener;
import de.oetting.bumpingbunnies.leveleditor.viewer.editingMode.SelectionModeProvider;
import de.oetting.bumpingbunnies.logger.Logger;
import de.oetting.bumpingbunnies.logger.LoggerFactory;
import de.oetting.bumpingbunnies.model.game.objects.Background;
import de.oetting.bumpingbunnies.model.game.objects.GameObject;
import de.oetting.bumpingbunnies.model.game.objects.GameObjectWithImage;
import de.oetting.bumpingbunnies.model.game.objects.IcyWall;
import de.oetting.bumpingbunnies.model.game.objects.Jumper;
import de.oetting.bumpingbunnies.model.game.objects.ModelConstants;
import de.oetting.bumpingbunnies.model.game.objects.SpawnPoint;
import de.oetting.bumpingbunnies.model.game.objects.Wall;
import de.oetting.bumpingbunnies.model.game.objects.Water;
import de.oetting.bumpingbunnies.model.game.world.World;
import de.oetting.bumpingbunnies.model.game.world.WorldProperties;
import de.oetting.bumpingbunnies.worldcreator.io.LevelStorer;
import de.oetting.bumpingbunnies.worldcreator.io.XmlStorer;
import de.oetting.bumpingbunnies.worldcreator.load.gameObjects.ObjectsFactory;
import de.oetting.bumpingbunnies.worldcreatorPc.load.XmlBuilder;

public class ViewerPanel extends JPanel {

	private static final Logger LOGGER = LoggerFactory.getLogger(ViewerPanel.class);
	private static final long serialVersionUID = 1L;
	private final EditorModel model;
	private MyCanvas myCanvas;
	private final XmlBuilder builder;
	private File lastFile;
	private JList<Wall> wall;
	private JList<IcyWall> icyWallList;
	private JList<Jumper> jumpersList;
	private JList<Water> watersList;
	private JList<SpawnPoint> spawns;
	private JList<Background> backgrounds;
	private EditingModePanel editingModePanel;
	private JFrame frame;
	private ViewableItemsPanel viewableItemsPanel;
	private PropertiesPanel propertiesPanel;

	public ViewerPanel(String file) {
		this.lastFile = new File(file);
		this.builder = new XmlBuilder();
		this.model = new EditorModel(new World(new WorldProperties()));
	}

	public ViewerPanel() {
		this.builder = new XmlBuilder();
		this.model = new EditorModel(new World(new WorldProperties()));
	}

	public void build() {
		setLayout(createLayout());
		parseFile();
		this.myCanvas = new MyCanvas(this.model);
		add(createLeftButtons(), BorderLayout.LINE_START);
		add(new JScrollPane(this.myCanvas), BorderLayout.CENTER);
		add(createRightBox(), BorderLayout.LINE_END);
		add(createBottomImages(), BorderLayout.PAGE_END);
		activateNewEditingMode();
		selectViewableCheckboxes();
	}

	private BorderLayout createLayout() {
		BorderLayout borderLayout = new BorderLayout();
		borderLayout.setHgap(10);
		return borderLayout;
	}

	private Component createLeftButtons() {
		Box box = Box.createVerticalBox();
		editingModePanel = new EditingModePanel();
		editingModePanel.addModeClickListener((event) -> activateNewEditingMode());
		box.add(editingModePanel);
		box.add(createUndoButton());
		box.add(createRedoButton());
		return box;
	}

	private Component createUndoButton() {
		JButton button = new JButton(loadImage("/images/undo.png"));
		button.addActionListener(event -> restorePreviousWorldState());
		return button;
	}

	private Component createRedoButton() {
		JButton button = new JButton(loadImage("/images/redo.png"));
		button.addActionListener(event -> restoreNextWorldState());
		return button;
	}

	private ImageIcon loadImage(String path) {
		try {
			return new ImageIcon(ImageIO.read(getClass().getResourceAsStream(path)));
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	private void restorePreviousWorldState() {
		model.restorePreviousState();
		refreshView();
	}

	private void restoreNextWorldState() {
		model.restoreNextState();
		refreshView();
	}

	private void activateNewEditingMode() {
		removeExistingMouseListeners();
		ModeMouseListener ml = findCurrentModeMouseListener();
		this.myCanvas.addMouseListener(ml);
		this.myCanvas.addMouseMotionListener(ml);
	}

	private JComponent createBottomImages() {
		ImagesPanel panel = new ImagesPanel(this.myCanvas, model);
		return panel.build();
	}

	private Box createRightBox() {
		Box box = new Box(BoxLayout.Y_AXIS);
		box.add(createTopPanel());
		addLists(box);
		return box;
	}

	private JComponent createTopPanel() {
		JComponent panel = new JPanel(new GridLayout(3, 1));
		panel.add(createButtons());
		panel.add(createVisibleButtons());
		panel.add(createPropertiesPanel());
		return panel;
	}

	private Component createPropertiesPanel() {
		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder("Properties"));
		propertiesPanel = new PropertiesPanel(this);
		panel.add(propertiesPanel.buildDialog());
		return panel;
	}

	private JComponent createButtons() {
		Box box = Box.createHorizontalBox();
		box.add(createLoadButton());
		box.add(createRefreshButton());
		box.add(createSaveButton());
		box.add(createSaveAsButton());
		box.add(createRoundButton());
		box.add(createCleanButton());
		return box;
	}

	private Component createVisibleButtons() {
		viewableItemsPanel = new ViewableItemsPanel(this);
		return viewableItemsPanel.build();
	}

	private Component createCleanButton() {
		JButton button = new JButton("Clean");
		button.addActionListener((event) -> clean());
		return button;
	}

	private void clean() {
		Collection<GameObjectWithImage> allObjects = getAllObjectsToClean();
		JOptionPane.showMessageDialog(this, "Cleaning " + allObjects.size() + " elements");
		model.getCurrentState().removeAll(allObjects);
		refreshView();
	}

	private Set<GameObjectWithImage> getAllObjectsToClean() {
		Set<GameObjectWithImage> hiddenAllObjects = getCurrentWorld().getAllObjects().stream()
				.filter(object -> isHidden(object)).collect(Collectors.toSet());
		Set<GameObjectWithImage> hiddenDrawnObjects = getCurrentWorld().getAllDrawingObjects().stream()
				.filter(object -> isHidden(object)).collect(Collectors.toSet());
		Set<GameObjectWithImage> sets = new HashSet<>();
		sets.addAll(hiddenDrawnObjects);
		sets.addAll(hiddenAllObjects);
		return sets;
	}

	private boolean isHidden(GameObjectWithImage object) {
		return isHiddenInList(object, getCurrentWorld().getAllObjects())
				|| isHiddenInList(object, getCurrentWorld().getAllDrawingObjects());
	}

	private boolean isHiddenInList(GameObjectWithImage object, List<GameObjectWithImage> list) {
		for (GameObjectWithImage otherObject : list) {
			if (otherObject != object) {
				if (otherObject.maxX() >= object.maxX() && otherObject.minX() <= object.minX()
						&& otherObject.minY() <= object.minY() && otherObject.maxY() >= object.maxY()) {
					if (otherObject.getZIndex() > object.getZIndex())
						return true;
				}
			}
		}
		return false;
	}

	private Component createRoundButton() {
		JButton button = new JButton("Round");
		button.addActionListener((event) -> round());
		return button;
	}

	private void round() {
		this.getCurrentWorld().getAllObjects().stream().forEach((object) -> round(object));
		myCanvas.repaint();
	}

	private void round(GameObject go) {
		go.setMinX(findRoundedValueX(go.minX()));
		if (findRoundedValueX(go.minX()) == findRoundedValueX(go.maxX()))
			go.setMaxX(go.minX() + ModelConstants.MAX_VALUE / 100);
		else
			go.setMaxX(findRoundedValueX(go.maxX()));
		go.setMinY(findRoundedValueY(go.minY()));
		if (findRoundedValueY(go.minY()) == findRoundedValueY(go.maxY()))
			go.setMaxY(go.minY() + ModelConstants.MAX_VALUE / 100);
		else
			go.setMaxY(findRoundedValueY(go.maxY()));
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
		configureList(wall);
		return this.wall;
	}

	private void setWallModel() {
		setObjectsModel(getCurrentWorld().getAllWalls(), wall);
	}

	public <S extends GameObject> void setObjectsModel(List<S> objects, JList<S> list) {
		final MyListModel<S> defaultListModel = new MyListModel<>(objects);
		list.setModel(defaultListModel);
	}

	private <S extends GameObjectWithImage> void configureList(JList<S> list) {
		list.setCellRenderer(new GameObjectRenderer());
		list.addListSelectionListener(new SelectionToCanvasSynchronizer(this.myCanvas));
		list.addMouseListener(new ListMouseAdapter<S>(this, list));
		list.addFocusListener(new FocusListener() {
			@Override
			public void focusLost(FocusEvent e) {
				list.clearSelection();
			}

			@Override
			public void focusGained(FocusEvent e) {
			}
		});
	}

	private JList<IcyWall> createIceWallList() {
		this.icyWallList = new JList<>();
		setIcyWallModel();
		configureList(icyWallList);
		return this.icyWallList;
	}

	private void setIcyWallModel() {
		setObjectsModel(getCurrentWorld().getAllIcyWalls(), icyWallList);
	}

	private JList<Jumper> createJumperList() {
		this.jumpersList = new JList<>();
		setJumperModel();
		configureList(jumpersList);
		return this.jumpersList;
	}

	private void setJumperModel() {
		setObjectsModel(getCurrentWorld().getAllJumper(), jumpersList);
	}

	private JList<Water> createWatersList() {
		this.watersList = new JList<>();
		setWaterModel();
		configureList(watersList);
		return this.watersList;
	}

	private void setWaterModel() {
		setObjectsModel(getCurrentWorld().getAllWaters(), watersList);
	}

	private JList<SpawnPoint> createSpawnList() {
		this.spawns = new JList<>();
		this.spawns.addMouseListener(new SpawnListAdapter(this, spawns));
		this.spawns.addListSelectionListener(new SelectionToCanvasSynchronizer(this.myCanvas));
		setSpawnModel();
		return this.spawns;
	}

	private JList<Background> createBackgroundsList() {
		this.backgrounds = new JList<>();
		setBackgroundsModel();
		configureList(backgrounds);
		return this.backgrounds;
	}

	private void setSpawnModel() {
		MyListModel<SpawnPoint> defaultListModel = new MyListModel<>(getCurrentWorld().getSpawnPoints());
		this.spawns.setCellRenderer(new SpawnpointRender());
		this.spawns.setModel(defaultListModel);
	}

	private void setBackgroundsModel() {
		setObjectsModel(getCurrentWorld().getBackgrounds(), backgrounds);
	}

	private JButton createRefreshButton() {
		JButton button = new JButton("Reload");
		button.addActionListener((event) -> askForRefresh());
		return button;
	}

	private void askForRefresh() {
		if (lastFile != null) {
			int showConfirmDialog = JOptionPane.showConfirmDialog(this,
					"Are you sure that you want to discard all current changes?", "Refresh", JOptionPane.YES_NO_OPTION);
			if (showConfirmDialog == JOptionPane.YES_OPTION)
				displayFile();
		}
	}

	private JButton createLoadButton() {
		JButton button = new JButton("Load");
		button.addActionListener((event) -> loadFilecontent());
		return button;
	}

	private void displayFile() {
		parseFile();
		this.myCanvas.setModel(this.model);
		setWallModel();
		setIcyWallModel();
		setJumperModel();
		setWaterModel();
		setSpawnModel();
		setBackgroundsModel();
		activateNewEditingMode();
		this.myCanvas.repaint();
		selectViewableCheckboxes();
	}

	private void selectViewableCheckboxes() {
		viewableItemsPanel.selectAllCheckboxes();
	}

	private void parseFile() {
		if (lastFile != null) {
			try {
				if (lastFile.getName().endsWith("xml"))
					parseXml();
				else
					parseZip();
			} catch (Exception e) {
				LOGGER.error("Error", e);
				model.clear();
				JOptionPane.showMessageDialog(this, "Error: " + e.getMessage());
			}
		}
	}

	private void parseZip() {
		try {
			if (this.lastFile != null) {
				ZipInputStream zipInput = new ZipInputStream(new FileInputStream(lastFile));
				parseZip(zipInput);
				zipInput.close();
			}
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	private void parseZip(ZipInputStream zipInput) {
		try {
			ZipEntry entry = zipInput.getNextEntry();
			while (entry != null) {
				if (entry.getName().equals("world.xml"))
					this.model.loadNewWorld(builder.parse(new NonClosingInputstream(zipInput), "files"));
				entry = zipInput.getNextEntry();
			}
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	private void loadFilecontent() {
		JFileChooser dialog = new JFileChooser(lastFile);
		dialog.setFileFilter(new FileNameExtensionFilter("All", "zip", "xml"));
		dialog.addChoosableFileFilter(new FileNameExtensionFilter("zip", "zip"));
		dialog.showOpenDialog(this);
		this.lastFile = dialog.getSelectedFile();
		if (dialog.getSelectedFile() != null)
			try {
				displayFile();
			} catch (Exception e) {
				LOGGER.error("error", e);
				JOptionPane.showMessageDialog(this, "error while loading file " + e.getMessage());
			}
	}

	private void parseXml() {
		try {
			if (this.lastFile != null) {
				World world = this.builder.parse(new FileInputStream(this.lastFile), "files");
				this.model.loadNewWorld(world);
			}
		} catch (FileNotFoundException e) {
			throw new RuntimeException(e);
		}
	}

	private JButton createSaveButton() {
		JButton button = new JButton("Save");
		button.addActionListener((event) -> save());
		return button;
	}

	private JButton createSaveAsButton() {
		JButton button = new JButton("Save as");
		button.addActionListener((event) -> saveAs());
		return button;
	}

	private ModeMouseListener findCurrentModeMouseListener() {
		if (editingModePanel.isSelectModeActive())
			return new SelectModeMouseListener(createSelectionModeProvider(), this);
		else if (editingModePanel.isDeleteModeActive())
			return new DeleteModeMouseListener(createSelectionModeProvider(), new CanvasObjectsFinder(
					createSelectionModeProvider()));
		else if (editingModePanel.isWallModeActive()) {
			return new CreateWallEditingMode(createSelectionModeProvider(),
					(minX, minY, maxX, maxY) -> ObjectsFactory.createWall(minX, minY, maxX, maxY), myCanvas);
		} else if (editingModePanel.isIceWallModeActive()) {
			return new CreateIceWallEditingMode(createSelectionModeProvider(),
					(minX, minY, maxX, maxY) -> ObjectsFactory.createIceWall(minX, minY, maxX, maxY), myCanvas);
		} else if (editingModePanel.isJumperModeActive()) {
			return new CreateJumperEditingMode(createSelectionModeProvider(),
					(minX, minY, maxX, maxY) -> ObjectsFactory.createJumper(minX, minY, maxX, maxY), myCanvas);
		} else if (editingModePanel.isWaterModeActive()) {
			return new CreateWaterEditingMode(createSelectionModeProvider(),
					(minX, minY, maxX, maxY) -> ObjectsFactory.createWater(minX, minY, maxX, maxY), myCanvas);
		} else if (editingModePanel.isBackgroundModeActive())
			return new CreateBackgroundEditingMode(createSelectionModeProvider(),
					(minX, minY, maxX, maxY) -> ObjectsFactory.createBackground(minX, minY, maxX, maxY), myCanvas);
		return new SelectModeMouseListener(createSelectionModeProvider(), this);
	}

	private SelectionModeProvider createSelectionModeProvider() {
		return new DefaultSelectionModeProvider(model, myCanvas, this);
	}

	private void removeExistingMouseListeners() {
		for (MouseListener ml : this.myCanvas.getMouseListeners()) {
			this.myCanvas.removeMouseListener(ml);
		}
		for (MouseMotionListener ml : this.myCanvas.getMouseMotionListeners()) {
			this.myCanvas.removeMouseMotionListener(ml);
		}
	}

	private void save() {
		try {
			if (lastFile != null)
				saveLevel();
		} catch (Exception e) {
			LOGGER.error("error", e);
			JOptionPane.showMessageDialog(ViewerPanel.this, "An error occured: " + e.getMessage());
		}
	}

	private void saveAs() {
		try {
			JFileChooser dialog = new JFileChooser(lastFile);
			dialog.setFileFilter(new FileNameExtensionFilter("Zip", "zip"));
			dialog.showSaveDialog(this);
			if (dialog.getSelectedFile() != null) {
				this.lastFile = dialog.getSelectedFile();
				saveLevel();
			}
		} catch (Exception e) {
			LOGGER.error("error", e);
			JOptionPane.showMessageDialog(ViewerPanel.this, "An error occured: " + e.getMessage());
		}
	}

	private void saveLevel() throws IOException {
		File newFile = lastFile;
		newFile.delete();
		newFile.createNewFile();
		if (newFile.getName().endsWith("xml"))
			new XmlStorer(getCurrentWorld()).saveXml(newFile);
		else
			new LevelStorer(new XmlStorer(getCurrentWorld())).storeLevel(newFile, getCurrentWorld());
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
		lModel.fireContentsChanged(this, 0, lModel.getSize() - 1);
	}

	public void setFrame(JFrame frame) {
		this.frame = frame;
	}

	public JFrame getFrame() {
		return frame;
	}

	public void repaintCanvas() {
		myCanvas.repaint();
	}

	public String getLastFileName() {
		if (lastFile != null) {
			return lastFile.getPath();
		}
		return null;
	}

	public World getCurrentWorld() {
		return model.getCurrentState();
	}

	public void refreshView() {
		refreshTables();
		repaintCanvas();
		propertiesPanel.updateMasks();
	}

	public void setSelectedObject(GameObjectWithImage object) {
		propertiesPanel.setSelectedObject(object);
	}

}
