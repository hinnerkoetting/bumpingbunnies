package de.oetting.bumpingbunnies.usecases.game.model.worldfactory;

import android.media.MediaPlayer;
import de.oetting.bumpingbunnies.usecases.game.factories.WallFactory;
import de.oetting.bumpingbunnies.usecases.game.model.IcyWall;
import de.oetting.bumpingbunnies.usecases.game.model.Jumper;
import de.oetting.bumpingbunnies.usecases.game.model.ModelConstants;
import de.oetting.bumpingbunnies.usecases.game.model.SpawnPoint;
import de.oetting.bumpingbunnies.usecases.game.model.Wall;

public class XmlRectToObjectConverter {

	public static Wall createWall(XmlRect rect) {
		return WallFactory.createWallFromDouble(rect.getMinX(), rect.getMinY(),
				rect.getMaxX(), rect.getMaxY());
	}

	public static IcyWall createIceWall(XmlRect rect) {
		return WallFactory.createIceWallFromDouble(rect.getMinX(),
				rect.getMinY(), rect.getMaxX(), rect.getMaxY());
	}

	public static Jumper createJumper(XmlRect rect, MediaPlayer mediaPlayer) {
		return WallFactory.createJumperFromDouble(rect.getMinX(),
				rect.getMinY(), rect.getMaxX(), rect.getMaxY(), mediaPlayer);
	}

	public static SpawnPoint createSpawn(String x, String y) {
		return new SpawnPoint(
				(int) (ModelConstants.MAX_VALUE * Double.parseDouble(x)),
				(int) (ModelConstants.MAX_VALUE * Double.parseDouble(y)));
	}
}
