package de.oetting.bumpingbunnies.usecases.start.sql;

import de.oetting.bumpingbunnies.android.input.DefaultConfiguration;
import de.oetting.bumpingbunnies.model.configuration.SettingsEntity;

public class DummySettingsDao implements SettingsStorage {

	@Override
	public SettingsEntity readStoredSettings() {
		return DefaultConfiguration.createDefaultEntity();
	}

	@Override
	public void store(SettingsEntity settings) {
	}

	@Override
	public void close() {
	}

}
