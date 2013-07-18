package de.jumpnbump.usecases.viewer;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Stroke;

import javax.swing.JPanel;

import de.jumpnbump.usecases.viewer.model.GameObject;
import de.jumpnbump.usecases.viewer.model.IcyWall;
import de.jumpnbump.usecases.viewer.model.Jumper;
import de.jumpnbump.usecases.viewer.model.SpawnPoint;
import de.jumpnbump.usecases.viewer.model.Wall;
import de.jumpnbump.usecases.viewer.model.Water;
import de.jumpnbump.usecases.viewer.xml.ObjectContainer;

public class MyCanvas extends JPanel {
	private static final long serialVersionUID = 1L;
	public static final int DIVIDER_X_CONST = 10000;
	public static final int DIVIDER_Y_CONST = 20000;
	public static final int SPAWN_RADIUS = 5;
	private ObjectContainer objectContainer;
	private Object selectedObject;

	public MyCanvas(ObjectContainer container) {
		this.objectContainer = container;

	}

	@Override
	public void paint(Graphics g) {
		super.paint(g);
		g.clearRect(0, 0, getWidth(), getHeight());
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

	private void drawObject(Graphics g, GameObject w) {
		int height = calculateHeight(w.minY(), w.maxY());
		g.fillRect(calculatePixelX(w.minX()), calculatePixelY(w.minY()) - height,
				calculatePixelWidht(w.minX(), w.maxX()),
				height);
		if (w == this.selectedObject) {
			Graphics2D g2d = (Graphics2D) g;
			Stroke oldStroke = g2d.getStroke();
			g2d.setStroke(new BasicStroke(5));
			g2d.setColor(Color.GREEN);
			// g.drawRect(calculatePixelX(w.minX()), calculatePixelY(w.minY()),
			// calculatePixelWidht(w.minX(), w.maxX()),
			// calculateHeight(w.minY(), w.maxY()));

			g2d.draw(new Rectangle(calculatePixelX(w.minX()),
					calculatePixelY(w.minY()) - height,
					calculatePixelWidht(w.minX(), w.maxX()),
					height));

			g2d.setStroke(oldStroke);
		}
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
		return (maxY - minY) / DIVIDER_Y_CONST;
	}

	public void setObjectContainer(ObjectContainer objectContainer) {
		this.objectContainer = objectContainer;
	}

	public void setSelectedObject(Object selectedObject) {
		this.selectedObject = selectedObject;
	}
}
