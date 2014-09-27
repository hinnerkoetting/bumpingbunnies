package de.oetting.bumpingbunnies.pc.graphics.drawables.factory;

import de.oetting.bumpingbunnies.core.game.graphics.Drawable;
import de.oetting.bumpingbunnies.core.game.graphics.RectDrawer;
import de.oetting.bumpingbunnies.core.game.graphics.factory.GameObjectDrawableFactory;
import de.oetting.bumpingbunnies.usecases.game.model.GameObject;

public class PcGameObjectDrawableFactory implements GameObjectDrawableFactory {
	@Override
	public Drawable create(GameObject object, int screenWidth, int screenHeight) {
		return new RectDrawer(object);
	}

}
