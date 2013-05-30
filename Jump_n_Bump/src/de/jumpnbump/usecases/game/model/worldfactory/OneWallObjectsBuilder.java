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
		allWalls.add(WallFactory.createWall(0, ModelConstants.MAX_VALUE / 2,
				ModelConstants.MAX_VALUE, ModelConstants.MAX_VALUE / 3 * 2));

		return allWalls;
	}

}
