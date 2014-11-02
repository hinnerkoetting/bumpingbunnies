package de.oetting.bumpingbunnies.core.worldCreation;

import de.oetting.bumpingbunnies.model.game.objects.Background;
import de.oetting.bumpingbunnies.model.game.objects.IcyWall;
import de.oetting.bumpingbunnies.model.game.objects.Jumper;
import de.oetting.bumpingbunnies.model.game.objects.SpawnPoint;
import de.oetting.bumpingbunnies.model.game.objects.Wall;
import de.oetting.bumpingbunnies.model.game.objects.Water;
import de.oetting.bumpingbunnies.model.game.world.WorldProperties;
import de.oetting.bumpingbunnies.model.game.world.XmlRect;

public class XmlRectToObjectConverter {

	public static Wall createWall(XmlRect rect, WorldProperties properties) {
		return ObjectsFactory.createWallFromDouble(rect.getMinX(), rect.getMinY(), rect.getMaxX(), rect.getMaxY(), properties);
	}

	public static IcyWall createIceWall(XmlRect rect, WorldProperties properties) {
		return ObjectsFactory.createIceWallFromDouble(rect.getMinX(), rect.getMinY(), rect.getMaxX(), rect.getMaxY(), properties);
	}

	public static Jumper createJumper(XmlRect rect, WorldProperties properties) {
		return ObjectsFactory.createJumperFromDouble(rect.getMinX(), rect.getMinY(), rect.getMaxX(), rect.getMaxY(), properties);
	}

	public static Water createWater(XmlRect rect, WorldProperties properties) {
		return ObjectsFactory.createWaterFromDouble(rect.getMinX(), rect.getMinY(), rect.getMaxX(), rect.getMaxY(), properties);
	}

	public static Background createBackground(XmlRect rect, WorldProperties properties) {
		return ObjectsFactory.createBackgroundFromDouble(rect.getMinX(), rect.getMinY(), rect.getMaxX(), rect.getMaxY(), properties);
	}

	public static SpawnPoint createSpawn(String x, String y, WorldProperties properties) {
		return new SpawnPoint((int) (properties.getWorldWidth() * Double.parseDouble(x)), (int) (properties.getWorldHeight() * Double.parseDouble(y)));
	}
}
