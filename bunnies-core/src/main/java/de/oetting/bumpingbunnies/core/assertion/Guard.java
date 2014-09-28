package de.oetting.bumpingbunnies.core.assertion;

public class Guard {

	public static void againstNull(Object object) {
		if (object == null)
			throw new NullPointerException();
	}
}
