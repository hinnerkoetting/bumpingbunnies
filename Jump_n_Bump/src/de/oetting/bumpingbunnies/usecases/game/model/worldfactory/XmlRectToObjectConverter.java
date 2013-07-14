package de.oetting.bumpingbunnies.usecases.game.model.worldfactory;

import android.media.MediaPlayer;
import de.oetting.bumpingbunnies.usecases.game.factories.WallFactory;
import de.oetting.bumpingbunnies.usecases.game.model.IcyWall;
import de.oetting.bumpingbunnies.usecases.game.model.Jumper;
import de.oetting.bumpingbunnies.usecases.game.model.SpawnPoint;
import de.oetting.bumpingbunnies.usecases.game.model.Wall;
import de.oetting.bumpingbunnies.usecases.game.model.Water;
import de.oetting.bumpingbunnies.usecases.game.model.WorldProperties;

public class XmlRectToObjectConverter {

	public static Wall createWall(XmlRect rect, WorldProperties properties) {
		return WallFactory.createWallFromDouble(rect.getMinX(), rect.getMinY(),
				rect.getMaxX(), rect.getMaxY(), properties);
	}

	public static IcyWall createIceWall(XmlRect rect, WorldProperties properties) {
		return WallFactory.createIceWallFromDouble(rect.getMinX(),
				rect.getMinY(), rect.getMaxX(), rect.getMaxY(), properties);
	}

	public static Jumper createJumper(XmlRect rect, MediaPlayer mediaPlayer, WorldProperties properties) {
		return WallFactory.createJumperFromDouble(rect.getMinX(),
				rect.getMinY(), rect.getMaxX(), rect.getMaxY(), mediaPlayer, properties);
	}

	public static Water createWater(XmlRect rect, WorldProperties properties) {
		return WallFactory.createWaterFromDouble(rect.getMinX(),
				rect.getMinY(), rect.getMaxX(), rect.getMaxY(), properties);
	}

	public static SpawnPoint createSpawn(String x, String y, WorldProperties properties) {
		return new SpawnPoint(
				(int) (properties.getWorldWidth() * Double.parseDouble(x)),
				(int) (properties.getWorldHeight() * Double.parseDouble(y)));
	}
}
