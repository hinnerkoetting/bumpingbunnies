package de.oetting.bumpingbunnies.sql;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import de.oetting.bumpingbunnies.logger.Logger;
import de.oetting.bumpingbunnies.logger.LoggerFactory;

public class SqlHelper extends SQLiteOpenHelper {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(SqlHelper.class);
	private static final int DB_VERSION = 1;
	private static final String DATABASE_NAME = "bumpingbunnies.db";

	private static final String CREATE_LOCAL_SETTINGS = "CREATE TABLE local_settings (zoom INTEGER NOT NULL, input_configuration VARCHAR(80));";

	public SqlHelper(Context context) {
		super(context, DATABASE_NAME, null, DB_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		LOGGER.info("creating new db");
		db.execSQL(CREATE_LOCAL_SETTINGS);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		LOGGER.info("updating db");
	}

}
