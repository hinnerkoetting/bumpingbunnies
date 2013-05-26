package de.jumpnbump.usecases.game.factories;

import de.jumpnbump.usecases.game.model.ModelConstants;
import de.jumpnbump.usecases.game.model.Wall;

public class WallFactory {

	public static Wall createWall(double x, double y) {
		int id = IdCounter.getNextId();
		Wall wall = new Wall(id, x, y, ModelConstants.WALL_WIDTH,
				ModelConstants.WALL_HEIGHT);
		return wall;
	}

	public static Wall createWall(double x, double y, double width,
			double height) {
		int id = IdCounter.getNextId();
		Wall wall = new Wall(id, x, y, width, height);
		return wall;
	}
}
