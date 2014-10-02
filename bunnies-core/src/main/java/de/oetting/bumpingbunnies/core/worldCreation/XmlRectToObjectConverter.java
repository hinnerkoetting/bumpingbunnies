package de.oetting.bumpingbunnies.core.worldCreation;

import de.oetting.bumpingbunnies.model.game.MusicPlayer;
import de.oetting.bumpingbunnies.model.game.objects.IcyWall;
import de.oetting.bumpingbunnies.model.game.objects.Jumper;
import de.oetting.bumpingbunnies.model.game.objects.SpawnPoint;
import de.oetting.bumpingbunnies.model.game.objects.Wall;
import de.oetting.bumpingbunnies.model.game.objects.Water;
import de.oetting.bumpingbunnies.model.game.world.WorldProperties;
import de.oetting.bumpingbunnies.model.game.world.XmlRect;

public class XmlRectToObjectConverter {

	public static Wall createWall(XmlRect rect, WorldProperties properties) {
		return WallFactory.createWallFromDouble(rect.getMinX(), rect.getMinY(), rect.getMaxX(), rect.getMaxY(), properties);
	}

	public static IcyWall createIceWall(XmlRect rect, WorldProperties properties) {
		return WallFactory.createIceWallFromDouble(rect.getMinX(), rect.getMinY(), rect.getMaxX(), rect.getMaxY(), properties);
	}

	public static Jumper createJumper(XmlRect rect, MusicPlayer mediaPlayer, WorldProperties properties) {
		return WallFactory.createJumperFromDouble(rect.getMinX(), rect.getMinY(), rect.getMaxX(), rect.getMaxY(), mediaPlayer, properties);
	}

	public static Water createWater(XmlRect rect, MusicPlayer musicPlayer, WorldProperties properties) {
		return WallFactory.createWaterFromDouble(rect.getMinX(), rect.getMinY(), rect.getMaxX(), rect.getMaxY(), properties, musicPlayer);
	}

	public static SpawnPoint createSpawn(String x, String y, WorldProperties properties) {
		return new SpawnPoint((int) (properties.getWorldWidth() * Double.parseDouble(x)), (int) (properties.getWorldHeight() * Double.parseDouble(y)));
	}
}
