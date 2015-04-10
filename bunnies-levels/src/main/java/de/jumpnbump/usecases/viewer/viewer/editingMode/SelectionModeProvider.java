package de.jumpnbump.usecases.viewer.viewer.editingMode;

import java.awt.Component;
import java.awt.Cursor;
import java.util.Collection;
import java.util.Optional;

import javax.swing.JFrame;

import de.oetting.bumpingbunnies.core.game.graphics.calculation.CoordinatesCalculation;
import de.oetting.bumpingbunnies.core.world.World;
import de.oetting.bumpingbunnies.model.game.objects.GameObject;
import de.oetting.bumpingbunnies.model.game.objects.GameObjectWithImage;

public interface SelectionModeProvider {

	World getWorld();

	void refreshTables();

	void repaintCanvas();

	Optional<? extends GameObject> getCurrentSelectedObject();

	void setCanvasCursor(Cursor cursor);

	void setSelectedObject(Optional<? extends GameObject> go);

	Collection<GameObjectWithImage> getAllDrawingObjects();

	Component getCanvas();

	CoordinatesCalculation createCoordinatesCalculation();

	void refreshAll();

	JFrame getFrame();

	int getMaxZIndexValue();
}
