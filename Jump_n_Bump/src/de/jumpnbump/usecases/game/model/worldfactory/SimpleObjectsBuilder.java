package de.jumpnbump.usecases.game.model.worldfactory;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import android.content.Context;
import de.jumpnbump.usecases.game.factories.PlayerFactory;
import de.jumpnbump.usecases.game.factories.WallFactory;
import de.jumpnbump.usecases.game.model.FixedWorldObject;
import de.jumpnbump.usecases.game.model.ModelConstants;
import de.jumpnbump.usecases.game.model.Player;

public class SimpleObjectsBuilder implements WorldObjectsBuilder {
	@Override
	public List<Player> createAllPlayers(int number) {
		List<Player> allPlayers = new LinkedList<Player>();
		for (int i = 0; i < number; i++) {
			allPlayers.add(PlayerFactory.createPlayer(i));
		}
		return allPlayers;
	}

	@Override
	public Collection<FixedWorldObject> createAllWalls(Context context) {
		List<FixedWorldObject> allWalls = new LinkedList<FixedWorldObject>();
		allWalls.add(WallFactory.createWall(0,
				(int) (0.25 * ModelConstants.MAX_VALUE),
				(ModelConstants.MAX_VALUE),
				(int) (0.30 * ModelConstants.MAX_VALUE)));

		allWalls.add(WallFactory.createIceWall(
				(int) (0.1 * ModelConstants.MAX_VALUE),
				(int) (0.40 * ModelConstants.MAX_VALUE),
				(int) (ModelConstants.MAX_VALUE * 0.4),
				(int) (0.45 * ModelConstants.MAX_VALUE)));
		allWalls.add(WallFactory.createIceWall(
				(int) (0.60 * ModelConstants.MAX_VALUE),
				(int) (0.40 * ModelConstants.MAX_VALUE),
				(int) (0.9 * ModelConstants.MAX_VALUE),
				(int) (0.45 * ModelConstants.MAX_VALUE)));

		allWalls.addAll(BuildBorderAroundWorldHelper.build());
		return allWalls;
	}

}
