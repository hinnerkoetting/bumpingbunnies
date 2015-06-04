package de.oetting.bumpingbunnies.worldcreator.load.gameObjects;

public class IdCounter {

	private static int GLOBAL_COUNT = 2;

	public static int getNextId() {
		return GLOBAL_COUNT++;
	}

	private IdCounter() {

	}
}
