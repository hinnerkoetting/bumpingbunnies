package de.oetting.bumpingbunnies.core.game.movement;

import de.oetting.bumpingbunnies.model.game.objects.GameObject;
import de.oetting.bumpingbunnies.model.game.objects.Bunny;
import de.oetting.bumpingbunnies.model.game.world.ObjectProvider;

public class CollisionDetection {

	private final ObjectProvider world;

	public CollisionDetection(ObjectProvider world) {
		this.world = world;
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

	public GameObject findObjectThisPlayerIsStandingOn(int segmentIndex, Bunny bunny) {
		for (GameObject go : this.world.getCandidateForCollisionObjects(segmentIndex)) {
			if (standsOn(bunny, go)) {
				return go;
			}
		}
		return null;
	}

	public Bunny findPlayerThisPlayerIsStandingOn(Bunny player) {
		for (Bunny p : this.world.getAllConnectedBunnies()) {
			if (standsOn(player, p)) {
				return p;
			}
		}
		return null;
	}

	public boolean collidesWithAnyFixedObject(Bunny player) {
		return findObjectThisPlayerIsCollidingWith(player) != null;
	}

	public GameObject findObjectThisPlayerIsCollidingWith(Bunny bunny) {
		for (GameObject go : this.world.getCandidateForCollisionObjects(bunny)) {
			if (collides(bunny, go)) {
				return go;
			}
		}
		return null;
	}

	public Bunny findBunnyThisBunnyIsCollidingWith(Bunny player) {
		for (Bunny go : this.world.getAllConnectedBunnies()) {
			if (player.id() != go.id() && collides(player, go)) {
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
		return SingleCollisionDetection.collides(gameObject, other);
	}

	/**
	 * This does not mean that vertical position is the same.
	 */
	public boolean sharesHorizontalPosition(GameObject gameObject, GameObject other) {
		return SingleCollisionDetection.sharesHorizontalPosition(gameObject, other);
	}

	/**
	 * This does not mean that horizontal position is the same.
	 */
	public boolean sharesVerticalPosition(GameObject gameObject, GameObject other) {
		return SingleCollisionDetection.sharesVerticalPosition(gameObject, other);
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

	public int getSegmentThatBunnyBelongsTo(Bunny bunny) {
		return world.getSegmentThatBunnyBelongsTo(bunny);
	}

}
