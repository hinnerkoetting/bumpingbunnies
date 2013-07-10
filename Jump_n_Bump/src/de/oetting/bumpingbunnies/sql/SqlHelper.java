package de.oetting.bumpingbunnies.sql;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import de.oetting.bumpingbunnies.logger.Logger;
import de.oetting.bumpingbunnies.logger.LoggerFactory;
import de.oetting.bumpingbunnies.usecases.start.sql.SettingsConstants;

public class SqlHelper extends SQLiteOpenHelper implements SettingsConstants {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(SqlHelper.class);
	private static final int DB_VERSION = 7;
	private static final String DATABASE_NAME = "bumpingbunnies.db";

	private static final String CREATE_SETTINGS = //
	//@formatter:off
			"CREATE TABLE " + SETTINGS_TABLE + " ("
			+ NUMBER_PLAYER_COL + " INTEGER NOT NULL, "
			+ ZOOM_COL + " INTEGER NOT NULL,"
			+ INPUT_COL + " VARCHAR(80),"
			+ NAME_COL + " VARCHAR(80),"
			+ BACKGROUND_COL + " INTEGER NOT NULL,"
			+ ALT_PIXELFORMAT + " INTEGER NOT NULL,"
			+ SPEED_COL + " INTEGER NOT NULL );";
	
	private static final String DROP_SETTINGS = "DROP TABLE " + SETTINGS_TABLE + ";";
	//@formatter:on

	public SqlHelper(Context context) {
		super(context, DATABASE_NAME, null, DB_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		LOGGER.info("creating new db");
		db.execSQL(CREATE_SETTINGS);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		LOGGER.info("updating db");
		try {
			db.execSQL(DROP_SETTINGS);
		} catch (Exception e) {
			LOGGER.warn("Could not drop settings table");
		}
		onCreate(db);
	}

}
