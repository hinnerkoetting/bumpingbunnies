package de.oetting.bumpingbunnies.usecases.start.sql;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import de.oetting.bumpingbunnies.android.input.distributedKeyboard.DistributedKeyboardinput;
import de.oetting.bumpingbunnies.logger.Logger;
import de.oetting.bumpingbunnies.logger.LoggerFactory;
import de.oetting.bumpingbunnies.model.configuration.SettingsEntityV11;
import de.oetting.bumpingbunnies.model.configuration.input.InputConfiguration;

public class SettingsV11Dao implements SettingsConstants {

	private static final Logger LOGGER = LoggerFactory.getLogger(SettingsV11Dao.class);
	private final SQLiteDatabase database;

	public SettingsV11Dao(SQLiteDatabase database) {
		this.database = database;
	}

	/**
	 * Returns stored settings if they exists. Returns Null otherwise.
	 * 
	 */
	public SettingsEntityV11 readStoredSettings() {
		Cursor query = this.database.query(SETTINGS_TABLE, new String[] { ZOOM_COL, INPUT_COL, SPEED_COL, NAME_COL,
				BACKGROUND_COL, ALT_PIXELFORMAT, PLAY_MUSIC, PLAY_SOUND, LEFTHANDED }, null, null, null, null, null);
		try {
			query.moveToFirst();
			if (!query.isAfterLast()) {
				return readLocalSettings(query);
			}
			LOGGER.info("Returning no entity settings");
			return null;
		} finally {
			query.close();
		}
	}

	private SettingsEntityV11 readLocalSettings(Cursor cursor) {
		int zoom = cursor.getInt(0);
		String inputConfiguration = cursor.getString(1);
		InputConfiguration inputEnum = create(inputConfiguration);
		int speed = cursor.getInt(2);
		String name = cursor.getString(3);
		boolean background = cursor.getInt(4) == 1;
		boolean altPixel = cursor.getInt(5) == 1;
		boolean playMusic = cursor.getInt(6) == 1;
		boolean playSound = cursor.getInt(7) == 1;
		boolean lefthanded = cursor.getInt(8) == 1;
		return new SettingsEntityV11(inputEnum, zoom, speed, name, background, altPixel, playMusic, playSound,
				lefthanded);
	}

	private InputConfiguration create(String inputConfiguration) {
		try {
			return (InputConfiguration) Class.forName(inputConfiguration).newInstance();
		} catch (Exception e) {
			LOGGER.error("Could not restore inputconfiguration from database. Creating distributed keyboard", e);
			return new DistributedKeyboardinput();
		}
	}

}
