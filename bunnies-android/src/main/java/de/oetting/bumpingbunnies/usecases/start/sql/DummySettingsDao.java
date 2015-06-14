package de.oetting.bumpingbunnies.usecases.start.sql;

import android.content.Context;
import de.oetting.bumpingbunnies.android.input.DefaultConfiguration;
import de.oetting.bumpingbunnies.model.configuration.SettingsEntity;

public class DummySettingsDao implements SettingsStorage {

	private final Context context;

	public DummySettingsDao(Context context) {
		this.context = context;
	}

	@Override
	public SettingsEntity readStoredSettings() {
		return DefaultConfiguration.createDefaultEntity(5, context);
	}

	@Override
	public void store(SettingsEntity settings) {
	}

	@Override
	public void close() {
	}

}
