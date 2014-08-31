package de.oetting.bumpingbunnies.usecases.game.model;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import de.oetting.bumpingbunnies.core.world.ObjectProvider;
import de.oetting.bumpingbunnies.logger.Logger;
import de.oetting.bumpingbunnies.logger.LoggerFactory;
import de.oetting.bumpingbunnies.usecases.game.configuration.PlayerProperties;
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

	public World() {
		super();
		this.allPlayer = new ArrayList<Player>();
		this.allObjects = new LinkedList<GameObjectWithImage>();
	}

	public void buildWorld(WorldObjectsBuilder factory) {
		this.allObjects.clear();
		this.allPlayer.clear();
		this.allWalls = new LinkedList<Wall>(factory.getAllWalls());
		this.allIcyWalls = new LinkedList<IcyWall>(factory.getAllIcyWalls());
		this.allJumpers = new LinkedList<Jumper>(factory.getAllJumpers());
		this.waters = new LinkedList<Water>(factory.getAllWaters());
		addToAllObjects();
		this.spawnPoints = factory.createSpawnPoints();
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

	public Player findPlayer(int id) {
		for (Player p : this.allPlayer) {
			if (p.id() == id) {
				return p;
			}
		}
		throw new PlayerDoesNotExist(id);
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

	public class PlayerDoesNotExist extends RuntimeException {
		public PlayerDoesNotExist(int playerId) {
			super("Id not found: " + playerId);
		}
	}

	public int getNextPlayerId() {
		return findMaxPlayerId() + 1;
	}

	private int findMaxPlayerId() {
		int maxId = -1;
		for (Player p : this.allPlayer) {
			if (p.id() > maxId) {
				maxId = p.id();
			}
		}
		return maxId;
	}

	public List<PlayerProperties> getPlayerProperties() {
		List<PlayerProperties> properties = new ArrayList<PlayerProperties>(
				this.allPlayer.size() - 1);
		for (Player p : this.allPlayer) {
			properties.add(new PlayerProperties(p.id(), p.getName()));
		}
		return properties;
	}
}
