package de.oetting.bumpingbunnies.android.input;

import java.util.LinkedList;
import java.util.List;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.Context;
import de.oetting.bumpingbunnies.android.input.distributedKeyboard.DistributedKeyboardinput;
import de.oetting.bumpingbunnies.model.configuration.SettingsEntity;
import de.oetting.bumpingbunnies.usecases.game.graphics.AndroidImagesColoror;

public class DefaultConfiguration {

	public static SettingsEntity createDefaultEntity() {
		return new SettingsEntity(new DistributedKeyboardinput(), 5, 30, getUsername(), true, false, true, true, false);
	}
	
	public static String getUsername() {
	    return Devices.getDeviceName();
	}
}
