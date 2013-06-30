package de.oetting.bumpingbunnies.usecases.game.model.worldfactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;
import java.util.List;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import android.content.Context;
import android.media.MediaPlayer;
import android.util.Xml;
import de.oetting.bumpingbunnies.R;
import de.oetting.bumpingbunnies.logger.Logger;
import de.oetting.bumpingbunnies.logger.MyLog;
import de.oetting.bumpingbunnies.usecases.game.model.FixedWorldObject;
import de.oetting.bumpingbunnies.usecases.game.model.Jumper;
import de.oetting.bumpingbunnies.usecases.game.model.SpawnPoint;
import de.oetting.bumpingbunnies.usecases.game.model.Wall;

public class XmlWorldBuilder implements WorldObjectsBuilder, XmlConstants {

	private static final MyLog LOGGER = Logger.getLogger(XmlWorldBuilder.class);
	private final int resourceId;
	private XmlWorldBuilderState state;
	private boolean parsed;

	public XmlWorldBuilder(int resourceId) {
		this.resourceId = resourceId;
		this.state = new XmlWorldBuilderState();
	}

	private void parse(Context context) {
		this.parsed = true;
		InputStream worldXml = context.getResources().openRawResource(
				this.resourceId);
		MediaPlayer mediaPlayer = MediaPlayer.create(context, R.raw.boing_test);
		try {
			XmlPullParser parser = Xml.newPullParser();
			parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
			parser.setInput(worldXml, "ISO-8859-1");
			parser.nextTag();
			fillXmlIntoState(parser, mediaPlayer);
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

	private void fillXmlIntoState(XmlPullParser parser, MediaPlayer mediaPlayer)
			throws XmlPullParserException, IOException {

		parser.require(XmlPullParser.START_TAG, null, WORLD);
		while (parser.next() != XmlPullParser.END_DOCUMENT) {
			if (parser.getEventType() != XmlPullParser.START_TAG) {
				continue;
			}
			readContent(parser, mediaPlayer);
		}

	}

	private void readContent(XmlPullParser parser, MediaPlayer mediaPlayer)
			throws XmlPullParserException, IOException {
		String name = parser.getName();
		if (XmlConstants.WALL.equals(name)) {
			readWall(parser);
		} else if (XmlConstants.ICEWALL.equals(name)) {
			readIcewall(parser);
		} else if (XmlConstants.JUMPER.equals(name)) {
			readJumper(parser, mediaPlayer);
		} else if (XmlConstants.SPAWNPOINT.equals(name)) {
			readSpawnpoint(parser);
		} else {
			LOGGER.debug("Found tag %s", name);
		}
	}

	private void readSpawnpoint(XmlPullParser parser) {
		String x = parser.getAttributeValue(null, X);
		String y = parser.getAttributeValue(null, Y);
		this.state.getSpawnPoints().add(
				XmlRectToObjectConverter.createSpawn(x, y));
	}

	private void readJumper(XmlPullParser parser, MediaPlayer mediaPlayer) {
		XmlRect rect = readRect(parser);
		Jumper jumper = XmlRectToObjectConverter
				.createJumper(rect, mediaPlayer);
		this.state.getAllObjects().add(jumper);
	}

	private void readIcewall(XmlPullParser parser) {
		XmlRect rect = readRect(parser);
		Wall wall = XmlRectToObjectConverter.createIceWall(rect);
		this.state.getAllObjects().add(wall);
	}

	private void readWall(XmlPullParser parser) throws XmlPullParserException,
			IOException {
		XmlRect rect = readRect(parser);
		Wall wall = XmlRectToObjectConverter.createWall(rect);
		this.state.getAllObjects().add(wall);

	}

	private XmlRect readRect(XmlPullParser parser) {
		String minX = parser.getAttributeValue(null, MIN_X);
		String minY = parser.getAttributeValue(null, MIN_Y);
		String maxX = parser.getAttributeValue(null, MAX_X);
		String maxY = parser.getAttributeValue(null, MAX_Y);
		return new XmlRect(minX, minY, maxX, maxY);
	}

	@Override
	public Collection<FixedWorldObject> createAllWalls(Context context) {
		parse(context);
		return this.state.getAllObjects();
	}

	@Override
	public List<SpawnPoint> createSpawnPoints() {
		if (!this.parsed) {
			throw new IllegalStateException("You need to parse first");
		}
		return this.state.getSpawnPoints();
	}
}
