package de.oetting.bumpingbunnies.core.world;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import de.oetting.bumpingbunnies.logger.Logger;
import de.oetting.bumpingbunnies.logger.LoggerFactory;
import de.oetting.bumpingbunnies.model.game.objects.GameObjectWithImage;
import de.oetting.bumpingbunnies.model.game.objects.IcyWall;
import de.oetting.bumpingbunnies.model.game.objects.Jumper;
import de.oetting.bumpingbunnies.model.game.objects.Player;
import de.oetting.bumpingbunnies.model.game.objects.SpawnPoint;
import de.oetting.bumpingbunnies.model.game.objects.Wall;
import de.oetting.bumpingbunnies.model.game.objects.Water;

public class World implements ObjectProvider {

	private static final Logger LOGGER = LoggerFactory.getLogger(World.class);

	private List<GameObjectWithImage> allObjects;
	private List<Wall> allWalls;
	private List<IcyWall> allIcyWalls;
	private List<Jumper> allJumpers;
	private List<Player> allPlayer;
	private List<SpawnPoint> allSpawnPoints;
	private List<Water> allWaters;

	public World() {
		super();
		this.allPlayer = new CopyOnWriteArrayList<Player>();
		this.allObjects = new LinkedList<GameObjectWithImage>();
		this.allWalls = new ArrayList<Wall>();
		this.allIcyWalls = new LinkedList<IcyWall>();
		this.allJumpers = new LinkedList<Jumper>();
		this.allWaters = new LinkedList<Water>();
		this.allSpawnPoints = new ArrayList<SpawnPoint>();
	}

	public void addToAllObjects() {
		this.allObjects.addAll(this.allWalls);
		this.allObjects.addAll(this.allIcyWalls);
		this.allObjects.addAll(this.allJumpers);
		this.allObjects.addAll(this.allWaters);
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
		return this.allSpawnPoints;
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
		return this.allWaters;
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

	public void replaceAllWalls(Collection<Wall> walls) {
		allWalls.clear();
		allWalls.addAll(walls);
	}

	public void replaceAllIcyWalls(Collection<IcyWall> icyWalls) {
		allIcyWalls.clear();
		allIcyWalls.addAll(icyWalls);
	}

	public void replaceAllJumpers(Collection<Jumper> jumpers) {
		allJumpers.clear();
		allJumpers.addAll(jumpers);
	}

	public void replaceAllWaters(Collection<Water> newWaters) {
		allWaters.clear();
		allWaters.addAll(newWaters);
	}

	public void replaceAllSpawnPoints(List<SpawnPoint> newSpawnPoints) {
		allSpawnPoints.clear();
		allSpawnPoints.addAll(newSpawnPoints);
	}

	public void removePlayer(Player p) {
		LOGGER.info("Remove player %d", p.id());
		boolean removed = allPlayer.remove(p);
		if (!removed)
			throw new IllegalArgumentException("Player was not removed");
	}

	@Override
	public String toString() {
		return "World [allObjects=" + allObjects + ", allWalls=" + allWalls + ", allIcyWalls=" + allIcyWalls + ", allJumpers=" + allJumpers + ", allPlayer="
				+ allPlayer + ", allSpawnPoints=" + allSpawnPoints + ", allWaters=" + allWaters + "]";
	}

	public boolean existsPlayer(int playerId) {
		for (Player player : allPlayer)
			if (player.id() == playerId)
				return true;
		return false;
	}

}
