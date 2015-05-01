package de.jumpnbump.usecases.viewer.viewer;

import java.util.Deque;
import java.util.LinkedList;

import de.oetting.bumpingbunnies.core.world.World;
import de.oetting.bumpingbunnies.model.game.objects.Background;
import de.oetting.bumpingbunnies.model.game.objects.IcyWall;
import de.oetting.bumpingbunnies.model.game.objects.Jumper;
import de.oetting.bumpingbunnies.model.game.objects.SpawnPoint;
import de.oetting.bumpingbunnies.model.game.objects.Wall;
import de.oetting.bumpingbunnies.model.game.objects.Water;

public class EditorModel {

	private World currentWorld;
	private World firstStateOfWorld;
	private final Deque<World> previousStates = new LinkedList<World>();
	
	public EditorModel(World world) {
		loadNewWorld(world);
	}
	
	public World getCurrentState() {
		return currentWorld;
	}

	public synchronized void storeState() {
		previousStates.push(cloneWorld(currentWorld));
	}
	
	private synchronized World cloneWorld(World world) {
		World clonedWorld = new World();
		world.getAllWalls().stream().forEach(wall -> clonedWorld.addWall(new Wall(wall)));
		world.getAllIcyWalls().stream().forEach(wall -> clonedWorld.addIcyWall(new IcyWall(wall)));
		world.getAllJumper().stream().forEach(wall -> clonedWorld.addJumper(new Jumper(wall)));
		world.getAllWaters().stream().forEach(wall -> clonedWorld.addWater(new Water(wall)));
		world.getBackgrounds().stream().forEach(background -> clonedWorld.addBackground(new Background(background)));
		world.getSpawnPoints().stream().forEach(spawnpoint -> clonedWorld.addSpawnpoint(new SpawnPoint(spawnpoint)));
		clonedWorld.sortObjectsByZIndex();
		return clonedWorld;
	}

	public synchronized void restorePreviousState() {
		if (previousStates.isEmpty())
			currentWorld = firstStateOfWorld;
		else 
			currentWorld = previousStates.pop();
	}

	public synchronized void loadNewWorld(World world) {
		previousStates.clear();
		this.currentWorld = world;
		this.firstStateOfWorld = cloneWorld(world);
	}

	public synchronized void clear() {
		loadNewWorld(new World());
	}
	
}
