package de.jumpnbump.usecases.viewer.viewer.editingMode;

import java.awt.Component;
import java.awt.Cursor;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import javax.swing.JFrame;

import de.jumpnbump.usecases.viewer.MyCanvas;
import de.jumpnbump.usecases.viewer.viewer.ViewerPanel;
import de.oetting.bumpingbunnies.core.game.graphics.calculation.AbsoluteCoordinatesCalculation;
import de.oetting.bumpingbunnies.core.game.graphics.calculation.CoordinatesCalculation;
import de.oetting.bumpingbunnies.core.world.World;
import de.oetting.bumpingbunnies.model.game.objects.FixedWorldObject;
import de.oetting.bumpingbunnies.model.game.objects.GameObjectWithImage;
import de.oetting.bumpingbunnies.model.game.world.WorldProperties;

public class DefaultSelectionModeProvider implements SelectionModeProvider {

	private final World world;
	private final MyCanvas canvas;
	private final ViewerPanel panel;
	
	public DefaultSelectionModeProvider(World world, MyCanvas canvas, ViewerPanel panel) {
		this.world = world;
		this.canvas = canvas;
		this.panel = panel;
	}

	@Override
	public World getWorld() {
		return world;
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
		canvas.setCursor(cursor);
	}

	@Override
	public void setSelectedObject(Optional<? extends GameObjectWithImage> go) {
		if (go.isPresent())
			canvas.setSelectedObject(go.get());
		else
			canvas.setSelectedObject(null);
		refresh();
	}

	private void refresh() {
		repaintCanvas();
		refreshTables();
	}

	@Override
	public Collection<GameObjectWithImage> getAllDrawingObjects() {
		return world.getAllDrawingObjects();
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
	public void refreshAll() {
		refresh();
	}

	@Override
	public JFrame getFrame() {
		return panel.getFrame();
	}

	@Override
	public int getMaxZIndexValue() {
		int max = 0;
		List<GameObjectWithImage> allDrawingObjects = world.getAllDrawingObjects();
		for (GameObjectWithImage go: allDrawingObjects) {
			if (go instanceof FixedWorldObject) {
				max = Math.max(((FixedWorldObject) go).getzIndex(), max);
			}
		}
		return max;
	}

	@Override
	public void addSelectedObject(Optional<? extends GameObjectWithImage> go) {
		go.ifPresent(object -> canvas.addSelectedObect(object));
		refresh();
	}

	@Override
	public void addSelectedObjects(List<GameObjectWithImage> allSelectedObjects) {
		allSelectedObjects.forEach(object -> canvas.addSelectedObect(object));
		refresh();
	}

	@Override
	public void setSelectedObjects(List<GameObjectWithImage> allSelectedObjects) {
		canvas.setSelectedObject(null);
		allSelectedObjects.forEach(object -> canvas.addSelectedObect(object));
		refresh();
	}
}
