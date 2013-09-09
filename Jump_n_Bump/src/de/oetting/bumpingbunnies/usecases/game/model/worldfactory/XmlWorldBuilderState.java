package de.oetting.bumpingbunnies.usecases.game.model.worldfactory;

import java.util.LinkedList;
import java.util.List;

import de.oetting.bumpingbunnies.usecases.game.model.IcyWall;
import de.oetting.bumpingbunnies.usecases.game.model.Jumper;
import de.oetting.bumpingbunnies.usecases.game.model.SpawnPoint;
import de.oetting.bumpingbunnies.usecases.game.model.Wall;
import de.oetting.bumpingbunnies.usecases.game.model.Water;

public class XmlWorldBuilderState {

	private List<Wall> allWalls = new LinkedList<Wall>();
	private List<IcyWall> allIcyWalls = new LinkedList<IcyWall>();
	private List<Jumper> allJumper = new LinkedList<Jumper>();
	private List<SpawnPoint> spawnPoints = new LinkedList<SpawnPoint>();
	private List<Water> waters = new LinkedList<Water>();

	public List<Wall> getAllWalls() {
		return this.allWalls;
	}

	public void setAllWalls(List<Wall> allWalls) {
		this.allWalls = allWalls;
	}

	public List<IcyWall> getAllIcyWalls() {
		return this.allIcyWalls;
	}

	public void setAllIcyWalls(List<IcyWall> allIcyWalls) {
		this.allIcyWalls = allIcyWalls;
	}

	public List<Jumper> getAllJumper() {
		return this.allJumper;
	}

	public void setAllJumper(List<Jumper> allJumper) {
		this.allJumper = allJumper;
	}

	public List<SpawnPoint> getSpawnPoints() {
		return this.spawnPoints;
	}

	public void setSpawnPoints(List<SpawnPoint> spawnPoints) {
		this.spawnPoints = spawnPoints;
	}

	public List<Water> getWaters() {
		return this.waters;
	}

	public void setWaters(List<Water> waters) {
		this.waters = waters;
	}

}
