package de.oetting.bumpingbunnies.usecases.game.factories;

import de.oetting.bumpingbunnies.usecases.game.model.IcyWall;
import de.oetting.bumpingbunnies.usecases.game.model.Jumper;
import de.oetting.bumpingbunnies.usecases.game.model.ModelConstants;
import de.oetting.bumpingbunnies.usecases.game.model.Wall;
import de.oetting.bumpingbunnies.usecases.game.model.Water;
import de.oetting.bumpingbunnies.usecases.game.music.MusicPlayer;
import de.oetting.bumpingbunnies.world.WorldProperties;

public class WallFactory {

	public static Wall createWall(int x, int y) {
		int id = IdCounter.getNextId();
		Wall wall = new Wall(id, x, y, ModelConstants.WALL_WIDTH,
				ModelConstants.WALL_HEIGHT);
		return wall;
	}

	public static Wall createWallFromDouble(double x, double y, double maxX,
			double maxY, WorldProperties properties) {
		return createWall((int) (x * properties.getWorldWidth()),
				(int) (y * properties.getWorldHeight()),
				(int) (maxX * properties.getWorldWidth()),
				(int) (maxY * properties.getWorldHeight()));
	}

	public static Wall createWall(long x, long y, long maxX, long maxY) {
		int id = IdCounter.getNextId();
		Wall wall = new Wall(id, x, y, maxX, maxY);
		return wall;
	}

	public static Water createWater(long x, long y, long maxX, long maxY, MusicPlayer musicPlayer) {
		Water water = new Water(x, y, maxX, maxY, musicPlayer);
		return water;
	}

	public static IcyWall createIceWallFromDouble(double x, double y,
			double maxX, double maxY, WorldProperties properties) {
		return createIceWall((int) (x * properties.getWorldWidth()),
				(int) (y * properties.getWorldHeight()),
				(int) (maxX * properties.getWorldWidth()),
				(int) (maxY * properties.getWorldHeight()));
	}

	public static IcyWall createIceWall(long x, long y, long maxX, long maxY) {
		int id = IdCounter.getNextId();
		IcyWall wall = new IcyWall(id, x, y, maxX, maxY);
		return wall;
	}

	public static Jumper createJumperFromDouble(double x, double y,
			double maxX, double maxY, MusicPlayer mediaPlayer, WorldProperties properties) {
		return createJumper((int) (x * properties.getWorldWidth()),
				(int) (y * properties.getWorldHeight()),
				(int) (maxX * properties.getWorldWidth()),
				(int) (maxY * properties.getWorldHeight()), mediaPlayer);
	}

	public static Water createWaterFromDouble(double x, double y, double maxX, double maxY, WorldProperties properties,
			MusicPlayer musicPlayer) {
		return createWater((int) (x * properties.getWorldWidth()),
				(int) (y * properties.getWorldHeight()),
				(int) (maxX * properties.getWorldWidth()),
				(int) (maxY * properties.getWorldHeight()), musicPlayer);
	}

	public static Jumper createJumper(long x, long y, long maxX, long maxY, MusicPlayer mediaPlayer) {
		int id = IdCounter.getNextId();
		Jumper jumper = new Jumper(id, x, y, maxX, maxY, mediaPlayer);
		return jumper;
	}
}
