package de.oetting.bumpingbunnies.android.sql;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap.Config;
import de.oetting.bumpingbunnies.android.input.DefaultConfiguration;
import de.oetting.bumpingbunnies.core.configuration.ConfigurationConstants;
import de.oetting.bumpingbunnies.logger.Logger;
import de.oetting.bumpingbunnies.logger.LoggerFactory;
import de.oetting.bumpingbunnies.model.configuration.SettingsEntity;
import de.oetting.bumpingbunnies.model.configuration.SettingsEntityV11;
import de.oetting.bumpingbunnies.usecases.start.sql.SettingsConstants;
import de.oetting.bumpingbunnies.usecases.start.sql.SettingsDao;
import de.oetting.bumpingbunnies.usecases.start.sql.SettingsV11Dao;

public class SqlHelper extends SQLiteOpenHelper implements SettingsConstants {

	private static final Logger LOGGER = LoggerFactory.getLogger(SqlHelper.class);
	private static final int DB_VERSION = 12;
	private static final String DATABASE_NAME = "bumpingbunnies.db";

	private static final String CREATE_SETTINGS = //
	// @formatter:off
	"CREATE TABLE " + SETTINGS_TABLE + " ("//
			+ ZOOM_COL + " INTEGER NOT NULL,"//
			+ INPUT_COL + " VARCHAR(80),"//
			+ NAME_COL + " VARCHAR(80),"//
			+ BACKGROUND_COL + " INTEGER NOT NULL,"//
			+ ALT_PIXELFORMAT + " INTEGER NOT NULL,"//
			+ SPEED_COL + " INTEGER NOT NULL,"//
			+ PLAY_MUSIC + " INTEGER NOT NULL,"//
			+ PLAY_SOUND + " INTEGER NOT NULL,"//
			+ LEFTHANDED + " INTEGER NOT NULL,"//
			+ VICTORY_LIMIT + " INTEGER NOT NULL"//
			+ " );";

	private static final String DROP_SETTINGS = "DROP TABLE " + SETTINGS_TABLE + ";";
	private final Context context;

	// @formatter:on

	public SqlHelper(Context context) {
		super(context, DATABASE_NAME, null, DB_VERSION);
		this.context = context;
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		LOGGER.info("creating new db");
		db.execSQL(CREATE_SETTINGS);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		if (oldVersion == 11)
			updateV11(db);
		else {
			dropOldTable(db);
			onCreate(db);
		}
	}

	private void updateV11(SQLiteDatabase db) {
		SettingsEntityV11 oldValues = new SettingsV11Dao(db).readStoredSettings();
		dropOldTable(db);
		onCreate(db);
		new SettingsDao(db, context).store(convert(oldValues));
	}

	private SettingsEntity convert(SettingsEntityV11 oldValue) {
		return new SettingsEntity(oldValue.getInputConfiguration(), oldValue.getZoom(), oldValue.getSpeed(),
				oldValue.getPlayerName(), oldValue.isPlayMusic(),
				oldValue.isPlaySound(), oldValue.isLefthanded(), ConfigurationConstants.DEFAULT_VICTORY_LIMIT);
	}

	private void dropOldTable(SQLiteDatabase db) {
		try {
			db.execSQL(DROP_SETTINGS);
		} catch (Exception e) {
			LOGGER.warn("Could not drop settings table");
		}
	}

}
