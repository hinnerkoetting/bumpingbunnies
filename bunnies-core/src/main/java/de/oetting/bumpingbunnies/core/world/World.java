package de.oetting.bumpingbunnies.core.world;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import de.oetting.bumpingbunnies.core.game.graphics.ZIndexComparator;
import de.oetting.bumpingbunnies.logger.Logger;
import de.oetting.bumpingbunnies.logger.LoggerFactory;
import de.oetting.bumpingbunnies.model.game.objects.Background;
import de.oetting.bumpingbunnies.model.game.objects.ConnectionIdentifier;
import de.oetting.bumpingbunnies.model.game.objects.GameObjectWithImage;
import de.oetting.bumpingbunnies.model.game.objects.IcyWall;
import de.oetting.bumpingbunnies.model.game.objects.Jumper;
import de.oetting.bumpingbunnies.model.game.objects.Bunny;
import de.oetting.bumpingbunnies.model.game.objects.SpawnPoint;
import de.oetting.bumpingbunnies.model.game.objects.Wall;
import de.oetting.bumpingbunnies.model.game.objects.Water;

public class World implements ObjectProvider {

	private static final Logger LOGGER = LoggerFactory.getLogger(World.class);

	private List<GameObjectWithImage> allCollidingObjects;
	private List<GameObjectWithImage> allDrawingObjects;
	private List<Wall> allWalls;
	private List<IcyWall> allIcyWalls;
	private List<Jumper> allJumpers;
	private List<Bunny> allPlayer;
	private List<SpawnPoint> allSpawnPoints;
	private List<Water> allWaters;
	private List<Background> backgrounds;

	public World() {
		this.allPlayer = new CopyOnWriteArrayList<Bunny>();
		this.allCollidingObjects = new LinkedList<GameObjectWithImage>();
		this.allDrawingObjects = new LinkedList<GameObjectWithImage>();
		this.allWalls = new ArrayList<Wall>();
		this.allIcyWalls = new LinkedList<IcyWall>();
		this.allJumpers = new LinkedList<Jumper>();
		this.allWaters = new LinkedList<Water>();
		this.allSpawnPoints = new ArrayList<SpawnPoint>();
		this.backgrounds = new LinkedList<Background>();
	}

	public void addToAllObjects() {
		addCollidingObjects(allCollidingObjects);
		addCollidingObjects(allDrawingObjects);
		allDrawingObjects.addAll(backgrounds);
	}

	private void addCollidingObjects(List<? super GameObjectWithImage> addToList) {
		// Objects which are added here first are first used during the
		// collision detection.
		addToList.addAll(this.allWaters);
		addToList.addAll(this.allJumpers);
		addToList.addAll(this.allIcyWalls);
		addToList.addAll(this.allWalls);
	}

	@Override
	public List<GameObjectWithImage> getAllObjects() {
		return this.allCollidingObjects;
	}

	@Override
	public List<Bunny> getAllPlayer() {
		return Collections.unmodifiableList(this.allPlayer);
	}

	public void addPlayer(Bunny player) {
		LOGGER.info("Adding player %s", player);
		allPlayer.add(player);
	}

	public Bunny findPlayer(int id) {
		for (Bunny p : this.allPlayer) {
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
		for (Bunny p : this.allPlayer) {
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

	public void removePlayer(Bunny p) {
		LOGGER.info("Remove player %d", p.id());
		boolean removed = allPlayer.remove(p);
		if (!removed)
			throw new IllegalArgumentException("Player was not removed");
	}

	@Override
	public String toString() {
		return "World [allObjects=" + allCollidingObjects + ", allWalls=" + allWalls + ", allIcyWalls=" + allIcyWalls
				+ ", allJumpers=" + allJumpers + ", allPlayer=" + allPlayer + ", allSpawnPoints=" + allSpawnPoints
				+ ", allWaters=" + allWaters + "]";
	}

	public boolean existsPlayer(int playerId) {
		for (Bunny player : allPlayer)
			if (player.id() == playerId)
				return true;
		return false;
	}

	public Bunny findPlayerOfConnection(ConnectionIdentifier owner) {
		synchronized (allPlayer) {
			for (Bunny p : allPlayer) {
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
		allCollidingObjects.add(object);
		allDrawingObjects.add(object);
	}

	private void removeDrawingAndCollidingObject(GameObjectWithImage object) {
		allCollidingObjects.remove(object);
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

	public void sortObjectsByZIndex() {
		Collections.sort(allDrawingObjects, new ZIndexComparator());
	}

	public void removeAllWallsFromDrawingObjects() {
		allDrawingObjects.removeAll(allWalls);
	}

	public void addAllWallstoDrawingObjects() {
		allDrawingObjects.addAll(allWalls);
		sortObjectsByZIndex();
	}

	public void addAllIceWallstoDrawingObjects() {
		allDrawingObjects.addAll(allIcyWalls);
		sortObjectsByZIndex();
	}

	public void removeAllIceWallsFromDrawingObjects() {
		allDrawingObjects.removeAll(allIcyWalls);
	}

	public void addAllWaterToDrawingObjects() {
		allDrawingObjects.addAll(allWaters);
		sortObjectsByZIndex();
	}

	public void removeAllWaterFromDrawingObjects() {
		allDrawingObjects.removeAll(allWaters);
	}

	public void addAllJumperToDrawingObjects() {
		allDrawingObjects.addAll(allJumpers);
		sortObjectsByZIndex();
	}

	public void removeAllJumperFromDrawingObjects() {
		allDrawingObjects.removeAll(allJumpers);
	}

	public void addAllBackgroundToDrawingObjects() {
		allDrawingObjects.addAll(backgrounds);
		sortObjectsByZIndex();
	}

	public void removeAllBackgroundFromDrawingObjects() {
		allDrawingObjects.removeAll(backgrounds);
	}

	public void removeAll(Collection<GameObjectWithImage> objectsToClean) {
		allDrawingObjects.removeAll(objectsToClean);
		allIcyWalls.removeAll(objectsToClean);
		allJumpers.removeAll(objectsToClean);
		allCollidingObjects.removeAll(objectsToClean);
		allWalls.removeAll(objectsToClean);
		allWaters.removeAll(objectsToClean);
		backgrounds.removeAll(objectsToClean);
		sortObjectsByZIndex();
	}

}
