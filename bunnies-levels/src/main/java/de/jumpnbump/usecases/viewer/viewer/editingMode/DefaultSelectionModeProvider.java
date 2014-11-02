package de.jumpnbump.usecases.viewer.viewer.editingMode;

import java.awt.Component;
import java.awt.Cursor;
import java.util.Collection;
import java.util.Optional;

import de.jumpnbump.usecases.viewer.MyCanvas;
import de.jumpnbump.usecases.viewer.viewer.ViewerPanel;
import de.oetting.bumpingbunnies.core.game.graphics.calculation.AbsoluteCoordinatesCalculation;
import de.oetting.bumpingbunnies.core.game.graphics.calculation.CoordinatesCalculation;
import de.oetting.bumpingbunnies.core.world.World;
import de.oetting.bumpingbunnies.model.game.objects.GameObject;
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
	public Optional<? extends GameObject> getCurrentSelectedObject() {
		GameObject selectedObject = canvas.getSelectedGameObject();
		return Optional.ofNullable((GameObject) selectedObject);
	}

	@Override
	public void setCanvasCursor(Cursor cursor) {
		canvas.setCursor(cursor);
	}

	@Override
	public void setSelectedObject(Optional<? extends GameObject> go) {
		if (go.isPresent())
			canvas.setSelectedObject(go.get());
		else
			canvas.setSelectedObject(null);
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
		WorldProperties properties = new WorldProperties();
		return new AbsoluteCoordinatesCalculation(canvas.getWidth(), canvas.getHeight(), properties);
	}

}
