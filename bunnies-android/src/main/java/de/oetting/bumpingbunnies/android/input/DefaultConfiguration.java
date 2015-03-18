package de.oetting.bumpingbunnies.android.input;

import android.os.Build;
import de.oetting.bumpingbunnies.android.input.distributedKeyboard.DistributedKeyboardinput;
import de.oetting.bumpingbunnies.model.configuration.SettingsEntity;

public class DefaultConfiguration {

	public static SettingsEntity createDefaultEntity() {
		return new SettingsEntity(new DistributedKeyboardinput(), 5, 30, getUsername(), true, false, true, true, false);
	}
	
	public static String getUsername() {
	    return Build.MODEL;
	}
}
