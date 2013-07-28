package de.jumpnbump.usecases.viewer.xml;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import de.jumpnbump.usecases.viewer.model.Background;
import de.jumpnbump.usecases.viewer.model.GameObject;
import de.jumpnbump.usecases.viewer.model.IcyWall;
import de.jumpnbump.usecases.viewer.model.Jumper;
import de.jumpnbump.usecases.viewer.model.SpawnPoint;
import de.jumpnbump.usecases.viewer.model.Wall;
import de.jumpnbump.usecases.viewer.model.Water;

public class ObjectContainer {

	private List<Wall> walls = new ArrayList<>();
	private List<IcyWall> iceWalls = new ArrayList<>();
	private List<SpawnPoint> spawnPoints = new ArrayList<>();
	private List<Jumper> jumpers = new ArrayList<>();
	private List<Water> waters = new ArrayList<>();
	private List<Background> backgrounds = new ArrayList<>();
	private List<GameObject> allObjects;

	public List<Wall> getWalls() {
		return this.walls;
	}

	public void setWalls(List<Wall> walls) {
		this.walls = walls;
	}

	public List<IcyWall> getIceWalls() {
		return this.iceWalls;
	}

	public void setIceWalls(List<IcyWall> iceWalls) {
		this.iceWalls = iceWalls;
	}

	public List<SpawnPoint> getSpawnPoints() {
		return this.spawnPoints;
	}

	public void setSpawnPoints(List<SpawnPoint> spawnPoints) {
		this.spawnPoints = spawnPoints;
	}

	public List<Jumper> getJumpers() {
		return this.jumpers;
	}

	public void setJumpers(List<Jumper> jumpers) {
		this.jumpers = jumpers;
	}

	public List<Water> getWaters() {
		return this.waters;
	}

	public void setWaters(List<Water> waters) {
		this.waters = waters;
	}

	public List<GameObject> allObjects() {
		if (this.allObjects == null) {
			this.allObjects = new LinkedList<>();
			this.allObjects.addAll(getWalls());
			this.allObjects.addAll(getIceWalls());
			this.allObjects.addAll(getJumpers());
			this.allObjects.addAll(getWaters());
			this.allObjects.addAll(getBackgrounds());
		}
		return this.allObjects;
	}

	public List<Background> getBackgrounds() {
		return this.backgrounds;
	}

	public void setBackgrounds(List<Background> backgrounds) {
		this.backgrounds = backgrounds;
	}

}
