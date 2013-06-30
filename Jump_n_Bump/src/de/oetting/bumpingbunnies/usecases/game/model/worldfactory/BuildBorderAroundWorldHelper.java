package de.oetting.bumpingbunnies.usecases.game.model.worldfactory;

import java.util.ArrayList;
import java.util.List;

import de.oetting.bumpingbunnies.usecases.game.factories.WallFactory;
import de.oetting.bumpingbunnies.usecases.game.model.ModelConstants;
import de.oetting.bumpingbunnies.usecases.game.model.Wall;

public class BuildBorderAroundWorldHelper {

	public static List<Wall> build() {
		List<Wall> walls = new ArrayList<Wall>();
		// bottom
		walls.add(WallFactory.createWall(0, -1, ModelConstants.MAX_VALUE, 0));
		// left
		walls.add(WallFactory.createWall(-1, 0, 0,
				(ModelConstants.MAX_VALUE * 2)));
		// right
		walls.add(WallFactory.createWall(ModelConstants.MAX_VALUE, 0,
				ModelConstants.MAX_VALUE + 1, (ModelConstants.MAX_VALUE * 2)));
		return walls;
	}
}
