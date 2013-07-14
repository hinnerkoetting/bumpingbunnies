package de.oetting.bumpingbunnies.usecases.game.model.worldfactory;

import java.util.ArrayList;
import java.util.List;

import de.oetting.bumpingbunnies.usecases.game.factories.WallFactory;
import de.oetting.bumpingbunnies.usecases.game.model.Wall;
import de.oetting.bumpingbunnies.usecases.game.model.WorldProperties;

public class BuildBorderAroundWorldHelper {

	public static List<Wall> build(WorldProperties worldProperties) {
		List<Wall> walls = new ArrayList<Wall>();
		// bottom
		walls.add(WallFactory.createWall(0, -1, worldProperties.getWorldWidth(), 0));
		// left
		walls.add(WallFactory.createWall(-1, 0, 0,
				(worldProperties.getWorldHeight() * 2)));
		// right
		walls.add(WallFactory.createWall(worldProperties.getWorldWidth(), 0,
				worldProperties.getWorldWidth() + 1, (worldProperties.getWorldHeight() * 2)));
		return walls;
	}
}
