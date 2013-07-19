package de.oetting.bumpingbunnies.usecases.game.model;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import android.content.Context;
import de.oetting.bumpingbunnies.logger.Logger;
import de.oetting.bumpingbunnies.logger.LoggerFactory;
import de.oetting.bumpingbunnies.usecases.game.ObjectProvider;
import de.oetting.bumpingbunnies.usecases.game.model.worldfactory.WorldObjectsBuilder;

public class World implements ObjectProvider {

	private static final Logger LOGGER = LoggerFactory.getLogger(World.class);
	private List<GameObject> allObjects;
	private List<GameObjectWithImage> allWalls;
	private List<Player> allPlayer;
	private List<SpawnPoint> spawnPoints;
	private WorldObjectsBuilder factory;
	private final Context context;

	public World(WorldObjectsBuilder factory, Context context) {
		super();
		this.factory = factory;
		this.context = context;
		this.allPlayer = new ArrayList<Player>();
		this.allWalls = new LinkedList<GameObjectWithImage>();
		this.allObjects = new LinkedList<GameObject>();
	}

	public void buildWorld() {
		this.allObjects.clear();
		this.allPlayer.clear();
		this.allWalls.addAll(this.factory.createAllWalls(this.context));
		this.allObjects.addAll(this.allPlayer);
		this.allObjects.addAll(this.allWalls);
		this.spawnPoints = this.factory.createSpawnPoints();
		LOGGER.info("Added %d objects and %d players", this.allObjects.size(),
				this.allPlayer.size());
	}

	@Override
	public List<GameObject> getAllObjects() {
		return this.allObjects;
	}

	@Override
	public List<Player> getAllPlayer() {
		return this.allPlayer;
	}

	public List<GameObjectWithImage> getAllWalls() {
		return this.allWalls;
	}

	public List<SpawnPoint> getSpawnPoints() {
		return this.spawnPoints;
	}

	public void addPlayer(Player p) {
		this.allPlayer.add(p);
	}

}
