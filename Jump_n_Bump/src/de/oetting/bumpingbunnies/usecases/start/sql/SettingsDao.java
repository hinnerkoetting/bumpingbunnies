package de.oetting.bumpingbunnies.usecases.start.sql;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import de.oetting.bumpingbunnies.usecases.game.configuration.InputConfiguration;
import de.oetting.bumpingbunnies.usecases.game.configuration.LocalSettings;

public class SettingsDao implements SettingsStorage {

	private final static String SETTINGS_TABLE = "local_settings";
	private final static String ZOOM_COL = "zoom";
	private final static String INPUT_COL = "input_configuration";

	private final SQLiteDatabase database;

	public SettingsDao(SQLiteDatabase database) {
		this.database = database;
	}

	@Override
	public void store(LocalSettings settings) {
		// there can only be one row entry so we delete the old entry and create
		// a new one
		this.database.beginTransaction();
		this.database.delete(SETTINGS_TABLE, null, null);
		ContentValues values = createDbValues(settings);
		long errorId = this.database.insert(SETTINGS_TABLE, null, values);
		if (errorId == -1) {
			throw new IllegalStateException("error for " + settings.toString());
		} else {
			this.database.setTransactionSuccessful();
		}
		this.database.endTransaction();
	}

	private ContentValues createDbValues(LocalSettings settings) {
		ContentValues values = new ContentValues();
		values.put(ZOOM_COL, settings.getZoom());
		values.put(INPUT_COL, settings.getInputConfiguration().toString());
		return values;
	}

	/**
	 * Returns stored settings if they exists. Returns Null otherwise.
	 * 
	 * @return
	 */
	@Override
	public LocalSettings readStoredSettings() {
		Cursor query = this.database.query("local_settings", new String[] {
				ZOOM_COL, INPUT_COL }, null, null, null, null, null);
		try {
			query.moveToFirst();
			if (!query.isAfterLast()) {
				return readLocalSettings(query);
			}
			return null;
		} finally {
			query.close();
		}
	}

	private LocalSettings readLocalSettings(Cursor cursor) {
		int zoom = cursor.getInt(0);
		String inputConfiguration = cursor.getString(1);
		InputConfiguration inputEnum = InputConfiguration
				.valueOf(inputConfiguration);

		return new LocalSettings(inputEnum, zoom);
	}

	@Override
	public void close() {
		this.database.close();
	}
}
