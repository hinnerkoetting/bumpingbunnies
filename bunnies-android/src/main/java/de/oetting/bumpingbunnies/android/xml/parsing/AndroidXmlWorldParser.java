package de.oetting.bumpingbunnies.android.xml.parsing;

import java.io.IOException;
import java.io.InputStream;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import android.util.Xml;
import de.oetting.bumpingbunnies.logger.Logger;
import de.oetting.bumpingbunnies.logger.LoggerFactory;
import de.oetting.bumpingbunnies.model.color.Color;
import de.oetting.bumpingbunnies.model.game.objects.Background;
import de.oetting.bumpingbunnies.model.game.objects.FixedWorldObject;
import de.oetting.bumpingbunnies.model.game.objects.GameObjectWithImage;
import de.oetting.bumpingbunnies.model.game.objects.IcyWall;
import de.oetting.bumpingbunnies.model.game.objects.ImageWrapper;
import de.oetting.bumpingbunnies.model.game.objects.Jumper;
import de.oetting.bumpingbunnies.model.game.objects.Wall;
import de.oetting.bumpingbunnies.model.game.objects.Water;
import de.oetting.bumpingbunnies.model.game.world.World;
import de.oetting.bumpingbunnies.model.game.world.WorldProperties;
import de.oetting.bumpingbunnies.model.game.world.XmlRect;
import de.oetting.bumpingbunnies.model.game.world.XmlWorldBuilderState;
import de.oetting.bumpingbunnies.worldcreator.constants.XmlConstants;
import de.oetting.bumpingbunnies.worldcreator.load.ResourceProvider;
import de.oetting.bumpingbunnies.worldcreator.load.XmlReader;
import de.oetting.bumpingbunnies.worldcreator.load.gameObjects.WorldFactory;
import de.oetting.bumpingbunnies.worldcreator.load.gameObjects.WorldObjectsParser;
import de.oetting.bumpingbunnies.worldcreator.load.gameObjects.XmlRectToObjectConverter;

public class AndroidXmlWorldParser implements WorldObjectsParser, XmlConstants {

	private static final Logger LOGGER = LoggerFactory.getLogger(AndroidXmlWorldParser.class);
	private XmlWorldBuilderState state;
	private WorldProperties worldProperties = new WorldProperties();
	private ResourceProvider provider;
	private int currentZIndex;

	public AndroidXmlWorldParser() {
		this.state = new XmlWorldBuilderState();
	}

	private void parse(ResourceProvider provider, XmlReader xmlReader) {
		this.provider = provider;
		InputStream worldXml = xmlReader.openXmlStream();
		readXmlFile(worldXml);
	}

	private void readXmlFile(InputStream worldXml) {
		try {
			XmlPullParser parser = Xml.newPullParser();
			parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
			parser.setInput(worldXml, "ISO-8859-1");
			parser.nextTag();
			fillXmlIntoState(parser);
		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			try {
				worldXml.close();
			} catch (Exception e1) {
				LOGGER.warn(e1.getMessage());
			}
		}
	}

	private void fillXmlIntoState(XmlPullParser parser) throws XmlPullParserException, IOException {

		parser.require(XmlPullParser.START_TAG, null, WORLD);
		while (parser.next() != XmlPullParser.END_DOCUMENT) {
			if (parser.getEventType() != XmlPullParser.START_TAG) {
				continue;
			}
			readContent(parser);
		}

	}

	private void readContent(XmlPullParser parser) throws XmlPullParserException, IOException {
		String name = parser.getName();
		if (XmlConstants.WALL.equals(name)) {
			readWall(parser);
		} else if (XmlConstants.ICEWALL.equals(name)) {
			readIcewall(parser);
		} else if (XmlConstants.JUMPER.equals(name)) {
			readJumper(parser);
		} else if (XmlConstants.SPAWNPOINT.equals(name)) {
			readSpawnpoint(parser);
		} else if (XmlConstants.WATER.equals(name)) {
			readWater(parser);
		} else if (XmlConstants.BACKGROUND.equals(name)) {
			readBackground(parser);
		} else {
			LOGGER.debug("Found tag %s", name);
		}
	}

	private void readBackground(XmlPullParser parser) {
		XmlRect rect = readRect(parser);
		Background background = XmlRectToObjectConverter.createBackground(rect, this.worldProperties);
		applyZIndex(background, parser);
		background.setBitmap(readBitmap(parser));
		applyMirrored(parser, background);
		if (background.getBitmap() == null) 
			background.setColor(Color.TRANSPARENT);
		this.state.getBackground().add(background);
	}

	private void applyZIndex(FixedWorldObject object, XmlPullParser parser) {
		String zIndex = parser.getAttributeValue(null, ZINDEX);
		if (zIndex != null)
			object.setzIndex(Integer.parseInt(zIndex));
		else
			object.setzIndex(currentZIndex++);
	}

	private void readWater(XmlPullParser parser) {
		XmlRect rect = readRect(parser);
		Water water = XmlRectToObjectConverter.createWater(rect, this.worldProperties);
		applyZIndex(water, parser);
		water.setBitmap(readBitmap(parser));
		applyMirrored(parser, water);
		if (water.getBitmap() == null) 
			water.setColor(Color.TRANSPARENT);
		this.state.getWaters().add(water);
	}

	private void readSpawnpoint(XmlPullParser parser) {
		String x = parser.getAttributeValue(null, X);
		String y = parser.getAttributeValue(null, Y);
		this.state.getSpawnPoints().add(XmlRectToObjectConverter.createSpawn(x, y, this.worldProperties));
	}

	private void readJumper(XmlPullParser parser) {
		XmlRect rect = readRect(parser);
		Jumper jumper = XmlRectToObjectConverter.createJumper(rect, this.worldProperties);
		applyZIndex(jumper, parser);
		jumper.setBitmap(readBitmap(parser));
		applyMirrored(parser, jumper);
		if (jumper.getBitmap() == null) 
			jumper.setColor(Color.TRANSPARENT);
		this.state.getAllJumper().add(jumper);
	}

	private void applyMirrored(XmlPullParser parser, GameObjectWithImage go) {
		String mirrored = parser.getAttributeValue(null, XmlConstants.MIRRORED);
		if (mirrored != null)
			go.setMirroredHorizontally(Boolean.valueOf(mirrored));
	}

	private void readIcewall(XmlPullParser parser) {
		XmlRect rect = readRect(parser);
		IcyWall wall = XmlRectToObjectConverter.createIceWall(rect, this.worldProperties);
		applyZIndex(wall, parser);
		wall.setBitmap(readBitmap(parser));
		applyMirrored(parser, wall);
		if (wall.getBitmap() == null) 
			wall.setColor(Color.TRANSPARENT);
		this.state.getAllIcyWalls().add(wall);
	}

	private void readWall(XmlPullParser parser) throws XmlPullParserException, IOException {
		XmlRect rect = readRect(parser);
		Wall wall = XmlRectToObjectConverter.createWall(rect, this.worldProperties);
		applyZIndex(wall, parser);
		wall.setBitmap(readBitmap(parser));
		applyMirrored(parser, wall);
		if (wall.getBitmap() == null) 
			wall.setColor(Color.TRANSPARENT);
		this.state.getAllWalls().add(wall);
	}

	private XmlRect readRect(XmlPullParser parser) {
		String minX = parser.getAttributeValue(null, MIN_X);
		String minY = parser.getAttributeValue(null, MIN_Y);
		String maxX = parser.getAttributeValue(null, MAX_X);
		String maxY = parser.getAttributeValue(null, MAX_Y);
		return new XmlRect(minX, minY, maxX, maxY);
	}

	@Override
	public World build(ResourceProvider provider, XmlReader xmlReader) {
		parse(provider, xmlReader);
		WorldFactory factory = new WorldFactory();
		return factory.create(state);
	}

	private ImageWrapper readBitmap(XmlPullParser parser) {
		String filename = parser.getAttributeValue(null, IMAGE);
		if (filename != null) {
			return readBitmap(filename);
		}
		return null;
	}

	private ImageWrapper readBitmap(String fileName) {
		return provider.readBitmap(fileName);
	}

}
