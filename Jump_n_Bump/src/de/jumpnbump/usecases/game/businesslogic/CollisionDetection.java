package de.jumpnbump.usecases.game.businesslogic;

import de.jumpnbump.usecases.game.model.GameObject;
import de.jumpnbump.usecases.game.model.World;

public class CollisionDetection implements GameScreenSizeChangeListener {

	private final World world;
	private int gameWidth;
	private int gameHeight;

	public CollisionDetection(World world) {
		this.world = world;
	}

	public boolean coolidesWithAnything(GameObject gameobject) {
		if (gameobject.minX() <= 0 || gameobject.minY() <= 0) {
			return true;
		}
		if (gameobject.maxX() >= this.gameWidth
				|| gameobject.maxY() >= this.gameHeight) {
			return true;
		}
		return false;
	}

	@Override
	public void setNewSize(int width, int height) {
		this.gameWidth = width;
		this.gameHeight = height;
	}
}
