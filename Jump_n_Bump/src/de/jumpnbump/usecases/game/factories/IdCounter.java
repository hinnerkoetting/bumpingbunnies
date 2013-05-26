package de.jumpnbump.usecases.game.factories;

public class IdCounter {

	private static int GLOBAL_COUNT = 0;

	public static int getNextId() {
		return GLOBAL_COUNT++;
	}

	private IdCounter() {

	}
}
