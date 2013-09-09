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
	private List<GameObjectWithImage> allObjects;
	private List<Wall> allWalls;
	private List<IcyWall> allIcyWalls;
	private List<Jumper> allJumpers;
	private List<Player> allPlayer;
	private List<SpawnPoint> spawnPoints;
	private List<Water> waters;
	private WorldObjectsBuilder factory;
	private final Context context;

	public World(WorldObjectsBuilder factory, Context context) {
		super();
		this.factory = factory;
		this.context = context;
		this.allPlayer = new ArrayList<Player>();
		this.allObjects = new LinkedList<GameObjectWithImage>();
	}

	public void buildWorld() {
		this.factory.build(this.context);
		this.allObjects.clear();
		this.allPlayer.clear();
		this.allWalls = new LinkedList<Wall>(this.factory.getAllWalls());
		this.allIcyWalls = new LinkedList<IcyWall>(this.factory.getAllIcyWalls());
		this.allJumpers = new LinkedList<Jumper>(this.factory.getAllJumpers());
		this.waters = new LinkedList<Water>(this.factory.getAllWaters());
		addToAllObjects();
		this.spawnPoints = this.factory.createSpawnPoints();
		LOGGER.info("Added %d objects and %d players", this.allObjects.size(),
				this.allPlayer.size());
	}

	private void addToAllObjects() {
		this.allObjects.addAll(this.allWalls);
		this.allObjects.addAll(this.allIcyWalls);
		this.allObjects.addAll(this.allJumpers);
		this.allObjects.addAll(this.waters);
	}

	@Override
	public List<GameObjectWithImage> getAllObjects() {
		return this.allObjects;
	}

	@Override
	public List<Player> getAllPlayer() {
		return this.allPlayer;
	}

	@Override
	public List<Wall> getAllWalls() {
		return this.allWalls;
	}

	public List<SpawnPoint> getSpawnPoints() {
		return this.spawnPoints;
	}

	public void addPlayer(Player p) {
		this.allPlayer.add(p);
	}

	@Override
	public List<Jumper> getAllJumper() {
		return this.allJumpers;
	}

	@Override
	public List<IcyWall> getAllIcyWalls() {
		return this.allIcyWalls;
	}

	@Override
	public List<Water> getAllWaters() {
		return this.waters;
	}

}
