package de.jumpnbump.usecases.game.businesslogic;

import de.jumpnbump.logger.Logger;
import de.jumpnbump.logger.MyLog;
import de.jumpnbump.usecases.game.model.GameObject;
import de.jumpnbump.usecases.game.model.World;

public class CollisionDetection implements GameScreenSizeChangeListener {

	private static final MyLog LOGGER = Logger
			.getLogger(CollisionDetection.class);
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
		boolean willCollideBottom = collidesWithBottom(gameobject
				.simulateNextStepY()) && gameobject.movementY() > 0;
		return willCollideBottom;
	}

	public boolean willCollideTop(GameObject gameobject) {
		boolean willCollideTop = collidesWithTop(gameobject.simulateNextStepY())
				&& gameobject.movementY() < 0;
		return willCollideTop;
	}

	public boolean willCollideHorizontal(GameObject gameobject) {
		boolean willCollideLeft = willCollideLeft(gameobject);
		boolean willCollideRight = willCollideRight(gameobject);
		LOGGER.info("left " + willCollideLeft + " - right " + willCollideRight);
		return willCollideLeft || willCollideRight;
	}

	private boolean willCollideRight(GameObject gameobject) {
		return collidesWithRight(gameobject.simulateNextStepX())
				&& gameobject.movementX() > 0;
	}

	private boolean willCollideLeft(GameObject gameobject) {
		return collidedWithLeft(gameobject.simulateNextStepX())
				&& gameobject.movementX() < 0;
	}

	public boolean collidesVertical(GameObject gameobject) {
		return collidesWithBottom(gameobject) || collidesWithTop(gameobject);
	}

	public boolean collidesHorizontal(GameObject gameobject) {
		return collidedWithLeft(gameobject) || collidesWithRight(gameobject);
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

	public boolean collidesWithBottom(GameObject objectToBeChecked) {
		for (GameObject otherObject : this.world.getAllObjects()) {
			if (SingleCollisionDetection.collidesObjectOnBottom(
					objectToBeChecked, otherObject)) {
				return true;
			}
		}
		return objectToBeChecked.maxY() >= 1;
	}

	public boolean collidesWithRight(GameObject gameobject) {
		for (GameObject otherObject : this.world.getAllObjects()) {
			if (otherObject.id() != gameobject.id()) {
				if (SingleCollisionDetection.collidesObjectOnRight(gameobject,
						otherObject)) {
					return true;
				}
			}
		}
		return gameobject.maxX() >= 1;
	}

	private boolean collidesWithTop(GameObject objectToBeChecked) {
		for (GameObject otherObject : this.world.getAllObjects()) {
			if (otherObject.id() != objectToBeChecked.id()) {
				if (SingleCollisionDetection.collidesObjectOnTop(otherObject,
						objectToBeChecked)) {
					return true;
				}
			}
		}
		return objectToBeChecked.minY() <= 0;
	}

	private boolean collidedWithLeft(GameObject gameobject) {
		for (GameObject otherObject : this.world.getAllObjects()) {
			if (otherObject.id() != gameobject.id()) {
				if (SingleCollisionDetection.collidesObjectOnLeft(gameobject,
						otherObject)) {
					return true;
				}
			}
		}
		return gameobject.minX() <= 0;
	}

	@Override
	public void setNewSize(int width, int height) {
		this.gameWidth = width;
		this.gameHeight = height;
	}

	public boolean objectStandsOnGround(GameObject gameobject) {
		return collidesWithBottom(gameobject.simulateNextStepY());
	}
}
