package de.oetting.bumpingbunnies.pc.worldcreation.parser;

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

import de.oetting.bumpingbunnies.core.resources.ResourceProvider;
import de.oetting.bumpingbunnies.core.world.World;
import de.oetting.bumpingbunnies.core.worldCreation.WorldFactory;
import de.oetting.bumpingbunnies.core.worldCreation.XmlRectToObjectConverter;
import de.oetting.bumpingbunnies.core.worldCreation.parser.WorldObjectsParser;
import de.oetting.bumpingbunnies.core.worldCreation.parser.XmlConstants;
import de.oetting.bumpingbunnies.core.worldCreation.parser.XmlReader;
import de.oetting.bumpingbunnies.model.game.objects.GameObjectWithImage;
import de.oetting.bumpingbunnies.model.game.objects.IcyWall;
import de.oetting.bumpingbunnies.model.game.objects.Jumper;
import de.oetting.bumpingbunnies.model.game.objects.SpawnPoint;
import de.oetting.bumpingbunnies.model.game.objects.Wall;
import de.oetting.bumpingbunnies.model.game.objects.Water;
import de.oetting.bumpingbunnies.model.game.world.WorldProperties;
import de.oetting.bumpingbunnies.model.game.world.XmlRect;
import de.oetting.bumpingbunnies.model.game.world.XmlWorldBuilderState;

public class PcWorldObjectsParser implements WorldObjectsParser {

	private XmlWorldBuilderState state;
	private WorldProperties worldProperties;
	private ResourceProvider resourceProvider;

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

	private <S extends GameObjectWithImage> List<S> readAllElements(NodeList nodeList, ObjectFactory<S> factory) {
		List<S> elements = new ArrayList<S>();
		for (int i = 0; i < nodeList.getLength(); i++) {
			Node item = nodeList.item(i);
			XmlRect rect = readRect(item);
			S wall = factory.create(rect, worldProperties);
			Node imageNode = item.getAttributes().getNamedItem(XmlConstants.IMAGE);
			if (imageNode != null)
				wall.setBitmap(resourceProvider.readBitmap(imageNode.getTextContent()));
			elements.add(wall);
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
