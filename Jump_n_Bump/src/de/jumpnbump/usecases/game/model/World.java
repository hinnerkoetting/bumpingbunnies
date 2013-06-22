package de.jumpnbump.usecases.game.model;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import android.content.Context;
import de.jumpnbump.logger.Logger;
import de.jumpnbump.logger.MyLog;
import de.jumpnbump.usecases.game.ObjectProvider;
import de.jumpnbump.usecases.game.model.worldfactory.WorldObjectsBuilder;

public class World implements ObjectProvider {

	private static final MyLog LOGGER = Logger.getLogger(World.class);
	private List<GameObject> allObjects;
	private List<FixedWorldObject> allWalls;
	private List<Player> allPlayer;
	private List<SpawnPoint> spawnPoints;
	private WorldObjectsBuilder factory;
	private final Context context;

	public World(WorldObjectsBuilder factory, Context context) {
		super();
		this.factory = factory;
		this.context = context;
		this.allPlayer = new ArrayList<Player>();
		this.allWalls = new LinkedList<FixedWorldObject>();
		this.allObjects = new LinkedList<GameObject>();
	}

	public void buildWorld(int numberPlayer) {
		this.allObjects.clear();
		this.allPlayer.clear();
		this.allPlayer.addAll(this.factory.createAllPlayers(numberPlayer));
		this.allWalls.addAll(this.factory.createAllWalls(this.context));
		this.allObjects.addAll(this.allPlayer);
		this.allObjects.addAll(this.allWalls);
		this.spawnPoints = this.factory.createSpawnPoints();
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

	@Override
	public List<GameObject> getAllObjects() {
		return this.allObjects;
	}

	@Override
	public List<Player> getAllPlayer() {
		return this.allPlayer;
	}

	public List<FixedWorldObject> getAllWalls() {
		return this.allWalls;
	}

	public List<SpawnPoint> getSpawnPoints() {
		return this.spawnPoints;
	}

}
