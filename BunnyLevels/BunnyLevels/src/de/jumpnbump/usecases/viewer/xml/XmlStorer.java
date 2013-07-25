package de.jumpnbump.usecases.viewer.xml;

import java.io.File;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import de.jumpnbump.usecases.viewer.model.Background;
import de.jumpnbump.usecases.viewer.model.GameObject;
import de.jumpnbump.usecases.viewer.model.IcyWall;
import de.jumpnbump.usecases.viewer.model.Jumper;
import de.jumpnbump.usecases.viewer.model.ModelConstants;
import de.jumpnbump.usecases.viewer.model.SpawnPoint;
import de.jumpnbump.usecases.viewer.model.Wall;
import de.jumpnbump.usecases.viewer.model.Water;

public class XmlStorer {

	private final ObjectContainer container;

	public XmlStorer(ObjectContainer container) {
		super();
		this.container = container;
	}

	public void saveXml(File file) {
		Document doc = createDocument();
		doc.appendChild(appendAllElements(doc));
		saveToDisk(doc, file);
	}

	private Element appendAllElements(Document doc) {
		Element worldElement = createRootElement(doc);
		worldElement.appendChild(createWalls(doc));
		worldElement.appendChild(createIceWalls(doc));
		worldElement.appendChild(createJumpers(doc));
		worldElement.appendChild(createSpawnpoints(doc));
		worldElement.appendChild(createWaters(doc));
		worldElement.appendChild(createBackgrounds(doc));
		return worldElement;
	}

	private Node createBackgrounds(Document doc) {
		Element bgs = doc.createElement(XmlConstants.BACKGROUNDS);
		for (Background w : this.container.getBackgrounds()) {
			Element wall = createGameObjectElement(doc, w, XmlConstants.BACKGROUND);
			bgs.appendChild(wall);
		}
		return bgs;
	}

	private Element createRootElement(Document doc) {
		Element worldElement = doc.createElement(XmlConstants.WORLD);
		worldElement.setAttribute("xmlns:xsi", "http://www.w3.org/2001/XMLSchema-instance");
		worldElement.setAttribute("xmlns", "http://world.bunnies.de");
		worldElement.setAttribute("xsi:noNamespaceSchemaLocation", "world.xsd");
		worldElement.setAttribute("xsi:schemaLocation", "http://world.bunnies.de\n world.xsd");
		return worldElement;
	}

	private void saveToDisk(Document doc, File file) {
		try {
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();

			DOMSource source = new DOMSource(doc);
			StreamResult result = new StreamResult(file);
			transformer.transform(source, result);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	private org.w3c.dom.Element createWalls(Document doc) {
		Element walls = doc.createElement(XmlConstants.WALLS);
		for (Wall w : this.container.getWalls()) {
			Element wall = createGameObjectElement(doc, w, XmlConstants.WALL);
			walls.appendChild(wall);
		}
		return walls;
	}

	private Element createIceWalls(Document doc) {
		Element walls = doc.createElement(XmlConstants.ICEWALLS);
		for (IcyWall w : this.container.getIceWalls()) {
			Element wall = createGameObjectElement(doc, w, XmlConstants.ICEWALL);
			walls.appendChild(wall);
		}
		return walls;
	}

	private Element createJumpers(Document doc) {
		Element walls = doc.createElement(XmlConstants.JUMPERS);
		for (Jumper j : this.container.getJumpers()) {
			Element jumper = createGameObjectElement(doc, j, XmlConstants.JUMPER);
			walls.appendChild(jumper);
		}
		return walls;
	}

	private Element createSpawnpoints(Document doc) {
		Element spawnpoints = doc.createElement(XmlConstants.SPAWPOINTS);
		for (SpawnPoint sp : this.container.getSpawnPoints()) {
			Element spawnElement = createSpawnElement(doc, sp, XmlConstants.SPAWNPOINT);
			spawnpoints.appendChild(spawnElement);
		}
		return spawnpoints;
	}

	private Element createWaters(Document doc) {
		Element waters = doc.createElement(XmlConstants.WATERS);
		for (Water w : this.container.getWaters()) {
			Element waterElement = createGameObjectElement(doc, w, XmlConstants.WATER);
			waters.appendChild(waterElement);
		}
		return waters;
	}

	private Element createGameObjectElement(Document doc, GameObject go, String name) {
		Element element = doc.createElement(name);
		element.setAttribute(XmlConstants.MIN_X, Double.toString((double) go.minX() / ModelConstants.MAX_VALUE));
		element.setAttribute(XmlConstants.MAX_X, Double.toString((double) go.maxX() / ModelConstants.MAX_VALUE));
		element.setAttribute(XmlConstants.MIN_Y, Double.toString((double) go.minY() / ModelConstants.MAX_VALUE));
		element.setAttribute(XmlConstants.MAX_Y, Double.toString((double) go.maxY() / ModelConstants.MAX_VALUE));
		if (go.hasImage()) {
			element.setAttribute(XmlConstants.image, go.getImageKey());
		}
		return element;
	}

	private Element createSpawnElement(Document doc, SpawnPoint sp, String name) {
		Element element = doc.createElement(name);
		element.setAttribute(XmlConstants.X, Double.toString((double) sp.getX() / ModelConstants.MAX_VALUE));
		element.setAttribute(XmlConstants.Y, Double.toString((double) sp.getY() / ModelConstants.MAX_VALUE));
		return element;
	}

	private Document createDocument() {
		try {
			DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
			Document document = docBuilder.newDocument();
			return document;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}
