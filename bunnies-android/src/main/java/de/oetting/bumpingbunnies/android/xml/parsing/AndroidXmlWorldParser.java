package de.oetting.bumpingbunnies.android.xml.parsing;

import java.io.IOException;
import java.io.InputStream;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import android.util.Xml;
import de.oetting.bumpingbunnies.core.resources.ResourceProvider;
import de.oetting.bumpingbunnies.core.world.World;
import de.oetting.bumpingbunnies.core.worldCreation.WorldFactory;
import de.oetting.bumpingbunnies.core.worldCreation.XmlRectToObjectConverter;
import de.oetting.bumpingbunnies.core.worldCreation.parser.WorldObjectsParser;
import de.oetting.bumpingbunnies.core.worldCreation.parser.XmlConstants;
import de.oetting.bumpingbunnies.core.worldCreation.parser.XmlReader;
import de.oetting.bumpingbunnies.logger.Logger;
import de.oetting.bumpingbunnies.logger.LoggerFactory;
import de.oetting.bumpingbunnies.model.game.MusicPlayer;
import de.oetting.bumpingbunnies.model.game.objects.IcyWall;
import de.oetting.bumpingbunnies.model.game.objects.ImageWrapper;
import de.oetting.bumpingbunnies.model.game.objects.Jumper;
import de.oetting.bumpingbunnies.model.game.objects.Wall;
import de.oetting.bumpingbunnies.model.game.objects.Water;
import de.oetting.bumpingbunnies.model.game.world.WorldProperties;
import de.oetting.bumpingbunnies.model.game.world.XmlRect;
import de.oetting.bumpingbunnies.model.game.world.XmlWorldBuilderState;

public class AndroidXmlWorldParser implements WorldObjectsParser, XmlConstants {

	private static final Logger LOGGER = LoggerFactory.getLogger(AndroidXmlWorldParser.class);
	private XmlWorldBuilderState state;
	private WorldProperties worldProperties = new WorldProperties();
	private MusicPlayer jumperMusic;
	private MusicPlayer waterMusic;
	private ResourceProvider provider;
	private int resourceId;

	public AndroidXmlWorldParser(int resourceId) {
		this.resourceId = resourceId;
		this.state = new XmlWorldBuilderState();
	}

	private void parse(ResourceProvider provider, XmlReader xmlReader) {
		this.provider = provider;
		InputStream worldXml = xmlReader.openXmlStream();
		this.jumperMusic = provider.readerJumperMusic();
		this.waterMusic = provider.readWaterMusic();
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
		} else {
			LOGGER.debug("Found tag %s", name);
		}
	}

	private void readWater(XmlPullParser parser) {
		XmlRect rect = readRect(parser);
		Water water = XmlRectToObjectConverter.createWater(rect, this.waterMusic, this.worldProperties);
		this.state.getWaters().add(water);
	}

	private void readSpawnpoint(XmlPullParser parser) {
		String x = parser.getAttributeValue(null, X);
		String y = parser.getAttributeValue(null, Y);
		this.state.getSpawnPoints().add(XmlRectToObjectConverter.createSpawn(x, y, this.worldProperties));
	}

	private void readJumper(XmlPullParser parser) {
		XmlRect rect = readRect(parser);
		Jumper jumper = XmlRectToObjectConverter.createJumper(rect, this.jumperMusic, this.worldProperties);
		this.state.getAllJumper().add(jumper);
	}

	private void readIcewall(XmlPullParser parser) {
		XmlRect rect = readRect(parser);
		IcyWall wall = XmlRectToObjectConverter.createIceWall(rect, this.worldProperties);
		this.state.getAllIcyWalls().add(wall);
	}

	private void readWall(XmlPullParser parser) throws XmlPullParserException, IOException {
		XmlRect rect = readRect(parser);
		Wall wall = XmlRectToObjectConverter.createWall(rect, this.worldProperties);
		wall.setBitmap(readBitmap(parser));
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

	@Override
	public int getResourceId() {
		return resourceId;
	}

}