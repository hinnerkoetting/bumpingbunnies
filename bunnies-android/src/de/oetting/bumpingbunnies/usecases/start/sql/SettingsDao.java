package de.oetting.bumpingbunnies.usecases.start.sql;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import de.oetting.bumpingbunnies.core.configuration.DefaultConfiguration;
import de.oetting.bumpingbunnies.logger.Logger;
import de.oetting.bumpingbunnies.logger.LoggerFactory;
import de.oetting.bumpingbunnies.usecases.game.configuration.InputConfiguration;
import de.oetting.bumpingbunnies.usecases.game.configuration.SettingsEntity;

public class SettingsDao implements SettingsStorage, SettingsConstants {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(SettingsDao.class);
	private final SQLiteDatabase database;

	public SettingsDao(SQLiteDatabase database) {
		this.database = database;
	}

	@Override
	public void store(SettingsEntity settings) {
		this.database.beginTransaction();
		insertLocalSettings(settings);
		this.database.setTransactionSuccessful();
		this.database.endTransaction();
	}

	private void insertLocalSettings(SettingsEntity settings) {
		// there can only be one row entry so we delete the old entry and create
		// a new one
		this.database.delete(SETTINGS_TABLE, null, null);
		ContentValues values = createDbValues(settings);
		strictInsert(values);
	}

	private void strictInsert(ContentValues values) {
		long errorId = this.database.insert(SETTINGS_TABLE, null, values);
		if (errorId == -1) {
			throw new IllegalStateException("error for " + values.toString());
		}
	}

	private ContentValues createDbValues(SettingsEntity settings) {
		ContentValues values = new ContentValues();
		values.put(ZOOM_COL, settings.getZoom());
		values.put(INPUT_COL, settings.getInputConfiguration().toString());
		values.put(NUMBER_PLAYER_COL, settings.getNumberPlayer());
		values.put(SPEED_COL, settings.getSpeed());
		values.put(NAME_COL, settings.getPlayerName());
		values.put(BACKGROUND_COL, settings.isBackground());
		values.put(ALT_PIXELFORMAT, settings.isAltPixelformat());
		return values;
	}

	/**
	 * Returns stored settings if they exists. Returns Null otherwise.
	 * 
	 * @return
	 */
	@Override
	public SettingsEntity readStoredSettings() {
		Cursor query = this.database.query(SETTINGS_TABLE, new String[] {
				ZOOM_COL, INPUT_COL, NUMBER_PLAYER_COL, SPEED_COL, NAME_COL, BACKGROUND_COL, ALT_PIXELFORMAT },
				null, null, null, null, null);
		try {
			query.moveToFirst();
			if (!query.isAfterLast()) {
				return readLocalSettings(query);
			}
			LOGGER.info("Retuning default entity settings");
			return DefaultConfiguration.createDefaultEntity();
		} finally {
			query.close();
		}
	}

	private SettingsEntity readLocalSettings(Cursor cursor) {
		int zoom = cursor.getInt(0);
		String inputConfiguration = cursor.getString(1);
		InputConfiguration inputEnum = InputConfiguration
				.valueOf(inputConfiguration);
		int numberPlayer = cursor.getInt(2);
		int speed = cursor.getInt(3);
		String name = cursor.getString(4);
		boolean background = cursor.getInt(5) == 1;
		boolean altPixel = cursor.getInt(6) == 1;
		return new SettingsEntity(inputEnum, zoom, numberPlayer, speed, name, background, altPixel);
	}

	@Override
	public void close() {
		this.database.close();
	}
}
