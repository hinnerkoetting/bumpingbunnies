package de.jumpnbump.usecases.game.model.worldfactory;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import de.jumpnbump.usecases.game.factories.PlayerFactory;
import de.jumpnbump.usecases.game.factories.WallFactory;
import de.jumpnbump.usecases.game.model.ModelConstants;
import de.jumpnbump.usecases.game.model.Player;
import de.jumpnbump.usecases.game.model.Wall;

public class FirstWorldObjectsBuilder implements WorldObjectsBuilder {

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
		allWalls.add(WallFactory.createWall(
				(int) (0.1 * ModelConstants.MAX_VALUE),
				(int) (0.15 * ModelConstants.MAX_VALUE),
				(int) (0.5 * ModelConstants.MAX_VALUE),
				(ModelConstants.WALL_HEIGHT * ModelConstants.MAX_VALUE)));

		allWalls.add(WallFactory.createWall(
				(int) (0.0 * ModelConstants.MAX_VALUE),
				(int) (0.3 * ModelConstants.MAX_VALUE),
				(int) (0.2 * ModelConstants.MAX_VALUE),
				(ModelConstants.WALL_HEIGHT * ModelConstants.MAX_VALUE)));

		allWalls.add(WallFactory.createWall(
				(int) (0.70 * ModelConstants.MAX_VALUE),
				(int) (0.35 * ModelConstants.MAX_VALUE),
				(int) (0.15 * ModelConstants.MAX_VALUE),
				(ModelConstants.WALL_HEIGHT)));
		allWalls.add(WallFactory.createWall(
				(int) (0.3 * ModelConstants.MAX_VALUE),
				(int) (0.45 * ModelConstants.MAX_VALUE),
				(int) (0.25 * ModelConstants.MAX_VALUE),
				(ModelConstants.WALL_HEIGHT)));

		allWalls.add(WallFactory.createWall(
				(int) (0.45 * ModelConstants.MAX_VALUE),
				(int) (0.6 * ModelConstants.MAX_VALUE),
				(int) (0.25 * ModelConstants.MAX_VALUE),
				(ModelConstants.WALL_HEIGHT)));

		allWalls.add(WallFactory.createWall(
				(int) (0.75 * ModelConstants.MAX_VALUE),
				(int) (0.8 * ModelConstants.MAX_VALUE),
				(int) (0.25 * ModelConstants.MAX_VALUE),
				(ModelConstants.WALL_HEIGHT)));
		allWalls.add(WallFactory.createWall(
				(int) (0.25 * ModelConstants.MAX_VALUE),
				(int) (0.8 * ModelConstants.MAX_VALUE),
				(int) (0.25 * ModelConstants.MAX_VALUE),
				(ModelConstants.WALL_HEIGHT)));

		return allWalls;
	}

}
