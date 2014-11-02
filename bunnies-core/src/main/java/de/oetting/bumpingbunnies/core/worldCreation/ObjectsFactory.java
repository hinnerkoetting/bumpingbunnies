package de.oetting.bumpingbunnies.core.worldCreation;

import de.oetting.bumpingbunnies.model.game.objects.Background;
import de.oetting.bumpingbunnies.model.game.objects.IcyWall;
import de.oetting.bumpingbunnies.model.game.objects.Jumper;
import de.oetting.bumpingbunnies.model.game.objects.ModelConstants;
import de.oetting.bumpingbunnies.model.game.objects.Wall;
import de.oetting.bumpingbunnies.model.game.objects.Water;
import de.oetting.bumpingbunnies.model.game.world.WorldProperties;

public class ObjectsFactory {

	public static Wall createWall(long x, long y) {
		int id = IdCounter.getNextId();
		Wall wall = new Wall(id, x, y, x + ModelConstants.WALL_WIDTH, y + ModelConstants.WALL_HEIGHT);
		return wall;
	}

	public static Wall createWallFromDouble(double x, double y, double maxX, double maxY, WorldProperties properties) {
		return createWall((int) (x * properties.getWorldWidth()), (int) (y * properties.getWorldHeight()), (int) (maxX * properties.getWorldWidth()),
				(int) (maxY * properties.getWorldHeight()));
	}

	public static Wall createWall(long x, long y, long maxX, long maxY) {
		int id = IdCounter.getNextId();
		Wall wall = new Wall(id, x, y, maxX, maxY);
		return wall;
	}

	public static Water createWater(long x, long y, long maxX, long maxY) {
		int id = IdCounter.getNextId();
		Water water = new Water(id, x, y, maxX, maxY);
		return water;
	}

	public static IcyWall createIceWallFromDouble(double x, double y, double maxX, double maxY, WorldProperties properties) {
		return createIceWall((int) (x * properties.getWorldWidth()), (int) (y * properties.getWorldHeight()), (int) (maxX * properties.getWorldWidth()),
				(int) (maxY * properties.getWorldHeight()));
	}

	public static IcyWall createIceWall(long x, long y, long maxX, long maxY) {
		int id = IdCounter.getNextId();
		IcyWall wall = new IcyWall(id, x, y, maxX, maxY);
		return wall;
	}

	public static Jumper createJumperFromDouble(double x, double y, double maxX, double maxY, WorldProperties properties) {
		return createJumper((int) (x * properties.getWorldWidth()), (int) (y * properties.getWorldHeight()), (int) (maxX * properties.getWorldWidth()),
				(int) (maxY * properties.getWorldHeight()));
	}

	public static Water createWaterFromDouble(double x, double y, double maxX, double maxY, WorldProperties properties) {
		return createWater((int) (x * properties.getWorldWidth()), (int) (y * properties.getWorldHeight()), (int) (maxX * properties.getWorldWidth()),
				(int) (maxY * properties.getWorldHeight()));
	}

	public static Jumper createJumper(long x, long y, long maxX, long maxY) {
		int id = IdCounter.getNextId();
		Jumper jumper = new Jumper(id, x, y, maxX, maxY);
		return jumper;
	}

	public static Background createBackgroundFromDouble(double x, double y, double maxX, double maxY, WorldProperties properties) {
		return createBackground((int) (x * properties.getWorldWidth()), (int) (y * properties.getWorldHeight()), (int) (maxX * properties.getWorldWidth()),
				(int) (maxY * properties.getWorldHeight()));
	}

	public static Background createBackground(long x, long y, long maxX, long maxY) {
		int id = IdCounter.getNextId();
		Background background = new Background(id, x, y, maxX, maxY);
		return background;
	}

}
