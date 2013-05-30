package de.jumpnbump.usecases.game.model.worldfactory;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import de.jumpnbump.usecases.game.factories.PlayerFactory;
import de.jumpnbump.usecases.game.factories.WallFactory;
import de.jumpnbump.usecases.game.model.ModelConstants;
import de.jumpnbump.usecases.game.model.Player;
import de.jumpnbump.usecases.game.model.Wall;

public class OneWallObjectsBuilder implements WorldObjectsBuilder {
	@Override
	public List<Player> createAllPlayers() {
		List<Player> allPlayers = new LinkedList<Player>();
		Player p1 = PlayerFactory.createPlayer1();
		allPlayers.add(p1);

		Player p2 = PlayerFactory.createPlayer2();

		allPlayers.add(p2);
		return allPlayers;
	}

	@Override
	public Collection<Wall> createAllWalls() {
		List<Wall> allWalls = new LinkedList<Wall>();
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
