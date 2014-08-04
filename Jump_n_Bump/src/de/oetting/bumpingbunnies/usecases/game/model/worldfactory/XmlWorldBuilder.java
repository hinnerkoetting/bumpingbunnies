package de.oetting.bumpingbunnies.usecases.game.model.worldfactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;
import java.util.List;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Xml;
import de.oetting.bumpingbunnies.logger.Logger;
import de.oetting.bumpingbunnies.logger.LoggerFactory;
import de.oetting.bumpingbunnies.usecases.game.model.AndroidBitmap;
import de.oetting.bumpingbunnies.usecases.game.model.IcyWall;
import de.oetting.bumpingbunnies.usecases.game.model.Jumper;
import de.oetting.bumpingbunnies.usecases.game.model.SpawnPoint;
import de.oetting.bumpingbunnies.usecases.game.model.Wall;
import de.oetting.bumpingbunnies.usecases.game.model.Water;
import de.oetting.bumpingbunnies.usecases.game.model.WorldProperties;
import de.oetting.bumpingbunnies.usecases.game.music.MusicPlayer;
import de.oetting.bumpingbunnies.usecases.game.sound.MusicPlayerFactory;

public class XmlWorldBuilder implements WorldObjectsBuilder, XmlConstants {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(XmlWorldBuilder.class);
	private final int resourceId;
	private XmlWorldBuilderState state;
	private boolean parsed;
	private WorldProperties worldProperties = new WorldProperties();
	private BitmapReader bitmapReader;
	private MusicPlayer jumperMusic;
	private MusicPlayer waterMusic;

	public XmlWorldBuilder(int resourceId) {
		this.resourceId = resourceId;
		this.state = new XmlWorldBuilderState();
	}

	private void parse(Context context) {
		this.bitmapReader = new BitmapReader(context.getResources());
		this.parsed = true;
		InputStream worldXml = context.getResources().openRawResource(
				this.resourceId);
		this.jumperMusic = MusicPlayerFactory.createJumper(context);
		this.waterMusic = MusicPlayerFactory.createWater(context);
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

	private void fillXmlIntoState(XmlPullParser parser)
			throws XmlPullParserException, IOException {

		parser.require(XmlPullParser.START_TAG, null, WORLD);
		while (parser.next() != XmlPullParser.END_DOCUMENT) {
			if (parser.getEventType() != XmlPullParser.START_TAG) {
				continue;
			}
			readContent(parser);
		}

	}

	private void readContent(XmlPullParser parser)
			throws XmlPullParserException, IOException {
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
		}
		else {
			LOGGER.debug("Found tag %s", name);
		}
	}

	private void readWater(XmlPullParser parser) {
		XmlRect rect = readRect(parser);
		Water water = XmlRectToObjectConverter
				.createWater(rect, this.worldProperties, this.waterMusic);
		this.state.getWaters().add(water);
	}

	private void readSpawnpoint(XmlPullParser parser) {
		String x = parser.getAttributeValue(null, X);
		String y = parser.getAttributeValue(null, Y);
		this.state.getSpawnPoints().add(
				XmlRectToObjectConverter.createSpawn(x, y, this.worldProperties));
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

	private void readWall(XmlPullParser parser) throws XmlPullParserException,
			IOException {
		XmlRect rect = readRect(parser);
		Wall wall = XmlRectToObjectConverter.createWall(rect, this.worldProperties);
		wall.setBitmap(new AndroidBitmap(readBitmap(parser)));
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
	public void build(Context context) {
		parse(context);
	}

	@Override
	public List<SpawnPoint> createSpawnPoints() {
		if (!this.parsed) {
			throw new IllegalStateException("You need to parse first");
		}
		return this.state.getSpawnPoints();
	}

	private Bitmap readBitmap(XmlPullParser parser) {
		String filename = parser.getAttributeValue(null, IMAGE);
		if (filename != null) {
			return readBitmap(filename);
		}
		return null;

	}

	private Bitmap readBitmap(String fileName) {
		return this.bitmapReader.readBitmap(fileName);
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
}
