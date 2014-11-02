package de.jumpnbump.usecases.viewer.viewer.editingMode;

import de.oetting.bumpingbunnies.model.game.objects.GameObject;

public interface EditorObjectFactory<S extends GameObject> {

	S create(long minX, long minY, long maxX, long maxY);
}
