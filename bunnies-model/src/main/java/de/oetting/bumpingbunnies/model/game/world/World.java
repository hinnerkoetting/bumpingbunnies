package de.oetting.bumpingbunnies.model.game.world;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import de.oetting.bumpingbunnies.logger.Logger;
import de.oetting.bumpingbunnies.logger.LoggerFactory;
import de.oetting.bumpingbunnies.model.game.objects.Background;
import de.oetting.bumpingbunnies.model.game.objects.Bunny;
import de.oetting.bumpingbunnies.model.game.objects.BunnyComparator;
import de.oetting.bumpingbunnies.model.game.objects.ConnectionIdentifier;
import de.oetting.bumpingbunnies.model.game.objects.GameObject;
import de.oetting.bumpingbunnies.model.game.objects.GameObjectWithImage;
import de.oetting.bumpingbunnies.model.game.objects.IcyWall;
import de.oetting.bumpingbunnies.model.game.objects.Jumper;
import de.oetting.bumpingbunnies.model.game.objects.ModelConstants;
import de.oetting.bumpingbunnies.model.game.objects.Rect;
import de.oetting.bumpingbunnies.model.game.objects.SpawnPoint;
import de.oetting.bumpingbunnies.model.game.objects.Wall;
import de.oetting.bumpingbunnies.model.game.objects.Water;

public class World implements ObjectProvider {

	private static final Logger LOGGER = LoggerFactory.getLogger(World.class);

	private final List<GameObjectWithImage> allCollidingObjects;
	private final List<GameObjectWithImage> allDrawingObjects;
	private final List<Wall> allWalls;
	private final List<IcyWall> allIcyWalls;
	private final List<Jumper> allJumpers;
	private List<Bunny> connectedBunnies;
	private final List<Bunny> disconnectedBunnies;
	private final List<SpawnPoint> allSpawnPoints;
	private final List<Water> allWaters;
	private final List<Background> backgrounds;
	private final List<Segment> segments;
	private final WorldProperties properties;

	public World(WorldProperties properties) {
		this.properties = properties;
		this.connectedBunnies = new CopyOnWriteArrayList<Bunny>();
		this.allCollidingObjects = new LinkedList<GameObjectWithImage>();
		this.allDrawingObjects = new LinkedList<GameObjectWithImage>();
		this.allWalls = new ArrayList<Wall>();
		this.allIcyWalls = new LinkedList<IcyWall>();
		this.allJumpers = new LinkedList<Jumper>();
		this.allWaters = new LinkedList<Water>();
		this.allSpawnPoints = new ArrayList<SpawnPoint>();
		this.backgrounds = new LinkedList<Background>();
		this.disconnectedBunnies = new ArrayList<Bunny>();
		this.segments = new ArrayList<Segment>();
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
	public List<Bunny> getAllConnectedBunnies() {
		return Collections.unmodifiableList(this.connectedBunnies);
	}

	public void addBunny(Bunny player) {
		LOGGER.info("Adding player %s", player);
		List<Bunny> newList = new ArrayList<Bunny>(connectedBunnies.size() + 1);
		newList.addAll(connectedBunnies);
		newList.add(player);
		Collections.sort(newList, new BunnyComparator());
		connectedBunnies = new CopyOnWriteArrayList<Bunny>(newList);
	}

	public Bunny findBunny(int id) {
		for (Bunny p : this.connectedBunnies) {
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

	public int getNextBunnyId() {
		int max = 0;
		List<Bunny> connectedAndDisconnectedBunnies = getConnectedAndDisconnectedBunnies();
		for (Bunny bunny : connectedAndDisconnectedBunnies) {
			if (bunny.id() > max)
				max = bunny.id();
		}
		return max + 1;
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

	public void disconnectBunny(Bunny p) {
		LOGGER.info("Remove player %d", p.id());
		boolean removed = connectedBunnies.remove(p);
		if (!removed)
			throw new IllegalArgumentException("Player was not removed");
		disconnectedBunnies.add(p);
	}

	@Override
	public String toString() {
		return "World [allObjects=" + allCollidingObjects + ", allWalls=" + allWalls + ", allIcyWalls=" + allIcyWalls
				+ ", allJumpers=" + allJumpers + ", allPlayer=" + connectedBunnies + ", allSpawnPoints="
				+ allSpawnPoints + ", allWaters=" + allWaters + "]";
	}

	public boolean existsBunny(int playerId) {
		for (Bunny player : connectedBunnies)
			if (player.id() == playerId)
				return true;
		return false;
	}

	public Bunny findBunnyOfConnection(ConnectionIdentifier owner) {
		for (Bunny p : connectedBunnies) {
			if (p.getOpponent().equals(owner)) {
				return p;
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

	public void addSpawnpoint(SpawnPoint spawnpoint) {
		allSpawnPoints.add(spawnpoint);
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

	public List<Bunny> getConnectedAndDisconnectedBunnies() {
		List<Bunny> list = new ArrayList<Bunny>();
		list.addAll(connectedBunnies);
		list.addAll(disconnectedBunnies);
		return list;
	}

	public int getIndexOfPlayer(Bunny player) {
		return connectedBunnies.indexOf(player);
	}

	public void initSegments() {
		int numberOfSegmentsVertically = 3;
		int numberOfSegmentsHorizontally = 3;
		for (int x = 0; x < numberOfSegmentsHorizontally; x++) {
			for (int y = 0; y < numberOfSegmentsVertically; y++) {
				long height = properties.getWorldHeight() / numberOfSegmentsVertically;
				long width = properties.getWorldWidth() / numberOfSegmentsVertically;
				Segment segment = new Segment(new Rect(x * width - ModelConstants.BUNNY_GAME_WIDTH, y * height
						- ModelConstants.BUNNY_GAME_HEIGHT, (x + 1) * width + ModelConstants.BUNNY_GAME_WIDTH, (y + 1)
						* height + ModelConstants.BUNNY_GAME_HEIGHT));
				segment.addObjects(getAllObjects());
				segments.add(segment);
			}
		}
	}

	@Override
	public List<GameObject> getCandidateForCollisionObjects(Bunny bunny) {
		if (segments.isEmpty()) {
			initSegments();
		}
		for (Segment segment : segments) {
			if (fitsHorizontallyCompletely(bunny, segment) && fitsVerticallyCompletely(bunny, segment)) {
				return segment.getObjectsInSegment();
			}
		}
		//Outside of world
		return new ArrayList<GameObject>();
//		throw new IllegalStateException("There has to be one segment in which this bunny fits");
	}

	private boolean fitsHorizontallyCompletely(Bunny bunny, Segment segment) {
		return segment.getMinX() < bunny.minX() && segment.getMaxX() > bunny.maxX();
	}

	private boolean fitsVerticallyCompletely(Bunny bunny, Segment segment) {
		return segment.getMinY() < bunny.minY() && segment.getMaxY() > bunny.maxY();
	}

	public WorldProperties getProperties() {
		return properties;
	}
	
	
	
	
}
