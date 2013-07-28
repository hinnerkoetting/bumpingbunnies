package de.jumpnbump.usecases.viewer.model;

public class WallFactory {

	public static Wall createWallFromDouble(double x, double y, double maxX,
			double maxY) {
		return createWall((int) Math.round(x * ModelConstants.MAX_VALUE),
				(int) Math.round(y * ModelConstants.MAX_VALUE),
				(int) Math.round(maxX * ModelConstants.MAX_VALUE),
				(int) Math.round(maxY * ModelConstants.MAX_VALUE));
	}

	public static Wall createWall(int x, int y, int maxX, int maxY) {
		int id = IdCounter.getNextId();
		Wall wall = new Wall(id, x, y, maxX, maxY);
		return wall;
	}

	public static IcyWall createIceWallFromDouble(double x, double y,
			double maxX, double maxY) {
		return createIceWall((int) Math.round(x * ModelConstants.MAX_VALUE),
				(int) Math.round(y * ModelConstants.MAX_VALUE),
				(int) Math.round(maxX * ModelConstants.MAX_VALUE),
				(int) Math.round(maxY * ModelConstants.MAX_VALUE));
	}

	public static IcyWall createIceWall(int x, int y, int maxX, int maxY) {
		int id = IdCounter.getNextId();
		IcyWall wall = new IcyWall(id, x, y, maxX, maxY);
		return wall;
	}

	public static Jumper createJumperFromDouble(double x, double y,
			double maxX, double maxY) {
		return createJumper((int) Math.round(x * ModelConstants.MAX_VALUE),
				(int) Math.round(y * ModelConstants.MAX_VALUE),
				(int) Math.round(maxX * ModelConstants.MAX_VALUE),
				(int) Math.round(maxY * ModelConstants.MAX_VALUE));
	}

	public static Water createWaterFromDouble(double x, double y,
			double maxX, double maxY) {
		return createWater((int) Math.round(x * ModelConstants.MAX_VALUE),
				(int) Math.round(y * ModelConstants.MAX_VALUE),
				(int) Math.round(maxX * ModelConstants.MAX_VALUE),
				(int) Math.round(maxY * ModelConstants.MAX_VALUE));
	}

	public static Background createBackgroundFromDouble(double x, double y,
			double maxX, double maxY) {
		return createBackground((int) Math.round(x * ModelConstants.MAX_VALUE),
				(int) Math.round(y * ModelConstants.MAX_VALUE),
				(int) Math.round(maxX * ModelConstants.MAX_VALUE),
				(int) Math.round(maxY * ModelConstants.MAX_VALUE));
	}

	public static Jumper createJumper(int x, int y, int maxX, int maxY) {
		int id = IdCounter.getNextId();
		Jumper jumper = new Jumper(id, x, y, maxX, maxY);
		return jumper;
	}

	public static Background createBackground(int x, int y, int maxX, int maxY) {
		int id = IdCounter.getNextId();
		Background bg = new Background(id, x, y, maxX, maxY);
		return bg;
	}

	public static Water createWater(int x, int y, int maxX, int maxY) {
		int id = IdCounter.getNextId();
		Water jumper = new Water(id, x, y, maxX, maxY);
		return jumper;
	}
}
