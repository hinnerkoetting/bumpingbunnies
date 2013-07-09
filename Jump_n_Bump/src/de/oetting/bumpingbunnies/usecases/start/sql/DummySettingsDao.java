package de.oetting.bumpingbunnies.usecases.start.sql;

import de.oetting.bumpingbunnies.usecases.game.configuration.SettingsEntity;

public class DummySettingsDao implements SettingsStorage {

	@Override
	public SettingsEntity readStoredSettings() {
		return null;
	}

	@Override
	public void store(SettingsEntity settings) {
	}

	@Override
	public void close() {
	}

}
