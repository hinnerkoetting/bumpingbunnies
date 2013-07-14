package de.jumpnbump.usecases.viewer.xml;

import java.util.LinkedList;
import java.util.List;

import de.jumpnbump.usecases.viewer.model.IcyWall;
import de.jumpnbump.usecases.viewer.model.Jumper;
import de.jumpnbump.usecases.viewer.model.SpawnPoint;
import de.jumpnbump.usecases.viewer.model.Wall;

public class ObjectContainer {

	private List<Wall> walls;
	private List<IcyWall> iceWalls;
	private List<SpawnPoint> spawnPoints;;
	private List<Jumper> jumpers;

	public List<Wall> getWalls() {
		return walls;
	}

	public void setWalls(List<Wall> walls) {
		this.walls = walls;
	}

	public List<IcyWall> getIceWalls() {
		return iceWalls;
	}

	public void setIceWalls(List<IcyWall> iceWalls) {
		this.iceWalls = iceWalls;
	}

	public List<SpawnPoint> getSpawnPoints() {
		return spawnPoints;
	}

	public void setSpawnPoints(List<SpawnPoint> spawnPoints) {
		this.spawnPoints = spawnPoints;
	}

	public List<Jumper> getJumpers() {
		return jumpers;
	}

	public void setJumpers(List<Jumper> jumpers) {
		this.jumpers = jumpers;
	}

	@Override
	public String toString() {
		return "ObjectContainer [walls=" + walls + ", iceWalls=" + iceWalls
				+ ", spawnPoints=" + spawnPoints + ", jumpers=" + jumpers + "]";
	}

}
