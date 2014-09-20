package de.oetting.bumpingbunnies.android.xml.parsing;

import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;
import java.util.List;

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
import de.oetting.bumpingbunnies.usecases.game.model.IcyWall;
import de.oetting.bumpingbunnies.usecases.game.model.Image;
import de.oetting.bumpingbunnies.usecases.game.model.Jumper;
import de.oetting.bumpingbunnies.usecases.game.model.SpawnPoint;
import de.oetting.bumpingbunnies.usecases.game.model.Wall;
import de.oetting.bumpingbunnies.usecases.game.model.Water;
import de.oetting.bumpingbunnies.usecases.game.music.MusicPlayer;
import de.oetting.bumpingbunnies.world.WorldProperties;
import de.oetting.bumpingbunnies.worldCreation.XmlRect;
import de.oetting.bumpingbunnies.worldCreation.XmlWorldBuilderState;

public class AndroidXmlWorldParser implements WorldObjectsParser, XmlConstants {

	private static final Logger LOGGER = LoggerFactory.getLogger(AndroidXmlWorldParser.class);
	private XmlWorldBuilderState state;
	private boolean parsed;
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
		this.parsed = true;
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
		Water water = XmlRectToObjectConverter.createWater(rect, this.worldProperties, this.waterMusic);
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
		return factory.create(this);
	}

	@Override
	public List<SpawnPoint> getAllSpawnPoints() {
		if (!this.parsed) {
			throw new IllegalStateException("You need to parse first");
		}
		return this.state.getSpawnPoints();
	}

	private Image readBitmap(XmlPullParser parser) {
		String filename = parser.getAttributeValue(null, IMAGE);
		if (filename != null) {
			return readBitmap(filename);
		}
		return null;
	}

	private Image readBitmap(String fileName) {
		return provider.readBitmap(fileName);
	}

	@Override
	public Collection<Wall> getAllWalls() {
		return this.state.getAllWalls();
	}

	@Override
	public Collection<IcyWall> getAllIcyWalls() {
		return this.state.getAllIcyWalls();
	}

	@Override
	public Collection<Jumper> getAllJumpers() {
		return this.state.getAllJumper();
	}

	@Override
	public Collection<Water> getAllWaters() {
		return this.state.getWaters();
	}

	public int getResourceId() {
		return resourceId;
	}

}
