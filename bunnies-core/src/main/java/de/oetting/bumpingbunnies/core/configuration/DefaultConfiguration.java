package de.oetting.bumpingbunnies.core.configuration;

import de.oetting.bumpingbunnies.model.configuration.InputConfiguration;
import de.oetting.bumpingbunnies.model.configuration.SettingsEntity;

public class DefaultConfiguration {

	public static SettingsEntity createDefaultEntity() {
		return new SettingsEntity(InputConfiguration.DISTRIBUTED_KEYBOARD, 5, 2, 40, "Player", true, false);
	}
}
