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

import de.oetting.bumpingbunnies.core.world.World;
import de.oetting.bumpingbunnies.model.game.objects.Background;
import de.oetting.bumpingbunnies.model.game.objects.GameObjectWithImage;
import de.oetting.bumpingbunnies.model.game.objects.IcyWall;
import de.oetting.bumpingbunnies.model.game.objects.Jumper;
import de.oetting.bumpingbunnies.model.game.objects.ModelConstants;
import de.oetting.bumpingbunnies.model.game.objects.SpawnPoint;
import de.oetting.bumpingbunnies.model.game.objects.Wall;
import de.oetting.bumpingbunnies.model.game.objects.Water;

public class XmlStorer {

	private final World container;

	public XmlStorer(World container) {
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
		for (Wall w : this.container.getAllWalls()) {
			Element wall = createGameObjectElement(doc, w, XmlConstants.WALL);
			walls.appendChild(wall);
		}
		return walls;
	}

	private Element createIceWalls(Document doc) {
		Element walls = doc.createElement(XmlConstants.ICEWALLS);
		for (IcyWall w : this.container.getAllIcyWalls()) {
			Element wall = createGameObjectElement(doc, w, XmlConstants.ICEWALL);
			walls.appendChild(wall);
		}
		return walls;
	}

	private Element createJumpers(Document doc) {
		Element walls = doc.createElement(XmlConstants.JUMPERS);
		for (Jumper j : this.container.getAllJumper()) {
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
		for (Water w : this.container.getAllWaters()) {
			Element waterElement = createGameObjectElement(doc, w, XmlConstants.WATER);
			waters.appendChild(waterElement);
		}
		return waters;
	}

	private Element createGameObjectElement(Document doc, GameObjectWithImage go, String name) {
		Element element = doc.createElement(name);
		element.setAttribute(XmlConstants.MIN_X, Double.toString((double) go.minX() / ModelConstants.MAX_VALUE));
		element.setAttribute(XmlConstants.MAX_X, Double.toString((double) go.maxX() / ModelConstants.MAX_VALUE));
		element.setAttribute(XmlConstants.MIN_Y, Double.toString((double) go.minY() / ModelConstants.MAX_VALUE));
		element.setAttribute(XmlConstants.MAX_Y, Double.toString((double) go.maxY() / ModelConstants.MAX_VALUE));
		if (go.getBitmap() != null) {
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
