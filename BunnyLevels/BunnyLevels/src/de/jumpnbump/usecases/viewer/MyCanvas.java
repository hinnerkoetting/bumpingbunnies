package de.jumpnbump.usecases.viewer;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Stroke;

import javax.swing.JPanel;

import de.jumpnbump.usecases.viewer.Viewer.CoordinatesCalculation;
import de.jumpnbump.usecases.viewer.model.Background;
import de.jumpnbump.usecases.viewer.model.GameObject;
import de.jumpnbump.usecases.viewer.model.IcyWall;
import de.jumpnbump.usecases.viewer.model.Jumper;
import de.jumpnbump.usecases.viewer.model.SpawnPoint;
import de.jumpnbump.usecases.viewer.model.Wall;
import de.jumpnbump.usecases.viewer.model.Water;
import de.jumpnbump.usecases.viewer.xml.ObjectContainer;

public class MyCanvas extends JPanel {
	private static final long serialVersionUID = 1L;

	public static final int SPAWN_RADIUS = 5;
	private ObjectContainer objectContainer;
	private Object selectedObject;
	private double zoom;

	public MyCanvas(ObjectContainer container) {
		this.objectContainer = container;
		this.zoom = 1;
	}

	@Override
	public void paint(Graphics g) {
		super.paint(g);
		g.clearRect(0, 0, getWidth(), getHeight());
		drawBackground(g);
		paintWalls(g);
		paintIceWalls(g);
		paintJumpers(g);
		paintWaters(g);
		paintSpawnPoints(g);
	}

	private void paintWaters(Graphics g) {
		for (Water w : this.objectContainer.getWaters()) {
			g.setColor(new Color(128, 128, 255));
			drawObject(g, w);
		}
	}

	private void paintJumpers(Graphics g) {
		for (Jumper w : this.objectContainer.getJumpers()) {
			g.setColor(Color.YELLOW);
			drawObject(g, w);
		}
	}

	private void paintIceWalls(Graphics g) {
		for (IcyWall w : this.objectContainer.getIceWalls()) {
			g.setColor(Color.BLUE);
			drawObject(g, w);
		}
	}

	private void paintWalls(Graphics g) {
		for (Wall w : this.objectContainer.getWalls()) {
			g.setColor(Color.GRAY);
			drawObject(g, w);
		}
	}

	private void paintSpawnPoints(Graphics g) {
		for (SpawnPoint spawn : this.objectContainer.getSpawnPoints()) {
			g.setColor(Color.RED);
			drawSpawn(g, spawn);
		}
	}

	private void drawSpawn(Graphics g, SpawnPoint spawn) {
		if (spawn == this.selectedObject) {
			g.setColor(Color.GREEN);
		}
		g.fillOval(calculatePixelX(spawn.getX()),
				calculatePixelY(spawn.getY()),
				SPAWN_RADIUS, SPAWN_RADIUS);
	}

	private void drawBackground(Graphics g) {
		for (Background bf : this.objectContainer.getBackgrounds()) {
			g.setColor(Color.LIGHT_GRAY);
			drawObject(g, bf);
		}
	}

	private int calculatePixelY(int y) {
		return (int) (CoordinatesCalculation.calculatePixelY(y, getHeight()) / this.zoom);
	}

	private int calculatePixelX(int x) {
		return (int) (CoordinatesCalculation.calculatePixelX(x) / this.zoom);
	}

	private void drawObject(Graphics g, GameObject w) {
		int height = calculateHeight(w.minY(), w.maxY());
		int minX = calculatePixelX(w.minX());
		int width = calculatePixelWidht(w.minX(), w.maxX());
		int minY = calculatePixelY(w.minY()) - height;
		if (w.hasImage()) {
			g.drawImage(w.getImage(), minX, minY, width, height, null);
		} else {
			g.fillRect(minX, minY, width, height);
		}
		if (w == this.selectedObject) {
			Graphics2D g2d = (Graphics2D) g;
			Stroke oldStroke = g2d.getStroke();
			g2d.setStroke(new BasicStroke(5));
			g2d.setColor(Color.GREEN);
			// g.drawRect(calculatePixelX(w.minX()), calculatePixelY(w.minY()),
			// calculatePixelWidht(w.minX(), w.maxX()),
			// calculateHeight(w.minY(), w.maxY()));

			g2d.draw(new Rectangle(minX, minY, width, height));

			g2d.setStroke(oldStroke);
		}
	}

	private int calculatePixelWidht(int minX, int maxX) {
		return (int) (CoordinatesCalculation.calculatePixelWidht(minX, maxX) / this.zoom);
	}

	private int calculateHeight(int minY, int maxY) {
		return (int) (CoordinatesCalculation.calculateHeight(minY, maxY) / this.zoom);
	}

	public void setObjectContainer(ObjectContainer objectContainer) {
		this.objectContainer = objectContainer;
	}

	public void setSelectedObject(Object selectedObject) {
		this.selectedObject = selectedObject;
	}

	public GameObject getSelectedGameObject() {
		if (this.selectedObject instanceof GameObject) {
			return (GameObject) this.selectedObject;
		}
		return null;
	}

	public double getZoom() {
		return this.zoom;
	}

	public void setZoom(double zoom) {
		this.zoom = zoom;
	}

}
