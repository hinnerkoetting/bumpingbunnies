package de.jumpnbump.usecases.game.model.worldfactory;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import de.jumpnbump.usecases.game.factories.PlayerFactory;
import de.jumpnbump.usecases.game.factories.WallFactory;
import de.jumpnbump.usecases.game.model.FixedWorldObject;
import de.jumpnbump.usecases.game.model.ModelConstants;
import de.jumpnbump.usecases.game.model.Player;

public class FirstWorldObjectsBuilder implements WorldObjectsBuilder {

	@Override
	public List<Player> createAllPlayers(int number) {
		List<Player> allPlayers = new LinkedList<Player>();
		for (int i = 0; i < number; i++) {
			allPlayers.add(PlayerFactory.createPlayer(i));
		}
		return allPlayers;
	}

	@Override
	public Collection<FixedWorldObject> createAllWalls() {
		List<FixedWorldObject> allWalls = new LinkedList<FixedWorldObject>();
		allWalls.add(WallFactory.createWall(
				(int) (0.1 * ModelConstants.MAX_VALUE),
				(int) (0.15 * ModelConstants.MAX_VALUE),
				(int) (0.5 * ModelConstants.MAX_VALUE),
				(int) (0.20 * ModelConstants.MAX_VALUE)));

		allWalls.add(WallFactory.createWall(
				(int) (0.0 * ModelConstants.MAX_VALUE),
				(int) (0.3 * ModelConstants.MAX_VALUE),
				(int) (0.2 * ModelConstants.MAX_VALUE),
				(int) (0.35 * ModelConstants.MAX_VALUE)));

		allWalls.add(WallFactory.createWall(
				(int) (0.70 * ModelConstants.MAX_VALUE),
				(int) (0.35 * ModelConstants.MAX_VALUE),
				(int) (0.85 * ModelConstants.MAX_VALUE),
				(int) (0.40 * ModelConstants.MAX_VALUE)));
		allWalls.add(WallFactory.createIceWall(
				(int) (0.3 * ModelConstants.MAX_VALUE),
				(int) (0.40 * ModelConstants.MAX_VALUE),
				(int) (0.55 * ModelConstants.MAX_VALUE),
				(int) (0.45 * ModelConstants.MAX_VALUE)));

		allWalls.add(WallFactory.createIceWall(
				(int) (0.45 * ModelConstants.MAX_VALUE),
				(int) (0.6 * ModelConstants.MAX_VALUE),
				(int) (0.70 * ModelConstants.MAX_VALUE),
				(int) (0.65 * ModelConstants.MAX_VALUE)));

		allWalls.add(WallFactory.createWall(
				(int) (0.75 * ModelConstants.MAX_VALUE),
				(int) (0.8 * ModelConstants.MAX_VALUE),
				(1 * ModelConstants.MAX_VALUE),
				(int) (0.85 * ModelConstants.MAX_VALUE)));
		allWalls.add(WallFactory.createWall(
				(int) (0.25 * ModelConstants.MAX_VALUE),
				(int) (0.8 * ModelConstants.MAX_VALUE),
				(int) (0.50 * ModelConstants.MAX_VALUE),
				(int) (0.85 * ModelConstants.MAX_VALUE)));

		allWalls.addAll(BuildBorderAroundWorldHelper.build());
		return allWalls;
	}
}
