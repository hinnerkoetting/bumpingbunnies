package de.oetting.bumpingbunnies.usecases.start.sql;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.util.DisplayMetrics;
import de.oetting.bumpingbunnies.android.input.DefaultConfiguration;
import de.oetting.bumpingbunnies.android.input.distributedKeyboard.DistributedKeyboardinput;
import de.oetting.bumpingbunnies.logger.Logger;
import de.oetting.bumpingbunnies.logger.LoggerFactory;
import de.oetting.bumpingbunnies.model.configuration.SettingsEntity;
import de.oetting.bumpingbunnies.model.configuration.input.InputConfiguration;

public class SettingsDao implements SettingsStorage, SettingsConstants {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(SettingsDao.class);
	private final SQLiteDatabase database;
	private final Context context;

	public SettingsDao(SQLiteDatabase database, Context context) {
		this.database = database;
		this.context = context;
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
		values.put(SPEED_COL, settings.getSpeed());
		values.put(INPUT_COL, settings.getInputConfiguration().getClass()
				.getName());
		values.put(NAME_COL, settings.getPlayerName());
		values.put(BACKGROUND_COL, settings.isBackground());
		values.put(ALT_PIXELFORMAT, settings.isAltPixelformat());
		values.put(PLAY_MUSIC, settings.isPlayMusic());
		values.put(PLAY_SOUND, settings.isPlaySound());
		values.put(LEFTHANDED, settings.isLefthanded());
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
				ZOOM_COL, INPUT_COL, SPEED_COL, NAME_COL, BACKGROUND_COL,
				ALT_PIXELFORMAT, PLAY_MUSIC, PLAY_SOUND, LEFTHANDED }, null,
				null, null, null, null);
		try {
			query.moveToFirst();
			if (!query.isAfterLast()) {
				return readLocalSettings(query);
			}
			LOGGER.info("Returning default entity settings");
			return DefaultConfiguration.createDefaultEntity(computeDefaultZoom());
		} finally {
			query.close();
		}
	}

	private int computeDefaultZoom() {
		 DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
		 int minPixel = Math.min(displayMetrics.widthPixels, displayMetrics.heightPixels);
		 int appropriateZoom = 5000 / minPixel;
		 return Math.max(Math.min(4, appropriateZoom), 10);
	}

	private SettingsEntity readLocalSettings(Cursor cursor) {
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
		return new SettingsEntity(inputEnum, zoom, speed, name, background,
				altPixel, playMusic, playSound, lefthanded);
	}

	private InputConfiguration create(String inputConfiguration) {
		try {
			return (InputConfiguration) Class.forName(inputConfiguration)
					.newInstance();
		} catch (Exception e) {
			LOGGER.error(
					"Could not restore inputconfiguration from database. Creating distributed keyboard",
					e);
			return new DistributedKeyboardinput();
		}
	}

	@Override
	public void close() {
		this.database.close();
	}
}
