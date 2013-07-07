package de.oetting.bumpingbunnies.sql;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

public class AsyncDatabaseCreation {

	public void createWritableDatabase(final Context context,
			final OnDatabaseCreation callback) {
		new Thread(new Runnable() {

			@Override
			public void run() {
				SqlHelper sqlHelper = new SqlHelper(context);
				SQLiteDatabase writableDatabase = sqlHelper
						.getWritableDatabase();
				callback.databaseCreated(writableDatabase);
			}
		}).start();
	}
}
