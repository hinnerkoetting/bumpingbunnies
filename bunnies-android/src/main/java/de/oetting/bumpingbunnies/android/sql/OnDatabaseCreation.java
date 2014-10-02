package de.oetting.bumpingbunnies.android.sql;

import android.database.sqlite.SQLiteDatabase;

public interface OnDatabaseCreation {

	void databaseCreated(SQLiteDatabase database);
}
