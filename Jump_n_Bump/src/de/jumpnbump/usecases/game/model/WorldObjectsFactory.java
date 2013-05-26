package de.jumpnbump.usecases.game.model;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import de.jumpnbump.usecases.game.factories.PlayerFactory;
import de.jumpnbump.usecases.game.factories.WallFactory;

public class WorldObjectsFactory {

	public List<GameObject> createAllObjects() {
		List<GameObject> allObjects = new LinkedList<GameObject>();
		return allObjects;
	}

	public List<Player> createAllPlayers() {
		List<Player> allPlayers = new LinkedList<Player>();
		Player p1 = PlayerFactory.createPlayer1();
		allPlayers.add(p1);

		Player p2 = PlayerFactory.createPlayer2();

		allPlayers.add(p2);
		return allPlayers;
	}

	public Collection<Wall> createAllWalls() {
		List<Wall> allWalls = new LinkedList<Wall>();
		allWalls.add(WallFactory.createWall(0, 0.1, 0.5,
				ModelConstants.WALL_HEIGHT));
		allWalls.add(WallFactory.createWall(0.5, 0.3, 0.5,
				ModelConstants.WALL_HEIGHT));
		allWalls.add(WallFactory.createWall(0.5, 0.5, 0.25,
				ModelConstants.WALL_HEIGHT));
		allWalls.add(WallFactory.createWall(0.75, 0.8, 0.25,
				ModelConstants.WALL_HEIGHT));

		return allWalls;
	}

}
