package de.jumpnbump.usecases.viewer.viewer.editingMode;

import java.awt.Component;
import java.awt.Cursor;
import java.util.Collection;
import java.util.Optional;

import de.oetting.bumpingbunnies.core.world.World;
import de.oetting.bumpingbunnies.model.game.objects.GameObject;
import de.oetting.bumpingbunnies.model.game.objects.GameObjectWithImage;

public interface SelectionModeProvider {

	World getWorld();

	void refreshTables();

	void repaintCanvas();

	Optional<? extends GameObject> getCurrentSelectedObject();

	void setCanvasCursor(Cursor cursor);

	void setSelectedObject(Optional<? extends GameObjectWithImage> go);

	Collection<GameObjectWithImage> getAllDrawingObjects();

	Component getCanvas();
}