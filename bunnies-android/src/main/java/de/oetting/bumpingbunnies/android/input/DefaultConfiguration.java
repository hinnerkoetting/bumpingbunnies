package de.oetting.bumpingbunnies.android.input;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.pm.PackageManager;
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

	public static SettingsEntity createDefaultEntity(int defaultZoom, Context context) {
		InputConfiguration inputConfiguration = getDefaultInput(context);
		return new SettingsEntity(inputConfiguration, defaultZoom, SpeedMode.SLOW.getSpeed(), getUsername(), true,
				true, false, ConfigurationConstants.DEFAULT_VICTORY_LIMIT);
	}

	private static InputConfiguration getDefaultInput(Context context) {
		boolean supportsTouch = context.getPackageManager().hasSystemFeature(PackageManager.FEATURE_TOUCHSCREEN);
		if (!supportsTouch) {
			LOGGER.info("Device has no touchscreen. Using Hardware-Keyboard by default");
			return new HardwareKeyboardInputConfiguration();
		}
		int[] deviceIds = InputDevice.getDeviceIds();
		LOGGER.info("Found %d devices", deviceIds.length);
		for (int deviceId : deviceIds) {
			LOGGER.info("Device id is %d", deviceId);
			InputDevice dev = InputDevice.getDevice(deviceId);
			if (isGamepad(dev)) {
				LOGGER.info("Using hardware keyboard");
				return new HardwareKeyboardInputConfiguration();
			}
		}
		return new DistributedKeyboardinput();
	}

	private static boolean isGamepad(InputDevice inputDevice) {
		if (android.os.Build.VERSION.SDK_INT >= 12) {
			return checkforGamePad(inputDevice);
		}
		return false;
	}

	@TargetApi(Build.VERSION_CODES.HONEYCOMB_MR1)
	private static boolean checkforGamePad(InputDevice inputDevice) {
		boolean hasGamepad = (inputDevice.getSources() & InputDevice.SOURCE_GAMEPAD) == InputDevice.SOURCE_GAMEPAD;
		boolean hasDpad = (inputDevice.getSources() & InputDevice.SOURCE_DPAD) == InputDevice.SOURCE_DPAD
				&& inputDevice.getKeyboardType() == InputDevice.KEYBOARD_TYPE_NON_ALPHABETIC;
		return (hasGamepad || hasDpad);
	}

	public static String getUsername() {
		return Build.MODEL;
	}
}
