package de.oetting.bumpingbunnies.usecases.game.model.worldfactory;

import java.util.LinkedList;
import java.util.List;

import de.oetting.bumpingbunnies.usecases.game.model.GameObjectWithImage;
import de.oetting.bumpingbunnies.usecases.game.model.SpawnPoint;

public class XmlWorldBuilderState {

	private List<GameObjectWithImage> allObjects = new LinkedList<GameObjectWithImage>();
	private List<SpawnPoint> spawnPoints = new LinkedList<SpawnPoint>();

	public List<GameObjectWithImage> getAllObjects() {
		return this.allObjects;
	}

	public void setAllObjects(List<GameObjectWithImage> allObjects) {
		this.allObjects = allObjects;
	}

	public List<SpawnPoint> getSpawnPoints() {
		return this.spawnPoints;
	}

	public void setSpawnPoints(List<SpawnPoint> spawnPoints) {
		this.spawnPoints = spawnPoints;
	}

}
