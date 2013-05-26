package de.jumpnbump.usecases.game.model;

import java.util.LinkedList;
import java.util.List;

import de.jumpnbump.usecases.game.factories.PlayerFactory;

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

}
