package de.jumpnbump.usecases.game.model;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import de.jumpnbump.logger.Logger;
import de.jumpnbump.logger.MyLog;

public class World {

	private static final MyLog LOGGER = Logger.getLogger(World.class);
	private List<GameObject> allObjects;
	private List<Wall> allWalls;
	private List<Player> allPlayer;
	private WorldObjectsFactory factory;

	public World(WorldObjectsFactory factory) {
		super();
		this.factory = factory;
		this.allPlayer = new ArrayList<Player>(2);
		this.allObjects = factory.createAllObjects();
		this.allWalls = new LinkedList<Wall>();
	}

	public void buildWorld() {
		this.allObjects.clear();
		this.allPlayer.clear();
		this.allObjects.addAll(this.factory.createAllObjects());
		this.allPlayer.addAll(this.factory.createAllPlayers());
		this.allWalls.addAll(this.factory.createAllWalls());
		this.allObjects.addAll(this.allPlayer);
		this.allObjects.addAll(this.allWalls);
		LOGGER.info("Added %d objects and %d players", this.allObjects.size(),
				this.allPlayer.size());
	}

	@Deprecated
	public Player getPlayer1() {
		return this.allPlayer.get(0);
	}

	@Deprecated
	public Player getPlayer2() {
		return this.allPlayer.get(1);
	}

	public List<GameObject> getAllObjects() {
		return this.allObjects;
	}

	public List<Player> getAllPlayer() {
		return this.allPlayer;
	}

	public List<Wall> getAllWalls() {
		return this.allWalls;
	}

}
