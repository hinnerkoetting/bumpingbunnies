package de.jumpnbump.usecases.game.businesslogic;

import de.jumpnbump.logger.Logger;
import de.jumpnbump.logger.MyLog;
import de.jumpnbump.usecases.game.ObjectProvider;
import de.jumpnbump.usecases.game.model.GameObject;
import de.jumpnbump.usecases.game.model.Player;

public class CollisionDetection {

	private static final MyLog LOGGER = Logger
			.getLogger(CollisionDetection.class);
	private final ObjectProvider world;

	public CollisionDetection(ObjectProvider world) {
		this.world = world;
	}

	public boolean objectStandsOnGround(GameObject gameobject) {
		for (GameObject otherObject : this.world.getAllObjects()) {
			if (standsOn(gameobject, otherObject)) {
				return true;
			}
		}
		return false;
	}

	private boolean standsOn(GameObject upperObject, GameObject lowerObject) {
		if (upperObject.minY() == lowerObject.maxY()) {
			return collides(upperObject, lowerObject);
		}
		return false;
	}

	public GameObject findObjectThisPlayerIsStandingOn(Player player) {
		for (GameObject go : this.world.getAllObjects()) {
			if (standsOn(player, go)) {
				return go;
			}
		}
		return null;
	}

	public Player findPlayerThisPlayerIsStandingOn(Player player) {
		for (Player p : this.world.getAllPlayer()) {
			if (standsOn(player, p)) {
				return p;
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
		return gameObject.minY() >= other.maxY()
				|| gameObject.maxY() <= other.minY();
	}

	public boolean isLeftOrRightToObject(GameObject targetObject,
			GameObject other) {
		return targetObject.minX() >= other.maxX()
				|| targetObject.maxX() <= other.minX();
	}

	public boolean collides(GameObject gameObject, GameObject other) {
		if (gameObject.maxX() < other.minX()) {
			return false;
		}
		if (gameObject.minX() > other.maxX()) {
			return false;
		}
		if (gameObject.maxY() < other.minY()) {
			return false;
		}
		if (gameObject.minY() > other.maxY()) {
			return false;
		}
		return true;
	}
}
