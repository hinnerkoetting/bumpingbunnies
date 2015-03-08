package de.jumpnbump.usecases.viewer;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Stroke;
import java.awt.image.BufferedImage;
import java.util.List;

import javax.swing.JPanel;

import de.oetting.bumpingbunnies.core.game.graphics.calculation.AbsoluteCoordinatesCalculation;
import de.oetting.bumpingbunnies.core.game.graphics.calculation.CoordinatesCalculation;
import de.oetting.bumpingbunnies.core.world.World;
import de.oetting.bumpingbunnies.model.game.objects.GameObject;
import de.oetting.bumpingbunnies.model.game.objects.GameObjectWithImage;
import de.oetting.bumpingbunnies.model.game.objects.ImageWrapper;
import de.oetting.bumpingbunnies.model.game.objects.ModelConstants;
import de.oetting.bumpingbunnies.model.game.objects.SpawnPoint;
import de.oetting.bumpingbunnies.model.game.world.WorldProperties;

public class MyCanvas extends JPanel {
	private static final long serialVersionUID = 1L;

	public static final int SPAWN_RADIUS = 5;
	private World objectContainer;
	private Object selectedObject;
	private double zoom;
	private CoordinatesCalculation coordinatesCalculation;

	private WorldProperties properties;

	public MyCanvas(World container) {
		this.objectContainer = container;
		this.zoom = 1;
		properties = new WorldProperties(ModelConstants.STANDARD_WORLD_SIZE, ModelConstants.STANDARD_WORLD_SIZE);
		this.coordinatesCalculation = new AbsoluteCoordinatesCalculation(getWidth(), getHeight(), properties);
	}

	@SuppressWarnings("deprecation")
	@Override
	public void resize(Dimension arg0) {
		super.resize(arg0);
		this.coordinatesCalculation = new AbsoluteCoordinatesCalculation(getWidth(), getHeight(), properties);
	}

	@Override
	public void paint(Graphics g) {
		super.paint(g);
		g.clearRect(0, 0, getWidth(), getHeight());
		drawObjects(objectContainer.getBackgrounds(), g);
		drawObjects(objectContainer.getAllWalls(), g);
		drawObjects(objectContainer.getAllIcyWalls(), g);
		drawObjects(objectContainer.getAllJumper(), g);
		drawObjects(objectContainer.getAllWaters(), g);
		paintSpawnPoints(g);
	}

	public void drawObjects(List<? extends GameObjectWithImage> objects, Graphics graphics) {
		objects.stream().forEach((object) -> drawObject(graphics, object));
	}

	private void paintSpawnPoints(Graphics g) {
		for (SpawnPoint spawn : this.objectContainer.getSpawnPoints()) {
			g.setColor(Color.red);
			drawSpawn(g, spawn);
		}
	}

	private void drawSpawn(Graphics g, SpawnPoint spawn) {
		if (spawn == this.selectedObject) {
			g.setColor(Color.GREEN);
		}
		g.fillOval(calculatePixelX(spawn.getX()), calculatePixelY(spawn.getY()), SPAWN_RADIUS, SPAWN_RADIUS);
	}

	private int calculatePixelY(long y) {
		return (this.coordinatesCalculation.getScreenCoordinateY(y));
	}

	private int calculatePixelX(long x) {
		return (this.coordinatesCalculation.getScreenCoordinateX(x));
	}

	private void drawObject(Graphics g, GameObjectWithImage w) {
		int height = calculateHeight(w.minY(), w.maxY());
		int minX = calculatePixelX(w.minX());
		int width = calculatePixelWidht(w.minX(), w.maxX());
		int minY = calculatePixelY(w.minY()) - height;
		if (w.getBitmap() != null) {
			ImageWrapper bitmap = w.getBitmap();
			g.drawImage((BufferedImage) bitmap.getBitmap(), minX, minY, width, height, null);
		} else {
			g.setColor(new Color(w.getColor()));
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

	private int calculatePixelWidht(long minX, long maxX) {
		return (this.coordinatesCalculation.getScreenCoordinateX(maxX) - this.coordinatesCalculation.getScreenCoordinateX(minX));
	}

	private int calculateHeight(long minY, long maxY) {
		return -(this.coordinatesCalculation.getScreenCoordinateY(maxY) - this.coordinatesCalculation.getScreenCoordinateY(minY));
	}

	public void setSelectedObject(Object selectedObject) {
		this.selectedObject = selectedObject;
	}

	public GameObjectWithImage getSelectedGameObject() {
		if (this.selectedObject instanceof GameObject) {
			return (GameObjectWithImage) this.selectedObject;
		}
		return null;
	}

	public double getZoom() {
		return this.zoom;
	}

	public void setZoom(double zoom) {
		this.zoom = zoom;
	}

	public void setWorld(World model) {
		this.objectContainer = model;
	}

}
