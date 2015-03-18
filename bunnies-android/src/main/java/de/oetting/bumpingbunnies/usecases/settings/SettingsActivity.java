package de.oetting.bumpingbunnies.usecases.settings;

import android.app.Activity;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import de.oetting.bumpingbunnies.R;
import de.oetting.bumpingbunnies.android.sql.AsyncDatabaseCreation;
import de.oetting.bumpingbunnies.android.sql.OnDatabaseCreation;
import de.oetting.bumpingbunnies.logger.Logger;
import de.oetting.bumpingbunnies.logger.LoggerFactory;
import de.oetting.bumpingbunnies.model.configuration.SettingsEntity;
import de.oetting.bumpingbunnies.usecases.start.sql.DummySettingsDao;
import de.oetting.bumpingbunnies.usecases.start.sql.SettingsDao;
import de.oetting.bumpingbunnies.usecases.start.sql.SettingsStorage;

/**
 * This activity is used control settings.<br>
 * All settings are saved in the DB when this activity is ended.
 * 
 */
public class SettingsActivity extends Activity implements OnDatabaseCreation {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(SettingsActivity.class);
	private SettingsStorage settingsDao;
	private SettingsViewAccess viewAccess;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_settings);
		this.viewAccess = new SettingsViewAccess(this);
		this.viewAccess.init();
		settingsDao = new DummySettingsDao();
		new AsyncDatabaseCreation().createWritableDatabase(this, this);
	}

	@Override
	protected void onResume() {
		super.onResume();
		fillStoredSettings();
	}

	@Override
	protected void onPause() {
		super.onPause();
		storeLocalSettings();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		this.settingsDao.close();
	}

	private void fillStoredSettings() {
		LOGGER.info("Reading settings from database");
		SettingsEntity storedSettings = this.settingsDao.readStoredSettings();
		this.viewAccess.fillView(storedSettings);
	}

	private void storeLocalSettings() {
		SettingsEntity selectedSettings = createLocalSettings();
		this.settingsDao.store(selectedSettings);
	}

	private SettingsEntity createLocalSettings() {
		return this.viewAccess.readFromView();
	}

	@Override
	public void databaseCreated(SQLiteDatabase database) {
		this.settingsDao = new SettingsDao(database);
		fillStoredSettings();
	}
}
