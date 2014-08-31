package de.jumpnbump.usecases.viewer.xml;

import de.jumpnbump.usecases.viewer.model.Background;
import de.jumpnbump.usecases.viewer.model.IcyWall;
import de.jumpnbump.usecases.viewer.model.Jumper;
import de.jumpnbump.usecases.viewer.model.ModelConstants;
import de.jumpnbump.usecases.viewer.model.SpawnPoint;
import de.jumpnbump.usecases.viewer.model.Wall;
import de.jumpnbump.usecases.viewer.model.WallFactory;
import de.jumpnbump.usecases.viewer.model.Water;

public class XmlRectToObjectConverter {

	public static Wall createWall(XmlRect rect) {
		return WallFactory.createWallFromDouble(rect.getMinX(), rect.getMinY(),
				rect.getMaxX(), rect.getMaxY());
	}

	public static IcyWall createIceWall(XmlRect rect) {
		return WallFactory.createIceWallFromDouble(rect.getMinX(),
				rect.getMinY(), rect.getMaxX(), rect.getMaxY());
	}

	public static Jumper createJumper(XmlRect rect) {
		return WallFactory.createJumperFromDouble(rect.getMinX(),
				rect.getMinY(), rect.getMaxX(), rect.getMaxY());
	}

	public static Water createWater(XmlRect rect) {
		return WallFactory.createWaterFromDouble(rect.getMinX(),
				rect.getMinY(), rect.getMaxX(), rect.getMaxY());
	}

	public static SpawnPoint createSpawn(String x, String y) {
		return new SpawnPoint(
				(int) (ModelConstants.MAX_VALUE * Double.parseDouble(x)),
				(int) (ModelConstants.MAX_VALUE * Double.parseDouble(y)));
	}

	public static Background createBackground(XmlRect rect) {
		return WallFactory.createBackgroundFromDouble(rect.getMinX(),
				rect.getMinY(), rect.getMaxX(), rect.getMaxY());
	}
}