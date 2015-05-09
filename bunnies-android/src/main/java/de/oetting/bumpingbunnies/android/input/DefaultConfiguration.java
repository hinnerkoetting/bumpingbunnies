package de.oetting.bumpingbunnies.android.input;

import android.os.Build;
import de.oetting.bumpingbunnies.android.input.distributedKeyboard.DistributedKeyboardinput;
import de.oetting.bumpingbunnies.model.configuration.SettingsEntity;

public class DefaultConfiguration {

	public static SettingsEntity createDefaultEntity(int defaultZoom) {
		return new SettingsEntity(new DistributedKeyboardinput(), defaultZoom, 22, getUsername(), true, false, true, true, false);
	}
	
	public static String getUsername() {
	    return Build.MODEL;
	}
}
