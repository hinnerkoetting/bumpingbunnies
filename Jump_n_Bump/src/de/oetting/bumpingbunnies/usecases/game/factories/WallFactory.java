package de.oetting.bumpingbunnies.usecases.game.factories;

import android.media.MediaPlayer;
import de.oetting.bumpingbunnies.usecases.game.model.IcyWall;
import de.oetting.bumpingbunnies.usecases.game.model.Jumper;
import de.oetting.bumpingbunnies.usecases.game.model.ModelConstants;
import de.oetting.bumpingbunnies.usecases.game.model.Wall;
import de.oetting.bumpingbunnies.usecases.game.model.Water;

public class WallFactory {

	public static Wall createWall(int x, int y) {
		int id = IdCounter.getNextId();
		Wall wall = new Wall(id, x, y, ModelConstants.WALL_WIDTH,
				ModelConstants.WALL_HEIGHT);
		return wall;
	}

	public static Wall createWallFromDouble(double x, double y, double maxX,
			double maxY) {
		return createWall((int) (x * ModelConstants.MAX_VALUE),
				(int) (y * ModelConstants.MAX_VALUE),
				(int) (maxX * ModelConstants.MAX_VALUE),
				(int) (maxY * ModelConstants.MAX_VALUE));
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
			double maxX, double maxY) {
		return createIceWall((int) (x * ModelConstants.MAX_VALUE),
				(int) (y * ModelConstants.MAX_VALUE),
				(int) (maxX * ModelConstants.MAX_VALUE),
				(int) (maxY * ModelConstants.MAX_VALUE));
	}

	public static IcyWall createIceWall(long x, long y, long maxX, long maxY) {
		int id = IdCounter.getNextId();
		IcyWall wall = new IcyWall(id, x, y, maxX, maxY);
		return wall;
	}

	public static Jumper createJumperFromDouble(double x, double y,
			double maxX, double maxY, MediaPlayer mediaPlayer) {
		return createJumper((int) (x * ModelConstants.MAX_VALUE),
				(int) (y * ModelConstants.MAX_VALUE),
				(int) (maxX * ModelConstants.MAX_VALUE),
				(int) (maxY * ModelConstants.MAX_VALUE), mediaPlayer);
	}

	public static Jumper createJumper(long x, long y, long maxX, long maxY,
			MediaPlayer mediaPlayer) {
		int id = IdCounter.getNextId();
		Jumper jumper = new Jumper(id, x, y, maxX, maxY, mediaPlayer);
		return jumper;
	}
}
