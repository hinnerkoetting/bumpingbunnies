package de.jumpnbump.usecases.viewer.xml;

import java.awt.image.BufferedImage;
import java.io.InputStream;
import java.util.LinkedList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import de.jumpnbump.usecases.viewer.model.Background;
import de.jumpnbump.usecases.viewer.model.IcyWall;
import de.jumpnbump.usecases.viewer.model.ImageReader;
import de.jumpnbump.usecases.viewer.model.ImageWrapper;
import de.jumpnbump.usecases.viewer.model.Jumper;
import de.jumpnbump.usecases.viewer.model.SpawnPoint;
import de.jumpnbump.usecases.viewer.model.Wall;
import de.jumpnbump.usecases.viewer.model.Water;

public class XmlBuilder implements XmlConstants {

	public ObjectContainer parse(InputStream is) {
		try {
			return parseInternal(is);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	private ObjectContainer parseInternal(InputStream is) throws Exception {
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
		Document doc = dBuilder.parse(is);
		ObjectContainer container = new ObjectContainer();
		container.setWalls(parseWalls(doc));
		container.setIceWalls(parseIceWalls(doc));
		container.setJumpers(parseJumper(doc));
		container.setWaters(parseWater(doc));
		container.setSpawnPoints(parseSpawnpoints(doc));
		container.setBackgrounds(parseBackgrounds(doc));
		return container;
	}

	private List<SpawnPoint> parseSpawnpoints(Document doc) {
		NodeList elements = doc.getElementsByTagName(XmlConstants.SPAWNPOINT);
		List<SpawnPoint> spawnpoints = new LinkedList<>();
		for (int i = 0; i < elements.getLength(); i++) {
			org.w3c.dom.Node node = elements.item(i);
			spawnpoints.add(extractSpawnpoint(node));
		}
		return spawnpoints;
	}

	private SpawnPoint extractSpawnpoint(org.w3c.dom.Node node) {
		NamedNodeMap attributes = node.getAttributes();
		String x = attributes.getNamedItem(X).getNodeValue();
		String y = attributes.getNamedItem(Y).getNodeValue();
		return XmlRectToObjectConverter.createSpawn(x, y);
	}

	private List<Jumper> parseJumper(Document doc) {
		NodeList elements = doc.getElementsByTagName(XmlConstants.JUMPER);
		List<Jumper> jumpers = new LinkedList<>();
		for (int i = 0; i < elements.getLength(); i++) {
			org.w3c.dom.Node node = elements.item(i);
			Jumper j = XmlRectToObjectConverter.createJumper(extractRectangle(node));
			j.applyImage(extractImage(node));
			jumpers.add(j);
		}
		return jumpers;
	}

	private List<Water> parseWater(Document doc) {
		NodeList elements = doc.getElementsByTagName(XmlConstants.WATER);
		List<Water> waters = new LinkedList<>();
		for (int i = 0; i < elements.getLength(); i++) {
			org.w3c.dom.Node node = elements.item(i);
			Water w = XmlRectToObjectConverter.createWater(extractRectangle(node));
			w.applyImage(extractImage(node));
			waters.add(w);
		}
		return waters;
	}

	private List<IcyWall> parseIceWalls(Document doc) {
		NodeList elements = doc.getElementsByTagName(XmlConstants.ICEWALL);
		List<IcyWall> walls = new LinkedList<>();
		for (int i = 0; i < elements.getLength(); i++) {
			org.w3c.dom.Node node = elements.item(i);
			IcyWall iw = XmlRectToObjectConverter.createIceWall(extractRectangle(node));
			iw.applyImage(extractImage(node));
			walls.add(iw);
		}
		return walls;
	}

	private List<Wall> parseWalls(Document doc) {
		NodeList elements = doc.getElementsByTagName(XmlConstants.WALL);
		List<Wall> walls = new LinkedList<>();
		for (int i = 0; i < elements.getLength(); i++) {
			org.w3c.dom.Node node = elements.item(i);
			Wall w = XmlRectToObjectConverter.createWall(extractRectangle(node));
			w.applyImage(extractImage(node));
			walls.add(w);
		}
		return walls;
	}

	private List<Background> parseBackgrounds(Document doc) {
		NodeList elements = doc.getElementsByTagName(XmlConstants.BACKGROUND);
		List<Background> bgs = new LinkedList<>();
		for (int i = 0; i < elements.getLength(); i++) {
			org.w3c.dom.Node node = elements.item(i);
			Background bg = XmlRectToObjectConverter.createBackground(extractRectangle(node));
			bg.applyImage(extractImage(node));
			bgs.add(bg);
		}
		return bgs;
	}

	private ImageWrapper extractImage(Node node) {
		Node namedItem = node.getAttributes().getNamedItem(image);
		if (namedItem != null) {
			String background = namedItem.getNodeValue();
			BufferedImage readImage = ImageReader.readImage(background + ".png");
			return new ImageWrapper(readImage, background);
		}
		return null;
	}

	private XmlRect extractRectangle(org.w3c.dom.Node node) {
		NamedNodeMap attributes = node.getAttributes();
		double minX = extractDouble(attributes.getNamedItem(MIN_X));
		double minY = extractDouble(attributes.getNamedItem(MIN_Y));
		double maxX = extractDouble(attributes.getNamedItem(MAX_X));
		double maxY = extractDouble(attributes.getNamedItem(MAX_Y));

		return new XmlRect(minX, minY, maxX, maxY);
	}

	private double extractDouble(org.w3c.dom.Node node) {
		String value = node.getNodeValue();
		return Double.parseDouble(value);
	}
}
