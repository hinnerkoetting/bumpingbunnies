package de.oetting.bumpingbunnies.usecases.start.sql;

import de.oetting.bumpingbunnies.usecases.game.configuration.LocalSettings;

public class DummySettingsDao implements SettingsStorage {

	@Override
	public LocalSettings readStoredSettings() {
		return null;
	}

	@Override
	public void store(LocalSettings settings) {
	}

	@Override
	public void close() {
	}

}
