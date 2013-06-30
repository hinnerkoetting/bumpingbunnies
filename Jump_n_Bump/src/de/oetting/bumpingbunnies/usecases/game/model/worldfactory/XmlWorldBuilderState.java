package de.oetting.bumpingbunnies.usecases.game.model.worldfactory;

import java.util.LinkedList;
import java.util.List;

import de.oetting.bumpingbunnies.usecases.game.model.FixedWorldObject;
import de.oetting.bumpingbunnies.usecases.game.model.SpawnPoint;

public class XmlWorldBuilderState {

	private List<FixedWorldObject> allObjects = new LinkedList<FixedWorldObject>();
	private List<SpawnPoint> spawnPoints = new LinkedList<SpawnPoint>();

	public List<FixedWorldObject> getAllObjects() {
		return this.allObjects;
	}

	public void setAllObjects(List<FixedWorldObject> allObjects) {
		this.allObjects = allObjects;
	}

	public List<SpawnPoint> getSpawnPoints() {
		return this.spawnPoints;
	}

	public void setSpawnPoints(List<SpawnPoint> spawnPoints) {
		this.spawnPoints = spawnPoints;
	}

}
