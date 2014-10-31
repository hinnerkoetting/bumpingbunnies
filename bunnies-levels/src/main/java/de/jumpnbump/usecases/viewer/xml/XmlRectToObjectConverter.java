package de.jumpnbump.usecases.viewer.xml;

import de.oetting.bumpingbunnies.core.worldCreation.WallFactory;
import de.oetting.bumpingbunnies.model.game.objects.Background;
import de.oetting.bumpingbunnies.model.game.objects.IcyWall;
import de.oetting.bumpingbunnies.model.game.objects.Jumper;
import de.oetting.bumpingbunnies.model.game.objects.ModelConstants;
import de.oetting.bumpingbunnies.model.game.objects.SpawnPoint;
import de.oetting.bumpingbunnies.model.game.objects.Wall;
import de.oetting.bumpingbunnies.model.game.objects.Water;
import de.oetting.bumpingbunnies.model.game.world.WorldProperties;

public class XmlRectToObjectConverter {

	public static Wall createWall(XmlRect rect, WorldProperties properties) {
		return WallFactory.createWallFromDouble(rect.getMinX(), rect.getMinY(), rect.getMaxX(), rect.getMaxY(), properties);
	}

	public static IcyWall createIceWall(XmlRect rect, WorldProperties properties) {
		return WallFactory.createIceWallFromDouble(rect.getMinX(), rect.getMinY(), rect.getMaxX(), rect.getMaxY(), properties);
	}

	public static Jumper createJumper(XmlRect rect, WorldProperties properties) {
		return WallFactory.createJumperFromDouble(rect.getMinX(), rect.getMinY(), rect.getMaxX(), rect.getMaxY(), properties);
	}

	public static Water createWater(XmlRect rect, WorldProperties properties) {
		return WallFactory.createWaterFromDouble(rect.getMinX(), rect.getMinY(), rect.getMaxX(), rect.getMaxY(), properties);
	}

	public static SpawnPoint createSpawn(String x, String y) {
		return new SpawnPoint((int) (ModelConstants.MAX_VALUE * Double.parseDouble(x)), (int) (ModelConstants.MAX_VALUE * Double.parseDouble(y)));
	}

	public static Background createBackground(XmlRect rect, WorldProperties properties) {
		return WallFactory.createBackgroundFromDouble(rect.getMinX(), rect.getMinY(), rect.getMaxX(), rect.getMaxY(), properties);
	}
}
