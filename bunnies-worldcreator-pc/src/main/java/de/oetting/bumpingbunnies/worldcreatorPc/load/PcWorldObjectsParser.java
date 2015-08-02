package de.oetting.bumpingbunnies.worldcreatorPc.load;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import de.oetting.bumpingbunnies.model.color.Color;
import de.oetting.bumpingbunnies.model.game.objects.Background;
import de.oetting.bumpingbunnies.model.game.objects.FixedWorldObject;
import de.oetting.bumpingbunnies.model.game.objects.IcyWall;
import de.oetting.bumpingbunnies.model.game.objects.Jumper;
import de.oetting.bumpingbunnies.model.game.objects.SpawnPoint;
import de.oetting.bumpingbunnies.model.game.objects.Wall;
import de.oetting.bumpingbunnies.model.game.objects.Water;
import de.oetting.bumpingbunnies.model.game.world.World;
import de.oetting.bumpingbunnies.model.game.world.WorldProperties;
import de.oetting.bumpingbunnies.model.game.world.XmlRect;
import de.oetting.bumpingbunnies.model.game.world.XmlWorldBuilderState;
import de.oetting.bumpingbunnies.worldcreator.constants.XmlConstants;
import de.oetting.bumpingbunnies.worldcreator.load.ResourceProvider;
import de.oetting.bumpingbunnies.worldcreator.load.XmlReader;
import de.oetting.bumpingbunnies.worldcreator.load.gameObjects.ObjectFactory;
import de.oetting.bumpingbunnies.worldcreator.load.gameObjects.WorldFactory;
import de.oetting.bumpingbunnies.worldcreator.load.gameObjects.WorldObjectsParser;
import de.oetting.bumpingbunnies.worldcreator.load.gameObjects.XmlRectToObjectConverter;

public class PcWorldObjectsParser implements WorldObjectsParser {

	private XmlWorldBuilderState state;
	private WorldProperties worldProperties;
	private ResourceProvider resourceProvider;
	private int currentZIndex;

	public PcWorldObjectsParser() {
		this.state = new XmlWorldBuilderState();
		worldProperties = new WorldProperties();
	}

	@Override
	public World build(ResourceProvider provider, XmlReader xmlReader) {
		this.resourceProvider = provider;
		try {
			Document document = createXmlDocument(xmlReader);
			fillDocumentIntoState(document);
			return new WorldFactory().create(state);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	private Document createXmlDocument(XmlReader xmlReader) throws ParserConfigurationException, SAXException, IOException {
		DocumentBuilderFactory instance = DocumentBuilderFactory.newInstance();
		DocumentBuilder documentBuilder = instance.newDocumentBuilder();
		Document document = documentBuilder.parse(xmlReader.openXmlStream());
		return document;
	}

	private void fillDocumentIntoState(Document document) {
		addAllIceWalls(document);
		addAllWalls(document);
		addAllJumper(document);
		addAllWater(document);
		addAllSpawns(document);
		addAllBackgrounds(document);
	}

	private void addAllIceWalls(Document document) {
		ObjectFactory<IcyWall> factory = (xmlRect, properties) -> XmlRectToObjectConverter.createIceWall(xmlRect, properties);
		state.getAllIcyWalls().addAll(readAllElements(document.getElementsByTagName(XmlConstants.ICEWALL), factory));
	}

	private void addAllWalls(Document document) {
		ObjectFactory<Wall> factory = (xmlRect, properties) -> XmlRectToObjectConverter.createWall(xmlRect, properties);
		state.getAllWalls().addAll(readAllElements(document.getElementsByTagName(XmlConstants.WALL), factory));
	}

	private void addAllJumper(Document document) {
		ObjectFactory<Jumper> factory = (xmlRect, properties) -> XmlRectToObjectConverter.createJumper(xmlRect, properties);
		state.getAllJumper().addAll(readAllElements(document.getElementsByTagName(XmlConstants.JUMPER), factory));
	}

	private void addAllWater(Document document) {
		ObjectFactory<Water> factory = (xmlRect, properties) -> XmlRectToObjectConverter.createWater(xmlRect, properties);
		state.getWaters().addAll(readAllElements(document.getElementsByTagName(XmlConstants.WATER), factory));
	}

	private void addAllSpawns(Document document) {
		state.getSpawnPoints().addAll(readAllSpawns(document.getElementsByTagName(XmlConstants.SPAWNPOINT)));
	}

	private void addAllBackgrounds(Document document) {
		ObjectFactory<Background> factory = (xmlRect, properties) -> XmlRectToObjectConverter.createBackground(xmlRect, properties);
		state.getBackground().addAll(readAllElements(document.getElementsByTagName(XmlConstants.BACKGROUND), factory));
	}

	private <S extends FixedWorldObject> List<S> readAllElements(NodeList nodeList, ObjectFactory<S> factory) {
		List<S> elements = new ArrayList<S>();
		for (int i = 0; i < nodeList.getLength(); i++) {
			Node item = nodeList.item(i);
			XmlRect rect = readRect(item);
			S object = factory.create(rect, worldProperties);
			Node imageNode = item.getAttributes().getNamedItem(XmlConstants.IMAGE);
			if (imageNode != null)
				object.setBitmap(resourceProvider.readBitmap(imageNode.getTextContent()));
			else 
				object.setColor(Color.TRANSPARENT);
			Node zIndexNode = item.getAttributes().getNamedItem(XmlConstants.ZINDEX);
			if (zIndexNode != null)
				object.setZIndex(Integer.parseInt(zIndexNode.getTextContent()));
			else 
				object.setZIndex(currentZIndex++);
			Node mirroredNode = item.getAttributes().getNamedItem(XmlConstants.MIRRORED);
			if (mirroredNode != null)
				object.setMirroredHorizontally(Boolean.valueOf(mirroredNode.getTextContent()));
			elements.add(object);
		}
		return elements;
	}

	private List<SpawnPoint> readAllSpawns(NodeList nodeList) {
		List<SpawnPoint> elements = new ArrayList<SpawnPoint>();
		for (int i = 0; i < nodeList.getLength(); i++) {
			Node item = nodeList.item(i);
			String x = item.getAttributes().getNamedItem(XmlConstants.X).getNodeValue();
			String y = item.getAttributes().getNamedItem(XmlConstants.Y).getNodeValue();
			SpawnPoint spawn = XmlRectToObjectConverter.createSpawn(x, y, worldProperties);
			elements.add(spawn);
		}
		return elements;
	}

	private XmlRect readRect(Node node) {
		String minX = node.getAttributes().getNamedItem(XmlConstants.MIN_X).getNodeValue();
		String minY = node.getAttributes().getNamedItem(XmlConstants.MIN_Y).getNodeValue();
		String maxX = node.getAttributes().getNamedItem(XmlConstants.MAX_X).getNodeValue();
		String maxY = node.getAttributes().getNamedItem(XmlConstants.MAX_Y).getNodeValue();
		return new XmlRect(minX, minY, maxX, maxY);
	}

}
