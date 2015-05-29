package de.oetting.bumpingbunnies.android.input;

import android.os.Build;
import android.view.InputDevice;
import de.oetting.bumpingbunnies.android.input.distributedKeyboard.DistributedKeyboardinput;
import de.oetting.bumpingbunnies.android.input.hardwareKeyboard.HardwareKeyboardInputConfiguration;
import de.oetting.bumpingbunnies.core.configuration.ConfigurationConstants;
import de.oetting.bumpingbunnies.logger.Logger;
import de.oetting.bumpingbunnies.logger.LoggerFactory;
import de.oetting.bumpingbunnies.model.configuration.SettingsEntity;
import de.oetting.bumpingbunnies.model.configuration.SpeedMode;
import de.oetting.bumpingbunnies.model.configuration.input.InputConfiguration;

public class DefaultConfiguration {

	private static final Logger LOGGER = LoggerFactory.getLogger(DefaultConfiguration.class);
	
	public static SettingsEntity createDefaultEntity(int defaultZoom) {
		InputConfiguration inputConfiguration = getDefaultInput();
		return new SettingsEntity(inputConfiguration, defaultZoom, SpeedMode.SLOW.getSpeed(), getUsername(), true, true, false, ConfigurationConstants.DEFAULT_VICTORY_LIMIT);
	}

	private static InputConfiguration getDefaultInput() {
		int[] deviceIds = InputDevice.getDeviceIds();
		LOGGER.info("Found %d devices", deviceIds.length);
		for (int deviceId : deviceIds) {
			LOGGER.info("Device id is %d", deviceId);
			InputDevice dev = InputDevice.getDevice(deviceId);
			int sources = dev.getSources();
			if (isGamepad(sources)) {
				LOGGER.info("Using hardware keyboard");
				return new HardwareKeyboardInputConfiguration();
			}
		}
		return new DistributedKeyboardinput();

	}

	private static boolean isGamepad(int sources) {
		if (android.os.Build.VERSION.SDK_INT >= 12) {
			return (sources & InputDevice.SOURCE_GAMEPAD) == InputDevice.SOURCE_GAMEPAD;
		}
		return false;
	}

	public static String getUsername() {
		return Build.MODEL;
	}
}
