package de.oetting.bumpingbunnies.sql;

import android.database.sqlite.SQLiteDatabase;

public interface OnDatabaseCreation {

	void databaseCreated(SQLiteDatabase database);
}
