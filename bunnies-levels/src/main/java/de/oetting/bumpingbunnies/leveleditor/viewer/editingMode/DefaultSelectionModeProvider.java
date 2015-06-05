package de.oetting.bumpingbunnies.leveleditor.viewer.editingMode;

import java.awt.Component;
import java.awt.Cursor;
import java.util.Collection;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import javax.swing.JFrame;

import de.oetting.bumpingbunnies.core.game.graphics.calculation.CoordinatesCalculation;
import de.oetting.bumpingbunnies.leveleditor.MyCanvas;
import de.oetting.bumpingbunnies.leveleditor.viewer.EditorModel;
import de.oetting.bumpingbunnies.leveleditor.viewer.ViewerPanel;
import de.oetting.bumpingbunnies.model.game.objects.Background;
import de.oetting.bumpingbunnies.model.game.objects.FixedWorldObject;
import de.oetting.bumpingbunnies.model.game.objects.GameObjectWithImage;
import de.oetting.bumpingbunnies.model.game.objects.IcyWall;
import de.oetting.bumpingbunnies.model.game.objects.Jumper;
import de.oetting.bumpingbunnies.model.game.objects.Wall;
import de.oetting.bumpingbunnies.model.game.objects.Water;
import de.oetting.bumpingbunnies.model.game.world.World;

public class DefaultSelectionModeProvider implements SelectionModeProvider {

	private EditorModel model;
	private final MyCanvas canvas;
	private final ViewerPanel panel;

	public DefaultSelectionModeProvider(EditorModel model, MyCanvas canvas, ViewerPanel panel) {
		this.model = model;
		this.canvas = canvas;
		this.panel = panel;
	}

	@Override
	public World getWorld() {
		return model.getCurrentState();
	}

	@Override
	public void refreshTables() {
		panel.refreshTables();
	}

	@Override
	public void repaintCanvas() {
		canvas.repaint();
	}

	@Override
	public List<GameObjectWithImage> getCurrentSelectedObjects() {
		return canvas.getSelectedGameObjects();
	}

	@Override
	public void setCanvasCursor(Cursor cursor) {
		if (canvas.getCursor().getType() != cursor.getType())
			canvas.setCursor(cursor);
	}

	@Override
	public void setSelectedObject(Optional<? extends GameObjectWithImage> go) {
		canvas.setSelectedObject(go.isPresent() ? go.get() : null);
		panel.setSelectedObject(go.isPresent() ? go.get() : null);
		refreshView();
	}

	private void refreshAndStoreState() {
		refreshView();
		model.storeState();
	}

	@Override
	public Collection<GameObjectWithImage> getAllDrawingObjects() {
		return model.getCurrentState().getAllDrawingObjects();
	}

	@Override
	public Component getCanvas() {
		return canvas;
	}

	@Override
	public CoordinatesCalculation createCoordinatesCalculation() {
		return canvas.getCoordinatesCalculation();
	}

	@Override
	public void refreshViewAndStoreState() {
		refreshAndStoreState();
	}

	@Override
	public JFrame getFrame() {
		return panel.getFrame();
	}

	@Override
	public int getMaxZIndexValue() {
		int max = 0;
		List<GameObjectWithImage> allDrawingObjects = model.getCurrentState().getAllDrawingObjects();
		for (GameObjectWithImage go : allDrawingObjects) {
			if (go instanceof FixedWorldObject) {
				max = Math.max(((FixedWorldObject) go).getzIndex(), max);
			}
		}
		return max;
	}

	@Override
	public void addSelectedObject(Optional<? extends GameObjectWithImage> go) {
		go.ifPresent(object -> canvas.addSelectedObect(object));
		refreshAndStoreState();
	}

	@Override
	public void addSelectedObjects(List<GameObjectWithImage> allSelectedObjects) {
		allSelectedObjects.forEach(object -> canvas.addSelectedObect(object));
		refreshAndStoreState();
	}

	@Override
	public void setSelectedObjects(List<GameObjectWithImage> allSelectedObjects) {
		canvas.setSelectedObject(null);
		allSelectedObjects.forEach(object -> canvas.addSelectedObect(object));
		refreshAndStoreState();
	}

	public void restorePreviousState() {
		model.restorePreviousState();
		refreshView();
	}

	@Override
	public void storeCurrentState() {
		model.storeState();
	}

	@Override
	public void refreshView() {
		panel.refreshView();
	}
}
