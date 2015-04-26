package de.oetting.bumpingbunnies.core.game.movement;

import de.oetting.bumpingbunnies.model.game.objects.GameObject;
import de.oetting.bumpingbunnies.model.game.objects.Rectangle;

public class SingleCollisionDetection {

	public static boolean collidesObjectOnBottom(GameObject objectToBeChecked,
			GameObject otherObject) {
		if (!otherObject.equals(objectToBeChecked)) {
			if (checkBottomCollisionWithOtherObject(objectToBeChecked,
					otherObject)) {
				return true;
			}
		}
		return false;
	}

	public static boolean collidesCircaObjectOnTop(
			GameObject objectToBeChecked, GameObject otherObject) {
		if (!otherObject.equals(objectToBeChecked)) {
			if (checkCircaBottomCollisionWithOtherObject(otherObject,
					objectToBeChecked)) {
				return true;
			}
		}
		return false;
	}

	public static boolean collidesObjectOnTop(GameObject objectToBeChecked,
			GameObject otherObject) {
		if (!otherObject.equals(objectToBeChecked)) {
			if (checkBottomCollisionWithOtherObject(otherObject,
					objectToBeChecked)) {
				return true;
			}
		}
		return false;
	}

	private static boolean checkCircaBottomCollisionWithOtherObject(
			GameObject objectToBeChecked, GameObject otherObject) {
		if (isAtCircaSomeHeightAsOtherObject(objectToBeChecked, otherObject)) {
			if (objectToBeChecked.maxX() <= otherObject.minX()) {
				return false;
			} else if (objectToBeChecked.minX() >= otherObject.maxX()) {
				return false;
			} else {
				return true;
			}
		}
		return false;
	}

	private static boolean checkBottomCollisionWithOtherObject(
			GameObject objectToBeChecked, GameObject otherObject) {
		if (isAtSomeHeightAsOtherObject(objectToBeChecked, otherObject)) {
			if (objectToBeChecked.maxX() <= otherObject.minX()) {
				return false;
			} else if (objectToBeChecked.minX() >= otherObject.maxX()) {
				return false;
			} else {
				return true;
			}
		}
		return false;
	}

	private static boolean isAtCircaSomeHeightAsOtherObject(
			GameObject objectToBeChecked, GameObject otherObject) {
		return objectToBeChecked.maxY() > otherObject.minY() - 0.005
				&& objectToBeChecked.minY() < otherObject.maxY() - 0.005;
	}

	private static boolean isAtSomeHeightAsOtherObject(
			GameObject objectToBeChecked, GameObject otherObject) {
		return objectToBeChecked.maxY() > otherObject.minY()
				&& objectToBeChecked.minY() < otherObject.maxY();
	}

	private static boolean isAtSameWidthAsOtherObject(
			GameObject objectToBeChecked, GameObject otherObject) {
		return objectToBeChecked.maxX() > otherObject.minX()
				&& objectToBeChecked.minX() < otherObject.maxX();
	}

	public static boolean collidesObjectOnRight(GameObject objectToBeChecked,
			GameObject otherObject) {
		if (isLeftToOtherObject(objectToBeChecked, otherObject)) {
			if (checkLeftCollisionWithOtherObject(otherObject,
					objectToBeChecked)) {
				return true;
			}
		}
		return false;
	}

	private static boolean isLeftToOtherObject(GameObject objectToBeChecked,
			GameObject otherObject) {
		return isAtSameWidthAsOtherObject(objectToBeChecked, otherObject);
	}

	public static boolean collidesObjectOnLeft(GameObject objectToBeChecked,
			GameObject otherObject) {
		if (isAtSameWidthAsOtherObject(objectToBeChecked, otherObject)) {
			if (checkLeftCollisionWithOtherObject(otherObject,
					objectToBeChecked)) {
				return true;
			}
		}
		return false;
	}

	private static boolean checkLeftCollisionWithOtherObject(
			GameObject objectToBeChecked, GameObject otherObject) {
		if (objectToBeChecked.maxY() <= otherObject.minY()) {
			return false;
		} else if (objectToBeChecked.minY() >= otherObject.maxY()) {
			return false;
		} else {
			return true;
		}
	}

	public static boolean sharesHorizontalPosition(Rectangle gameObject, Rectangle other) {
		return gameObject.maxX() > other.minX() && gameObject.minX() < other.maxX();
	}

	public static boolean sharesVerticalPosition(Rectangle gameObject, Rectangle other) {
		return gameObject.maxY() > other.minY() && gameObject.minY() < other.maxY();
	}

	public static boolean collides(Rectangle gameObject, Rectangle other) {
		boolean collidesX = sharesHorizontalPosition(gameObject, other);
		boolean collidesY = sharesVerticalPosition(gameObject, other);
		return collidesX && collidesY;
	}

}
