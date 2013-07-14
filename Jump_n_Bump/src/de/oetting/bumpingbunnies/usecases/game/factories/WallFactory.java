package de.oetting.bumpingbunnies.usecases.game.factories;

import android.media.MediaPlayer;
import de.oetting.bumpingbunnies.usecases.game.model.IcyWall;
import de.oetting.bumpingbunnies.usecases.game.model.Jumper;
import de.oetting.bumpingbunnies.usecases.game.model.ModelConstants;
import de.oetting.bumpingbunnies.usecases.game.model.Wall;
import de.oetting.bumpingbunnies.usecases.game.model.Water;
import de.oetting.bumpingbunnies.usecases.game.model.WorldProperties;

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

	public static Water createWater(long x, long y, long maxX, long maxY) {
		Water water = new Water(x, y, maxX, maxY);
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
			double maxX, double maxY, MediaPlayer mediaPlayer, WorldProperties properties) {
		return createJumper((int) (x * properties.getWorldWidth()),
				(int) (y * properties.getWorldHeight()),
				(int) (maxX * properties.getWorldWidth()),
				(int) (maxY * properties.getWorldHeight()), mediaPlayer);
	}

	public static Jumper createJumper(long x, long y, long maxX, long maxY,
			MediaPlayer mediaPlayer) {
		int id = IdCounter.getNextId();
		Jumper jumper = new Jumper(id, x, y, maxX, maxY, mediaPlayer);
		return jumper;
	}
}
