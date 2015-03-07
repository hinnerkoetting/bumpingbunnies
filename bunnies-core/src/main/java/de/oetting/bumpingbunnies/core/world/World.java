package de.oetting.bumpingbunnies.core.world;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import de.oetting.bumpingbunnies.logger.Logger;
import de.oetting.bumpingbunnies.logger.LoggerFactory;
import de.oetting.bumpingbunnies.model.game.objects.Background;
import de.oetting.bumpingbunnies.model.game.objects.ConnectionIdentifier;
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
	private List<GameObjectWithImage> allDrawingObjects;
	private List<Wall> allWalls;
	private List<IcyWall> allIcyWalls;
	private List<Jumper> allJumpers;
	private List<Player> allPlayer;
	private List<SpawnPoint> allSpawnPoints;
	private List<Water> allWaters;
	private List<Background> backgrounds;

	public World() {
		this.allPlayer = new CopyOnWriteArrayList<Player>();
		this.allObjects = new LinkedList<GameObjectWithImage>();
		this.allDrawingObjects = new LinkedList<GameObjectWithImage>();
		this.allWalls = new ArrayList<Wall>();
		this.allIcyWalls = new LinkedList<IcyWall>();
		this.allJumpers = new LinkedList<Jumper>();
		this.allWaters = new LinkedList<Water>();
		this.allSpawnPoints = new ArrayList<SpawnPoint>();
		this.backgrounds = new LinkedList<Background>();
	}

	public void addToAllObjects() {
		addCollidingObjects(allObjects);
		addCollidingObjects(allDrawingObjects);
		allDrawingObjects.addAll(backgrounds);
	}

	private void addCollidingObjects(List<? super GameObjectWithImage> addToList) {
		addToList.addAll(this.allWalls);
		addToList.addAll(this.allIcyWalls);
		addToList.addAll(this.allJumpers);
		addToList.addAll(this.allWaters);
	}

	@Override
	public List<GameObjectWithImage> getAllObjects() {
		return this.allObjects;
	}

	@Override
	public List<Player> getAllPlayer() {
		return Collections.unmodifiableList(this.allPlayer);
	}

	public void addPlayer(Player player) {
		LOGGER.info("Adding player %s", player);
		allPlayer.add(player);
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

	public List<Background> getBackgrounds() {
		return backgrounds;
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

	public Player findPlayerOfConnection(ConnectionIdentifier owner) {
		synchronized (allPlayer) {
			for (Player p : allPlayer) {
				if (p.getOpponent().equals(owner)) {
					return p;
				}
			}
		}
		throw new IllegalArgumentException("No player on this connection exists " + owner);
	}

	public void replaceBackgrounds(List<Background> parseBackgrounds) {
		this.backgrounds.clear();
		backgrounds.addAll(parseBackgrounds);
	}

	public List<GameObjectWithImage> getAllDrawingObjects() {
		return allDrawingObjects;
	}

	public void addWall(Wall newWall) {
		allWalls.add(newWall);
		addDrawingAndCollidingObject(newWall);
	}

	public void removeIcyWall(IcyWall object) {
		allIcyWalls.remove(object);
		removeDrawingAndCollidingObject(object);
	}

	public void removeWall(Wall object) {
		allWalls.remove(object);
		removeDrawingAndCollidingObject(object);
	}

	public void removeJumper(Jumper object) {
		allJumpers.remove(object);
		removeDrawingAndCollidingObject(object);
	}

	public void removeWater(Water object) {
		allWaters.remove(object);
		removeDrawingAndCollidingObject(object);
	}

	public void removeBackground(Background object) {
		backgrounds.remove(object);
		allDrawingObjects.remove(object);
	}

	public void addIcyWall(IcyWall newWall) {
		allIcyWalls.add(newWall);
		addDrawingAndCollidingObject(newWall);
	}

	private void addDrawingAndCollidingObject(GameObjectWithImage object) {
		allObjects.add(object);
		allDrawingObjects.add(object);
	}

	private void removeDrawingAndCollidingObject(GameObjectWithImage object) {
		allObjects.remove(object);
		allDrawingObjects.remove(object);
	}

	public void addJumper(Jumper newJumper) {
		allJumpers.add(newJumper);
		addDrawingAndCollidingObject(newJumper);
	}

	public void addBackground(Background newBackground) {
		backgrounds.add(newBackground);
		allDrawingObjects.add(newBackground);
	}

	public void addWater(Water newWater) {
		allWaters.add(newWater);
		addDrawingAndCollidingObject(newWater);
	}
}
