package de.jumpnbump.usecases.game.factories;

import de.jumpnbump.usecases.game.model.IcyWall;
import de.jumpnbump.usecases.game.model.Jumper;
import de.jumpnbump.usecases.game.model.ModelConstants;
import de.jumpnbump.usecases.game.model.Wall;

public class WallFactory {

	public static Wall createWall(int x, int y) {
		int id = IdCounter.getNextId();
		Wall wall = new Wall(id, x, y, ModelConstants.WALL_WIDTH,
				ModelConstants.WALL_HEIGHT);
		return wall;
	}

	public static Wall createWall(int x, int y, int maxX, int maxY) {
		int id = IdCounter.getNextId();
		Wall wall = new Wall(id, x, y, maxX, maxY);
		return wall;
	}

	public static Wall createIceWall(int x, int y, int maxX, int maxY) {
		int id = IdCounter.getNextId();
		Wall wall = new IcyWall(id, x, y, maxX, maxY);
		return wall;
	}

	public static Jumper createJumper(int x, int y, int maxX, int maxY) {
		int id = IdCounter.getNextId();
		Jumper jumper = new Jumper(id, x, y, maxX, maxY);
		return jumper;
	}
}
