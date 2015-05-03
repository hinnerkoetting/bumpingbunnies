package de.oetting.bumpingbunnies.leveleditor.viewer.editingMode;

import java.awt.Component;
import java.awt.Cursor;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import javax.swing.JFrame;

import de.oetting.bumpingbunnies.core.game.graphics.calculation.CoordinatesCalculation;
import de.oetting.bumpingbunnies.core.world.World;
import de.oetting.bumpingbunnies.model.game.objects.GameObjectWithImage;

public interface SelectionModeProvider {

	World getWorld();

	void refreshTables();

	void repaintCanvas();

	List<GameObjectWithImage> getCurrentSelectedObjects();

	void setCanvasCursor(Cursor cursor);

	void setSelectedObject(Optional<? extends GameObjectWithImage> go);

	Collection<GameObjectWithImage> getAllDrawingObjects();

	Component getCanvas();

	CoordinatesCalculation createCoordinatesCalculation();

	void refreshView();
	
	void refreshViewAndStoreState();

	JFrame getFrame();

	int getMaxZIndexValue();

	void addSelectedObject(Optional<? extends GameObjectWithImage> go);

	void addSelectedObjects(List<GameObjectWithImage> allSelectedObjects);

	void setSelectedObjects(List<GameObjectWithImage> allSelectedObjects);
	
	void storeCurrentState();
}
