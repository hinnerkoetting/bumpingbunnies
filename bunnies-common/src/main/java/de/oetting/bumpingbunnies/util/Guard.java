package de.oetting.bumpingbunnies.util;

import de.oetting.bumpingbunnies.exceptions.AssertionException;

public class Guard {

	public static void againstNull(String message, Object objectToCheck) {
		if (objectToCheck == null) {
			throw new AssertionException(message);
		}
	}
}
