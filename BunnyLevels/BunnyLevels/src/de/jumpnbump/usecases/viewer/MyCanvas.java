package de.jumpnbump.usecases.viewer;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;

import de.jumpnbump.usecases.viewer.model.GameObject;
import de.jumpnbump.usecases.viewer.model.IcyWall;
import de.jumpnbump.usecases.viewer.model.Jumper;
import de.jumpnbump.usecases.viewer.model.SpawnPoint;
import de.jumpnbump.usecases.viewer.model.Wall;
import de.jumpnbump.usecases.viewer.model.Water;
import de.jumpnbump.usecases.viewer.xml.ObjectContainer;

public class MyCanvas extends Canvas {
	private static final long serialVersionUID = 1L;
	public static final int DIVIDER_X_CONST = 10000;
	public static final int DIVIDER_Y_CONST = 20000;
	public static final int SPAWN_RADIUS = 5;
	private ObjectContainer objectContainer;

	public MyCanvas(ObjectContainer container) {
		this.objectContainer = container;
	}

	@Override
	public void paint(Graphics g) {
		super.paint(g);
		paintWalls(g);
		paintIceWalls(g);
		paintJumpers(g);
		paintWaters(g);
		paintSpawnPoints(g);
	}

	private void paintWaters(Graphics g) {
		g.setColor(new Color(128, 128, 255));
		for (Water w : this.objectContainer.getWaters()) {
			drawObject(g, w);
		}
	}

	private void paintJumpers(Graphics g) {
		g.setColor(Color.YELLOW);
		for (Jumper w : this.objectContainer.getJumpers()) {
			drawObject(g, w);
		}
	}

	private void paintIceWalls(Graphics g) {
		g.setColor(Color.BLUE);
		for (IcyWall w : this.objectContainer.getIceWalls()) {
			drawObject(g, w);
		}
	}

	private void paintWalls(Graphics g) {
		g.setColor(Color.GRAY);
		for (Wall w : this.objectContainer.getWalls()) {
			drawObject(g, w);
		}
	}

	private void paintSpawnPoints(Graphics g) {
		g.setColor(Color.RED);
		for (SpawnPoint spawn : this.objectContainer.getSpawnPoints()) {
			drawSpawn(g, spawn);
		}
	}

	private void drawSpawn(Graphics g, SpawnPoint spawn) {
		g.fillOval(calculatePixelX(spawn.getX()),
				calculatePixelY(spawn.getY()),
				SPAWN_RADIUS, SPAWN_RADIUS);
	}

	private void drawObject(Graphics g, GameObject w) {
		g.fillRect(calculatePixelX(w.minX()), calculatePixelY(w.minY()),
				calculatePixelWidht(w.minX(), w.maxX()),
				calculateHeight(w.minY(), w.maxY()));
	}

	private int calculatePixelX(int origX) {
		return origX / DIVIDER_X_CONST;
	}

	private int calculatePixelWidht(int minX, int maxX) {
		return calculatePixelX(maxX - minX);
	}

	private int calculatePixelY(int origY) {
		return (int) (getHeight() * 0.9 - origY / DIVIDER_Y_CONST);
	}

	private int calculateHeight(int minY, int maxY) {
		return (-maxY + minY) / DIVIDER_Y_CONST;
	}

	public void setObjectContainer(ObjectContainer objectContainer) {
		this.objectContainer = objectContainer;
	}

}
