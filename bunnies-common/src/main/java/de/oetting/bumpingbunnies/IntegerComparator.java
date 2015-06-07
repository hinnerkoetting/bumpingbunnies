package de.oetting.bumpingbunnies;

public class IntegerComparator {

	// used to fixed noSuchMethodError on Android 2.3 for Integer.compare()
	public static int compareInt(int x, int y) {
		return (x < y) ? -1 : ((x == y) ? 0 : 1);
	}
}
