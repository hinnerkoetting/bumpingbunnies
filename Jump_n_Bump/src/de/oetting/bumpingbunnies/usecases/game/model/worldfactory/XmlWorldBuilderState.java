package de.oetting.bumpingbunnies.usecases.game.model.worldfactory;

import java.util.LinkedList;
import java.util.List;

import de.oetting.bumpingbunnies.usecases.game.model.GameObject;
import de.oetting.bumpingbunnies.usecases.game.model.SpawnPoint;

public class XmlWorldBuilderState {

	private List<GameObject> allObjects = new LinkedList<GameObject>();
	private List<SpawnPoint> spawnPoints = new LinkedList<SpawnPoint>();

	public List<GameObject> getAllObjects() {
		return this.allObjects;
	}

	public void setAllObjects(List<GameObject> allObjects) {
		this.allObjects = allObjects;
	}

	public List<SpawnPoint> getSpawnPoints() {
		return this.spawnPoints;
	}

	public void setSpawnPoints(List<SpawnPoint> spawnPoints) {
		this.spawnPoints = spawnPoints;
	}

}
