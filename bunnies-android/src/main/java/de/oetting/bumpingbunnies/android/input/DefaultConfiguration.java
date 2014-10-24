package de.oetting.bumpingbunnies.android.input;

import de.oetting.bumpingbunnies.android.input.distributedKeyboard.DistributedKeyboardinput;
import de.oetting.bumpingbunnies.model.configuration.SettingsEntity;

public class DefaultConfiguration {

	public static SettingsEntity createDefaultEntity() {
		return new SettingsEntity(new DistributedKeyboardinput(), 5, 2, 40, "Player", true, false, true, true);
	}
}
