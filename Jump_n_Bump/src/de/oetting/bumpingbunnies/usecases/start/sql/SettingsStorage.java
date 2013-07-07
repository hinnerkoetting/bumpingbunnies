package de.oetting.bumpingbunnies.usecases.start.sql;

import de.oetting.bumpingbunnies.usecases.game.configuration.LocalSettings;

public interface SettingsStorage {

	LocalSettings readStoredSettings();

	void store(LocalSettings settings);

	public abstract void close();

}