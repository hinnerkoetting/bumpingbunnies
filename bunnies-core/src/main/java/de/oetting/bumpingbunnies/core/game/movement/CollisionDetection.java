package de.oetting.bumpingbunnies.core.game.movement;

import de.oetting.bumpingbunnies.core.world.ObjectProvider;
import de.oetting.bumpingbunnies.usecases.game.model.GameObject;
import de.oetting.bumpingbunnies.usecases.game.model.Player;

public class CollisionDetection {

	private final ObjectProvider world;

	public CollisionDetection(ObjectProvider world) {
		this.world = world;
	}

	public boolean objectStandsOnGround(Player gameobject) {
		return findObjectThisPlayerIsStandingOn(gameobject) != null;
	}

	public boolean standsOn(GameObject upperObject, GameObject lowerObject) {
		if (upperObject.minY() == lowerObject.maxY()) {
			if (upperObject.maxX() <= lowerObject.minX()) {
				return false;
			}
			if (upperObject.minX() >= lowerObject.maxX()) {
				return false;
			}
			return true;
		}
		return false;
	}

	public GameObject findObjectThisPlayerIsStandingOn(Player player) {
		for (GameObject go : this.world.getAllObjects()) {
			if (standsOn(player, go)) {
				if (go instanceof Player) {
					Player p = (Player) go;
					return p.isDead() ? null : p;
				}
				return go;
			}
		}
		return null;
	}

	public Player findPlayerThisPlayerIsStandingOn(Player player) {
		for (Player p : this.world.getAllPlayer()) {
			if (!p.isDead() && standsOn(player, p)) {
				return p;
			}
		}
		return null;
	}

	public GameObject findObjectThisPlayerIsCollidingWith(Player player) {
		for (GameObject go : this.world.getAllObjects()) {
			if (collides(player, go)) {
				return go;
			}
		}
		return null;
	}

	public boolean collidesWithRight(GameObject player, GameObject object) {
		return SingleCollisionDetection.collidesObjectOnRight(player, object);
	}

	public boolean collidesWithLeft(GameObject player, GameObject object) {
		return SingleCollisionDetection.collidesObjectOnLeft(player, object);
	}

	public boolean collidesWithBottom(GameObject player, GameObject object) {
		return SingleCollisionDetection.collidesObjectOnBottom(player, object);
	}

	public boolean collidesWithTop(GameObject player, GameObject object) {
		return SingleCollisionDetection.collidesObjectOnTop(player, object);
	}

	public boolean isExactlyUnderObject(GameObject gameObject, GameObject other) {
		return gameObject.minY() == other.maxY();
	}

	public boolean isOverOrUnderObject(GameObject gameObject, GameObject other) {
		return gameObject.minY() >= other.maxY() || gameObject.maxY() <= other.minY();
	}

	public boolean isLeftOrRightToObject(GameObject targetObject, GameObject other) {
		return targetObject.minX() >= other.maxX() || targetObject.maxX() <= other.minX();
	}

	public boolean collides(GameObject gameObject, GameObject other) {
		boolean collidesX = sharesHorizontalPosition(gameObject, other);
		boolean collidesY = sharesVerticalPosition(gameObject, other);
		return collidesX && collidesY;
	}

	/**
	 * This does not mean that vertical position is the same.
	 */
	public boolean sharesHorizontalPosition(GameObject gameObject, GameObject other) {
		return gameObject.maxX() > other.minX() && gameObject.minX() < other.maxX();
	}

	/**
	 * This does not mean that horizontal position is the same.
	 */
	public boolean sharesVerticalPosition(GameObject gameObject, GameObject other) {
		return gameObject.maxY() > other.minY() && gameObject.minY() < other.maxY();
	}

	public boolean isExactlyOverObject(GameObject gameObject, GameObject other) {
		return gameObject.maxY() == other.minY();
	}

	public boolean touches(GameObject square1, GameObject square2) {
		boolean touchesX = touchesHorizontal(square1, square2) && sharesVerticalPosition(square1, square2);
		boolean touchesY = touchesVertical(square1, square2) && sharesHorizontalPosition(square1, square2);
		return touchesX || touchesY;
	}

	public boolean touchesHorizontal(GameObject gameObject, GameObject other) {
		return gameObject.maxX() == other.minX() || gameObject.minX() == other.maxX();
	}

	public boolean touchesVertical(GameObject gameObject, GameObject other) {
		return gameObject.maxY() == other.minY() || gameObject.minY() == other.maxY();
	}
}
