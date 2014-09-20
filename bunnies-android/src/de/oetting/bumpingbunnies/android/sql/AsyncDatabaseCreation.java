package de.oetting.bumpingbunnies.android.sql;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import de.oetting.bumpingbunnies.logger.Logger;
import de.oetting.bumpingbunnies.logger.LoggerFactory;

public class AsyncDatabaseCreation {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(AsyncDatabaseCreation.class);

	public void createWritableDatabase(final Context context,
			final OnDatabaseCreation callback) {
		new Thread(new Runnable() {

			@Override
			public void run() {
				SqlHelper sqlHelper = new SqlHelper(context);
				SQLiteDatabase writableDatabase = sqlHelper
						.getWritableDatabase();
				LOGGER.info("Writable DB created");
				callback.databaseCreated(writableDatabase);
			}
		}).start();
	}

	public void createReadonlyDatabase(final Context context,
			final OnDatabaseCreation callback) {
		new Thread(new Runnable() {

			@Override
			public void run() {
				SqlHelper sqlHelper = new SqlHelper(context);
				SQLiteDatabase readableDatabase = sqlHelper
						.getReadableDatabase();
				LOGGER.info("Readable DB created");
				callback.databaseCreated(readableDatabase);
			}
		}).start();
	}
}
