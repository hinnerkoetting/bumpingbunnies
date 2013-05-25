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

	public boolean willCollideVertical(GameObject gameobject) {
		boolean willCollideTop = willCollideTop(gameobject);
		boolean willCollideBottom = willCollideBottom(gameobject);
		return willCollideBottom || willCollideTop;
	}

	public boolean willCollideBottom(GameObject gameobject) {
		boolean willCollideBottom = collidesWithBottomScreen(gameobject)
				&& gameobject.movementY() > 0;
		return willCollideBottom;
	}

	public boolean willCollideTop(GameObject gameobject) {
		boolean willCollideTop = collidesWithTopScreen(gameobject)
				&& gameobject.movementY() < 0;
		return willCollideTop;
	}

	public boolean willCollideHorizontal(GameObject gameobject) {
		boolean willCollideLeft = collidedWithLeftScreen(gameobject)
				&& gameobject.movementX() < 0;
		boolean willCollideRight = collidesWithRightScreen(gameobject)
				&& gameobject.movementX() > 0;
		return willCollideLeft || willCollideRight;
	}

	public boolean collidesVertical(GameObject gameobject) {
		return collidesWithBottomScreen(gameobject)
				|| collidesWithTopScreen(gameobject);
	}

	public boolean collidesHorizontal(GameObject gameobject) {
		return collidedWithLeftScreen(gameobject)
				|| collidesWithRightScreen(gameobject);
	}

	public boolean coolidesWithAnything(GameObject gameobject) {
		if (collidesHorizontal(gameobject)) {
			return true;
		}
		if (collidesVertical(gameobject)) {
			return true;
		}
		return false;
	}

	public boolean collidesWithBottomScreen(GameObject gameobject) {
		return gameobject.maxY() >= 1;
	}

	public boolean collidesWithRightScreen(GameObject gameobject) {
		return gameobject.maxX() >= 1;
	}

	private boolean collidesWithTopScreen(GameObject gameobject) {
		return gameobject.minY() <= 0;
	}

	private boolean collidedWithLeftScreen(GameObject gameobject) {
		return gameobject.minX() <= 0;
	}

	@Override
	public void setNewSize(int width, int height) {
		this.gameWidth = width;
		this.gameHeight = height;
	}

	public boolean objectStandsOnGround(GameObject gameobject) {
		return collidesWithBottomScreen(gameobject);
	}
}
