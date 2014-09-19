package de.oetting.bumpingbunnies.usecases.start.sql;

import de.oetting.bumpingbunnies.usecases.game.configuration.SettingsEntity;

public interface SettingsStorage {

	SettingsEntity readStoredSettings();

	void store(SettingsEntity settings);

	public abstract void close();

}